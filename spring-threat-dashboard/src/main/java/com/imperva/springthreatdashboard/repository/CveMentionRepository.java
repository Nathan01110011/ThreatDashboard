package com.imperva.springthreatdashboard.repository;

import com.imperva.springthreatdashboard.entity.CveMention;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CveMentionRepository extends JpaRepository<CveMention, String> {
}
