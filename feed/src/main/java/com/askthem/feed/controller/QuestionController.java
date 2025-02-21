package com.askthem.feed.controller;

import com.askthem.feed.model.QuestionDAL;
import com.askthem.feed.repository.QuestionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/feed")
public class QuestionController {
    private final QuestionRepository questionRepository;
    private final QuestionDAL questionDAL;
    public QuestionController(QuestionRepository questionRepository, QuestionDAL questionDAL){
        this.questionRepository = questionRepository;
        this.questionDAL = questionDAL;
    }


    @GetMapping("/home")
    public ResponseEntity<?> listQuestionsForHome(@RequestParam(name = "tags", required = false) List<String> tags, @RequestParam(name="zip", required = false) String zip){
        return ResponseEntity.ok().body(questionDAL.getQuestionsForFeed(tags, zip));
    }

}
