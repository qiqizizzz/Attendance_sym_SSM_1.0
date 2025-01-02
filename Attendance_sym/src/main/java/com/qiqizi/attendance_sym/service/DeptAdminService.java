package com.qiqizi.attendance_sym.service;

import com.qiqizi.attendance_sym.pojo.AttendanceStatistics;
import com.qiqizi.attendance_sym.pojo.Records;
import com.qiqizi.attendance_sym.respository.DeptAdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageImpl;

import java.sql.Timestamp;
import java.util.List;

@Service
public class DeptAdminService {
    @Autowired
    private DeptAdminMapper deptAdminMapper;


    // 新增考勤记录
    public boolean addAttendanceRecord(int employee_id, Timestamp attendance_time, String attendance_type, String leave_reason) {
        return !(deptAdminMapper.insertAttendanceRecord(employee_id, attendance_time, attendance_type, leave_reason) <= 0);
    }


    // 删除考勤记录
    public boolean deleteAttendanceRecordById(Integer id,Timestamp time) {
        return deptAdminMapper.deleteAttendanceRecordById(id,time) > 0;
    }

    // 更新考勤记录
    public boolean updateAttendanceRecord(int id, Timestamp time,Records record) {
        return deptAdminMapper.updateAttendanceRecord(id,time,record) > 0;
    }

    // 查询考勤记录
    public List<Records> getAttendanceByPeriod(String startDate, String endDate) {
        return deptAdminMapper.selectAttendanceByPeriod(startDate, endDate);
    }

    // 查询月度考勤统计
    public AttendanceStatistics getMonthlyAttendanceStats(String month) {
        return deptAdminMapper.selectMonthlyAttendanceStats(month);
    }

    // 查询部门考勤记录
    public Page<Records> getAttendanceRecordsByDepartment(String department, Pageable pageable) {
        int limit = pageable.getPageSize();  // 每页记录数
        int offset = pageable.getPageNumber() * pageable.getPageSize();  // 从哪一条记录开始查询

        List<Records> records = deptAdminMapper.selectDeptRecordsByDepartment(department, limit, offset);
        long total = deptAdminMapper.selectDeptRecordsCount(department);

        return new PageImpl<>(records, pageable, total);
    }

    //按日期范围查询部门考勤记录
    public Page<Records> getAttendanceRecordsByDepartmentDateRange(String department, Timestamp startDateTime, Timestamp endDateTime, Pageable pageable) {
        List<Records> recordsList = deptAdminMapper.findByEmployeeIdAndAttendanceTimeBetween(department, startDateTime, endDateTime, pageable.getPageSize(), pageable.getPageNumber() * pageable.getPageSize());

        // 获取总记录数
        long totalRecords = recordsList.size(); // 你可以改为使用一个查询来获取总记录数

        // 返回 Page 对象，分页是由 PageImpl 创建的
        return new PageImpl<>(recordsList, pageable, totalRecords);
    }
}
