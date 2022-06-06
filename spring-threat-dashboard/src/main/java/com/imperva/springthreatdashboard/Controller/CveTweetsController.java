package com.imperva.springthreatdashboard.Controller;

import com.imperva.springthreatdashboard.entity.CveMention;
import com.imperva.springthreatdashboard.entity.CveTweets;
import com.imperva.springthreatdashboard.repository.CveTweetsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cve")
@CrossOrigin(origins = "*")
public class CveTweetsController {

    @Autowired
    CveTweetsRepository repository;

    @GetMapping("/getAll")
    public List<CveTweets> getAll(){
        System.out.println("getting all tweets ");
        List<CveTweets> cveTweets = repository.findAll();
        if (cveTweets != null || cveTweets.size() != 0){
            System.out.println("tweets list found, count : " + cveTweets.size());
        } else {
            System.out.println("no tweets in db");
        }
        return cveTweets;
    }


    @GetMapping("/get")
    public CveTweets get(@RequestParam(name = "tweetId") Long tweetId){
        System.out.println("getting tweet id: " + tweetId);
        CveTweets cveTweets = repository.findByTweetId(tweetId).get(0);
        if (cveTweets != null){
            System.out.println("tweet found: " + cveTweets);
        } else {
            System.out.println("no tweet found for that id");
        }
        return cveTweets;
    }

}
