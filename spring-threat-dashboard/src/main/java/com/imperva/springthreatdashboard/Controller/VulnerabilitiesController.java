package com.imperva.springthreatdashboard.Controller;

import com.imperva.springthreatdashboard.entity.Vulnerabilities;
import com.imperva.springthreatdashboard.repository.VulnerabilitiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("vulnerabilities")
@CrossOrigin(origins = "*")
public class VulnerabilitiesController {

    @Autowired
    private VulnerabilitiesRepository repository;

    @GetMapping("/getAll")
    public List<Vulnerabilities> getAll(){
        return repository.findAll();
    }

    @GetMapping("/get")
    public Vulnerabilities get(@RequestParam(name = "id") String id){

        Vulnerabilities vulnerabilities;
        try {
            vulnerabilities = repository.findById(id).get();
        } catch (Exception e){
            vulnerabilities = null;
        }
        return vulnerabilities;
    }
}
