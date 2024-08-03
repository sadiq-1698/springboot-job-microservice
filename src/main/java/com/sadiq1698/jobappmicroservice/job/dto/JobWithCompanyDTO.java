package com.sadiq1698.jobappmicroservice.job.dto;

import com.sadiq1698.jobappmicroservice.job.Job;
import com.sadiq1698.jobappmicroservice.job.external.Company;

public class JobWithCompanyDTO {
    private Job job;
    private Company company;

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
