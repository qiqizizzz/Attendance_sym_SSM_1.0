package com.qiqizi.attendance_sym.service;

import com.qiqizi.attendance_sym.pojo.Department;
import com.qiqizi.attendance_sym.pojo.DeptAdmin;
import com.qiqizi.attendance_sym.pojo.Employee;
import com.qiqizi.attendance_sym.pojo.Records;
import com.qiqizi.attendance_sym.respository.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminMapper adminMapper;

    /**
     * 增加员工
     * @return 增加是否成功
     */

    public boolean addEmployee(int employee_id, String employeeName, String employeeUsername, String password, String department, String gender,String phone,String role ) {
        int rowsAffected = adminMapper.addEmployee(employee_id, employeeName, employeeUsername, password, department, gender, phone, role);
        return rowsAffected > 0;
    }

    /**
     * 增加部门管理员
     * @param DeptAdmin 部门管理员对象
     * @return 增加是否成功
     */
    public boolean addDeptAdmin(DeptAdmin DeptAdmin) {
        int rowsAffected = adminMapper.addDeptAdmin(DeptAdmin);
        return rowsAffected > 0;
    }

    /**
     * 增加部门
     * @return 增加是否成功
     */
    public boolean addDepartment(int department_id,String departmentName,String Remark) {
        int rowsAffected = adminMapper.addDepartment(department_id,departmentName, Remark);
        return rowsAffected > 0;
    }

    /**
     * 删除员工
     * @param employee_id 员工ID
     * @return 删除是否成功
     */
    public boolean deleteEmployeeById(Integer employee_id) {
        int rowsAffected = adminMapper.deleteEmployeeById(employee_id);
        return rowsAffected > 0;
    }

    /**
     * 删除部门管理员
     * @param id 部门管理员ID
     * @return 删除是否成功
     */
    public boolean deleteDeptAdminById(Integer id) {
        int rowsAffected = adminMapper.deleteDeptAdminById(id);
        return rowsAffected > 0;
    }


    /**
     * 删除部门
     * @param department_id 部门ID
     * @return 删除是否成功
     */
    public boolean deleteDepartmentById(Integer department_id) {
        int rowsAffected = adminMapper.deleteDepartmentById(department_id);
        return rowsAffected > 0;
    }


    /**
     * 修改部门管理员
     * @param DeptAdmin 部门管理员对象
     * @return 修改是否成功
     */
    public boolean updateDeptAdmin(DeptAdmin DeptAdmin) {
        int rowsAffected = adminMapper.updateDeptAdmin(DeptAdmin);
        return rowsAffected > 0;
    }



    //查找所有员工
    public Page<Employee> findEmployee(Pageable pageable) {
        int limit = pageable.getPageSize();  // 每页记录数
        int offset = pageable.getPageNumber() * pageable.getPageSize();  // 从哪一条记录开始查询

        List<Employee> employeeList = adminMapper.findAllEmployee(limit, offset);
        long total = adminMapper.countAllEmployee();

        return new PageImpl<>(employeeList, pageable, total);
    }


    //编辑员工信息
    public boolean editEmployee(int employeeId, String employeeName, String employeeUsername, String password, String department, String gender, String phone, String role) {
        int rowsAffected = adminMapper.editEmployee(employeeId, employeeName, employeeUsername, password, department, gender, phone, role);
        return rowsAffected > 0;
    }

    //实现分页查询部门
    public Page<Department> findDepartment(Pageable pageable) {
        int limit = pageable.getPageSize();  // 每页记录数
        int offset = pageable.getPageNumber() * pageable.getPageSize();  // 从哪一条记录开始查询
        List<Department> departmentList = adminMapper.findAllDepartment(limit, offset);
        long total = adminMapper.countAllDepartment();

        return new PageImpl<>(departmentList, pageable, total);
    }

    //编辑部门信息
    public boolean editDepartment(int departmentId, String departmentName, String remark) {
        int rowsAffected = adminMapper.editDepartment(departmentId, departmentName, remark);
        return rowsAffected > 0;
    }

}

