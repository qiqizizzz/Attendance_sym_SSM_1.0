package com.qiqizi.attendance_sym.service;

import com.qiqizi.attendance_sym.pojo.Employee;
import com.qiqizi.attendance_sym.pojo.Records;
import com.qiqizi.attendance_sym.respository.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 查询考勤信息
     * @param id
     * @return
     */
    public String queryAttendance(int id) {
        return "查询到" + id + "的考勤信息";
    }


    /**
     * 添加请假记录
     * @param employee_id
     * @param attendance_time
     * @param attendance_type
     * @param leave_reason
     * @return
     */
    public boolean addAttendanceRecord(Integer attendance_id,int employee_id, Timestamp attendance_time, String attendance_type, String leave_reason) {
        boolean rowsAffected = employeeMapper.addAttendanceRecord(employee_id, attendance_time, attendance_type, leave_reason);
        return rowsAffected;
    }

    //增加打卡记录
    public boolean addClockRecord(Integer employee_id, Timestamp attendance_time,String attendance_type) {
        boolean rowsAffected = employeeMapper.addClockRecord(employee_id, attendance_time, attendance_type);
        return rowsAffected;
    }

    // 根据员工ID获取考勤记录（分页查询）
    public Page<Records> getAttendanceRecordsByEmployeeId(int id, Pageable pageable) {
        int limit = pageable.getPageSize();  // 每页记录数
        int offset = pageable.getPageNumber() * pageable.getPageSize();  // 从哪一条记录开始查询

        // 获取分页查询结果
        List<Records> records = employeeMapper.getRecordByEmployeeIdWithPagination(id, limit, offset);

        // 获取员工考勤记录总数
        long totalRecords = employeeMapper.getTotalRecordCount(id);

        // 返回分页数据
        return new PageImpl<>(records, pageable, totalRecords);
    }


    // 获取员工考勤记录，根据日期范围
    public Page<Records> getAttendanceRecordsByEmployeeIdAndDateRange(int id, Timestamp startDateTime, Timestamp endDateTime, Pageable pageable) {
        // 查询数据
        List<Records> recordsList = employeeMapper.findByEmployeeIdAndAttendanceTimeBetween(id, startDateTime, endDateTime, pageable.getPageSize(), pageable.getPageNumber() * pageable.getPageSize());

        // 获取总记录数
        long totalRecords = recordsList.size(); // 你可以改为使用一个查询来获取总记录数

        // 返回 Page 对象，分页是由 PageImpl 创建的
        return new PageImpl<>(recordsList, pageable, totalRecords);
    }

    // 注册员工
    public boolean registerEmployee(int id, String employee_name, String username, String password1, String department, String gender,String phone, String role) {
        boolean rowsAffected = employeeMapper.registerEmployee(id,employee_name,username,password1,department,gender,phone,role);
        return rowsAffected;
    }


}
