package com.example.stockportfolioapi.bdd;

import com.example.stockportfolioapi.StockPortfolioApiApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Configuration class that connects Cucumber with Spring Boot test context.
 */
@CucumberContextConfiguration
@SpringBootTest(
        classes = StockPortfolioApiApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureTestRestTemplate
public class CucumberSpringConfiguration {
}