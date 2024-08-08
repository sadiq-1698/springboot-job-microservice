package com.sadiq1698.jobappmicroservice.job;

import com.sadiq1698.jobappmicroservice.job.dto.JobDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/job")
public class JobController {
    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    //  Get all jobs
    @GetMapping("/all")
    public ResponseEntity<List<JobDTO>> findAll() {
        return new ResponseEntity<>(jobService.findAll(), HttpStatus.OK);
    }

    //  Create a job
    @PostMapping
    public ResponseEntity<String> addJob(@RequestBody  Job _job) {
        jobService.createJob(_job);
        return new ResponseEntity<>("Job Added Successfully", HttpStatus.CREATED);
    }

    //  Get a job by ID
    @GetMapping("/{id}")
    public ResponseEntity<JobDTO> findAJob(@PathVariable Long id) {
        JobDTO jobDTO = jobService.getJobyId(id);
        if(jobDTO != null) return new ResponseEntity<>(jobDTO, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //  Update a job
    @PutMapping("/{id}")
    public ResponseEntity<String> updateJob(@PathVariable Long id, @RequestBody  Job _job) {
        boolean isUpdated = jobService.updateJob(id, _job);
        if (isUpdated) return new ResponseEntity<String>("Job Updated Successfully", HttpStatus.OK);
        return new ResponseEntity<String>("Failed Updating job", HttpStatus.NOT_FOUND);
    }

    //  Delete a job
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable Long id) {
        boolean isDeleted = jobService.deleteJob(id);
        if (isDeleted) return new ResponseEntity<String>("Job Deleted Successfully", HttpStatus.OK);
        return new ResponseEntity<String>("Failed Deleting job", HttpStatus.NOT_FOUND);
    }
}
