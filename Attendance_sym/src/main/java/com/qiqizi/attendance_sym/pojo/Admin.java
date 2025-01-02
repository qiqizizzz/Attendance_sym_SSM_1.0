package com.qiqizi.attendance_sym.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
    private int admin_id;
    private String admin_Name;
    private String admin_username;
    private String Password;
}
