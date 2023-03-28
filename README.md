# *EasyTrade*

Paper Money Stock Market System

Buy, sell, and view stocks in a low-pressure, paper money environment.

# Server
The backend server is is a Spring Boot application which follows the M(V)C architecture. *V* is parenthesized because the View is really going to be powered by our external frontend client.
Authentication is implemented with Spring Security and the [JSON Web Token Protocol](https://jwt.io/).

## API
### Auth Endpoints
#### Signup
##### Example Request (cURL)
```bash
curl --location 'http://localhost:8080/api/v1/auth/signup' \
--header 'Content-Type: application/json' \
--data '{
    "firstName": "John",
    "lastName": "Smith",
    "username":"johnsmith",
    "password":"11111"
}'
```
##### Response
```json
{
    "username": "johnsmith",
    "token": "<JSON Web Token literal>"
}
```
#### Login
##### Example Request (cURL)
```bash
curl --location 'http://localhost:8080/api/v1/auth/login' \
--header 'Content-Type: application/json' \
--data '{
    "username":"johnsmith",
    "password":"11111"
}'
```
##### Response
```json
{
    "username": "johnsmith",
    "token": "<JSON Web Token literal>"
}
```
### Stock Endpoints
#### Buy Stock
##### Example Request (cURL)
```bash
curl --location --request PATCH 'http://localhost:8080/api/v1/stock/buy' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huc21pdGgiLCJpYXQiOjE2Nzk5MzAyNjcsImV4cCI6MTY3OTkzMTE2N30.9u0y0GS5HZaRVuSa3ZAYWe3JlJ1XiDgyoNcrsSq2WOU' \
--header 'Content-Type: application/json' \
--data '{
    "symbol": "META",
    "quantity": 100
}'
```
##### Response
HTTP 204 (No Content)

#### Sell Stock
##### Example Request (cURL)
```bash
curl --location --request PATCH 'http://localhost:8080/api/v1/stock/sell' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2hudHJvbjEiLCJpYXQiOjE2Nzk4NjAyNDcsImV4cCI6MTY3OTg2MTE0N30.puMwEON6gGUah8UWEdzjNAMj1sCmSx39PlNXt615beg' \
--header 'Content-Type: application/json' \
--data '{
    "symbol":"AAPL",
    "quantity":3
}'
```
##### Response
HTTP 204 (No Content)

#### Get All Stocks
##### Example Request (cURL)
```bash
curl --location 'http://localhost:8080/api/v1/stock/all'
```

##### Response
```json
{
    "stocks": [
        {
            "symbol": "AAPL",
            "company": "todo",
            "currentOpen": 158.86,
            "changeInPercent": 0.83
        },
        {
            "symbol": "GOOG",
            "company": "todo",
            "currentOpen": 105.74,
            "changeInPercent": -0.19
        },
        {
            "symbol": "GOOGL",
            "company": "todo",
            "currentOpen": 104.99,
            "changeInPercent": -0.15
        },
        {
            "symbol": "META",
            "company": "todo",
            "currentOpen": 205.18,
            "changeInPercent": 0.85
        },
        {
            "symbol": "MSFT",
            "company": "todo",
            "currentOpen": 277.24,
            "changeInPercent": 1.05
        },
        {
            "symbol": "NVDA",
            "company": "todo",
            "currentOpen": 270.31,
            "changeInPercent": -1.52
        },
        {
            "symbol": "PCAR",
            "company": "todo",
            "currentOpen": 69.26,
            "changeInPercent": 0.13
        },
        {
            "symbol": "TSLA",
            "company": "todo",
            "currentOpen": 191.65,
            "changeInPercent": -0.94
        }
    ]
}
```

#### Get History of Stock Data
##### Example Request (cURL)
```bash
curl --location 'http://localhost:8080/api/v1/stock/history?symbol=AAPL&from=2023-03-06&to=2023-03-09'
```
##### Response
```json
{
    "stockData": [
        {
            "stock": {
                "symbol": "AAPL",
                "company": "todo",
                "currentOpen": 159.94,
                "changeInPercent": -0.42
            },
            "date": "2023-03-06",
            "low": 153.46,
            "high": 156.30,
            "open": 153.79,
            "close": 153.83,
            "adjClose": 153.83,
            "price": 153.83,
            "volume": 87558000
        },
        {
            "stock": {
                "symbol": "AAPL",
                "company": "todo",
                "currentOpen": 159.94,
                "changeInPercent": -0.42
            },
            "date": "2023-03-07",
            "low": 151.13,
            "high": 154.03,
            "open": 153.70,
            "close": 151.60,
            "adjClose": 151.60,
            "price": 151.60,
            "volume": 56182000
        },
        {
            "stock": {
                "symbol": "AAPL",
                "company": "todo",
                "currentOpen": 159.94,
                "changeInPercent": -0.42
            },
            "date": "2023-03-08",
            "low": 151.83,
            "high": 153.47,
            "open": 152.81,
            "close": 152.87,
            "adjClose": 152.87,
            "price": 152.87,
            "volume": 47204800
        },
        {
            "stock": {
                "symbol": "AAPL",
                "company": "todo",
                "currentOpen": 159.94,
                "changeInPercent": -0.42
            },
            "date": "2023-03-09",
            "low": 150.23,
            "high": 154.54,
            "open": 153.56,
            "close": 150.59,
            "adjClose": 150.59,
            "price": 150.59,
            "volume": 53833600
        }
    ]
}
```


### User Endpoints
#### Get Portfolio
##### Example Request (cURL)
```bash
curl --location 'http://localhost:8080/api/v1/user/portfolio/johntron13' 
```
##### Response
```json
{
    "username": "johnsmith",
    "investments": [
        {
            "stock": {
                "symbol": "AAPL",
                "company": "Apple Inc",
                "currentOpen": 159.94,
                "changeInPercent": -0.42
            },
            "quantity": 15
        },
        {
            "stock": {
                "symbol": "GOOG",
                "company": "Google",
                "currentOpen": 105.32,
                "changeInPercent": -2.04
            },
            "quantity": 4
        },
        {
            "stock": {
                "symbol": "META",
                "company": "Meta",
                "currentOpen": 204.81,
                "changeInPercent": -1.32
            },
            "quantity": 100
        }
    ]
}
```
