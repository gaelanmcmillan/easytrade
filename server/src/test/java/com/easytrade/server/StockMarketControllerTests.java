package com.easytrade.server;

import com.easytrade.server.config.EasyTradeProperties;
import com.jayway.jsonpath.JsonPath;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class StockMarketControllerTests {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    EasyTradeProperties easyTradeProperties;

    private final String API_PREFIX = "/api/v1/stock";
    @Test
    public void requestAllStocksShouldReturnAllStocks() throws Exception {
        String[] recognizedSymbols = easyTradeProperties.getSymbols();

        var allStocksRequest = MockMvcRequestBuilders.get(API_PREFIX + "/all");

        mockMvc.perform(allStocksRequest)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stocks", Matchers.hasSize(recognizedSymbols.length)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stocks[*].symbol", Matchers.containsInAnyOrder(recognizedSymbols)));
    }

    @Test
    public void buyStockWithSufficientFundsShouldReturnNoContent() throws Exception {
        String loginBodyJson = TestConfig.dummyUserLoginJson;

        var loginRequest = MockMvcRequestBuilders.post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginBodyJson);

        String loginJson = mockMvc.perform(loginRequest)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        String token = JsonPath.read(loginJson, "$.token");

        String buyRequestBodyJson = """
        {
            "symbol": "AAPL",
            "quantity": 25
        }
        """;

        var buyStockRequest = MockMvcRequestBuilders.patch(API_PREFIX + "/buy")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .content(buyRequestBodyJson);

        mockMvc.perform(buyStockRequest)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}