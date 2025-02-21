package com.askthem.feed.model;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDAL {
    List<Questions> getQuestionsForFeed(List<String> tags, String zipcode);
}
