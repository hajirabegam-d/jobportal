package com.campus.jobportal.service;
import com.campus.jobportal.entity.Job;
import com.campus.jobportal.repository.JobRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
@Service public class JobService {
 private final JobRepository repo;
 public JobService(JobRepository repo){this.repo=repo;}
 public List<Job> getAll(String keyword){if(keyword==null||keyword.isBlank())return repo.findAll();return repo.findByTitleContainingIgnoreCaseOrCompanyContainingIgnoreCaseOrLocationContainingIgnoreCase(keyword,keyword,keyword);}
 public Job get(Long id){return repo.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Job not found"));}
 public Job create(Job j){checkSalary(j);return repo.save(j);}
 public Job update(Long id,Job u){Job j=get(id);j.setTitle(u.getTitle());j.setCompany(u.getCompany());j.setLocation(u.getLocation());j.setDescription(u.getDescription());j.setEmploymentType(u.getEmploymentType());j.setSalaryMin(u.getSalaryMin());j.setSalaryMax(u.getSalaryMax());checkSalary(j);return repo.save(j);}
 public void delete(Long id){repo.delete(get(id));}
 private void checkSalary(Job j){if(j.getSalaryMin()!=null&&j.getSalaryMax()!=null&&j.getSalaryMax()<j.getSalaryMin())throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"salaryMax must be >= salaryMin");}
}
