package com.poly.sof309.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * =========================================================================
 * [Y4 - TUẦN 3] PAGE OBJECT MODEL (POM) CHO TRANG QUẢN TRỊ SẢN PHẨM
 * Quản lý các Locator và thao tác trên form thêm mới/sửa và bảng sản phẩm
 * =========================================================================
 */
public class ProductPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // 1. Định nghĩa các Locator trên trang form.jsp và list-sp.jsp
    private By txtProductName = By.name("productName");
    private By txtPrice = By.name("price");
    private By txtDiscountPrice = By.name("discountPrice");
    private By txtStockQuantity = By.name("stockQuantity");
    private By txtDescription = By.name("description");
    private By selCategory = By.name("categoryID");
    private By btnSubmit = By.cssSelector("button[type='submit']");
    private By lblAlertError = By.cssSelector(".alert.alert-danger");
    private By btnAddNew = By.cssSelector("a[href*='action=new']");
    private By tblProducts = By.cssSelector("table");

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // 2. Các hành động nghiệp vụ
    public void enterProductName(String name) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(txtProductName));
        el.clear();
        el.sendKeys(name);
    }

    public void enterPrice(String price) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(txtPrice));
        el.clear();
        el.sendKeys(price);
    }

    public void enterStockQuantity(String stock) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(txtStockQuantity));
        el.clear();
        el.sendKeys(stock);
    }

    public void enterDescription(String description) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(txtDescription));
        el.clear();
        el.sendKeys(description);
    }

    public void selectCategory(String categoryId) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(selCategory));
        Select select = new Select(el);
        select.selectByValue(categoryId);
    }

    public void selectFirstAvailableCategory() {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(selCategory));
        Select select = new Select(el);
        if (select.getOptions().size() > 1) {
            select.selectByIndex(1);
        }
    }

    public void clickSubmit() {
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(btnSubmit));
        el.click();
    }

    public void fillProductForm(String name, String price, String stock, String categoryId) {
        enterProductName(name);
        enterPrice(price);
        enterStockQuantity(stock);
        if (categoryId != null && !categoryId.isEmpty()) {
            selectCategory(categoryId);
        }
        clickSubmit();
    }

    public String getAlertError() {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(lblAlertError));
        return el.getText().trim();
    }

    public boolean isAlertErrorDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(lblAlertError)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickAddNew() {
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(btnAddNew));
        el.click();
    }

    public boolean isProductTableDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(tblProducts)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isProductDisplayedInTable(String productName) {
        WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(tblProducts));
        return table.getText().contains(productName);
    }
}
