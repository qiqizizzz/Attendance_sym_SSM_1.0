package com.qiqizi.attendance_sym.respository;

import com.qiqizi.attendance_sym.pojo.Employee;
import com.qiqizi.attendance_sym.pojo.Records;
import org.apache.ibatis.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface EmployeeMapper {
    /*
     *  定义EmployeeMapper接口
     *  定义SQL语句
     */

    // 根据员工ID查询考勤记录，支持分页
    @Select("SELECT * FROM attendance_records WHERE employee_id = #{id} ORDER BY attendance_time asc LIMIT #{limit} OFFSET #{offset}")
    @Results({
            @Result(property = "attendance_id", column = "attendance_id"),
            @Result(property = "employee_id", column = "employee_id"),
            @Result(property = "attendance_time", column = "attendance_time"),
            @Result(property = "attendance_type", column = "attendance_type"),
            @Result(property = "leave_reason", column = "leave_reason")
    })
    List<Records> getRecordByEmployeeIdWithPagination(
            @Param("id") int id,
            @Param("limit") int limit,
            @Param("offset") int offset);

    // 获取员工考勤记录的总数
    @Select("SELECT COUNT(*) FROM attendance_records WHERE employee_id = #{id}")
    long getTotalRecordCount(@Param("id") int id);

    //增加请假记录
    @Insert("INSERT INTO attendance_records(employee_id, attendance_time, attendance_type,leave_reason) VALUES( #{employee_id},#{attendance_time}, #{attendance_type},#{leave_reason})")
    boolean addAttendanceRecord(int employee_id, Timestamp attendance_time, String attendance_type, String leave_reason);

    //增加打卡记录
    @Insert("INSERT INTO attendance_records(employee_id, attendance_time, attendance_type) VALUES( #{employee_id},#{attendance_time}, #{attendance_type})")
    boolean addClockRecord(int employee_id, Timestamp attendance_time, String attendance_type);


    //注册员工
    @Insert("INSERT INTO employee(employee_id, employee_name,employee_username,password,department,gender,phone, role) VALUES(#{id}, #{employee_name}, #{username}, #{password1}, #{department}, #{gender}, #{phone}, #{role})")
    boolean registerEmployee(int id, String employee_name, String username, String password1, String department, String gender, String phone, String role);

    //按日期查询员工考勤记录(分页)
    @Select("SELECT * FROM attendance_records WHERE employee_id = #{id} AND attendance_time BETWEEN #{startDateTime} AND #{endDateTime} ORDER BY attendance_time asc LIMIT #{limit} OFFSET #{offset} ")
    @Results({
            @Result(property = "attendance_id", column = "attendance_id"),
            @Result(property = "employee_id", column = "employee_id"),
            @Result(property = "attendance_time", column = "attendance_time"),
            @Result(property = "attendance_type", column = "attendance_type"),
            @Result(property = "leave_reason", column = "leave_reason")
    })
    List<Records> findByEmployeeIdAndAttendanceTimeBetween(int id, Timestamp startDateTime, Timestamp endDateTime, int limit, int offset);
}