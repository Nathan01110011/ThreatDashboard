package com.imperva.springthreatdashboard.repository;

import com.imperva.springthreatdashboard.entity.CveGithubPocs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CveGithubPocsRepository extends JpaRepository<CveGithubPocs, Long> {
}
