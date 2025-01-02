package com.qiqizi.attendance_sym;

import com.qiqizi.attendance_sym.service.AdminService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AdminServiceTest {

    @Autowired
    private AdminService adminService;

    @Test
    public void testDeleteEmployeeById() {
        boolean result = adminService.deleteEmployeeById(1); // 假设ID为1的员工存在
        Assertions.assertTrue(result);
    }
}
