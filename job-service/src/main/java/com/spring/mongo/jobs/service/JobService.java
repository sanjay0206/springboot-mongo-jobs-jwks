package com.spring.mongo.jobs.service;

import com.spring.mongo.jobs.entity.Job;
import com.spring.mongo.jobs.exceptions.ResourceNotFoundException;
import com.spring.mongo.jobs.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class JobService {

    private final JobRepository jobRepository;

    @Autowired
    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    public Job getJobById(String id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job", "id", id));
    }

    public Job createJob(Job job) {
        job.setPostedAt(LocalDate.now());
        return jobRepository.save(job);
    }

    public Job updateJob(String id, Job updatedJob) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job", "id", id));

        job.setTitle(updatedJob.getTitle());
        job.setType(updatedJob.getType());
        job.setDescription(updatedJob.getDescription());
        job.setLocation(updatedJob.getLocation());
        job.setSalary(updatedJob.getSalary());
        job.setCompany(updatedJob.getCompany());
        // Preserve original postedAt
        job.setPostedAt(job.getPostedAt());

        return jobRepository.save(job);
    }

    public void deleteJob(String id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job", "id", id));
        jobRepository.delete(job);
    }
}
