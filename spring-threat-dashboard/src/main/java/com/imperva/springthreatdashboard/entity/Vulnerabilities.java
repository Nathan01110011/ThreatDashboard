package com.imperva.springthreatdashboard.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "vulnerabilities")
public class Vulnerabilities {

    @Id
    @Column(name = "vulnerability_id")
    private String vulnerabilityId;

    @Column(name = "vulnerability_title")
    private String vulnerabilityTitle;

    @Column(name = "vulnerability_cwe")
    private String vulnerabilityCwe;

    @Column(name = "vulnerability_date_published")
    private Date vulnerabilityDatePublished;

    @Column(name = "vulnerability_date_updated")
    private Date vulnerabilityDateUpdated;

    @Column(name = "vulnerability_cpe")
    private String vulnerabilityCpe;

    @Column(name = "cvss_base_score")
    private float cvssBaseScore;

    @Column(name = "priority")
    private Integer priority;

    public String getVulnerabilityId() {
        return vulnerabilityId;
    }

    public void setVulnerabilityId(String vulnerabilityId) {
        this.vulnerabilityId = vulnerabilityId;
    }

    public String getVulnerabilityTitle() {
        return vulnerabilityTitle;
    }

    public void setVulnerabilityTitle(String vulnerabilityTitle) {
        this.vulnerabilityTitle = vulnerabilityTitle;
    }

    public String getVulnerabilityCwe() {
        return vulnerabilityCwe;
    }

    public void setVulnerabilityCwe(String vulnerabilityCwe) {
        this.vulnerabilityCwe = vulnerabilityCwe;
    }

    public Date getVulnerabilityDatePublished() {
        return vulnerabilityDatePublished;
    }

    public void setVulnerabilityDatePublished(Date vulnerabilityDatePublished) {
        this.vulnerabilityDatePublished = vulnerabilityDatePublished;
    }

    public Date getVulnerabilityDateUpdated() {
        return vulnerabilityDateUpdated;
    }

    public void setVulnerabilityDateUpdated(Date vulnerabilityDateUpdated) {
        this.vulnerabilityDateUpdated = vulnerabilityDateUpdated;
    }

    public String getVulnerabilityCpe() {
        return vulnerabilityCpe;
    }

    public void setVulnerabilityCpe(String vulnerabilityCpe) {
        this.vulnerabilityCpe = vulnerabilityCpe;
    }

    public float getCvssBaseScore() {
        return cvssBaseScore;
    }

    public void setCvssBaseScore(float cvssBaseScore) {
        this.cvssBaseScore = cvssBaseScore;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String toString(){
        return "Vulnerability [ vulnerability id: " + vulnerabilityId + ", vulnerability title: " + vulnerabilityTitle + ", vulnerability cwe: " + vulnerabilityCwe + ", vulnerability date published: " + vulnerabilityDatePublished + ", vulnerability date updated: " + vulnerabilityDateUpdated + ", vulnerability cpe: " + vulnerabilityCpe + ", cvss base score: " + cvssBaseScore + ", priority: " + priority + " ]";
    }
}
