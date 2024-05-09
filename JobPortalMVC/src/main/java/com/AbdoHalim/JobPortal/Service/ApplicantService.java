package com.AbdoHalim.JobPortal.Service;

import com.AbdoHalim.JobPortal.Entity.Job;
import com.AbdoHalim.JobPortal.Model.UserModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface ApplicantService {


    ResponseEntity<String> savejob(long id);

    ResponseEntity<String> ApplyForJob(Long id);

    List<Job> RetriveSavedJobs();

    String deleteSavedJob(Long id);
}
