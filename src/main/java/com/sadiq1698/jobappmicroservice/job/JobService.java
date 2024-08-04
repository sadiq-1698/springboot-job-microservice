package com.sadiq1698.jobappmicroservice.job;

import com.sadiq1698.jobappmicroservice.job.dto.JobWithCompanyDTO;

import java.util.List;

public interface JobService {
   List<JobWithCompanyDTO> findAll();
   void createJob(Job job);
   boolean deleteJob(Long id);
   boolean updateJob(Long id, Job job);
   Job getJobyId(Long id);
}
