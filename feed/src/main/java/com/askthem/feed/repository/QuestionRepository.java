package com.askthem.feed.repository;

import com.askthem.feed.model.Questions;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuestionRepository extends MongoRepository<Questions, ObjectId> {
}
