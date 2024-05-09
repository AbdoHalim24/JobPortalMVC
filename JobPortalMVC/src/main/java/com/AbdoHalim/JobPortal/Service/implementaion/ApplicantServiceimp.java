package com.AbdoHalim.JobPortal.Service.implementaion;


import com.AbdoHalim.JobPortal.Entity.Job;
import com.AbdoHalim.JobPortal.Entity.Resume;
import com.AbdoHalim.JobPortal.Repository.JobRepo;
import com.AbdoHalim.JobPortal.Repository.ResumeRepo;
import com.AbdoHalim.JobPortal.Repository.UserRepo;
import com.AbdoHalim.JobPortal.Service.ApplicantService;
import com.AbdoHalim.JobPortal.Service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ApplicantServiceimp implements ApplicantService {
    private UserService userService;
    private final UserRepo userRepo;
    private JobRepo jobRepo;
    private ResumeRepo resumeRepo;

    public ApplicantServiceimp(JobRepo jobRepo
                    , ResumeRepo resumeRepo, UserService userService, UserRepo userRepo) {
        this.jobRepo = jobRepo;
        this.resumeRepo=resumeRepo;
        this.userService=userService;
        this.userRepo = userRepo;
    }

    @Override
    public ResponseEntity<String> savejob(long id) {
        Job job=jobRepo.GetJob((long) id);
        if (userService.CurrantUser().getSavedJobs().contains(job)){
            return ResponseEntity.ok("Job Saved");
        }
        userService.CurrantUser().getSavedJobs().add(job);
        userRepo.save(userService.CurrantUser());
        return ResponseEntity.ok("Job Saved");
    }

    @Override
    public ResponseEntity<String> ApplyForJob(Long id) {
        Job job = jobRepo.GetJob(id);
        if (job == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Job Not Found");
        }
        if (userService.CurrantUser().getResume() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You don't have a resume");
        }
        Resume userResume = userService.CurrantUser().getResume();
        List<Resume> resumeList = job.getResumeList();
        if (resumeList.contains(userResume)) {
            return ResponseEntity.ok("You have already applied for this job");
        }
        resumeList.add(userResume);
        jobRepo.save(job);
        return ResponseEntity.ok("Applied Successfully");
    }
    @Override
    public List<Job>RetriveSavedJobs() {
        List<Job> jobList=userService.CurrantUser().getSavedJobs();
        return jobList;
    }

    @Override
    public String deleteSavedJob(Long id) {
        List<Job> jobList=userService.CurrantUser().getSavedJobs();
        Job job=jobRepo.GetJob(id);
        if (jobList.contains(job)){
            jobList.remove(job);
            userService.CurrantUser().setSavedJobs(jobList);;
            userRepo.save(userService.CurrantUser());
            return "Job Remove From Your List";
        }
        return "Job is Not in Your List";

    }


}
