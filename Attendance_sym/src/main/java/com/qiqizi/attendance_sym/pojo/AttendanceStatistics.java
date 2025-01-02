package com.qiqizi.attendance_sym.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//封装考勤统计信息
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceStatistics {
    private String attendanceType;
    private Integer total;
}

