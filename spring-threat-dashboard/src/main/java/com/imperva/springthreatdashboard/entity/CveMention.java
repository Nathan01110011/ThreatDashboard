package com.imperva.springthreatdashboard.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.*;

@Entity
@Table(name = "cve_mentions")
public class CveMention implements Comparable {

    /*
    [''CVE-2020-17514'']
     */
    @Id
    @Column(name= "id")
    private String id;

    /*
    16/11/2021
    */
    @Column(name= "creation_date")
    private Date creationDate;

    /*
    001, 002, 003
     */
    @Column(name= "tweet_ids", length = 20000)
    private String tweetIds;

    @Column(name= "mention_count")
    private Long mentionCount;

    @Column(name= "affected_products", length = 20000)
    private String affectedProducts;

    public CveMention(String id, Date creationDate, String tweetId) {
        this.id = id;
        this.creationDate = creationDate;

        this.tweetIds = tweetId;

        this.mentionCount = 1L;
    }

    public CveMention() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getTweetIds() {
        return tweetIds;
    }

    public void setTweetIds(String tweetIds) {
        this.tweetIds = tweetIds;
    }

    public void addTweetId(String tweetIds) {
        if (! this.tweetIds.contains(tweetIds)){
            this.tweetIds += ", " + tweetIds;
        }
    }

    public Long getMentionCount() {
        return mentionCount;
    }

    public void setMentionCount(long mentionCount) {
        this.mentionCount = mentionCount;
    }

    public void incrementMentionCount(long value) {
        this.mentionCount += value;
    }

    public String getAffectedProducts() {
        return affectedProducts;
    }

    public void setAffectedProducts(String affectedProducts) {
        this.affectedProducts = affectedProducts;
    }

    @Override
    public int compareTo(Object o) {
        CveMention cveMention = (CveMention) o;
        return cveMention.getMentionCount().compareTo(getMentionCount());
    }
}
