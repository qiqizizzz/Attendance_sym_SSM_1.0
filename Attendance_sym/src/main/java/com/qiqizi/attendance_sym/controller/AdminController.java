package com.qiqizi.attendance_sym.controller;

import com.qiqizi.attendance_sym.pojo.Department;
import com.qiqizi.attendance_sym.pojo.DeptAdmin;
import com.qiqizi.attendance_sym.pojo.Employee;
import com.qiqizi.attendance_sym.pojo.Records;
import com.qiqizi.attendance_sym.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    // 管理员工页面
    @GetMapping("/adminEmployee")
    public String adminEmployee(HttpServletRequest request, Model model,
                                @RequestParam(value = "page", defaultValue = "1") Integer page,
                                @RequestParam(value = "size", defaultValue = "10") Integer size) {
        Employee employee = (Employee) request.getSession().getAttribute("Employee");
        String username = employee.getEmployee_username();

        // 校验页码范围，避免无效页码
        page = (page < 1) ? 1 : page;

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(page - 1, size);  // page 从 0 开始

        // 分页查询考勤记录
        Page<Employee> employeePage;
        employeePage=adminService.findEmployee(pageable);

        if(employeePage.isEmpty())
        {
            model.addAttribute("NoEmployee", "No employee found");
            System.out.println("没有员工");
        }

        model.addAttribute("employeeList", employeePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", employeePage.getTotalPages());
        model.addAttribute("size", size);
        model.addAttribute("username", username);
        return "adminEmployee";
    }

    // 管理员页面添加员工
    @PostMapping("/adminEmployee/add")
    public ResponseEntity<?> addEmployee(@RequestParam("employee_id") String employeeId,
                                         @RequestParam("employee_name") String employeeName,
                                         @RequestParam("employee_username") String employeeUsername,
                                         @RequestParam("password") String password,
                                         @RequestParam("department") String department,
                                         @RequestParam("gender") String gender,
                                         @RequestParam("phone") String phone,
                                         @RequestParam("role") String role) {
        int employee_id = Integer.parseInt(employeeId);
        boolean success = adminService.addEmployee(employee_id, employeeName, employeeUsername, password, department, gender, phone, role);
        if (success) {
            return ResponseEntity.ok("{\"success\": true, \"message\": \"Employee added successfully\"}");
        } else {
            return ResponseEntity.status(400).body("{\"success\": false, \"message\": \"Employee already exists\"}");
        }
    }

    // 管理员页面编辑员工
    @PutMapping("/adminEmployee/edit/{employee_id}")
    public ResponseEntity<?> editEmployee(@PathVariable("employee_id") String employeeId,
                                          @RequestParam("employee_name") String employeeName,
                                          @RequestParam("employee_username") String employeeUsername,
                                          @RequestParam("password") String password,
                                          @RequestParam("department") String department,
                                          @RequestParam("gender") String gender,
                                          @RequestParam("phone") String phone,
                                          @RequestParam("role") String role) {

        int employee_id = Integer.parseInt(employeeId);
        boolean success = adminService.editEmployee(employee_id, employeeName, employeeUsername, password, department, gender, phone, role);
        if (success) {
            return ResponseEntity.ok("{\"success\": true, \"message\": \"Employee edited successfully\"}");
        } else {
            return ResponseEntity.status(400).body("{\"success\": false, \"message\": \"Employee not found\"}");
        }
    }


    // 管理员页面删除员工
    @DeleteMapping("/adminEmployee/delete/{employee_id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("employee_id") String employeeId) {
        int employee_id = Integer.parseInt(employeeId);
        boolean success = adminService.deleteEmployeeById(employee_id);
        if (success) {
            return ResponseEntity.ok("{\"success\": true, \"message\": \"Employee deleted successfully\"}");
        } else {
            return ResponseEntity.status(404).body("{\"success\": false, \"message\": \"Employee not found\"}");
        }
    }

    //管理部门页面
    @GetMapping("/adminDepartment")
    public String adminDepartment(HttpServletRequest request, Model model,
                                  @RequestParam(value = "page", defaultValue = "1") Integer page,
                                  @RequestParam(value = "size", defaultValue = "10") Integer size) {
        Employee employee = (Employee) request.getSession().getAttribute("Employee");
        String username = employee.getEmployee_username();
        model.addAttribute("username", username);
        model.addAttribute("employee", employee);

        // 校验页码范围，避免无效页码
        page = (page < 1) ? 1 : page;

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(page - 1, size);  // page 从 0 开始

        // 分页查询考勤记录
        Page<Department> departmentPage;
        departmentPage=adminService.findDepartment(pageable);

        if(departmentPage.isEmpty())
        {
            model.addAttribute("NoDepartment", "No department found");
            System.out.println("没有部门");
        }

        model.addAttribute("departmentList", departmentPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", departmentPage.getTotalPages());
        model.addAttribute("size", size);


        return "adminDepartment";
    }

    // 管理员页面添加部门
    @PostMapping("/adminDepartment/add")
    public ResponseEntity<?> addDepartment(@RequestParam("department_id") String departmentId,
                                           @RequestParam("department_name") String departmentName,
                                           @RequestParam("remark") String Remark) {
        int department_id = Integer.parseInt(departmentId);
        boolean success = adminService.addDepartment(department_id,departmentName, Remark);
        if (success) {
            return ResponseEntity.ok("{\"success\": true, \"message\": \"Department added successfully\"}");
        } else {
            return ResponseEntity.status(400).body("{\"success\": false, \"message\": \"Department already exists\"}");
        }
    }



    // 管理员页面编辑部门
    @PutMapping("/adminDepartment/edit/{department_id}")
    public ResponseEntity<?> editDepartment(@PathVariable("department_id") String departmentId,
                                            @RequestParam("department_name") String departmentName,
                                            @RequestParam("remark") String Remark) {

        int department_id = Integer.parseInt(departmentId);
        boolean success = adminService.editDepartment(department_id, departmentName, Remark);
        if (success) {
            return ResponseEntity.ok("{\"success\": true, \"message\": \"Department edited successfully\"}");
        } else {
            return ResponseEntity.status(400).body("{\"success\": false, \"message\": \"Department not found\"}");
        }
    }

    // 管理员页面删除部门
    @DeleteMapping("/adminDepartment/delete/{department_id}")
    public ResponseEntity<?> deleteDepartment(@PathVariable("department_id") String departmentId) {
        int department_id = Integer.parseInt(departmentId);
        boolean success = adminService.deleteDepartmentById(department_id);
        if (success) {
            return ResponseEntity.ok("{\"success\": true, \"message\": \"Department deleted successfully\"}");
        } else {
            return ResponseEntity.status(404).body("{\"success\": false, \"message\": \"Department not found\"}");
        }
    }
    /**
     * 增加部门管理员
     * @param deptAdmin 部门管理员信息
     * @return 操作结果
     */
    @PostMapping("/deptAdmin")
    public ResponseEntity<String> addDeptAdmin(@RequestBody DeptAdmin deptAdmin) {
        boolean success = adminService.addDeptAdmin(deptAdmin);
        if (success) {
            return ResponseEntity.ok("Department Admin added successfully");
        } else {
            return ResponseEntity.status(400).body("Department Admin already exists");
        }
    }

    /**
     * 根据部门管理员ID删除部门管理员
     * @param id 部门管理员ID
     * @return 操作结果
     */
    @DeleteMapping("/deptAdmin/{id}")
    public ResponseEntity<String> deleteDeptAdmin(@PathVariable Integer id) {
        boolean success = adminService.deleteDeptAdminById(id);
        if (success) {
            return ResponseEntity.ok("Department Admin deleted successfully");
        } else {
            return ResponseEntity.status(404).body("Department Admin not found");
        }
    }



    /**
     * 更新部门管理员信息
     * @param deptAdmin 部门管理员信息
     * @return 操作结果
     */
    @PutMapping("/deptAdmin")
    public ResponseEntity<String> updateDeptAdmin(@RequestBody DeptAdmin deptAdmin) {
        boolean success = adminService.updateDeptAdmin(deptAdmin);
        if (success) {
            return ResponseEntity.ok("Department Admin added successfully");
        } else {
            return ResponseEntity.status(400).body("Department Admin already exists");
        }
    }


}
