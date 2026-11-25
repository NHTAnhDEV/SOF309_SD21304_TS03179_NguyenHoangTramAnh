package com.poly.sof309.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * =========================================================================
 * [Y4 - TUẦN 3] PAGE OBJECT MODEL (POM) CHO TRANG ĐĂNG KÝ
 * Quản lý các Locator và hành động người dùng trên trang register.jsp
 * Sử dụng WebDriverWait xử lý bất đồng bộ
 * =========================================================================
 */
public class RegisterPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // 1. Định nghĩa các Locator khớp chính xác với views/auth/register.jsp
    private By txtName = By.name("name");
    private By txtEmail = By.name("email");
    private By txtPhone = By.name("phone");
    private By txtPassword = By.name("password");
    private By btnRegister = By.cssSelector("button[type='submit']");
    private By lblError = By.cssSelector(".message.error");
    private By lblSuccess = By.cssSelector(".message.success");
    private By lnkLogin = By.cssSelector(".footer-links a");

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // 2. Các hành động nghiệp vụ
    public void enterName(String name) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(txtName));
        el.clear();
        el.sendKeys(name);
    }

    public void enterEmail(String email) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(txtEmail));
        el.clear();
        el.sendKeys(email);
    }

    public void enterPhone(String phone) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(txtPhone));
        el.clear();
        el.sendKeys(phone);
    }

    public void enterPassword(String password) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(txtPassword));
        el.clear();
        el.sendKeys(password);
    }

    public void clickRegister() {
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(btnRegister));
        el.click();
    }

    public void register(String name, String email, String phone, String password) {
        enterName(name);
        enterEmail(email);
        enterPhone(phone);
        enterPassword(password);
        clickRegister();
    }

    public String getErrorMessage() {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(lblError));
        return el.getText().trim();
    }

    public boolean isErrorMessageDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(lblError)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickLoginLink() {
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(lnkLogin));
        el.click();
    }
}
