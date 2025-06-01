package com.spring.mongo.jobs.controller;

import com.spring.mongo.jobs.entity.Job;
import com.spring.mongo.jobs.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
@Tag(name = "Job Controller", description = "APIs for managing jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    @Operation(summary = "Get all jobs", description = "Retrieve a list of all available jobs")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list")
    @GetMapping
    public List<Job> getAllJobs() {
        return jobService.getAllJobs();
    }

    @Operation(summary = "Get job by ID", description = "Retrieve a job by its ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved job")
    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable String id) {
        Job job = jobService.getJobById(id);
        return ResponseEntity.ok(job);
    }

    @PreAuthorize("hasRole('EMPLOYER')")
    @Operation(summary = "Create a new job", description = "Create a new job posting (EMPLOYER role required)")
    @ApiResponse(responseCode = "201", description = "Successfully created job")
    @PostMapping
    public Job createJob(@RequestBody Job job) {
        return jobService.createJob(job);
    }

    @Operation(summary = "Update a job", description = "Update an existing job by ID")
    @ApiResponse(responseCode = "200", description = "Successfully updated job")
    @PutMapping("/{id}")
    public ResponseEntity<Job> updateJob(@PathVariable String id, @RequestBody Job job) {
        Job updatedJob = jobService.updateJob(id, job);
        return ResponseEntity.ok(updatedJob);
    }

    @PreAuthorize("hasRole('EMPLOYER')")
    @Operation(summary = "Delete a job", description = "Delete a job by ID (EMPLOYER role required)")
    @ApiResponse(responseCode = "200", description = "Successfully deleted job")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable String id) {
        jobService.deleteJob(id);
        return ResponseEntity.ok().build();
    }
}
