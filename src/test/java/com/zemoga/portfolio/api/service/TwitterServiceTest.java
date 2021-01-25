package com.zemoga.portfolio.api.service;

import com.zemoga.portfolio.api.config.TwitterConfig;
import com.zemoga.portfolio.api.model.Tweet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TwitterConfig.class)
@TestPropertySource(locations="classpath:application.properties")
public class TwitterServiceTest {

    private static final String USER = "PibeValderramap";
    private static final String USER_NOT_FOUND = "testzemoga";
    private static final int NUM_TWEETS = 5;

    @Autowired
    private Twitter twitter;
    private TwitterService twitterService;

    @BeforeEach
    public void init() {
        twitterService = new TwitterService(twitter);
    }

    @Test
    public void testGetTweetList() throws TwitterException {
        List<Tweet> tweetList = twitterService.getTweets(USER, NUM_TWEETS);
        assertNotNull(tweetList);
        assertTrue(tweetList.size() == 5);
    }

    @Test
    public void testGetTweetListUserNotFound() {
        assertThrows(TwitterException.class, () -> twitterService.getTweets(USER_NOT_FOUND, NUM_TWEETS));
    }

}
