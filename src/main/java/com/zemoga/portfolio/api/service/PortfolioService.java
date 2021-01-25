package com.zemoga.portfolio.api.service;

import com.zemoga.portfolio.api.exception.NotFoundException;
import com.zemoga.portfolio.api.model.Portfolio;
import com.zemoga.portfolio.api.repository.PortfolioRepository;
import org.springframework.stereotype.Service;
import twitter4j.Logger;
import twitter4j.TwitterException;

import java.util.Optional;

@Service
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final TwitterService twitterService;
    private static final Logger logger = Logger.getLogger(PortfolioService.class);

    public PortfolioService(PortfolioRepository portfolioRepository, TwitterService twitterService) {
        this.portfolioRepository = portfolioRepository;
        this.twitterService = twitterService;
    }

    public Portfolio getPortfolio(int id, int numTweets) throws NotFoundException {
        Portfolio portfolio = getPortfolioById(id);
        try {
            portfolio.setTweetList(twitterService.getTweets(portfolio.getTwitterUserName(), numTweets));
        } catch (TwitterException e) {
            logger.debug("Tweet list not pulled");
        }
        return portfolio;
    }

    public Portfolio updatePortfolio(int id, Portfolio portfolio) throws NotFoundException {
        Portfolio portfolioExisting = getPortfolioById(id);
        portfolio.setId(portfolioExisting.getId());
        return portfolioRepository.save(portfolio);
    }

    private Portfolio getPortfolioById(int id) throws NotFoundException {
        Optional<Portfolio> portfolio = portfolioRepository.findById(id);
        if (!portfolio.isPresent()) {
            throw new NotFoundException();
        }
        return portfolio.get();
    }
}
