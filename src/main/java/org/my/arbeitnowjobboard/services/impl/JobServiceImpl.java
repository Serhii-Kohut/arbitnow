package org.my.arbeitnowjobboard.services.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.my.arbeitnowjobboard.entities.Job;
import org.my.arbeitnowjobboard.repositories.JobRepository;
import org.my.arbeitnowjobboard.services.JobService;
import org.my.arbeitnowjobboard.utils.JobResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JobServiceImpl implements JobService {

    JobRepository jobRepository;
    RestTemplate restTemplate;

    static String API_URL = "https://www.arbeitnow.com/api/job-board-api?page=";

    @Autowired
    public JobServiceImpl(JobRepository jobRepository, RestTemplate restTemplate) {
        this.jobRepository = jobRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public Page<Job> getAllJobs(int page, int size, String sortBy) {
        return jobRepository.findAll(PageRequest.of(page, size, Sort.by(sortBy)));
    }

    @Override
    public List<Job> getTop10Jobs() {
        return jobRepository.findAll(PageRequest.of(0, 10,
                Sort.by(Sort.Direction.DESC, "createdAt"))).getContent();
    }

    @Override
    public Map<String, Long> getLocationStats() {
        return jobRepository.findAll().stream()
                .collect(Collectors.groupingBy(Job::getLocation, Collectors.counting()));
    }

    @Override
    @Transactional
    public void fetchJobs() {
        RestTemplate restTemplate = new RestTemplate();
        for (int i = 1; i <= 5; i++) {
            JobResponse response = restTemplate.getForObject(API_URL + i, JobResponse.class);
            if (response != null) {
                List<Job> jobs = response.getData();
                System.out.println("Fetched " + jobs.size() + " jobs from page " + i);
                jobRepository.saveAll(jobs);
            }
        }
    }
}
