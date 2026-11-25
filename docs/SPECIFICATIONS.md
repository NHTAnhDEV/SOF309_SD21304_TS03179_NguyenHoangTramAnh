# ĐẶC TẢ NGHIỆP VỤ WEB CÁ NHÂN (SPECIFICATIONS)
> **Dự án:** Website Thương Mại Điện Tử Bán Mỹ Phẩm - CosmeticsStore  
> **Sinh viên thực hiện:** Nguyễn Hoàng Trâm Anh  
> **Mã số sinh viên (MSSV):** TS03179  
> **Mã lớp:** SD21304  
> **Môn học:** SOF309 - Kiểm thử nâng cao cùng AI  
> **Giảng viên hướng dẫn:** ThS. Trần Quang Bình (binhtq7 - Thầy Mười Giáo)  

---

## 1. THÔNG TIN DỰ ÁN
* **Tên đề tài:** Hệ Thống Website Bán Mỹ Phẩm Trực Tuyến (CosmeticsStore)
* **Kiến trúc hệ thống:** MVC (Model - View - Controller), kết hợp DAO Pattern và JDBC/Hibernate.
* **Ngôn ngữ & Nền tảng:** Java 17 / 21, Jakarta EE 10 (Jakarta Servlet 6.0, JSP, JSTL 3.0).
* **Cơ sở dữ liệu:** Microsoft SQL Server 2019/2022 (Database: `CosmeticStore`).
* **Framework Kiểm thử:** JUnit 5 (Unit Test Controller), Selenium WebDriver 4 (UI Automation Test), TestNG (Test Suite, Groups, DataProvider, ITestListener).
* **CI/CD:** GitHub Actions (Ubuntu, Chrome Headless, Maven).
* **URL chạy local:** `http://localhost:8080` (hoặc `http://localhost:8080/CosmeticsStore`)

---

## 2. KIẾN TRÚC VÀ CÁC THÀNH PHẦN CHÍNH
* **Packages Backend:**
  * `com.cosmeticstore.controller.auth`: Xử lý xác thực người dùng (`LoginController`, `RegisterController`, `LogoutServlet`).
  * `com.cosmeticstore.controller.admin`: Quản trị hệ thống (`ProductServlet`, `AdminDashboardServlet`, `UserController`, `OrderServlet`, `NewsServlet`).
  * `com.cosmeticstore.dao`: Tương tác cơ sở dữ liệu (`CustomerDAO`, `AccountDAO`, `ProductDao`, `CategoryDao`, `CartDao`).
  * `com.cosmeticstore.utils`: Tiện ích kết nối CSDL và xử lý dữ liệu (`JdbcUtil`, `DateUtil`, `FileUtil`, `ParamUtil`).
  * `entity`: Lớp thực thể (`Customer`, `User`, `Product`, `Category`, `Orders`, `Cart`).
* **Giao diện Frontend (`src/main/webapp`):**
  * `/views/auth/`: Màn hình Đăng nhập (`login.jsp`), Đăng ký (`register.jsp`).
  * `/views/admin/products/`: Danh sách sản phẩm (`list-sp.jsp`), Form thêm mới & sửa (`form.jsp`).
  * `/views/customer/`: Giỏ hàng (`cart.jsp`), Thanh toán (`checkout.jsp`).

---

## 3. QUY TẮC NGHIỆP VỤ CHI TIẾT (BUSINESS RULES)

### 3.1. Chức năng Đăng nhập (`/login` - `LoginController`)
* **URL Controller:** `@WebServlet("/login")`
* **Phương thức GET:** Điều hướng hiển thị trang `/views/auth/login.jsp`.
* **Phương thức POST:** Tiếp nhận thông tin `email` và `password`.
* **Quy tắc kiểm tra:**
  1. Loại bỏ khoảng trắng đầu/cuối của `email` và `password`.
  2. **Đăng nhập Khách hàng:** Gọi `customerDAO.checkLogin(email, password)`. Nếu đúng:
     - Gán Session: `currentUser = customer`, `role = "CUSTOMER"`.
     - Chuyển hướng (Redirect): `/home`.
  3. **Đăng nhập Quản trị viên (Admin):** Gọi `accountDAO.login(email, password)`. Nếu đúng:
     - Gán Session: `currentUser = adminUser`, `role = "ADMIN"`.
     - Chuyển hướng (Redirect): `/admin/dashboard`.
  4. **Đăng nhập thất bại:** Nếu không khớp tài khoản Khách hàng hoặc Admin:
     - Lưu lại giá trị: `oldEmail = email`.
     - Đặt thông báo lỗi: `errorMessage = "Sai email hoặc mật khẩu không đúng"`.
     - Điều hướng (Forward): trở lại trang `/views/auth/login.jsp`.

### 3.2. Chức năng Đăng ký tài khoản (`/register` - `RegisterController`)
* **URL Controller:** `@WebServlet("/register")`
* **Phương thức GET:** Điều hướng hiển thị trang `/views/auth/register.jsp`.
* **Phương thức POST:** Tiếp nhận dữ liệu form gồm:
  * `name`: Họ và tên khách hàng (bắt buộc, không để trống).
  * `email`: Địa chỉ email đăng ký (định dạng hợp lệ, duy nhất trong hệ thống).
  * `phone`: Số điện thoại liên hệ (chuỗi ký tự số 10 chữ số).
  * `password`: Mật khẩu đăng nhập.
* **Quy tắc kiểm tra:**
  1. **Kiểm tra trùng lặp Email:** Thực hiện truy vấn `SELECT CustomerID FROM Customer WHERE Email = ?`. Nếu đã tồn tại tài khoản:
     - Đặt thông báo lỗi: `error = "Email này đã được sử dụng"`.
     - Forward trở lại `/views/auth/register.jsp`.
  2. **Thêm mới tài khoản:** Nếu Email chưa tồn tại:
     - Thực thi câu lệnh `INSERT INTO Customer (CustomerName, Phone, Address, Email, Password) VALUES (?, ?, N'Chưa cập nhật', ?, ?)`.
     - Nếu thành công (`rowsAffected > 0`): Chuyển hướng thẳng sang trang `/login`.

### 3.3. Chức năng Quản lý Sản phẩm (CRUD Product - `ProductServlet`)
* **URL Controller:** `@WebServlet({"/product", "/products", "/admin/product", "/admin/products", "/admin/product/new", "/admin/product/edit", "/admin/product/delete"})`
* **Các trường dữ liệu sản phẩm:**
  * `productID`: Mã định danh tự tăng (int).
  * `productName`: Tên sản phẩm mỹ phẩm (String, bắt buộc).
  * `price`: Giá bán (BigDecimal, bắt buộc, phải là số thực > 0).
  * `discountPrice`: Giá khuyến mãi (BigDecimal, tùy chọn, nếu có phải > 0 và < `price`).
  * `stockQuantity`: Số lượng tồn kho (int, bắt buộc, số nguyên >= 0).
  * `categoryID`: Mã loại mỹ phẩm (int, bắt buộc phải chọn danh mục hợp lệ).
  * `image`: Tên file ảnh đại diện sản phẩm (String).
  * `description`: Mô tả chi tiết sản phẩm (String).
* **Quy tắc kiểm tra & xử lý:**
  1. **Thêm mới (Create):** Khi tham số `productID` rỗng:
     - Bắt lỗi tên rỗng: Ném ngoại lệ *"Tên sản phẩm không được để trống."*.
     - Bắt lỗi giá rỗng hoặc âm: Ném ngoại lệ *"Vui lòng nhập giá."* hoặc *"Giá phải lớn hơn 0."*.
     - Bắt lỗi số lượng: Ném ngoại lệ *"Vui lòng nhập số lượng."* hoặc *"Số lượng không được âm."*.
     - Bắt lỗi danh mục: Ném ngoại lệ *"Vui lòng chọn loại sản phẩm."*.
     - Xử lý định dạng số sai: Báo lỗi *"Dữ liệu số không hợp lệ."*.
     - Thành công: Lưu vào CSDL qua `productDao.create(p)` và chuyển hướng về `/admin/product`.
  2. **Hiển thị danh sách (Read):** Lấy toàn bộ sản phẩm từ `productDao.findAll()` và hiển thị lên bảng `/views/admin/products/list-sp.jsp`.
  3. **Cập nhật (Update):** Khi `productID` có giá trị, thực hiện kiểm tra validation tương tự thêm mới, sau đó gọi `productDao.update(p)`.
  4. **Xóa (Delete):** Gọi `productDao.delete(id)` khi nhận yêu cầu `action=delete&productID=...`.

---

## 4. CHIẾN LƯỢC PHÂN VÙNG TƯƠNG ĐƯƠNG VÀ GIÁ TRỊ BIÊN CHO TEST AUTOMATION
* **Phân vùng giá trị cho Giá sản phẩm (`price`):**
  * Vùng không hợp lệ: `price <= 0`, `price` là chữ hoặc ký tự đặc biệt, để trống.
  * Vùng biên: `0.0`, `0.01`, `100000000`.
  * Vùng hợp lệ: `price > 0`.
* **Phân vùng cho Số lượng tồn (`stockQuantity`):**
  * Vùng không hợp lệ: `stockQuantity < 0`, số thập phân (`1.5`), chuỗi ký tự, để trống.
  * Vùng biên: `-1`, `0`, `1`, `99999`.
  * Vùng hợp lệ: `stockQuantity >= 0` (số nguyên).
* **Phân vùng cho Xác thực Đăng nhập:**
  * Hợp lệ: Email đúng định dạng + Mật khẩu khớp dữ liệu DB (Khách hàng hoặc Admin).
  * Không hợp lệ: Email để trống, Mật khẩu để trống, Email không tồn tại, Sai mật khẩu, Ký tự SQL Injection (`' OR '1'='1`).
