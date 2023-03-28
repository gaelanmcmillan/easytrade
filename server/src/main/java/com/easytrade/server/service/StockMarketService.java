package com.easytrade.server.service;

import com.easytrade.server.dto.stock.*;
import com.easytrade.server.exception.*;
import com.easytrade.server.model.*;
import com.easytrade.server.repository.StockDataRepository;
import com.easytrade.server.repository.StockRepository;
import com.easytrade.server.repository.UserRepository;
import com.easytrade.server.repository.UserStockHoldingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.Interval;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StockMarketService {
    private final UserRepository userRepository;
    private final UserStockHoldingRepository userStockHoldingRepository;
    private final StockRepository stockRepository;
    private final StockDataRepository stockDataRepository;
    private final JsonWebTokenService jwtService;

    public BuyStockResponse buyStock(String bearerToken, BuyStockRequest request)
            throws  NonexistentUserException,
                    InvalidQuantityException,
                    UnknownTickerSymbolException,
                    InsufficientFundsException
    {
        String tokenLiteral = bearerToken.substring("Bearer ".length());
        String username = jwtService.extractUsername(tokenLiteral);

        // Error case: User doesn't exist
        User user = userRepository.findByUsername(username)
                                  .orElseThrow(() -> new NonexistentUserException(
                                          String.format("User with username '%s' doesn't exist", username)));

        // Error case: Quantity is invalid
        int quantity = request.getQuantity();
        if (quantity < 0) {
            throw new InvalidQuantityException(
                    String.format(
                            "Quantity '%d' is not valid. Quantity should be a positive integer.", quantity));
        }

        // Error case: Ticker symbol doesn't exist
        String tickerSymbol = request.getSymbol();
        BigDecimal price = getLatestPrice(tickerSymbol);

        BigDecimal costOfPurchase = price.multiply(BigDecimal.valueOf(quantity));

        // Error case: Insufficient funds (throws InsufficientFundsError)
        user.makePurchase(costOfPurchase);

        // TODO: Could save a transaction here

        // Update user's holding in the stock
        UserStockHolding userStockHolding = userStockHoldingRepository.getUserStockHoldingBySymbol(username, tickerSymbol)
                .orElse(UserStockHolding.builder()
                        .user(user)
                        .stock(stockRepository.getStockBySymbol(tickerSymbol).get()) // It's verified that the stock exists
                        .quantity(0)
                        .build());

        userStockHolding.setQuantity(userStockHolding.getQuantity() + quantity);
        userStockHoldingRepository.save(userStockHolding);

        return BuyStockResponse.builder().message("Success").build();
    }

    public void sellStock(String bearerToken, SellStockRequest request) throws NonexistentUserException, InvalidQuantityException, UnknownTickerSymbolException, InsufficientFundsException, InsufficientHoldingsExpception {
        String tokenLiteral = bearerToken.substring("Bearer ".length());
        String username = jwtService.extractUsername(tokenLiteral);

        // Error case: User doesn't exist
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NonexistentUserException(
                        String.format("User with username '%s' doesn't exist", username)));

        // Error case: Quantity is invalid
        int quantity = request.getQuantity();
        if (quantity < 0) {
            throw new InvalidQuantityException(
                    String.format(
                            "Quantity '%d' is not valid. Quantity should be a positive integer.", quantity));
        }

        // Error case: Ticker symbol doesn't exist
        String tickerSymbol = request.getSymbol();
        BigDecimal price = getLatestPrice(tickerSymbol);

        // Error case: User doesn't hold enough of the given stock
        Optional<UserStockHolding> maybeHolding = userStockHoldingRepository.getUserStockHoldingBySymbol(username, tickerSymbol);

        if (maybeHolding.isEmpty() || maybeHolding.get().getQuantity() < quantity) {
            throw new InsufficientHoldingsExpception(tickerSymbol);
        }

        UserStockHolding userStockHolding = maybeHolding.get();

        BigDecimal earningsFromSale = price.multiply(BigDecimal.valueOf(quantity));

        user.setAccountBalance(user.getAccountBalance().add(earningsFromSale));

        // TODO: Could save a transaction here

        // Update user's holding in the stock
        userStockHolding.setQuantity(userStockHolding.getQuantity() - quantity);
        userStockHoldingRepository.save(userStockHolding);
    }

    public AllStocksResponse getAllStocks() {
        List<Stock> stocks = (List<Stock>) stockRepository.findAll();
        return AllStocksResponse.builder().stocks(stocks).build();
    }


    public SingleStockResponse getStock(SingleStockRequest request) throws UnknownTickerSymbolException {
        String symbol = request.getSymbol();
        StockData stockData = stockDataRepository.getPriceBySymbolAndDate(symbol, Date.valueOf(LocalDate.from(LocalDate.now().atStartOfDay())))
                .orElseThrow(() -> new UnknownTickerSymbolException(symbol));

        return SingleStockResponse.builder().symbol(symbol).build();
    }


    /**
     * Success cases:
     * --------------
     * (1) Full: Database contains stock data for all days in [from, to].
     * (2) Left-partial: Database contains stock data for [d0, to] for from < d0 <= to
     * (3) Right-partial: Database contains stock data for [from, d1] for from >= d1 > to
     * (4) Patchy: Database contains stock data for 1 or more sub-intervals inside (from, to).
     * (5) None: Database contains no data from [from, to].
     *
     * Error cases:
     * ------------
     * (1) Invalid dates
     * */
    public StockDataListResponse getDailyStockDataBetweenDates(String symbol, LocalDate fromLocal, LocalDate toLocal)
            throws InvalidDateException, IOException, UnknownTickerSymbolException {
        // We add one to `to` to ensure we enclose an inclusive date range.
        toLocal = toLocal.plusDays(1);


        // Handle error case: Stock doesn't exist
        Stock stock = stockRepository.getStockBySymbol(symbol).orElseThrow(() -> new UnknownTickerSymbolException(symbol));
        // Handle error case: Invalid dates
        Date today = Date.valueOf(LocalDate.now());
        Date from = Date.valueOf(fromLocal);
        Date to = Date.valueOf(toLocal);

        if (to.before(from) || today.before(from)) {
            throw new InvalidDateException(from);
        }
        if (today.before(to)) {
            throw new InvalidDateException(to);
        }

        // We add one because this is an inclusive interval
        long intervalWidthInDays = ChronoUnit.DAYS.between(fromLocal, toLocal);

        System.out.printf("INTERVAL WIDTH IN DAYS: '%d'\n", intervalWidthInDays);

        Calendar calendarFrom = Calendar.getInstance();
        Calendar calendarTo = Calendar.getInstance();

        calendarFrom.set(fromLocal.getYear(), fromLocal.getMonthValue()-1, fromLocal.getDayOfMonth());
        calendarTo.set(toLocal.getYear(), toLocal.getMonthValue()-1, toLocal.getDayOfMonth());

        // Handle (1) Full
        {
            List<StockData> stockDataList = stockDataRepository.getPricesBySymbolBetweenDates(symbol, from, to);
            System.out.printf("FOUND %d ENTRIES", stockDataList.size());
            System.out.println(stockDataList);
            if (stockDataList.size() == intervalWidthInDays) {
                System.out.println("Nice, we have these dates.");
                return StockDataListResponse.builder().stockData(stockDataList).build();
            }
        }
        // Handle (2) Left-partial
        // Handle (3) Right-partial

        // Handle (4) Patchy and (5) None
        // Solution: Make a request to YahooFinance for [from, to]
        {
            yahoofinance.Stock yahooStockData = YahooFinance.get(symbol, calendarFrom, calendarTo, Interval.DAILY);
            List<StockData> stockDataList = yahooStockData.getHistory().stream().map(historicalQuote -> {
                LocalDate asLocalDate = LocalDate.ofInstant(historicalQuote.getDate().toInstant(), ZoneId.systemDefault());
                Date date = Date.valueOf(LocalDate.from(asLocalDate.atStartOfDay()));
                // TODO: Abstract this to StockData.fromHistoricalQuote
                return StockData.builder()
                        .stock(stock)
                        .date(date)
                        .open(historicalQuote.getOpen())
                        .close(historicalQuote.getClose())
                        .price(historicalQuote.getAdjClose())
                        .adjClose(historicalQuote.getAdjClose())
                        .high(historicalQuote.getHigh())
                        .low(historicalQuote.getLow())
                        .volume(historicalQuote.getVolume())
                        .build();
            }).toList();

            stockDataRepository.saveAll(stockDataList);

            return StockDataListResponse.builder()
                    .stockData(stockDataList)
                    .build();
        }
    }

    private BigDecimal getLatestPrice(String tickerSymbol) throws UnknownTickerSymbolException {
        Stock stock = stockRepository.getStockBySymbol(tickerSymbol).orElseThrow(
                () -> new UnknownTickerSymbolException(tickerSymbol));

        return stock.getCurrentOpen();
    }
}
