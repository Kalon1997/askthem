package com.askthem.feed.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuestionDalImpl implements QuestionDAL{

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Questions> getQuestionsForFeed(List<String> tags, String zipcode) throws RuntimeException{
        try{
            Query query = new Query();
            if(tags != null && !tags.isEmpty() && tags.size() != 0){
                query.addCriteria(Criteria.where("tags").in(tags));
            }
            if(zipcode != null && !zipcode.isEmpty()){
                query.addCriteria(Criteria.where("zipcode").is(zipcode));
            }
            return mongoTemplate.find(query, Questions.class);
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
}
