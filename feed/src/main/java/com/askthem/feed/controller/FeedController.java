package com.askthem.feed.controller;

import com.askthem.feed.dto.request.AddQuestion;
import com.askthem.feed.model.Questions;
import com.askthem.feed.repository.FeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/feed")
public class FeedController {

    @Autowired
    private FeedRepository feedRepository;

    @PostMapping("/add")
    public ResponseEntity<?> addQuestion(@RequestBody AddQuestion addQuestion){
        Questions q = new Questions();
        q.setIsAnswered(false);
        q.setQuestion(addQuestion.getQuestion());
        q.setTags(addQuestion.getTags());
        q.setUserId(addQuestion.getUserId());
        q.setZipcode(addQuestion.getZipcode());
        return ResponseEntity.ok().body(feedRepository.save(q));
    }

    @GetMapping("/all")
    public ResponseEntity<?> feed(){
        return ResponseEntity.ok().body(feedRepository.listQuestions());
    }
}
