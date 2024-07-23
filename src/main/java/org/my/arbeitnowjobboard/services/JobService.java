package org.my.arbeitnowjobboard.services;

import org.my.arbeitnowjobboard.entities.Job;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface JobService {
    Page<Job> getAllJobs(int page, int size, String sortBy);
    List<Job> getTop10Jobs();
    Map<String, Long> getLocationStats();
    void fetchJobs();
}
