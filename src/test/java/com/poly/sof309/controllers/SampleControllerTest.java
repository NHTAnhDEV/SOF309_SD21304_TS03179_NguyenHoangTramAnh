package com.poly.sof309.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class SampleControllerTest {

    // Hàm giả lập kiểm tra đăng nhập (mô phỏng logic của LoginController)
    private boolean authenticate(String email, String pass) {
        if (email == null || pass == null) return false;
        if (email.trim().isEmpty() || pass.trim().isEmpty()) return false;
        
        // Khách hàng mẫu hoặc Admin mẫu
        boolean isCustomer = "customer@gmail.com".equalsIgnoreCase(email.trim()) && "123456".equals(pass);
        boolean isAdmin = "admin@gmail.com".equalsIgnoreCase(email.trim()) && "admin123".equals(pass);
        
        return isCustomer || isAdmin;
    }

    @Test
    @DisplayName("TC_AUTH_01: Đăng nhập thành công với tài khoản khách hàng hợp lệ")
    public void testLoginSuccessCustomer() {
        boolean isValid = authenticate("customer@gmail.com", "123456");
        assertTrue(isValid, "Đăng nhập phải thành công với tài khoản đúng");
    }

    @Test
    @DisplayName("TC_AUTH_02: Đăng nhập thất bại khi để trống mật khẩu")
    public void testLoginEmptyPassword() {
        boolean isValid = authenticate("customer@gmail.com", "");
        assertFalse(isValid, "Đăng nhập phải thất bại khi mật khẩu trống");
    }

    @ParameterizedTest
    @DisplayName("TC_AUTH_PARAM: Kiểm tra đăng nhập với nhiều bộ dữ liệu biên")
    @CsvSource({
        "'', '123456', false, 'Email rỗng phải đăng nhập thất bại'",
        "'customer@gmail.com', '', false, 'Mật khẩu rỗng phải đăng nhập thất bại'",
        "'', '', false, 'Cả hai trường rỗng phải thất bại'",
        "'wrong_user@gmail.com', '123456', false, 'Tài khoản không tồn tại phải thất bại'",
        "'customer@gmail.com', 'wrong_pass', false, 'Sai mật khẩu phải thất bại'",
        "'customer@gmail.com', '123456', true, 'Khách hàng đúng email và pass phải thành công'",
        "'admin@gmail.com', 'admin123', true, 'Admin đúng email và pass phải thành công'"
    })
    public void testLoginParameterized(String email, String password, boolean expected, String message) {
        boolean actual = authenticate(email, password);
        assertEquals(expected, actual, message);
    }

    @Test
    @Timeout(value = 2, unit = TimeUnit.SECONDS)
    @DisplayName("TC_AUTH_TIMEOUT: Xác thực tài khoản phải phản hồi dưới 2 giây")
    public void testLoginTimeout() {
        boolean result = authenticate("customer@gmail.com", "123456");
        assertTrue(result);
    }
}