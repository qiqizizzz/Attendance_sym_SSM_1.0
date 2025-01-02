package com.qiqizi.attendance_sym.respository;

import com.qiqizi.attendance_sym.pojo.Department;
import com.qiqizi.attendance_sym.pojo.DeptAdmin;
import com.qiqizi.attendance_sym.pojo.Employee;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@Mapper
public interface AdminMapper {
    /*
     *  定义AdminMapper接口
     *  定义SQL语句
     */

    //查询员工总数的SQL语句
    @Select("SELECT count(*) from employee")
    long countAllEmployee();

    //定义查询所有员工的SQL语句
    @Select("SELECT * from employee LIMIT #{limit} OFFSET #{offset}")
    @Results({
            @Result(property = "employee_id", column = "employee_id"),
            @Result(property = "employee_name", column = "employee_name"),
            @Result(property = "employee_username", column = "employee_username"),
            @Result(property = "password", column = "password"),
            @Result(property = "department", column = "department"),
            @Result(property = "gender", column = "gender"),
            @Result(property = "phone", column = "phone"),
            @Result(property = "role", column = "role")
    })
    List<Employee> findAllEmployee(int limit, int offset);

    //定义添加部门的SQL语句
    @Insert("INSERT INTO departments(department_id,department_name,remark) VALUES(#{department_id},#{departmentName},#{Remark})")
    int addDepartment(int department_id,String departmentName,String Remark);

    //定义添加员工的SQL语句
    @Insert("INSERT INTO employee(employee_id,employee_name,employee_username,password,department,gender,phone,role) VALUES(#{employee_id},#{employeeName},#{employeeUsername},#{password},#{department},#{gender},#{phone},#{role})")
    int addEmployee(int employee_id, String employeeName, String employeeUsername, String password, String department, String gender,String phone,String role );

    //定义添加部门管理员的SQL语句
    @Insert("INSERT INTO department_managers(manager_id,manager_name,manager_username,password,gender,department_name,phone) VALUES(#{manager_id},#{manager_name},#{manager_username},#{password},#{gender},#{department_name},#{phone})")
    int addDeptAdmin(DeptAdmin deptAdmin);

    //定义删除员工的SQL语句
    @Delete("DELETE FROM employee WHERE employee_id = #{employee_id}")
    int deleteEmployeeById(Integer employee_id);

    //定义删除部门管理员的SQL语句
    @Delete("DELETE FROM department_managers WHERE manager_id = #{manager_id}")
    int deleteDeptAdminById(Integer id);

    //定义删除部门的SQL语句
    @Delete("DELETE FROM departments WHERE department_id = #{department_id}")
    int deleteDepartmentById(Integer department_id);

    //定义更新员工信息的SQL语句
    @Update("UPDATE employee SET employee_name = #{employeeName},employee_username = #{employeeUsername},password = #{password},department = #{department},gender = #{gender},phone = #{phone},role = #{role} WHERE employee_id = #{employeeId}")
    int editEmployee(int employeeId, String employeeName, String employeeUsername, String password, String department, String gender, String phone, String role);

    //定义更新部门管理员信息的SQL语句
    @Update("UPDATE department_managers SET manager_name = #{manager_name},manager_username = #{manager_username},password = #{password},gender = #{gender},department_name = #{department_name},phone = #{phone} WHERE manager_id = #{manager_id}")
    int updateDeptAdmin(DeptAdmin deptAdmin);

    //定义通过员工ID查询员工的SQL语句
    @Select("SELECT * FROM employee WHERE employee_id = #{employee_id}")
    Employee findById(Integer id);


    //查找所有部门的SQL语句
    @Select("SELECT * FROM departments LIMIT #{limit} OFFSET #{offset}")
    @Results({
            @Result(property = "department_id", column = "department_id"),
            @Result(property = "department_name", column = "department_name"),
            @Result(property = "remark", column = "remark")
    })
    List<Department> findAllDepartment(int limit, int offset);

    //查找部门总数的SQL语句
    @Select("SELECT count(*) FROM departments")
    long countAllDepartment();

    //更新部门信息的SQL语句
    @Update("UPDATE departments SET department_name = #{departmentName},remark = #{remark} WHERE department_id = #{departmentId}")
    int editDepartment(int departmentId, String departmentName, String remark);
}
