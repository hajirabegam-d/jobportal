package com.campus.jobportal.controller;
import com.campus.jobportal.entity.Job;
import com.campus.jobportal.service.JobService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/jobs") public class JobController {
 private final JobService service; public JobController(JobService service){this.service=service;}
 @GetMapping public List<Job> all(@RequestParam(required=false) String keyword){return service.getAll(keyword);}
 @GetMapping("/{id}") public Job one(@PathVariable Long id){return service.get(id);}
 @PostMapping @ResponseStatus(HttpStatus.CREATED) public Job create(@Valid @RequestBody Job job){return service.create(job);}
 @PutMapping("/{id}") public Job update(@PathVariable Long id,@Valid @RequestBody Job job){return service.update(id,job);}
 @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id){service.delete(id);}
}
