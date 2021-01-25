package com.zemoga.portfolio.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zemoga.portfolio.api.model.Portfolio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;


@SpringBootTest(classes = SpringApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PortfolioControllerIntegrationTest {

    private static final String PORTFOLIO_SERVICE_URL = "http://localhost:%s/v1/portfolio/";

    private static int USER_NOT_FOUND = 100;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void testGetPortfolioNotFound()
    {
        ResponseEntity response = restTemplate.getForEntity(buildUrl() + USER_NOT_FOUND, Portfolio.class);
        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void testPutPortfolio()
    {
        try {
            restTemplate.put(buildUrl() + 1, jsonPortfolio());
        } catch ( Exception ex) {
            fail();
        }

    }

    private String jsonPortfolio()
          throws JsonProcessingException
    {
        Portfolio portfolio = new Portfolio();
        portfolio.setId(1);
        portfolio.setDescription("desc");
        portfolio.setImageUrl("www.google.com");
        portfolio.setTitle("twitter user");
        portfolio.setTwitterUserName("test");
        return mapper.writeValueAsString( portfolio );
    }

    private String buildUrl(){
        return String.format(PORTFOLIO_SERVICE_URL, port);
    }


}
