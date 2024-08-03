package com.sadiq1698.jobappmicroservice.job.impl;

import com.sadiq1698.jobappmicroservice.job.Job;
import com.sadiq1698.jobappmicroservice.job.JobRepository;
import com.sadiq1698.jobappmicroservice.job.JobService;
import com.sadiq1698.jobappmicroservice.job.external.Company;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {

    JobRepository jobRepository;

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public List<Job> findAll() {
        RestTemplate restTemplate = new RestTemplate();
        Company company = restTemplate.getForObject("http://localhost:8082/company/1", Company.class);
        return jobRepository.findAll();
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
    public Job getJobyId(Long id) {
        return jobRepository.findById(id).orElse(null);
    }
}
