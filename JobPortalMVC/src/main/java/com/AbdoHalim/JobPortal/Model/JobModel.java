package com.AbdoHalim.JobPortal.Model;

import com.AbdoHalim.JobPortal.Entity.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobModel {
    private String title;
    private String description;
    private String qualifications;
    private String country;
    private String city;
    private String benefits;
    private String responsibilities;
}
