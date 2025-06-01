package com.spring.mongo.jobs.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Company {
    private String name;
    private String description;
    private String contactEmail;
    private String contactPhone;
}