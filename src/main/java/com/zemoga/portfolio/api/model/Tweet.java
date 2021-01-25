package com.zemoga.portfolio.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class Tweet implements Serializable {

    private String text;
    private Date creationDate;

}
