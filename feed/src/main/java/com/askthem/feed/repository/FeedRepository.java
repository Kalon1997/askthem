package com.askthem.feed.repository;

import com.askthem.feed.model.Questions;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedRepository extends MongoRepository<Questions,ObjectId> {

    @Aggregation(pipeline = {
            "{$match: {'tags': {$in:['library','fit']}}, {'zipcode':'575003'} }",
            "{$sort: {'question':-1}}",
            "{$project: {'questionId':1, 'question':1, 'zipcode':-1 } }"
    }, collation = "{ 'locale': 'en', 'strength': 2 }")
    List<Questions> listQuestions();
}
