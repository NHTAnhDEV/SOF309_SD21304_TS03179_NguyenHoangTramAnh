package com.poly.sof309.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * =========================================================================
 * [Y4 - TUẦN 3] PAGE OBJECT MODEL (POM) CHO TRANG ĐĂNG NHẬP
 * Quản lý các Locator và hành động người dùng trên trang login.jsp
 * Sử dụng WebDriverWait xử lý bất đồng bộ, không dùng Thread.sleep()
 * =========================================================================
 */
public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // 1. Định nghĩa các Locator khớp chính xác với giao diện views/auth/login.jsp
    private By txtEmail = By.name("email");
    private By txtPassword = By.name("password");
    private By btnLogin = By.cssSelector("button[type='submit']");
    private By lblErrorMessage = By.cssSelector(".error-message");
    private By lnkRegister = By.cssSelector(".footer-links a");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // 2. Các hành động nghiệp vụ tương tác với trang Đăng nhập
    public void enterEmail(String email) {
        WebElement emailElement = wait.until(ExpectedConditions.visibilityOfElementLocated(txtEmail));
        emailElement.clear();
        emailElement.sendKeys(email);
    }

    public void enterPassword(String password) {
        WebElement passElement = wait.until(ExpectedConditions.visibilityOfElementLocated(txtPassword));
        passElement.clear();
        passElement.sendKeys(password);
    }

    public void clickLogin() {
        WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(btnLogin));
        loginBtn.click();
    }

    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLogin();
    }

    public String getErrorMessage() {
        WebElement errorElem = wait.until(ExpectedConditions.visibilityOfElementLocated(lblErrorMessage));
        return errorElem.getText().trim();
    }

    public boolean isErrorMessageDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(lblErrorMessage)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickRegisterLink() {
        WebElement registerBtn = wait.until(ExpectedConditions.elementToBeClickable(lnkRegister));
        registerBtn.click();
    }
}
