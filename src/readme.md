# Stock Portfolio REST API

## Project Description

This project is a RESTful Web Service created with Java and Spring Boot.
The application is designed for managing a simple stock portfolio system.

The system allows users to:

* create, view, update and delete stocks;
* add stocks to a watchlist;
* create BUY and SELL stock transactions;
* view a calculated portfolio summary;
* receive daily stock suggestions from a third-party stock market API;
* test the API using Swagger UI, Swagger Editor and Postman;
* use a simple browser-based user interface.

This application is created for educational purposes. It does not provide real financial advice or real trading functionality.

---

## Technologies Used

* Java 17
* Spring Boot 4
* Spring Web
* Spring Data JPA
* H2 Database
* Spring HATEOAS
* Spring Cache
* Spring Validation
* Springdoc OpenAPI / Swagger
* JUnit
* Mockito
* Cucumber BDD
* Maven
* HTML, CSS and JavaScript

---

## Main Features

### Stock Management

The application supports full CRUD operations for stocks.

Endpoints:

```http
GET    /api/stocks
GET    /api/stocks/{id}
POST   /api/stocks
PUT    /api/stocks/{id}
DELETE /api/stocks/{id}
```

Example stock JSON:

```json
{
  "symbol": "AAPL",
  "companyName": "Apple Inc.",
  "sector": "Technology",
  "currentPrice": 195.40,
  "currency": "USD"
}
```

---

### Watchlist Management

Users can add existing stocks to a watchlist with a note and target price.

Endpoints:

```http
GET    /api/watchlist
GET    /api/watchlist/{id}
POST   /api/watchlist
DELETE /api/watchlist/{id}
```

Example watchlist JSON:

```json
{
  "stockId": 1,
  "note": "Watching because it is a strong technology company",
  "targetPrice": 180.00
}
```

---

### Transaction Management

Users can create BUY and SELL stock transactions.

Endpoints:

```http
GET    /api/stocks/{stockId}/transactions
GET    /api/transactions/{id}
POST   /api/stocks/{stockId}/transactions
DELETE /api/transactions/{id}
```

Example transaction JSON:

```json
{
  "transactionType": "BUY",
  "quantity": 5,
  "price": 190.00,
  "transactionDate": "2026-06-18"
}
```

---

### Portfolio Summary

The application calculates a portfolio summary from all BUY and SELL transactions.

Endpoint:

```http
GET /api/portfolio/summary
```

Example response:

```json
{
  "totalBuyAmount": 950.00,
  "totalSellAmount": 410.00,
  "netInvestedAmount": 540.00,
  "totalSharesOwned": 3,
  "numberOfDifferentStocks": 1,
  "transactionCount": 2
}
```

---

### Daily Stock Recommendations

The application integrates with a third-party API, Alpha Vantage, to get daily stock suggestions based on top market gainers.

Endpoint:

```http
GET /api/recommendations/daily
```

The recommendation endpoint is for educational purposes only and does not provide financial advice.

---

## REST Requirements

### Stateless

The application is stateless because every request contains all required information.
The server does not store client session state.

### Cacheable

GET requests include cache headers such as:

```http
Cache-Control: max-age=60
```

The application also uses Spring Cache with annotations such as:

```java
@Cacheable
@CacheEvict
@CachePut
```

### HATEOAS

The API uses Spring HATEOAS.
Responses include `_links`, for example:

```json
"_links": {
  "self": {
    "href": "http://localhost:8080/api/stocks/1"
  },
  "allStocks": {
    "href": "http://localhost:8080/api/stocks"
  }
}
```

This makes the API more discoverable and supports the Richardson Maturity Model requirement.

---

## Database

The project uses H2 Database.

Database console:

```text
http://localhost:8080/h2-console
```

Example JDBC URL:

```text
jdbc:h2:file:./data/stockdb
```

The main database tables are:

* `stocks`
* `watchlist_items`
* `stock_transactions`

---

## Swagger UI

When the application is running, Swagger UI is available here:

```text
http://localhost:8080/swagger-ui/index.html
```

OpenAPI JSON is available here:

```text
http://localhost:8080/v3/api-docs
```

The OpenAPI JSON can be copied into Swagger Editor.

---

## Simple User Interface

The project includes a simple browser-based user interface created with HTML, CSS and JavaScript.

Open it here:

```text
http://localhost:8080
```

The UI allows users to:

* view stocks;
* add stocks;
* delete stocks;
* view portfolio summary;
* view daily stock recommendations.

---

## How to Run the Project

1. Open the project in IntelliJ IDEA.
2. Make sure JDK 17 or newer is selected.
3. Run the main Spring Boot application class.
4. Open:

```text
http://localhost:8080
```

or Swagger UI:

```text
http://localhost:8080/swagger-ui/index.html
```

---

## How to Run Tests

Run all unit and BDD tests:

```bash
mvn test
```

The project includes:

* JUnit unit tests;
* Mockito service tests;
* Cucumber BDD tests.

Cucumber report is generated here:

```text
target/cucumber-report.html
```

---

## How to Generate JavaDoc

Run:

```bash
mvn javadoc:javadoc
```

Generated JavaDoc is available here:

```text
target/site/apidocs/index.html
```

---

## SOLID Principles

The project follows SOLID principles by separating responsibilities into different layers:

* controllers handle HTTP requests;
* services contain business logic;
* repositories handle database access;
* DTOs transfer request and response data;
* models represent database entities;
* exception handlers manage API errors.

This structure makes the project easier to test, maintain and extend.

---

## Author

Justas Gudonis
