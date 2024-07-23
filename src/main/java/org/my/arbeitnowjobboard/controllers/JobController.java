package org.my.arbeitnowjobboard.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.my.arbeitnowjobboard.entities.Job;
import org.my.arbeitnowjobboard.repositories.JobRepository;
import org.my.arbeitnowjobboard.services.impl.JobServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JobController {

    JobServiceImpl jobService;

    @GetMapping
    public Page<Job> getAllJobs(@RequestParam int page, @RequestParam int size, @RequestParam String sortBy) {
        return jobService.getAllJobs(page, size, sortBy);
    }

    @GetMapping("/top10")
    public List<Job> getTop10Jobs() {
        return jobService.getTop10Jobs();
    }

    @GetMapping("/location-stats")
    public Map<String, Long> getLocationStats() {
        return jobService.getLocationStats();
    }

    @PostMapping("/fetch")
    public void fetchJobs() {
        jobService.fetchJobs();
    }
}
