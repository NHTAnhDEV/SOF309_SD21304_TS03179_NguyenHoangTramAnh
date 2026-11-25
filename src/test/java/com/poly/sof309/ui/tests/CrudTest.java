package com.poly.sof309.ui.tests;

import com.poly.sof309.ui.pages.LoginPage;
import com.poly.sof309.ui.pages.ProductPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * =========================================================================
 * [Y4 - TUẦN 3] KỊCH BẢN KIỂM THỬ GIAO DIỆN CRUD SẢN PHẨM (SELENIUM POM)
 * Kiểm tra hiển thị danh sách và thêm mới sản phẩm dưới quyền Admin
 * =========================================================================
 */
public class CrudTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private LoginPage loginPage;
    private ProductPage productPage;

    private final String ROOT_URL = System.getProperty("baseUrl", "http://localhost:8080");
    private final String LOGIN_URL = ROOT_URL + "/login";
    private final String ADMIN_PRODUCT_URL = ROOT_URL + "/admin/product";

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
        loginPage = new LoginPage(driver);
        productPage = new ProductPage(driver);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void loginAsAdmin() {
        driver.get(LOGIN_URL);
        loginPage.login("admin@gmail.com", "admin123");
        wait.until(ExpectedConditions.urlContains("/admin"));
    }

    @Test
    @DisplayName("TC_PROD_UI_01: Hiển thị danh sách sản phẩm trong trang quản trị Admin")
    public void testAdminProductListDisplayedUI() {
        loginAsAdmin();
        driver.get(ADMIN_PRODUCT_URL);

        Assertions.assertTrue(productPage.isProductTableDisplayed(),
                "Bảng danh sách sản phẩm phải hiển thị cho tài khoản Admin");
    }

    @Test
    @DisplayName("TC_PROD_UI_02: Thêm mới sản phẩm thành công qua giao diện Admin")
    public void testCreateProductSuccessUI() {
        loginAsAdmin();
        driver.get(ADMIN_PRODUCT_URL);
        productPage.clickAddNew();

        String newProductName = "Son Kem Test Automation " + System.currentTimeMillis();
        productPage.enterProductName(newProductName);
        productPage.enterPrice("185000");
        productPage.enterStockQuantity("50");
        productPage.enterDescription("Sản phẩm tạo tự động bởi Selenium POM");
        productPage.selectFirstAvailableCategory();
        productPage.clickSubmit();

        wait.until(ExpectedConditions.urlContains("/admin/product"));
        Assertions.assertTrue(productPage.isProductDisplayedInTable(newProductName),
                "Sản phẩm mới thêm phải xuất hiện trong bảng danh sách sản phẩm");
    }
}
