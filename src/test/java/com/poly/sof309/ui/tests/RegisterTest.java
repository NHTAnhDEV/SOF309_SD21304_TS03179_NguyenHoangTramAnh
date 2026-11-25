package com.poly.sof309.ui.tests;

import com.poly.sof309.ui.pages.RegisterPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * =========================================================================
 * [Y4 - TUẦN 3] KỊCH BẢN KIỂM THỬ GIAO DIỆN ĐĂNG KÝ (SELENIUM POM)
 * =========================================================================
 */
public class RegisterTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private RegisterPage registerPage;
    private final String REGISTER_URL = System.getProperty("baseUrl", "http://localhost:8080") + "/register";

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        if (Boolean.parseBoolean(System.getenv("CI")) || System.getProperty("headless") != null) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
        }

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        registerPage = new RegisterPage(driver);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @DisplayName("TC_REG_UI_01: Đăng ký thất bại khi Email đã tồn tại trong hệ thống")
    public void testRegisterDuplicateEmailUI() {
        driver.get(REGISTER_URL);
        
        // Nhập email đã tồn tại sẵn
        registerPage.register("Nguyễn Hoàng Trâm Anh", "customer@gmail.com", "0987654321", "Pass@123");

        // Kiểm tra thông báo lỗi hiển thị
        Assertions.assertTrue(registerPage.isErrorMessageDisplayed(),
                "Thông báo lỗi phải hiển thị khi email bị trùng");
        Assertions.assertTrue(registerPage.getErrorMessage().contains("Email này đã được sử dụng"),
                "Nội dung thông báo phải báo email đã được sử dụng");
    }

    @Test
    @DisplayName("TC_REG_UI_02: Điều hướng từ trang Đăng ký quay lại trang Đăng nhập")
    public void testNavigateBackToLoginUI() {
        driver.get(REGISTER_URL);
        registerPage.clickLoginLink();

        wait.until(ExpectedConditions.urlContains("/login"));
        String currentUrl = driver.getCurrentUrl();
        Assertions.assertTrue(currentUrl.contains("/login"),
                "Nhấp vào 'Đăng nhập ngay' phải chuyển hướng sang trang /login");
    }
}
