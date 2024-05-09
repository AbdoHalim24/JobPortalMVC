package com.AbdoHalim.JobPortal.Controller;

import com.AbdoHalim.JobPortal.Entity.Job;
import com.AbdoHalim.JobPortal.Entity.Resume;
import com.AbdoHalim.JobPortal.Entity.User;
import com.AbdoHalim.JobPortal.Model.JobModel;
import com.AbdoHalim.JobPortal.Model.SearchModel;
import com.AbdoHalim.JobPortal.Service.CompanyService;
import com.AbdoHalim.JobPortal.Service.UserService;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/company")
public class CompanyController {
    private  final CompanyService companyService;
    private final UserService userService;

    public CompanyController(CompanyService companyService, UserService userService) {
        this.companyService = companyService;
        this.userService = userService;
    }
    @GetMapping("addjob")
    public String showjobForm(Model model){
        model.addAttribute("user",userService.CurrantUser());
        return "AddJob";
    }
    @GetMapping("/job/{id}")
    public String ShowJob(@PathVariable long id ,Model model){
        model.addAttribute("job",userService.FindJob(id));
        model.addAttribute("user",userService.CurrantUser());
        return "job";
    }

    @PostMapping("/job")
    public String addNewJob(@ModelAttribute JobModel jobModel,Model model){
        ResponseEntity<String> response=companyService.addJob(jobModel);
        if (response.getStatusCode().is2xxSuccessful()) {
            return "redirect:/company/jobs";
        }
        else {
            model.addAttribute("message", response.getBody());
            model.addAttribute("jobmodel", jobModel);
            return "AddJob";
        }
    }
    @GetMapping("/jobs")
    public  String RetriveAllCompanyJob(Model model ){
        model.addAttribute("jobs",companyService.RetriveAllJobs());
        model.addAttribute("user",userService.CurrantUser());
        return "jobs";
    }
    @GetMapping("/delete/job/{id}")
    public String DeleteJob(@PathVariable Long id, Model model ){
        companyService.DeleteJob(id);
        return "redirect:/company/jobs";
    }
    @GetMapping("/update/job/{id}")
    public String EditeJob(@PathVariable Long id,Model model){
        Job job= userService.FindJob(id);
        model.addAttribute("jobModel",job);
        model.addAttribute("id",id);
        model.addAttribute("user",userService.CurrantUser());
        return "UpdateJob";
    }
    @PostMapping("/upadate/job/{id}")
    public String EditeJob(@PathVariable Long id,Model model,@ModelAttribute JobModel jobModel){
        ResponseEntity<String> response=companyService.EditeJob(id, jobModel);
        if (response.getStatusCode().is2xxSuccessful()) {
            model.addAttribute("message", response.getBody());
            model.addAttribute("job",userService.FindJob((long) id));
            model.addAttribute("user",userService.CurrantUser());
            return "job";
        }
        else {
            model.addAttribute("message", response.getBody());
            model.addAttribute("jobmodel", jobModel);
            return "UpdateJob";
        }
    }
    @GetMapping("/pause/job/{id}")
    public String pause(@PathVariable Long id,Model model){
       ResponseEntity<String> response= companyService.PauseJob(id);
        model.addAttribute("message", response.getBody());
        model.addAttribute("job",userService.FindJob((long) id));
        model.addAttribute("user",userService.CurrantUser());
        return "job" ;
    }
    @GetMapping("/resumes/job/{id}")
    public String retriveallJobresume(@PathVariable int id ,Model model) {

        model.addAttribute("resumes", companyService.retriveResumes(id).getBody());
        model.addAttribute("user", userService.CurrantUser());
        return "Resume";
    }
    @GetMapping("/profile/{userid}")
    public ResponseEntity<User> ViewUserProfile(@PathVariable Long userid){
        return companyService.ViewProfile(userid);
    }
    @PostMapping("/search")
    public String retriveCompanyJob(@ModelAttribute SearchModel searchModel,Model model){
        model.addAttribute("jobs",companyService.searchInCompany(searchModel));
        model.addAttribute("user",userService.CurrantUser());
        return "jobs";
    }

}
