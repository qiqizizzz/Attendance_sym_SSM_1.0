package com.qiqizi.attendance_sym.controller;


import com.qiqizi.attendance_sym.pojo.Employee;
import com.qiqizi.attendance_sym.pojo.Records;
import com.qiqizi.attendance_sym.service.EmployeeService;
import com.qiqizi.attendance_sym.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping
//@CrossOrigin(origins = "http://localhost:63342")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private EmployeeService employeeService;

    //登陆
    @GetMapping("/login")
    public String loginPage() {
        return "login";  // 返回登录页面的视图名，假设有一个 login.html 页面
    }

    // 登录验证
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password, HttpServletRequest request, Model model) {
        Employee employee = userService.validateUser(username, password);
        if (employee != null) {
            String role = employee.getRole();
            int id=employee.getEmployee_id();
            // 登录成功，存入 session
            request.getSession().setAttribute("Employee", employee);  // 存入 Employee 对象
            request.getSession().setAttribute("role", role);
            request.getSession().setAttribute("username", username); // 存入 username
            request.getSession().setAttribute("id", id); // 存入 id
            return ResponseEntity.ok(employee.getRole());  // 返回角色作为响应
        } else {
            model.addAttribute("error", "Invalid username or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");  // 登录失败
        }
    }

    // 注册
    @GetMapping("/register")
    public String registerPage() {
        return "register";  // 返回注册页面的视图名，假设有一个 register.html 页面
    }

    // 注册验证
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestParam("employee_id") String employee_id,
                                      @RequestParam("employee_name") String employee_name,
                                      @RequestParam("employee_username") String username,
                                      @RequestParam("password") String password1,
                                      @RequestParam("confirm_password")  String password2,
                                      @RequestParam("department") String department,
                                      @RequestParam("gender") String gender,
                                      @RequestParam("phone") String phone,
                                      @RequestParam("role") String role,HttpServletRequest request, Model model) {
        int id=Integer.parseInt(employee_id);
        boolean success= employeeService.registerEmployee(id,employee_name,username,password1,department,gender,phone,role);
        if (success) {
            return ResponseEntity.ok("注册成功！");
        } else {
            model.addAttribute("error", "注册失败！");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("注册失败！");
        }
    }

    // 员工首页
    @GetMapping("/employee")
    public String employeeDashboard(HttpServletRequest request, Model model) {
        // 从 session 获取 Employee 对象
        Employee employee = (Employee) request.getSession().getAttribute("Employee");
        String role = (String) request.getSession().getAttribute("role");
        String username = (String) request.getSession().getAttribute("username");
        int id =  (int)request.getSession().getAttribute("id");
        if (employee != null && "员工".equals(role)) {
            // 添加到模型中
            model.addAttribute("username", username);
            model.addAttribute("role", role);
            model.addAttribute("id", id);
            return "employee"; // 返回员工首页视图
        } else {
            return "redirect:/login"; // 如果 session 中没有用户信息，跳转到登录页
        }
    }

    // 打卡
    @PostMapping("/employee")
    public ResponseEntity<?> addAttendanceRecord(HttpServletRequest request, Model model, @RequestBody Records records){
        int id = (int) request.getSession().getAttribute("id");
        model.addAttribute("id", id);
        if (id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid employee id.");
        }

        boolean result=employeeService.addClockRecord(records.getEmployee_id(),records.getAttendance_time(),records.getAttendance_type());
        // 返回结果
        if(result){
            return ResponseEntity.ok("打卡成功！");
        }
        else{
            return ResponseEntity.ok("打卡失败！");
        }
    }

    // 部门管理员首页
    @GetMapping("/deptAdmin")
    public String departmentDashboard(HttpServletRequest request, Model model) {
        // 从 session 获取 Employee 对象
        Employee employee = (Employee) request.getSession().getAttribute("Employee");
        String role = (String) request.getSession().getAttribute("role");
        String username = (String) request.getSession().getAttribute("username");
        int id =  (int)request.getSession().getAttribute("id");
        String department=employee.getDepartment();
        if (employee != null && "部门管理员".equals(role)) {
            // 添加到模型中
            model.addAttribute("Employee",employee);
            model.addAttribute("username", username);
            model.addAttribute("role", role);
            model.addAttribute("id", id);
            model.addAttribute("department",department);
            return "deptAdmin"; // 返回部门管理员首页视图
        } else {
            return "redirect:/login"; // 如果 session 中没有用户信息，跳转到登录页
        }
    }

    // 管理员首页
    @GetMapping("/admin")
    public String adminDashboard(HttpServletRequest request, Model model) {
        // 从 session 获取 Employee 对象
        Employee employee = (Employee) request.getSession().getAttribute("Employee");
        String role = (String) request.getSession().getAttribute("role");
        String username = (String) request.getSession().getAttribute("username");
        int id =  (int)request.getSession().getAttribute("id");
        if (employee != null && "管理员".equals(role)) {
            // 添加到模型中
            model.addAttribute("username", username);
            model.addAttribute("role", role);
            model.addAttribute("id", id);
            return "admin"; // 返回管理员首页视图
        } else {
            return "redirect:/login"; // 如果 session 中没有用户信息，跳转到登录页
        }
    }
}
