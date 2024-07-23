package org.my.arbeitnowjobboard.utils;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.my.arbeitnowjobboard.entities.Job;

import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobResponse {
    List<Job> data;
}
