package com.qiqizi.attendance_sym.controller;

import com.qiqizi.attendance_sym.pojo.Employee;
import com.qiqizi.attendance_sym.pojo.Records;
import com.qiqizi.attendance_sym.service.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.time.format.DateTimeFormatter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    //考勤记录页面
    @GetMapping("/employeeRecords")
    public String employeeRecords(HttpServletRequest request, Model model,
                                  @RequestParam(value = "page", defaultValue = "1") Integer page,
                                  @RequestParam(value = "size", defaultValue = "10") Integer size,
                                  @RequestParam(value = "startDate", required = false) String startDate,
                                  @RequestParam(value = "endDate", required = false) String endDate) {

        // 从 session 获取 Employee 信息
        Employee employee = (Employee) request.getSession().getAttribute("Employee");
        String username = (String) request.getSession().getAttribute("username");
        int id = (int) request.getSession().getAttribute("id");

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

        // 获取考勤记录分页数据，按日期筛选
        Page<Records> recordsPage;
        if (startTimestamp != null && endTimestamp != null) {
            // 如果传入了日期，按日期范围查询
            recordsPage = employeeService.getAttendanceRecordsByEmployeeIdAndDateRange(id, startTimestamp, endTimestamp, pageable);
        } else {
            // 如果没有传入日期，查询所有记录
            recordsPage = employeeService.getAttendanceRecordsByEmployeeId(id, pageable);
        }

        // 检查是否有考勤记录
        if (recordsPage.isEmpty()) {
            model.addAttribute("noRecords", "没有找到符合条件的考勤记录。");
        }

        // 将分页数据传递到前端
        model.addAttribute("employee", employee);
        model.addAttribute("username", username);
        model.addAttribute("id", id);
        model.addAttribute("attendanceRecords", recordsPage != null ? recordsPage.getContent() : Collections.emptyList());
        model.addAttribute("totalPages", recordsPage.getTotalPages());  // 总页数
        model.addAttribute("currentPage", page);  // 当前页
        model.addAttribute("size", size);

        return "employeeRecords";  // 返回视图
    }



    //请假申请界面
    @GetMapping("/leaveApply")
    public String leaveApply(HttpServletRequest request, Model model) {
        // 从 session 获取 Employee 信息
        Employee employee = (Employee) request.getSession().getAttribute("Employee");
        String username = (String) request.getSession().getAttribute("username");
        int id = (int) request.getSession().getAttribute("id");

        // 将 Employee 信息传递到前端
        model.addAttribute("employee", employee);
        model.addAttribute("username", username);
        model.addAttribute("id", id);
        return "leaveApply";  // 返回视图
    }

    @PostMapping("/leaveApply")
    public ResponseEntity<?> leaveApply(HttpServletRequest request, Model model, @RequestBody Records records){
        boolean result=employeeService.addAttendanceRecord(null,records.getEmployee_id(),records.getAttendance_time(),records.getAttendance_type(),records.getLeave_reason());
        // 返回结果
        if(result){
            return ResponseEntity.ok("请假申请成功！");
        }
        else{
            return ResponseEntity.ok("请假申请失败！");
        }
    }

    //加班记录页面
    @GetMapping("/overtime")
    public String overtimeApply(HttpServletRequest request, Model model) {
        return "overtime";  // 返回视图
    }

    //考勤统计页面
    @GetMapping("/attendanceStatistics")
    public String attendanceStatistics(HttpServletRequest request, Model model) {
        return "attendanceStatistics";  // 返回视图
    }

    //帮助与支持页面
    @GetMapping("/helpAndSupport")
    public String helpAndSupport(HttpServletRequest request, Model model) {
        // 从 session 获取 username
        String username = (String) request.getSession().getAttribute("username");
        model.addAttribute("username", username);
        return "helpAndSupport";  // 返回帮助与支持页面的视图名，假设有一个 helpAndSupport.html 页面
    }


}

