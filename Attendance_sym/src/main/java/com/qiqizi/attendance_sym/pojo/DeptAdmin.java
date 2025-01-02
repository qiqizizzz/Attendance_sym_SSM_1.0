package com.qiqizi.attendance_sym.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeptAdmin {
    private int manager_id;
    private String manager_name;
    private String manager_username;
    private String password;
    private String gender;
    private int department_name;
    private String phone;
}
