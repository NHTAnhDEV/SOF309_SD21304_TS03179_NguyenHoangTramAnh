# Nhật ký AI: Xây dựng khung kiểm thử Selenium theo mô hình Page Object Model (POM)

* **Giai đoạn:** Tuần 3 (16/11 - 22/11/2026)  
* **Sinh viên thực hiện:** Nguyễn Hoàng Trâm Anh  
* **Mã số sinh viên:** TS03179  
* **Lớp:** SD21304  
* **Mục tiêu nghiệm thu:** Hoàn thành Yêu cầu Y4 - Phần 1 (Khung Selenium POM) & Y6 (Minh chứng AI Điểm A)  

---

## 1. Ngữ cảnh & Mục tiêu
- **Ngữ cảnh:** Hệ thống Website Bán Mỹ Phẩm *CosmeticsStore* có các trang giao diện người dùng:
  - Trang Đăng nhập (`/views/auth/login.jsp`)
  - Trang Đăng ký tài khoản (`/views/auth/register.jsp`)
  - Trang Quản trị Sản phẩm (`/views/admin/products/form.jsp` và `list-sp.jsp`)
- **Mục tiêu:** Áp dụng mô hình chuẩn công nghiệp **Page Object Model (POM)** để phân tách rành mạch:
  - Thư mục `pages/`: Quản lý các bộ định vị phần tử (Locators) và các hàm hành động của trang.
  - Thư mục `tests/`: Chứa kịch bản kiểm thử điều phối luồng và assert kết quả.
  - Tối ưu hóa xử lý bất đồng bộ DOM bằng **`WebDriverWait`**, nghiêm cấm lạm dụng `Thread.sleep()`.

---

## 2. Câu lệnh đã gửi cho AI (Prompt)

> **Prompt:**  
> *"Dưới đây là mã nguồn HTML/JSP của các trang trong dự án Web Mỹ phẩm CosmeticsStore của tôi:*  
> *1. Trang login.jsp: gồm input name='email', input name='password', button type='submit', và thẻ div class='error-message'.*  
> *2. Trang register.jsp: gồm input name='name', input name='email', input name='phone', input name='password', button submit, div class='message error'.*  
> *3. Trang form.jsp (admin): gồm input name='productName', name='price', name='stockQuantity', select name='categoryID', button submit, alert-danger.*  
>  
> *Hãy đóng vai trò Kỹ sư Tự động hóa Kiểm thử (SDET):*  
> *1. Thiết kế các class Page Object chuẩn: `LoginPage.java`, `RegisterPage.java`, `ProductPage.java` trong package `com.poly.sof309.ui.pages`. Yêu cầu sử dụng `WebDriverWait` và `ExpectedConditions` cho toàn bộ thao tác click/sendKeys/getText để chống lỗi StaleElementReferenceException hoặc ElementNotInteractableException.*  
> *2. Viết class kịch bản `LoginTest.java`, `RegisterTest.java` và `CrudTest.java` trong package `com.poly.sof309.ui.tests` bằng JUnit 5. Cấu hình hỗ trợ cả chạy mở trình duyệt Chrome ở local và chạy Chrome Headless (`--headless=new`) khi chạy trên môi trường GitHub Actions CI."*

---

## 3. Kết quả do AI sinh ra (Raw Output)
AI đã thiết kế:
- Cấu trúc 3 class Page Object: `LoginPage`, `RegisterPage`, `ProductPage` với các By locators tương ứng và cơ chế đợi tường minh qua `WebDriverWait(driver, Duration.ofSeconds(10))`.
- Cấu trúc 3 class Test: `LoginTest`, `RegisterTest` và `CrudTest` với các hàm `@BeforeEach` khởi tạo `ChromeDriver`, cấu hình cờ headless tự động khi nhận diện biến môi trường `CI`, và `@AfterEach` gọi `driver.quit()`.
- Các ca test UI: Kiểm tra đăng nhập khách hàng thành công, đăng nhập sai hiển thị thông báo lỗi, đăng ký trùng email báo lỗi, kiểm tra điều hướng qua lại giữa các trang, và kiểm tra hiển thị bảng/thêm mới sản phẩm trong trang quản trị Admin.

---

## 4. Chỉnh sửa của Sinh viên & Lý do sửa (Quan trọng để đạt điểm A)

Sau khi kiểm nghiệm thực tế trên trình duyệt và mã nguồn dự án, sinh viên đã tiến hành các cải tiến quan trọng:
1. **Khắc phục Locator sai trong template cũ:** File template mẫu ban đầu dùng `By.id("username")` và `By.id("password")`, nhưng giao diện thật của web dùng `By.name("email")` và `By.name("password")`. Sinh viên đã sửa triệt để các By locator trong `LoginPage.java` để khớp đúng với `login.jsp`.
2. **Loại bỏ hoàn toàn cơ chế chờ tĩnh (`Thread.sleep`):** Toàn bộ các thao tác `click()` và `sendKeys()` đều được bọc trong `wait.until(ExpectedConditions.visibilityOfElementLocated(...))` và `ExpectedConditions.elementToBeClickable(...)`. Điều này giúp test chạy mượt mà, không bị lãng phí thời gian chờ cố định và tránh lỗi văng ngoại lệ khi trang web tải chậm.
3. **Thêm phương thức kiểm tra an toàn `isErrorMessageDisplayed()`:** Sinh viên bổ sung khối `try-catch` trong hàm kiểm tra hiển thị thông báo lỗi để tránh trường hợp `TimeoutException` khi không có thông báo lỗi xuất hiện trên màn hình.
4. **Bổ sung đầy đủ các trường mới trên form đăng ký:** Thêm trường `phone` (`By.name("phone")`) vào `RegisterPage.java` do mã nguồn `register.jsp` của web yêu cầu cả số điện thoại khi đăng ký tài khoản.
