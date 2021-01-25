package com.zemoga.portfolio.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

@Configuration
public class TwitterConfig {

  @Value("${api.key}")
  private String apiKey;

  @Value("${api.secret.key}")
  private String apiSecretKey;

  @Value("${access.token}")
  private String accessToken;

  @Value("${access.token.secret}")
  private String accessTokenSecret;

  @Bean
  public Twitter twitterConfiguration() {
    ConfigurationBuilder cb = new ConfigurationBuilder();
    cb.setDebugEnabled(true)
        .setOAuthConsumerKey(apiKey)
        .setOAuthConsumerSecret(apiSecretKey)
        .setOAuthAccessToken(accessToken)
        .setOAuthAccessTokenSecret(accessTokenSecret);
    return new TwitterFactory(cb.build()).getInstance();
  }

}
