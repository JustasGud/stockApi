package com.example.stockportfolioapi.bdd;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Step definitions for stock BDD scenarios.
 */
public class StockStepDefinitions {

    @Value("${local.server.port}")
    private int port;

    private final HttpClient httpClient = HttpClient.newHttpClient();

    private int responseStatus;
    private String responseBody;

    /**
     * Confirms that the API test server is available.
     */
    @Given("the stock API is available")
    public void theStockApiIsAvailable() {
        assertTrue(port > 0);
    }

    /**
     * Sends a POST request to create a new stock.
     */
    @When("I create a stock with symbol {string}, company name {string}, sector {string}, price {double} and currency {string}")
    public void iCreateAStock(
            String symbol,
            String companyName,
            String sector,
            double price,
            String currency
    ) throws IOException, InterruptedException {
        String uniqueSymbol = symbol + (System.currentTimeMillis() % 1000000);

        String jsonBody = """
            {
              "symbol": "%s",
              "companyName": "%s",
              "sector": "%s",
              "currentPrice": %s,
              "currency": "%s"
            }
            """.formatted(
                uniqueSymbol,
                companyName,
                sector,
                BigDecimal.valueOf(price),
                currency
        );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/api/stocks"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        responseStatus = response.statusCode();
        responseBody = response.body();
    }

    /**
     * Sends a GET request to receive all stocks.
     */
    @When("I request all stocks")
    public void iRequestAllStocks() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/api/stocks"))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        responseStatus = response.statusCode();
        responseBody = response.body();
    }

    /**
     * Sends a GET request for one stock by ID.
     */
    @When("I request stock with id {long}")
    public void iRequestStockWithId(Long id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/api/stocks/" + id))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        responseStatus = response.statusCode();
        responseBody = response.body();
    }

    /**
     * Checks that stock creation was successful.
     */
    @Then("the stock should be created successfully")
    public void theStockShouldBeCreatedSuccessfully() {
        assertEquals(201, responseStatus);
    }

    /**
     * Checks that stock list request was successful.
     */
    @Then("the stock list response should be successful")
    public void theStockListResponseShouldBeSuccessful() {
        assertEquals(200, responseStatus);
    }

    /**
     * Checks the response status code.
     */
    @Then("the response status should be {int}")
    public void theResponseStatusShouldBe(int statusCode) {
        assertEquals(statusCode, responseStatus);
    }

    /**
     * Checks that the response body contains expected stock symbol prefix.
     */
    @Then("the response should contain symbol {string}")
    public void theResponseShouldContainSymbol(String symbol) {
        assertNotNull(responseBody);
        assertTrue(responseBody.contains(symbol));
    }
}