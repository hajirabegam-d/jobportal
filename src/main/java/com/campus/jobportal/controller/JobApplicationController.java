package com.campus.jobportal.controller;
import com.campus.jobportal.entity.JobApplication;
import com.campus.jobportal.service.JobApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api") public class JobApplicationController {
 private final JobApplicationService service; public JobApplicationController(JobApplicationService service){this.service=service;}
 @PostMapping("/jobs/{jobId}/applications") @ResponseStatus(HttpStatus.CREATED)
 public JobApplication apply(@PathVariable Long jobId,@Valid @RequestBody JobApplication a){return service.apply(jobId,a);}
 @GetMapping("/jobs/{jobId}/applications") public List<JobApplication> byJob(@PathVariable Long jobId){return service.byJob(jobId);}
 @GetMapping("/applications") public List<JobApplication> all(){return service.all();}
 @DeleteMapping("/applications/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
 public void delete(@PathVariable Long id){service.delete(id);}
}
