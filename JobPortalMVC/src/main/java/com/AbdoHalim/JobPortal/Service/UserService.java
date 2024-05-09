package com.AbdoHalim.JobPortal.Service;

import com.AbdoHalim.JobPortal.Entity.Job;
import com.AbdoHalim.JobPortal.Entity.User;
import com.AbdoHalim.JobPortal.Model.ChangePasswordModel;
import com.AbdoHalim.JobPortal.Model.SearchModel;
import com.AbdoHalim.JobPortal.Model.UserModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
      ResponseEntity<String> uploadFile(MultipartFile file) ;


    ResponseEntity<String> saveNewUser(UserModel userModel);

    User retriveUserByEmail(String email);
    public User CurrantUser();

//    ResponseEntity<List<Job>> retriveJobs(SearchModel searchModel);


    ResponseEntity<List<Job>> retriveJobs(SearchModel searchModel, boolean b);

    ResponseEntity<String> UpdateInfo(UserModel userModel);

    ResponseEntity<String> ChangePassword(ChangePasswordModel changePasswordModel);

    List<Job>retriveAllJobs();

    Job FindJob(long id);

    User retriveUserById(Long id);

    List<Job> RetriveAllCompanyJobs(Long id);
}
