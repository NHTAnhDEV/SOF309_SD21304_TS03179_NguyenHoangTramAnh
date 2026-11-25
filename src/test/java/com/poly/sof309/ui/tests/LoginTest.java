package com.poly.sof309.ui.tests;

import com.poly.sof309.ui.pages.LoginPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * =========================================================================
 * [Y4 - TUẦN 3] KỊCH BẢN KIỂM THỬ GIAO DIỆN ĐĂNG NHẬP (SELENIUM POM)
 * Hỗ trợ tự động chuyển sang Chrome Headless khi chạy trên GitHub Actions (CI)
 * =========================================================================
 */
public class LoginTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private LoginPage loginPage;
    private final String BASE_URL = System.getProperty("baseUrl", "http://localhost:8080") + "/login";

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        
        // Tự động kích hoạt Headless mode khi chạy trên CI/CD GitHub Actions
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
        loginPage = new LoginPage(driver);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @DisplayName("TC_AUTH_UI_01: Đăng nhập thành công với tài khoản Khách hàng hợp lệ")
    public void testLoginCustomerSuccessUI() {
        driver.get(BASE_URL);
        loginPage.login("customer@gmail.com", "123456");

        // Chờ chuyển hướng sang trang chủ /home sau khi đăng nhập thành công
        wait.until(ExpectedConditions.urlContains("/home"));
        String currentUrl = driver.getCurrentUrl();
        Assertions.assertTrue(currentUrl.contains("/home"),
                "Đăng nhập thành công phải chuyển hướng sang trang chủ /home");
    }

    @Test
    @DisplayName("TC_AUTH_UI_02: Đăng nhập thất bại khi nhập sai mật khẩu")
    public void testLoginFailWrongPasswordUI() {
        driver.get(BASE_URL);
        loginPage.login("customer@gmail.com", "WrongPassword@2026");
        
        // Kiểm tra thông báo lỗi xuất hiện trên màn hình
        Assertions.assertTrue(loginPage.isErrorMessageDisplayed(),
                "Thông báo lỗi phải hiển thị khi đăng nhập sai");
        Assertions.assertTrue(loginPage.getErrorMessage().contains("Sai email hoặc mật khẩu không đúng"),
                "Nội dung thông báo lỗi phải đúng chuẩn");
    }

    @Test
    @DisplayName("TC_AUTH_UI_03: Điều hướng từ trang Đăng nhập sang trang Đăng ký")
    public void testNavigateToRegisterUI() {
        driver.get(BASE_URL);
        loginPage.clickRegisterLink();

        wait.until(ExpectedConditions.urlContains("/register"));
        String currentUrl = driver.getCurrentUrl();
        Assertions.assertTrue(currentUrl.contains("/register"),
                "Nhấp vào 'Đăng ký ngay' phải chuyển hướng sang trang /register");
    }
}
