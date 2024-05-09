package com.AbdoHalim.JobPortal.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchModel {
    private String title;
    private String country;
    private String city;
}
