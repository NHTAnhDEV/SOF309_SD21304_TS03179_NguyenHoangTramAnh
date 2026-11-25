package com.poly.sof309.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class ProductControllerTest {

    // Hàm phụ trợ kiểm tra giá (mô phỏng logic ném ngoại lệ trong ProductServlet)
    private void validateProductPrice(double price) throws Exception {
        if (price <= 0) {
            throw new Exception("Giá phải lớn hơn 0.");
        }
    }

    // Hàm phụ trợ kiểm tra dữ liệu form sản phẩm
    private boolean validateProduct(String name, String priceStr, String stockStr) {
        if (name == null || name.trim().isEmpty()) return false;
        try {
            double price = Double.parseDouble(priceStr);
            if (price <= 0) return false;

            int stock = Integer.parseInt(stockStr);
            if (stock < 0) return false;

            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
        @ParameterizedTest
    @DisplayName("TC_PROD_VAL: Kiểm tra validation form sản phẩm với nhiều bộ dữ liệu biên")
    @CsvSource({
        "'', '150000', '10', false, 'Tên sản phẩm không được để trống'",
        "'Son Black Rouge', '0', '10', false, 'Giá bằng 0 không hợp lệ'",
        "'Son Black Rouge', '-50000', '10', false, 'Giá âm không hợp lệ'",
        "'Son Black Rouge', 'abc', '10', false, 'Giá chứa chữ không hợp lệ'",
        "'Son Black Rouge', '150000', '-1', false, 'Số lượng âm không hợp lệ'",
        "'Son Black Rouge', '150000', '0', true, 'Số lượng bằng 0 hợp lệ'",
        "'Son Black Rouge', '185000', '50', true, 'Dữ liệu hoàn toàn hợp lệ'"
    })
    void testProductValidation(String name, String priceStr, String stockStr, boolean expectedValid, String message) {
        boolean actualResult = validateProduct(name, priceStr, stockStr);
        assertEquals(expectedValid, actualResult, message);
    }
        @Test
    @DisplayName("TC_PROD_EXP_01: Ném Exception khi giá sản phẩm nhỏ hơn hoặc bằng 0")
    void testInvalidPriceThrowsException() {
        Exception exception = assertThrows(Exception.class, () -> {
            validateProductPrice(-10000);
        });
        assertTrue(exception.getMessage().contains("Giá phải lớn hơn 0."));
    }

    @Test
    @DisplayName("TC_PROD_EXP_02: Ném NumberFormatException khi nhập giá là chuỗi chữ")
    void testPriceNotNumberThrowsException() {
        assertThrows(NumberFormatException.class, () -> {
            Double.parseDouble("abc_price");
        });
    }
        @Test
    @Timeout(value = 2, unit = TimeUnit.SECONDS)
    @DisplayName("TC_PROD_PERF: Xử lý dữ liệu sản phẩm không vượt quá 2 giây")
    void testProductProcessingTimeout() {
        boolean result = validateProduct("Kem Chống Nắng", "250000", "100");
        assertTrue(result);
    }
}