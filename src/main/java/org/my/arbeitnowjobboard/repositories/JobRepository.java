package org.my.arbeitnowjobboard.repositories;

import org.my.arbeitnowjobboard.entities.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
}
