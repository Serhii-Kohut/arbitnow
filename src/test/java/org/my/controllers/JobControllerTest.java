package org.my.controllers;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.my.arbeitnowjobboard.controllers.JobController;
import org.my.arbeitnowjobboard.entities.Job;
import org.my.arbeitnowjobboard.repositories.JobRepository;
import org.my.arbeitnowjobboard.services.impl.JobServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobControllerTest {

    @Mock
    JobRepository jobRepository;

    @Mock
    JobServiceImpl jobService;

    @InjectMocks
    JobController jobController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllJobs() {
        // Arrange
        int page = 0;
        int size = 10;
        String sortBy = "createdAt";
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortBy));
        when(jobService.getAllJobs(page, size, sortBy)).thenReturn(new PageImpl<>(Collections.emptyList()));

        // Act
        Page<Job> result = jobController.getAllJobs(page, size, sortBy);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.getContent().size());
    }

    @Test
    void testGetTop10Jobs() {
        // Arrange
        List<Job> top10Jobs = Collections.emptyList();
        when(jobService.getTop10Jobs()).thenReturn(top10Jobs);

        // Act
        List<Job> result = jobController.getTop10Jobs();

        // Assert
        assertNotNull(result);
        assertEquals(0, result.size());
        verify(jobService).getTop10Jobs();
    }

    @Test
    void testGetLocationStats() {
        // Arrange
        Map<String, Long> locationStats = Collections.singletonMap("Berlin", 10L);
        when(jobService.getLocationStats()).thenReturn(locationStats);

        // Act
        Map<String, Long> result = jobController.getLocationStats();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.containsKey("Berlin"));
        assertEquals(10L, result.get("Berlin"));

        verify(jobService).getLocationStats();
    }

    @Test
    void testFetchJobs() {
        // Arrange
        doNothing().when(jobService).fetchJobs();

        // Act
        jobController.fetchJobs();

        // Assert
        verify(jobService).fetchJobs();
    }
}

