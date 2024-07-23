package org.my.services;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.my.arbeitnowjobboard.entities.Job;
import org.my.arbeitnowjobboard.repositories.JobRepository;
import org.my.arbeitnowjobboard.services.impl.JobServiceImpl;
import org.my.arbeitnowjobboard.utils.JobResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobServiceImplTest {
    @Mock
    JobRepository jobRepository;

    @InjectMocks
    JobServiceImpl jobService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetTop10Jobs() {
        // Arrange
        Job job1 = new Job();
        job1.setCreatedAt(Instant.now());
        Job job2 = new Job();
        job2.setCreatedAt(Instant.now().minusSeconds(3600));
        List<Job> jobs = Arrays.asList(job1, job2);
        Page<Job> jobPage = new PageImpl<>(jobs, PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdAt")), jobs.size());
        when(jobRepository.findAll(PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdAt")))).thenReturn(jobPage);

        // Act
        List<Job> result = jobService.getTop10Jobs();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testGetLocationStats() {
        // Arrange
        Job job1 = new Job();
        job1.setLocation("Berlin");
        Job job2 = new Job();
        job2.setLocation("Berlin");
        Job job3 = new Job();
        job3.setLocation("Munich");
        List<Job> jobs = Arrays.asList(job1, job2, job3);
        when(jobRepository.findAll()).thenReturn(jobs);

        // Act
        Map<String, Long> result = jobService.getLocationStats();

        // Assert
        assertEquals(2, result.size());
        assertEquals(2, result.get("Berlin"));
        assertEquals(1, result.get("Munich"));
    }

    @Test
    void testFetchJobs() {
        // Arrange
        JobResponse jobResponse = new JobResponse();
        jobResponse.setData(Arrays.asList(new Job(), new Job()));
        RestTemplate restTemplate = mock(RestTemplate.class);
        when(restTemplate.getForObject(anyString(), eq(JobResponse.class))).thenReturn(jobResponse);
        JobServiceImpl jobService = new JobServiceImpl(jobRepository, restTemplate);

        // Act
        jobService.fetchJobs();

        // Assert
        verify(jobRepository, times(5)).saveAll(anyList());
    }

}
