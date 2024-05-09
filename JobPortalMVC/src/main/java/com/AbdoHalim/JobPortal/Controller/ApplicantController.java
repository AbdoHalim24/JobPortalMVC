package com.AbdoHalim.JobPortal.Controller;

import com.AbdoHalim.JobPortal.Entity.Job;
import com.AbdoHalim.JobPortal.Model.ChangePasswordModel;
import com.AbdoHalim.JobPortal.Model.SearchModel;
import com.AbdoHalim.JobPortal.Model.UserModel;
import com.AbdoHalim.JobPortal.Service.ApplicantService;
import com.AbdoHalim.JobPortal.Service.UserService;
import org.aspectj.bridge.IMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/applicant")
public class ApplicantController {
    private ApplicantService applicantService;
    private UserService userService;
    public ApplicantController(ApplicantService applicantService,UserService userService) {
        this.applicantService = applicantService;
        this.userService=userService;
    }


    @GetMapping("/job/{id}")
    public String showjob(@PathVariable int id,Model model){
        model.addAttribute("job",userService.FindJob((long) id));
        model.addAttribute("user",userService.CurrantUser());
        return "job";
    }
    //save job for future refrence
    @GetMapping("/savejob/{id}")
    public String futureRefrenace(@PathVariable int id, Model model, RedirectAttributes redirectAttributes){
         ResponseEntity<String> response=applicantService.savejob((long) id);
        model.addAttribute("message", response.getBody());
        model.addAttribute("job",userService.FindJob((long) id));
        model.addAttribute("user",userService.CurrantUser());
        return "job";
    }
    @GetMapping("/apply/job/{id}")
    public String applyForJob(@PathVariable Long id ,Model model,RedirectAttributes redirectAttributes){
        ResponseEntity<String> response= applicantService.ApplyForJob(id);
        model.addAttribute("message",response.getBody());
        redirectAttributes.addAttribute("id", id);
        model.addAttribute("job",userService.FindJob((long) id));
        model.addAttribute("user",userService.CurrantUser());
        return "job";
    }
    @GetMapping("/savedjobs")
    public String retrieveSavedJobs(Model model) {
        model.addAttribute("jobs", applicantService.RetriveSavedJobs());
        model.addAttribute("user", userService.CurrantUser());
        model.addAttribute("saved", "saved");
        return "jobs";
    }

    @GetMapping("/delete/saved/job/{id}")
    public String deleteSavedJob(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        String response = applicantService.deleteSavedJob(id);
        redirectAttributes.addFlashAttribute("message", response);
        return "redirect:/applicant/savedjobs";
    }

    @PostMapping("/search")
    public String searchForJob(@ModelAttribute SearchModel searchModel,Model model){
           ResponseEntity<List<Job>> response= userService.retriveJobs(searchModel,false);
        model.addAttribute("user",userService.CurrantUser());
        model.addAttribute("searchModel",searchModel);

        if (response.getStatusCode().is2xxSuccessful()) {
                List<Job> jobs = response.getBody();
                if (jobs != null && !jobs.isEmpty()) {
                    model.addAttribute("jobs", jobs);
                } else {
                    model.addAttribute("jobs", null); // Ensure jobs attribute is set to null if no jobs are available
                }
            } else {
                model.addAttribute("jobs", null); // Ensure jobs attribute is set to null in case of an error
                model.addAttribute("errorMessage", "An error occurred while retrieving job listings. Please try again later.");
            }
            return "jobs";
    }

    @GetMapping("/jobs")
    public String RetriveAllJobs(Model model){
        model.addAttribute("jobs",userService.retriveAllJobs());
        model.addAttribute("user",userService.CurrantUser());
        return "jobs";
    }
    @GetMapping("/company/jobs/{id}")
    public  String RetriveAllCompanyJob(@PathVariable Long id,Model model){
        model.addAttribute("jobs",userService.RetriveAllCompanyJobs(id));
        model.addAttribute("user",userService.CurrantUser());
        return "jobs";
    }


}
