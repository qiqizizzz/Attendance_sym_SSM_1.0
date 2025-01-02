package com.qiqizi.attendance_sym.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.sql.Timestamp;

public class Records {
    private int attendance_id;
    private int employee_id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")  // 设置日期时间格式
    private Timestamp attendance_time;
    private String attendance_type;
    private String leave_reason;

    public Records(int attendance_id, int employee_id, Timestamp attendance_time, String attendance_type, String leave_reason) {
        this.attendance_id = attendance_id;
        this.employee_id = employee_id;
        this.attendance_time = attendance_time;
        this.attendance_type = attendance_type;
        this.leave_reason = leave_reason;
    }

    public Records() {
    }

    public int getAttendance_id() {
        return attendance_id;
    }

    public void setAttendance_id(int attendance_id) {
        this.attendance_id = attendance_id;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public Timestamp getAttendance_time() {
        return attendance_time;
    }

    public void setAttendance_time(Timestamp attendance_time) {
        this.attendance_time = attendance_time;
    }

    public String getAttendance_type() {
        return attendance_type;
    }

    public void setAttendance_type(String attendance_type) {
        this.attendance_type = attendance_type;
    }

    public String getLeave_reason() {
        return leave_reason;
    }

    public void setLeave_reason(String leave_reason) {
        this.leave_reason = leave_reason;
    }

    @Override
    public String toString() {
        return "Records{" +
                "attendance_id=" + attendance_id +
                ", employee_id=" + employee_id +
                ", attendance_time=" + attendance_time +
                ", attendance_type='" + attendance_type + '\'' +
                ", leave_reason='" + leave_reason + '\'' +
                '}';
    }
}
