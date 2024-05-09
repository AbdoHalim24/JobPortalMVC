package com.AbdoHalim.JobPortal.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String image;
    private String name;
    private String email;
    private String password;
    private String title;
    private String jobPreference;
    private String description;
    private String phone;
    private String role;
    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    private Resume resume;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Job> companyJobs=new ArrayList<>();
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(  name = "saved_jobs", joinColumns = @JoinColumn(name = "userId",referencedColumnName = "userId"),
            inverseJoinColumns = @JoinColumn(name = "jobId",referencedColumnName = "jobId")
    )
    private List<Job> savedJobs=new ArrayList<>();;
}
