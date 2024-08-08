package com.sadiq1698.jobappmicroservice.job.mapper;

import com.sadiq1698.jobappmicroservice.job.Job;
import com.sadiq1698.jobappmicroservice.job.dto.JobDTO;
import com.sadiq1698.jobappmicroservice.job.external.Company;
import com.sadiq1698.jobappmicroservice.job.external.Review;

import java.util.List;

public class JobMapper {
    public static JobDTO mapJobWithDTO(
            Job job,
            Company company,
            List<Review> reviews
    ){
        JobDTO jobDTO = new JobDTO();
        jobDTO.setId(job.getId());
        jobDTO.setTitle(job.getTitle());
        jobDTO.setDescription(job.getDescription());
        jobDTO.setLocation(job.getLocation());
        jobDTO.setMaxSalary(job.getMaxSalary());
        jobDTO.setMinSalary(job.getMinSalary());
        jobDTO.setCompany(company);
        jobDTO.setReviews(reviews);
        return jobDTO;
    }
}
