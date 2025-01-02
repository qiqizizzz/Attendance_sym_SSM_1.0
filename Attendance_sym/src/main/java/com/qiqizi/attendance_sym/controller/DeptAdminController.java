package com.qiqizi.attendance_sym.controller;


import com.qiqizi.attendance_sym.pojo.AttendanceStatistics;
import com.qiqizi.attendance_sym.pojo.Employee;
import com.qiqizi.attendance_sym.pojo.Records;
import com.qiqizi.attendance_sym.service.DeptAdminService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/deptAdmin")
public class DeptAdminController {
    @Autowired
    private DeptAdminService deptAdminService;

    // 部门考勤记录页面
    @GetMapping("/deptRecords")
    public String deptRecords(HttpServletRequest request, Model model,
                              @RequestParam(value = "page", defaultValue = "1") Integer page,
                              @RequestParam(value = "size", defaultValue = "10") Integer size,
                              @RequestParam(value = "startDate", required = false) String startDate,
                              @RequestParam(value = "endDate", required = false) String endDate) {
        // 获取部门名称
        Employee employee = (Employee) request.getSession().getAttribute("Employee");
        String username = (String) request.getSession().getAttribute("username");
        String department=employee.getDepartment();

        if (employee == null) {
            System.out.println("出现未登录情况");
        }


        // 校验页码范围，避免无效页码
        page = (page < 1) ? 1 : page;

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(page - 1, size);  // page 从 0 开始

        // 处理日期参数，构造日期范围（开始时间和结束时间）
        Timestamp startTimestamp = null;
        Timestamp endTimestamp = null;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        if (startDate != null && !startDate.isEmpty()) {
            // 转换 startDate 字符串为 LocalDateTime
            LocalDateTime startDateTime = LocalDateTime.parse(startDate, formatter);
            // 转换为 Timestamp
            startTimestamp = Timestamp.valueOf(startDateTime);
        }

        if (endDate != null && !endDate.isEmpty()) {
            // 转换 endDate 字符串为 LocalDateTime
            LocalDateTime endDateTime = LocalDateTime.parse(endDate, formatter);
            // 转换为 Timestamp
            endTimestamp = Timestamp.valueOf(endDateTime);
        }

        // 分页查询考勤记录
        Page<Records> recordsPage ;
        if(startTimestamp != null && endTimestamp != null)
        {
            //如果传入了日期参数，则按日期范围和部门查询
            recordsPage = deptAdminService.getAttendanceRecordsByDepartmentDateRange(department,startTimestamp,endTimestamp, pageable);
        }else {
            //如果没有传入日期参数，则按部门查询
            recordsPage = deptAdminService.getAttendanceRecordsByDepartment(department, pageable);
        }

        // 检查是否有考勤记录
        if (recordsPage.isEmpty()) {
            model.addAttribute("noRecords", "没有找到符合条件的考勤记录。");
            System.out.println("没有找到符合条件的考勤记录。");
        }

        // 封装数据到 model
        model.addAttribute("employee", employee);
        model.addAttribute("username", username);
        model.addAttribute("department", department);
        model.addAttribute("attendanceRecords", recordsPage.getContent());
        model.addAttribute("totalPages", recordsPage.getTotalPages());  // 总页数
        model.addAttribute("currentPage", page);  // 当前页
        model.addAttribute("size", size);

        //返回视图
        return "deptRecords";
    }


    // 增加考勤记录
    @PostMapping("deptRecords/add")
    public ResponseEntity<?> addAttendance(@RequestBody Records records) {

            int employeeId = records.getEmployee_id();
            Timestamp attendanceTime = records.getAttendance_time();
            String attendanceType = records.getAttendance_type();
            String leaveReason = records.getLeave_reason();

            boolean result = deptAdminService.addAttendanceRecord(employeeId, attendanceTime, attendanceType, leaveReason);
        if (result) {
            return ResponseEntity.ok(Map.of("success", true)); // 返回一个 JSON 对象
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("success", false)); // 返回失败的 JSON
        }

    }


    // 删除考勤记录
    @DeleteMapping("/deptRecords/delete/{deleteRecordId}/{deleteAttendanceTime}")
    public ResponseEntity<String> deleteAttendanceRecord(@PathVariable("deleteRecordId") String deleteRecordId, @PathVariable("deleteAttendanceTime") String deleteAttendanceTime) {
        Integer id=Integer.parseInt(deleteRecordId);
        Timestamp timestamp;
        try {
            // 假设传递的日期格式为 "yyyy-MM-dd HH:mm:ss"
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            java.util.Date date = sdf.parse(deleteAttendanceTime);
            timestamp = new java.sql.Timestamp(date.getTime());
        } catch (ParseException e) {
            return ResponseEntity.badRequest().body("时间格式错误");
        }
        boolean success = deptAdminService.deleteAttendanceRecordById(id, timestamp);
        if (success) {
            return ResponseEntity.ok("Attendance record deleted successfully");
        } else {
            return ResponseEntity.status(404).body("Attendance record not found");
        }
    }

    // 更新考勤记录
    @PutMapping("/deptRecords/update/{employeeId}/{attendanceTime}")
    public ResponseEntity<String> updateAttendanceRecord(@PathVariable("employeeId") String employeeId, @PathVariable("attendanceTime") String attendanceTime, @RequestBody Records record) {
        int id=Integer.parseInt(employeeId);
        Timestamp timestamp;
        try {
            // 假设传递的日期格式为 "yyyy-MM-dd HH:mm:ss"
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            java.util.Date date = sdf.parse(attendanceTime);
            timestamp = new java.sql.Timestamp(date.getTime());
        } catch (ParseException e) {
            return ResponseEntity.badRequest().body("时间格式错误");
        }
        boolean success = deptAdminService.updateAttendanceRecord(id, timestamp, record);
        if (success) {
            return ResponseEntity.ok("Attendance record updated successfully");
        } else {
            return ResponseEntity.status(404).body("Attendance record not found");
        }
    }

    /**
     * 查询指定时间段的考勤信息
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 考勤记录列表
     */
    @GetMapping("/attendance")
    public ResponseEntity<List<Records>> getAttendanceByPeriod(@RequestParam String startDate, @RequestParam String endDate) {
        List<Records> records = deptAdminService.getAttendanceByPeriod(startDate, endDate);
        return ResponseEntity.ok(records);
    }

    /**
     * 按月统计考勤数据
     * @param month 月份
     * @return 考勤统计数据
     */
    @GetMapping("/attendance/stats/month/{month}")
    public ResponseEntity<AttendanceStatistics> getMonthlyAttendanceStats(@PathVariable String month) {
        AttendanceStatistics stats = deptAdminService.getMonthlyAttendanceStats(month);
        return ResponseEntity.ok(stats);
    }
}
