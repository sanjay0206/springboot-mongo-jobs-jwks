package com.spring.mongo.jobs.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "jobs")
@Data
@AllArgsConstructor
public class Job {
    @Id
    private String id;
    private String title;
    private String type;
    private String location;
    private String description;
    private String salary;
    private LocalDate postedAt;
    private Company company;
}
