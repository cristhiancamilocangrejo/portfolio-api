package com.zemoga.portfolio.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Portfolio implements Serializable {

  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  @Column(name = "idportfolio")
  private int id;
  private String description;
  private String imageUrl;
  private String twitterUserName;
  private String title;

  @Transient
  private List<Tweet> tweetList;
}
