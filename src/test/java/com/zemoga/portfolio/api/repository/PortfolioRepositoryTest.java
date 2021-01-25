package com.zemoga.portfolio.api.repository;

import com.zemoga.portfolio.api.model.Portfolio;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import java.util.Optional;
import java.util.stream.StreamSupport;

@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DatabaseSetup(value = "classpath:portfolioInitializer.xml")
@DataJpaTest
public class PortfolioRepositoryTest {

    private static final int PORTFOLIO_ID = 1;

    @Autowired
    private PortfolioRepository repository;

    @Test
    public void testGetPortfolio() {
        Iterable<Portfolio> portfolios = repository.findAll();
        assertNotNull(portfolios);
        assertTrue(StreamSupport.stream(portfolios.spliterator(), false).toArray().length == 3);
        portfolios.forEach(portfolio -> {
            assertNotNull(portfolio.getId());
            assertNull(portfolio.getTweetList());
        });
    }

    @Test
    public void testSavePortfolio() {
        Portfolio portfolio = repository.save(getPortfolio());
        assertNotNull(portfolio);
        assertNotNull(portfolio.getId());

        Optional<Portfolio> portfolioSaved = repository.findById(portfolio.getId());
        assertTrue(portfolioSaved.isPresent());
        assertEquals(portfolioSaved.get().getTitle(), portfolio.getTitle());
    }

    @Test
    public void testUpdatePortfolio() {
        Portfolio portfolioSaved = repository.findById(PORTFOLIO_ID).get();
        String titlePortfolio = portfolioSaved.getTitle();
        portfolioSaved.setTitle("Editing");

        Portfolio portfolio = repository.save(portfolioSaved);
        assertNotNull(portfolio);
        assertEquals(portfolio.getId(), portfolioSaved.getId());
        assertNotEquals(portfolio.getTitle(), titlePortfolio);
    }

    @Test
    public void testDeletePortfolio() {
        repository.deleteById(PORTFOLIO_ID);

        Optional<Portfolio> portfolioSaved = repository.findById(PORTFOLIO_ID);
        assertFalse(portfolioSaved.isPresent());
    }

    private Portfolio getPortfolio() {
        Portfolio portfolio = new Portfolio();
        portfolio.setDescription("desc");
        portfolio.setImageUrl("www.google.com");
        portfolio.setTitle("twitter user");
        portfolio.setTwitterUserName("test");
        return portfolio;
    }
}
