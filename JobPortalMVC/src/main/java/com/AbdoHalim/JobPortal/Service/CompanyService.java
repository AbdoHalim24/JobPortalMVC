package com.AbdoHalim.JobPortal.Service;

import com.AbdoHalim.JobPortal.Entity.Job;
import com.AbdoHalim.JobPortal.Entity.Resume;
import com.AbdoHalim.JobPortal.Entity.User;
import com.AbdoHalim.JobPortal.Model.JobModel;
import com.AbdoHalim.JobPortal.Model.SearchModel;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface CompanyService {
    ResponseEntity<String> addJob(JobModel jobModel);

    ResponseEntity<List<Resume>> retriveResumes(int id);

    ResponseEntity<Resource> downloadResume(String file);

    List<Job> RetriveAllJobs();

    ResponseEntity<String> DeleteJob(Long id);

    ResponseEntity<String> EditeJob(Long id, JobModel jobModel);

    ResponseEntity<String> PauseJob(Long id);

    ResponseEntity<User> ViewProfile(Long userid);

    List<Job> searchInCompany(SearchModel searchModel);
}
