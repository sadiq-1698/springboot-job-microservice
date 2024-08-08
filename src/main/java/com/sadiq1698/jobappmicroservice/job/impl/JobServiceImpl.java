package com.sadiq1698.jobappmicroservice.job.impl;

import com.sadiq1698.jobappmicroservice.job.Job;
import com.sadiq1698.jobappmicroservice.job.JobRepository;
import com.sadiq1698.jobappmicroservice.job.JobService;
import com.sadiq1698.jobappmicroservice.job.dto.JobDTO;
import com.sadiq1698.jobappmicroservice.job.external.Company;
import com.sadiq1698.jobappmicroservice.job.external.Review;
import com.sadiq1698.jobappmicroservice.job.mapper.JobMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

    JobRepository jobRepository;

    @Autowired
    RestTemplate restTemplate;

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public List<JobDTO> findAll() {
        List<Job> jobs = jobRepository.findAll();

        return jobs.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private JobDTO convertToDTO(Job job) {
        Company company = restTemplate.getForObject(
                "http://mohammed-thinkpad-p14s-gen-2a.domain.name:8081/company/1",
                Company.class
        );

        ResponseEntity<List<Review>> reviewResponse = restTemplate.exchange(
                "http://mohammed-thinkpad-p14s-gen-2a.domain.name:8083/review/all?companyId=" + job.getCompanyId(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Review>>() {}
        );

        List<Review> reviews = reviewResponse.getBody();

        JobDTO jobDTO = JobMapper.mapJobWithDTO(job, company, reviews);
        return jobDTO;
    }

    @Override
    public void createJob(Job job) {
        jobRepository.save(job);
    }

    @Override
    public boolean deleteJob(Long id) {
        try {
            jobRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updateJob(Long id, Job job) {
        Optional<Job> jobOptional = jobRepository.findById(id);
            if (jobOptional.isPresent()) {
                Job oldJob = jobOptional.get();
                oldJob.setTitle(job.getTitle());
                oldJob.setDescription(job.getDescription());
                oldJob.setLocation(job.getLocation());
                oldJob.setMinSalary(job.getMinSalary());
                oldJob.setMaxSalary(job.getMaxSalary());
                jobRepository.save(oldJob);
                return true;
        }
        return false;
    }

    @Override
    public JobDTO getJobyId(Long id) {
        Job job =  jobRepository.findById(id).orElse(null);
        return convertToDTO(job);
    }
}
