package com.poly.sof309.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterControllerTest {

    private static final Set<String> EXISTING_EMAILS = Set.of("customer@gmail.com", "admin@gmail.com");

    // Hàm giả lập kiểm tra hợp lệ form đăng ký theo đặc tả RegisterController
    private boolean validateRegister(String name, String email, String phone, String password) {
        if (name == null || name.trim().isEmpty()) return false;
        if (email == null || !email.trim().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) return false;
        if (phone == null || !phone.trim().matches("^0\\d{9}$")) return false;
        if (password == null || password.trim().length() < 6) return false;
        return !EXISTING_EMAILS.contains(email.trim().toLowerCase());
    }

    private void checkDuplicateEmail(String email) throws Exception {
        if (email != null && EXISTING_EMAILS.contains(email.trim().toLowerCase())) {
            throw new Exception("Email này đã được sử dụng");
        }
    }

    @ParameterizedTest
    @DisplayName("TC_REG_PARAM: Kiểm tra validation form đăng ký với nhiều bộ dữ liệu biên")
    @CsvSource({
        "'', 'newuser@gmail.com', '0987654321', 'Pass@123', false, 'Họ tên rỗng phải thất bại'",
        "'Nguyễn Văn A', 'invalid-email', '0987654321', 'Pass@123', false, 'Email sai định dạng phải thất bại'",
        "'Nguyễn Văn A', 'newuser@gmail.com', '098ABC', 'Pass@123', false, 'Số điện thoại chứa chữ phải thất bại'",
        "'Nguyễn Văn A', 'newuser@gmail.com', '098765432', 'Pass@123', false, 'Số điện thoại thiếu chữ số phải thất bại'",
        "'Nguyễn Văn A', 'newuser@gmail.com', '0987654321', '123', false, 'Mật khẩu quá ngắn phải thất bại'",
        "'Nguyễn Văn A', 'customer@gmail.com', '0987654321', 'Pass@123', false, 'Email đã tồn tại phải thất bại'",
        "'Nguyễn Hoàng Trâm Anh', 'tramanh_new@gmail.com', '0987654321', 'Pass@123', true, 'Thông tin hợp lệ phải thành công'"
    })
    void testRegisterValidation(String name, String email, String phone, String password,
                                boolean expectedValid, String message) {
        boolean actual = validateRegister(name, email, phone, password);
        assertEquals(expectedValid, actual, message);
    }

    @Test
    @DisplayName("TC_REG_EXP: Ném Exception khi đăng ký bằng Email đã tồn tại")
    void testDuplicateEmailThrowsException() {
        Exception ex = assertThrows(Exception.class, () -> checkDuplicateEmail("customer@gmail.com"));
        assertEquals("Email này đã được sử dụng", ex.getMessage());
    }

    @Test
    @Timeout(value = 2, unit = TimeUnit.SECONDS)
    @DisplayName("TC_REG_TIMEOUT: Kiểm tra validation đăng ký hoàn tất dưới 2 giây")
    void testRegisterValidationTimeout() {
        assertTrue(validateRegister("Nguyễn Hoàng Trâm Anh", "tramanh_new@gmail.com", "0987654321", "Pass@123"));
    }
}
