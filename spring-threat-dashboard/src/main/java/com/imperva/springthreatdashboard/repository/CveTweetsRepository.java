package com.imperva.springthreatdashboard.repository;

import com.imperva.springthreatdashboard.entity.CveTweets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CveTweetsRepository extends JpaRepository <CveTweets, Long> {

    List<CveTweets> findByTweetId(Long id);
    List<CveTweets> findAll();
}
