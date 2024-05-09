package com.AbdoHalim.JobPortal.Controller;

import com.AbdoHalim.JobPortal.Entity.Job;
import com.AbdoHalim.JobPortal.Model.SearchModel;
import com.AbdoHalim.JobPortal.Model.UserModel;
import com.AbdoHalim.JobPortal.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/public")
public class PublicController {
    private  UserService userService;

    public PublicController(UserService userService) {

        this.userService = userService;
    }
    @GetMapping("/home")
    public String inde( Model model){
        if(userService.CurrantUser().getRole().equals("APPLICANT"))
            return "redirect:/applicant/jobs";
        else
            return "redirect:/company/jobs";
    }
    @GetMapping("/register")
    public String index(){
        return "Register";
    }
    @PostMapping("/register" )
    public String registration(@ModelAttribute UserModel userModel, Model model) {
        ResponseEntity<String> response = userService.saveNewUser(userModel);
        if (response.getStatusCode().is2xxSuccessful()) {
            return "redirect:/public/login";
        } else {
            model.addAttribute("message", response.getBody());
            model.addAttribute("userModel", userModel);
            return "Register";
        }
    }
    @GetMapping("/login")
    public String ShowLogin(){
        return "Login";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/public/login";
    }



}
