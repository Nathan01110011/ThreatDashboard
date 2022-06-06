package com.imperva.springthreatdashboard.Controller;

import com.imperva.springthreatdashboard.entity.CveGithubPocs;
import com.imperva.springthreatdashboard.repository.CveGithubPocsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("github")
@CrossOrigin(origins = "*")
public class CveGithubPocsController {

    @Autowired
    CveGithubPocsRepository cveGithubPocsRepository;

    List<CveGithubPocs> cveGithubPocsList = new ArrayList<>();

    @EventListener(ApplicationReadyEvent.class)
    public void initialiseCaptchaList() throws Exception{

        cveGithubPocsList = cveGithubPocsRepository.findAll();
    }

    @GetMapping("/latest")
    public List<CveGithubPocs> getLatestSince(@RequestParam(name = "hoursFromNow", defaultValue = "1") long hoursFromNow){

        List<CveGithubPocs> cveGithubPocsSince = new ArrayList<>();
        long hoursToMilis = 1000 * 60 * 60 * hoursFromNow ;
        Date startTimeRange = new Date(System.currentTimeMillis() - hoursToMilis);

        for (CveGithubPocs cveGithubPocs: cveGithubPocsList){
            int compareResult = cveGithubPocs.getDateCreated().compareTo(startTimeRange);
            if (compareResult >= 0){
                cveGithubPocsSince.add(cveGithubPocs);
            }
        }

        return cveGithubPocsSince;
    }

}
