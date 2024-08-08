package com.sadiq1698.jobappmicroservice.job;

import com.sadiq1698.jobappmicroservice.job.dto.JobDTO;

import java.util.List;

public interface JobService {
   List<JobDTO> findAll();
   void createJob(Job job);
   boolean deleteJob(Long id);
   boolean updateJob(Long id, Job job);
   JobDTO getJobyId(Long id);
}
