package com.zemoga.portfolio.api.service;

import com.zemoga.portfolio.api.model.Tweet;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import twitter4j.Logger;
import twitter4j.Paging;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TwitterService {

  private final Twitter twitter;
  private static Logger logger = Logger.getLogger(TwitterService.class);

  public TwitterService(Twitter twitter) {
    this.twitter = twitter;
  }

  @Retryable(value = TwitterException.class, backoff = @Backoff(delay = 2000))
  public List<Tweet> getTweets(String userTwitter, int numTweets) throws TwitterException {
    Paging paging = new Paging();
    paging.setCount(numTweets);
    return twitter.getUserTimeline(userTwitter, paging).stream()
        .map(tweet -> new Tweet(tweet.getText(), tweet.getCreatedAt()))
        .collect(Collectors.toList());
  }

  @Recover
  void recover(TwitterException ex, String userTwitter, int numTweets) {
      logger.debug(String.format("Twitter service called with user %s and num tweets %s", userTwitter, numTweets));
      logger.error("Twitter service error", ex);
  }
}
