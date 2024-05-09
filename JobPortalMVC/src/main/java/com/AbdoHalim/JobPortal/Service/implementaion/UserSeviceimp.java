package com.AbdoHalim.JobPortal.Service.implementaion;

import com.AbdoHalim.JobPortal.Entity.Job;
import com.AbdoHalim.JobPortal.Entity.Resume;
import com.AbdoHalim.JobPortal.Entity.User;
import com.AbdoHalim.JobPortal.Model.ChangePasswordModel;
import com.AbdoHalim.JobPortal.Model.SearchModel;
import com.AbdoHalim.JobPortal.Model.UserModel;
import com.AbdoHalim.JobPortal.Repository.JobRepo;
import com.AbdoHalim.JobPortal.Repository.ResumeRepo;
import com.AbdoHalim.JobPortal.Repository.UserRepo;
import com.AbdoHalim.JobPortal.Service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@Service
public class UserSeviceimp implements UserService {

    private PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final ResumeRepo resumeRepo;
    private final JobRepo jobRepo;

    public UserSeviceimp(UserRepo userRepo, PasswordEncoder passwordEncoder, ResumeRepo resumeRepo, JobRepo jobRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder=passwordEncoder;
        this.resumeRepo = resumeRepo;
        this.jobRepo =jobRepo;
    }
    @Value("${file.path}")
    private String filePath;
    @Override
    public ResponseEntity<String> uploadFile(MultipartFile file) {
        String dir = System.getProperty("user.dir") + File.separator + filePath;
        String fullPath = Paths.get(dir, file.getOriginalFilename()).toString();
        try {
            file.transferTo(new File(fullPath));

            Resume resume1=resumeRepo.GetUserResume(CurrantUser().getUserId());
            // to change an existing one
            if (resume1!=null){
                resume1.setName(file.getOriginalFilename());
                resumeRepo.save(resume1);
                return ResponseEntity.ok("Resume Changed successfully.");
            }
            // to add new one
            Resume resume=new Resume();
            resume.setName(file.getOriginalFilename());
            resume.setUser(CurrantUser());
            resumeRepo.save(resume);

            return ResponseEntity.ok("Resume uploaded successfully.");
        } catch (IOException e) {
            throw new RuntimeException("Error uploading file", e);
        }
    }

    @Override
    public ResponseEntity<String> saveNewUser(UserModel userModel) {
        if (userModel.getDescription().isEmpty()||
                userModel.getRole().isEmpty()|| userModel.getTitle().isEmpty()||
                userModel.getPassword().isEmpty()|| userModel.getEmail().isEmpty()||
                userModel.getName().isEmpty()|| userModel.getPhone().isEmpty()){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid credentials");
        }
        if (userRepo.findByEmail(userModel.getEmail())!=null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("this email is already exist");
        }

        User user=new User();
        user.setName(userModel.getName());
        user.setEmail(userModel.getEmail());
        user.setTitle(userModel.getTitle());
        user.setDescription(userModel.getDescription());
        user.setPassword(passwordEncoder.encode(userModel.getPassword()));
        user.setPhone(userModel.getPhone());
        user.setJobPreference(userModel.getJobPreference());

        user.setRole(userModel.getRole());
        userRepo.save(user);
        if (user.getRole().equals("APPLICANT"))
            return ResponseEntity.status(HttpStatus.OK).body("User Added");
        else
            return ResponseEntity.status(HttpStatus.OK).body("Company Added");

    }

    @Override
    public User retriveUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }
    public User CurrantUser(){
        String authentication= SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepo.findByName(authentication);
    }

    @Override
    public ResponseEntity<List<Job>> retriveJobs(SearchModel searchModel, boolean b) {
//        if (searchModel.getTitle().isEmpty() && searchModel.getCity().isEmpty()
//                && searchModel.getCountry().isEmpty()) {
//
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
//
//        // search for country
//        if (searchModel.getTitle().isEmpty() && searchModel.getCity().isEmpty()) {
//            List<Job> jobs = jobRepo.findByCountry(searchModel.getCountry());
//            return ResponseEntity.ok().body(jobs);
//        }
//        //search for city
//        if (searchModel.getTitle().isEmpty() && searchModel.getCountry().isEmpty()) {
//            List<Job> jobs = jobRepo.findByCity(searchModel.getCity());
//            return ResponseEntity.ok().body(jobs);
//        }
//        //search for title
//        if (searchModel.getCountry().isEmpty() && searchModel.getCity().isEmpty()) {
//            List<Job> jobs = jobRepo.findByTitle(searchModel.getTitle());
//            return ResponseEntity.ok().body(jobs);
//        }
//        //search for city and country
//        if (searchModel.getTitle().isEmpty()) {
//            List<Job> jobs = jobRepo.findByCountryAndCity(searchModel.getCountry()
//                    , searchModel.getCity());
//            return ResponseEntity.ok().body(jobs);
//        }
//        // search for Country and title
//        if (searchModel.getCity().isEmpty()) {
//            List<Job> jobs = jobRepo.findByCountryAndTitle(searchModel.getCountry()
//                    , searchModel.getTitle());
//            return ResponseEntity.ok().body(jobs);
//        }
//        // search for title and city
//        if (searchModel.getCountry().isEmpty()){
//        List<Job> jobs = jobRepo.findByTitleAndCity(searchModel.getTitle()
//                , searchModel.getCity());
//        return ResponseEntity.ok().body(jobs);
//        }
//    List<Job> jobs = jobRepo.findJob(searchModel.getCountry(),searchModel.getTitle()
//            , searchModel.getCity());
//        return ResponseEntity.ok().body(jobs);

        return ResponseEntity.ok().body(jobRepo.Search(searchModel.getCountry(),
                searchModel.getCity(),searchModel.getTitle(),false,null));
    }
    @Override
    public ResponseEntity<String> UpdateInfo(UserModel userModel) {
        if (userModel.getDescription().isEmpty()||
                userModel.getRole().equalsIgnoreCase("")|| userModel.getTitle().isEmpty()||
                userModel.getEmail().isEmpty()|| userModel.getName().isEmpty()||
                userModel.getPhone().isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid credentials");
        }
        User user=userRepo.findByEmail(userModel.getEmail());
        user.setName(userModel.getName());
        user.setEmail(userModel.getEmail());
        user.setTitle(userModel.getTitle());
        user.setDescription(userModel.getDescription());
        user.setPhone(userModel.getPhone());
        user.setJobPreference(userModel.getJobPreference());
        user.setRole(userModel.getRole());
        userRepo.save(user);
        if (user.getRole().equals("APPLICANT"))
            return ResponseEntity.status(HttpStatus.OK).body("Applicant Updated");
        else
            return ResponseEntity.status(HttpStatus.OK).body("Company Updated");
    }
    @Override
    public ResponseEntity<String> ChangePassword(ChangePasswordModel changePasswordModel) {
        if(!CurrantUser().getPassword().equals(passwordEncoder.encode(changePasswordModel.getOldPassword()))){
            CurrantUser().setPassword(passwordEncoder.encode(changePasswordModel.getNewPassword()));
            return ResponseEntity.ok("Password Changed Successfully");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Old Password dosen't Match");
    }

    @Override
    public List<Job> retriveAllJobs() {
        return jobRepo.findAllJobs();
    }

    @Override
    public Job FindJob(long id) {
        return jobRepo.findByJobId(id);
    }

    @Override
    public User retriveUserById(Long id) {
        return userRepo.findById(id).get();
    }

    @Override
    public List<Job> RetriveAllCompanyJobs(Long id) {

        return jobRepo.findAllJobsByCompanyId(id);
    }
}
