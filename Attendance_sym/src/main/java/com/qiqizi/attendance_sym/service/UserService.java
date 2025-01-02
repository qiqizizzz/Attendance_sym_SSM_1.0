package com.qiqizi.attendance_sym.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.qiqizi.attendance_sym.respository.UserMapper;
import com.qiqizi.attendance_sym.pojo.*;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public Employee validateUser(String username, String password)
    {
        return userMapper.getEmployeeByNameAndPassword(username, password);
    }
}
