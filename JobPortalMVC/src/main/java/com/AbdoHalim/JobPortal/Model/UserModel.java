package com.AbdoHalim.JobPortal.Model;

import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
    private String name;
    private String email;
    private String title;
    private String jobPreference;
    private String description;
    private String password;
    private String phone;
    private String role;
}
