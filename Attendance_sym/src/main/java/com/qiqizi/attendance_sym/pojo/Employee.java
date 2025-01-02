package com.qiqizi.attendance_sym.pojo;

public class Employee {
    private int employee_id;
    private String employee_name;
    private String employee_username;
    private String password;
    private String department;
    private String gender;
    private String phone;
    private String role;

    public Employee(int employee_id, String employee_name, String employee_username, String password, String department, String gender, String phone, String role) {
        this.employee_id = employee_id;
        this.employee_name = employee_name;
        this.employee_username = employee_username;
        this.password = password;
        this.department = department;
        this.gender = gender;
        this.phone = phone;
        this.role = role;
    }

    public Employee() {
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public String getEmployee_username() {
        return employee_username;
    }

    public void setEmployee_username(String employee_username) {
        this.employee_username = employee_username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employee_id=" + employee_id +
                ", employee_name='" + employee_name + '\'' +
                ", employee_username='" + employee_username + '\'' +
                ", password='" + password + '\'' +
                ", department='" + department + '\'' +
                ", gender='" + gender + '\'' +
                ", phone='" + phone + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}

