package com.campus.jobportal.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
@Entity @Table(name="job_applications", uniqueConstraints=@UniqueConstraint(columnNames={"job_id","applicant_email"}))
public class JobApplication {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @NotBlank @Column(nullable=false) private String applicantName;
    @NotBlank @Email @Column(name="applicant_email",nullable=false) private String applicantEmail;
    private String phone; private String resumeUrl;
    @ManyToOne(fetch=FetchType.EAGER,optional=false) @JoinColumn(name="job_id",nullable=false) private Job job;
    @Column(nullable=false) private LocalDateTime appliedAt=LocalDateTime.now();
    public JobApplication() {}
    @PrePersist public void prePersist(){if(appliedAt==null) appliedAt=LocalDateTime.now();}
    public Long getId(){return id;} public String getApplicantName(){return applicantName;} public void setApplicantName(String v){applicantName=v;}
    public String getApplicantEmail(){return applicantEmail;} public void setApplicantEmail(String v){applicantEmail=v;}
    public String getPhone(){return phone;} public void setPhone(String v){phone=v;}
    public String getResumeUrl(){return resumeUrl;} public void setResumeUrl(String v){resumeUrl=v;}
    public Job getJob(){return job;} public void setJob(Job v){job=v;}
    public LocalDateTime getAppliedAt(){return appliedAt;} public void setAppliedAt(LocalDateTime v){appliedAt=v;}
}
