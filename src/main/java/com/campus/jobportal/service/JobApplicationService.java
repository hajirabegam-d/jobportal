package com.campus.jobportal.service;
import com.campus.jobportal.entity.Job;
import com.campus.jobportal.entity.JobApplication;
import com.campus.jobportal.repository.JobApplicationRepository;
import com.campus.jobportal.repository.JobRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
@Service public class JobApplicationService {
 private final JobApplicationRepository applications; private final JobRepository jobs;
 public JobApplicationService(JobApplicationRepository applications,JobRepository jobs){this.applications=applications;this.jobs=jobs;}
 public JobApplication apply(Long jobId,JobApplication a){Job j=jobs.findById(jobId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Job not found"));a.setJob(j);return applications.save(a);}
 public List<JobApplication> byJob(Long id){if(!jobs.existsById(id))throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Job not found");return applications.findByJob_Id(id);}
 public List<JobApplication> all(){return applications.findAll();}
 public void delete(Long id){JobApplication application=applications.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Application not found"));applications.delete(application);}
}
