package com.campus.jobportal.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
@Entity @Table(name="jobs")
public class Job {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @NotBlank @Column(nullable=false) private String title;
    @NotBlank @Column(nullable=false) private String company;
    @NotBlank @Column(nullable=false) private String location;
    @NotBlank @Column(nullable=false, length=4000) private String description;
    @NotBlank @Column(nullable=false) private String employmentType;
    @NotNull @Min(0) @Column(nullable=false) private Integer salaryMin;
    @NotNull @Min(0) @Column(nullable=false) private Integer salaryMax;
    @Column(nullable=false) private LocalDateTime postedAt=LocalDateTime.now();
    public Job() {}
    @PrePersist public void prePersist(){ if(postedAt==null) postedAt=LocalDateTime.now(); }
    public Long getId(){return id;} public String getTitle(){return title;} public void setTitle(String v){title=v;}
    public String getCompany(){return company;} public void setCompany(String v){company=v;}
    public String getLocation(){return location;} public void setLocation(String v){location=v;}
    public String getDescription(){return description;} public void setDescription(String v){description=v;}
    public String getEmploymentType(){return employmentType;} public void setEmploymentType(String v){employmentType=v;}
    public Integer getSalaryMin(){return salaryMin;} public void setSalaryMin(Integer v){salaryMin=v;}
    public Integer getSalaryMax(){return salaryMax;} public void setSalaryMax(Integer v){salaryMax=v;}
    public LocalDateTime getPostedAt(){return postedAt;} public void setPostedAt(LocalDateTime v){postedAt=v;}
}
