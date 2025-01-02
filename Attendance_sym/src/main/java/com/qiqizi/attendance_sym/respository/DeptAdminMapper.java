package com.qiqizi.attendance_sym.respository;

import com.qiqizi.attendance_sym.pojo.AttendanceStatistics;
import com.qiqizi.attendance_sym.pojo.Records;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface DeptAdminMapper {
    // 插入考勤记录
    @Insert("INSERT INTO attendance_records (employee_id, attendance_time, attendance_type, leave_reason) VALUES (#{employee_id}, #{attendance_time}, #{attendance_type}, #{leave_reason})")
    @Results({
            @Result(property = "employee_id", column = "employee_id"),
            @Result(property = "attendance_time", column = "attendance_time"),
            @Result(property = "attendance_type", column = "attendance_type"),
            @Result(property = "leave_reason", column = "leave_reason")
    })
    int insertAttendanceRecord(int employee_id, Timestamp attendance_time, String attendance_type, String leave_reason);

    // 根据ID删除考勤记录
    @Delete("DELETE FROM attendance_records WHERE employee_id = #{id} and attendance_time = #{time}")
    int deleteAttendanceRecordById(Integer id,Timestamp time);

    // 更新考勤记录
    @Update("UPDATE attendance_records SET attendance_type = #{record.attendance_type}, leave_reason = #{record.leave_reason} WHERE employee_id = #{id} and attendance_time = #{time} ")
    int updateAttendanceRecord(int id,Timestamp time,Records record);

    // 根据时间段查询考勤记录
    @Select("SELECT * FROM attendance_records WHERE attendance_time BETWEEN #{startDate} AND #{endDate}")
    List<Records> selectAttendanceByPeriod(@Param("startDate") String startDate, @Param("endDate") String endDate);

    // 根据月份统计考勤数据
    @Select("SELECT attendance_type, COUNT(*) as total FROM attendance_records WHERE MONTH(attendance_time) = #{month} GROUP BY attendance_type")
    AttendanceStatistics selectMonthlyAttendanceStats(String month);

    //获取部门考勤记录的总数
    @Select("SELECT COUNT(*) as total FROM attendance_records join employee on attendance_records.employee_id = employee.employee_id WHERE department = #{department}")
    long selectDeptRecordsCount(String department);

    // 根据部门查询考勤记录(支持分页）
    @Select("select * from attendance_records join employee on attendance_records.employee_id = employee.employee_id WHERE department = #{department} order by attendance_time asc , employee.employee_id asc LIMIT #{limit} OFFSET #{offset} ")
    @Results({
            @Result(property = "attendance_id", column = "attendance_id"),
            @Result(property = "employee_id", column = "employee_id"),
            @Result(property = "attendance_time", column = "attendance_time"),
            @Result(property = "attendance_type", column = "attendance_type"),
            @Result(property = "leave_reason", column = "leave_reason")
    })
    List<Records> selectDeptRecordsByDepartment( @Param("department")String department, @Param("limit")int limit, @Param("offset")int offset);


    //根据员工ID和考勤时间段查询考勤记录(分页)
    @Select("SELECT * FROM attendance_records join employee on attendance_records.employee_id = employee.employee_id WHERE department = #{department} AND attendance_time BETWEEN #{startDateTime} AND #{endDateTime} order by attendance_time asc , employee.employee_id asc LIMIT #{limit} OFFSET #{offset}")
    @Results({
            @Result(property = "attendance_id", column = "attendance_id"),
            @Result(property = "employee_id", column = "employee_id"),
            @Result(property = "attendance_time", column = "attendance_time"),
            @Result(property = "attendance_type", column = "attendance_type"),
            @Result(property = "leave_reason", column = "leave_reason")
    })
    List<Records> findByEmployeeIdAndAttendanceTimeBetween(String department, Timestamp startDateTime, Timestamp endDateTime, int limit, int offset);
}
