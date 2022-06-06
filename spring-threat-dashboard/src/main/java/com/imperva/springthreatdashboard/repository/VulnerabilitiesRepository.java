package com.imperva.springthreatdashboard.repository;

import com.imperva.springthreatdashboard.entity.Vulnerabilities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VulnerabilitiesRepository extends JpaRepository<Vulnerabilities, String> {

    List<Vulnerabilities> findAll();

}
