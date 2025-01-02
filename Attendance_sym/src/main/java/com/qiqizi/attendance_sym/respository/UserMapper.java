package com.qiqizi.attendance_sym.respository;

import com.qiqizi.attendance_sym.pojo.*;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM employee where employee_username=#{employee_username} and password=#{password}")
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
    Employee getEmployeeByNameAndPassword(@Param("employee_username") String employee_username, @Param("password")String password);
}
