package com.imperva.springthreatdashboard.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "cve_tweets")
public class CveTweets implements Serializable {

    @Id
    @Column(name = "tweet_id")
    private long tweetId;

    @Column(name = "tweet_text", length= 500)
    private String tweetText;

    @Column(name = "poc_flag")
    private Boolean pocFlags;

    @Column(name = "cves_mentioned")
    private String cvesMentioned;

    @Column(name = "date_created")
    private Date dateCreated ;

    @Column(name = "user_id")
    private long userId ;

    public long getTweetId(){
        return this.tweetId;
    }

    public void setTweetId(long cveId){
        this.tweetId = cveId;
    }

    public String getTweetText(){
        return this.tweetText;
    }

    public void setTweetText(String tweetText){
        this.tweetText = tweetText;
    }

    public Boolean getPocFlags(){
        return this.pocFlags;
    }

    public void setTweetText(boolean pocFlags){
        this.pocFlags = pocFlags;
    }

    public String getCvesMentioned(){
        return this.cvesMentioned;
    }

    public void setCvesMentioned(String cvesMentioned){
        this.cvesMentioned = cvesMentioned;
    }

    public Date getDateCreated(){
        return this.dateCreated;
    }

    public void setDateCreated(Date dateCreated){
        this.dateCreated = dateCreated;
    }

    public long getUserId(){
        return this.userId;
    }

    public void setUserId(long userId){
        this.userId = userId;
    }

    @Override
    public String toString(){
        return "CveTweet [ tweetId: " + tweetId + ", tweet text: " + tweetText + ", pocFlags: " + pocFlags + ", cves mentioned: " + cvesMentioned + ", date created: " + dateCreated + ", user id: " + userId + "]";
    }
}
