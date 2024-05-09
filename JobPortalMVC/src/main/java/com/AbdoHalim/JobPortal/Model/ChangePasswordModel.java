package com.AbdoHalim.JobPortal.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordModel {
    private String oldPassword;
    private String newPassword;
}
