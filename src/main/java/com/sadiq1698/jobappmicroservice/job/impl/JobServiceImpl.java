package com.sadiq1698.jobappmicroservice.job.impl;

import com.sadiq1698.jobappmicroservice.job.Job;
import com.sadiq1698.jobappmicroservice.job.JobRepository;
import com.sadiq1698.jobappmicroservice.job.JobService;
import com.sadiq1698.jobappmicroservice.job.dto.JobWithCompanyDTO;
import com.sadiq1698.jobappmicroservice.job.external.Company;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

    JobRepository jobRepository;

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public List<JobWithCompanyDTO> findAll() {
        List<Job> jobs = jobRepository.findAll();
        List <JobWithCompanyDTO> jobsWithCompanyDTO = new ArrayList();

        return jobs.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private JobWithCompanyDTO convertToDTO(Job job) {
        RestTemplate restTemplate = new RestTemplate();

        JobWithCompanyDTO jobWithCompanyDTO = new JobWithCompanyDTO();
        jobWithCompanyDTO.setJob(job);

        Company company = restTemplate.getForObject("http://localhost:8081/company/" + job.getCompanyId(), Company.class);
        jobWithCompanyDTO.setCompany(company);

        return jobWithCompanyDTO;
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
    public JobWithCompanyDTO getJobyId(Long id) {
        Job job =  jobRepository.findById(id).orElse(null);
        return convertToDTO(job);
    }
}
