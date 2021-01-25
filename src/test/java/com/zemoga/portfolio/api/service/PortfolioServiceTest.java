package com.zemoga.portfolio.api.service;

import com.zemoga.portfolio.api.exception.NotFoundException;
import com.zemoga.portfolio.api.model.Portfolio;
import com.zemoga.portfolio.api.model.Tweet;
import com.zemoga.portfolio.api.repository.PortfolioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twitter4j.TwitterException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PortfolioServiceTest {

    private static final int USER = 1;
    private static final int NUM_TWEETS = 5;

    private PortfolioService service;
    private PortfolioRepository repository = mock(PortfolioRepository.class);
    private TwitterService twitterService = mock(TwitterService.class);

    @BeforeEach
    public void init() {
        service = new PortfolioService(repository, twitterService);
    }

    @Test
    public void testGetPortfolio() throws NotFoundException, TwitterException {
        when(repository.findById(USER)).thenReturn(Optional.of(getPortfolio()));
        when(twitterService.getTweets(anyString(), anyInt())).thenReturn(getTweetList());

        Portfolio portfolio = service.getPortfolio(USER, NUM_TWEETS);

        assertNotNull(portfolio);
        assertEquals(portfolio.getId(), USER);
        assertEquals(portfolio.getTweetList().size(), 2);

        verify(repository).findById(USER);
        verify(twitterService).getTweets(portfolio.getTwitterUserName(), NUM_TWEETS);
    }

    @Test
    public void testGetPortfolioNotFound() throws TwitterException {
        when(repository.findById(USER)).thenReturn(Optional.empty());
        when(twitterService.getTweets(anyString(), anyInt())).thenReturn(getTweetList());

        assertThrows(NotFoundException.class, () -> service.getPortfolio(USER, NUM_TWEETS));

        verify(repository).findById(USER);
        verify(twitterService, never()).getTweets(anyString(), anyInt());
    }

    @Test
    public void testGetPortfolioTwitterConnectionFailedShouldReturnObjectWithEmptyTweets() throws NotFoundException, TwitterException {
        when(repository.findById(USER)).thenReturn(Optional.of(getPortfolio()));
        when(twitterService.getTweets(anyString(), anyInt())).thenThrow(new TwitterException("Something happened"));

        Portfolio portfolio = service.getPortfolio(USER, NUM_TWEETS);

        assertNotNull(portfolio);
        assertEquals(portfolio.getId(), USER);
        assertNull(portfolio.getTweetList());

        verify(repository).findById(USER);
        verify(twitterService).getTweets(anyString(), anyInt());
    }

    @Test
    public void testUpdatePortfolio() throws NotFoundException {
        when(repository.findById(USER)).thenReturn(Optional.of(getPortfolio()));
        when(repository.save(any(Portfolio.class))).thenReturn(getPortfolio());

        Portfolio portfolio = service.updatePortfolio(USER, getPortfolio());
        assertNotNull(portfolio);
        assertEquals(portfolio.getId(), USER);

        verify(repository).findById(USER);
        verify(repository).save(any(Portfolio.class));
    }

    @Test
    public void testUpdatePortfolioNotFound(){
        when(repository.findById(USER)).thenReturn(Optional.empty());
        when(repository.save(any(Portfolio.class))).thenReturn(getPortfolio());

        assertThrows(NotFoundException.class, () -> service.getPortfolio(USER, NUM_TWEETS));

        verify(repository).findById(USER);
        verify(repository, never()).save(getPortfolio());
    }

    private List<Tweet> getTweetList(){
        List<Tweet> tweets = new ArrayList();
        tweets.add(new Tweet("@test what a beautiful day", new Date()));
        tweets.add(new Tweet("@test what a beautiful day", new Date()));
        return tweets;
    }

    private Portfolio getPortfolio() {
        Portfolio portfolio = new Portfolio();
        portfolio.setId(USER);
        portfolio.setDescription("desc");
        portfolio.setImageUrl("www.google.com");
        portfolio.setTitle("twitter user");
        portfolio.setTwitterUserName("test");
        return portfolio;
    }
}
