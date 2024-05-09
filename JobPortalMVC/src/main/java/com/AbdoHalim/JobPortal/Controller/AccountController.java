package com.AbdoHalim.JobPortal.Controller;

import com.AbdoHalim.JobPortal.Entity.User;
import com.AbdoHalim.JobPortal.Model.ChangePasswordModel;
import com.AbdoHalim.JobPortal.Model.UserModel;
import com.AbdoHalim.JobPortal.Service.ApplicantService;
import com.AbdoHalim.JobPortal.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/account")
public class AccountController {
    private final UserService userService;

    public AccountController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile/{id}")
    public  String showAccount(@PathVariable Long id, Model model, HttpSession session){
        User user= userService.retriveUserById(id);
        String message = (String) session.getAttribute("message");
        model.addAttribute("message", message);
        if (user==null){
            model.addAttribute("message","User Not Found");
        }
        model.addAttribute("userModel",user);
        model.addAttribute("user",userService.CurrantUser());
        return "UserPage";
    }

    @PostMapping("/upload")
    public String uploadResume(@RequestParam("newResume") MultipartFile file,
                               RedirectAttributes redirectAttributes) {
        ResponseEntity<String> response = userService.uploadFile(file);
        redirectAttributes.addFlashAttribute("message", response.getBody());
        Long id = userService.CurrantUser().getUserId();
        return "redirect:/account/profile/" + id;
    }

    @PostMapping("/changepassword")
    public String changePassword(@RequestParam("oldPassword") String oldPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 RedirectAttributes redirectAttributes) {
        Logger logger = LoggerFactory.getLogger(LoggerFactory.class);
        logger.info("Changing password");
        ChangePasswordModel changePasswordModel = new ChangePasswordModel();
        changePasswordModel.setOldPassword(oldPassword);
        changePasswordModel.setNewPassword(newPassword);
        ResponseEntity<String> response = userService.ChangePassword(changePasswordModel);

        redirectAttributes.addFlashAttribute("message", response.getBody());
        Long id = userService.CurrantUser().getUserId();
        return "redirect:/account/profile/" + id;
    }
    @GetMapping("/updateinfo")
    public String UpdateUserInfo(Model model){
        model.addAttribute("userModel",userService.CurrantUser());
        return "UpdateInfo";
    }

    @PostMapping("/updateinfo")
    public String UpdateUserInfo(@ModelAttribute UserModel userModel,Model model){
        ResponseEntity<String> response= userService.UpdateInfo(userModel);
        if (response.getStatusCode().is2xxSuccessful()) {
            Long id = userService.CurrantUser().getUserId();
            return "redirect:/account/profile/" + id;
        } else {
            model.addAttribute("message", response.getBody());
            model.addAttribute("userModel", userModel);
            return "UpdateInfo";
        }
    }
}
