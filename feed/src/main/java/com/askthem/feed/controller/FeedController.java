package com.askthem.feed.controller;

import com.askthem.feed.dto.request.AddQuestion;
import com.askthem.feed.model.Questions;
import com.askthem.feed.repository.FeedRepository;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
//    public ResponseEntity<?> feed(@RequestParam(value="keywords") String keywords, @RequestParam String zipcode){
    public ResponseEntity<?> feed(@RequestParam("keywords") List<String> values, @RequestParam("zipcode") String zipcode){
//        List<String> tags = Arrays.asList(keywords.split(","));
//        List<String> tags = Arrays.asList(values);
        System.out.println(values);
        System.out.println(zipcode);
        String[] val = new String[values.size()];
        for(int i=0; i<values.size(); ++i){
            val[i] = values.get(i);
        }

        System.out.println(val[0]);
//        return null;





        // List
        List<String> li = new ArrayList<>();
        li.add("library");
        li.add("fit");

        Character[] s = { 'f', 'l' };


        List<String> tags = List.of("fit", "library");
//        List<?> tt = new ArrayList<>();
        String ss = "'fit', 'library'";

        return ResponseEntity.ok().body(feedRepository.listQuestions(ss, zipcode));
    }

    @GetMapping("/list")
//    public ResponseEntity<?> feed(@RequestParam(value="keywords") String keywords, @RequestParam String zipcode){
    public ResponseEntity<?> feed(){
        List<String> tags = List.of("Fit", "library");
        return ResponseEntity.ok().body(feedRepository.listQ(tags));
    }
}
