# BỘ KỊCH BẢN KIỂM THỬ CHI TIẾT (TEST CASES SPECIFICATION)
## DỰ ÁN: WEBSITE BÁN MỸ PHẨM COSMETICSSTORE
* **Môn học:** SOF309 - Kiểm thử nâng cao cùng AI  
* **Sinh viên thực hiện:** Nguyễn Hoàng Trâm Anh | **MSSV:** TS03179 | **Lớp:** SD21304  
* **Áp dụng kỹ thuật kiểm thử:** Phân tích giá trị biên (BVA) & Phân vùng tương đương (EP)  

---

## 📌 PHẦN I. MA TRẬN THEO DÕI KIỂM THỬ (TRACEABILITY MATRIX)

| Mã Module | Tên Module Chức Năng | Số lượng Test Case | Positive (Hợp lệ) | Negative (Không hợp lệ/Biên) | Mức độ ưu tiên |
| :---: | :--- | :---: | :---: | :---: | :---: |
| **AUTH** | Xác thực & Phân quyền (Login, Register) | 14 | 4 | 10 | High (Smoke) |
| **PROD** | Quản lý Sản phẩm Mỹ phẩm (Admin CRUD) | 12 | 5 | 7 | High (Smoke / Regression) |
| **TỔNG CỘNG** | **Toàn bộ hệ thống** | **26** | **9** | **17** | **100% Pass** |

---

## 📌 PHẦN II. BẢNG CHI TIẾT KỊCH BẢN KIỂM THỬ (TEST CASES DETAILS)

### 1. MODULE XÁC THỰC: ĐĂNG NHẬP (AUTHENTICATION - LOGIN)

| Test Case ID | Tên ca kiểm thử | Loại test | Các bước thực hiện | Dữ liệu kiểm thử (Test Data) | Kết quả mong đợi | Nhóm Test |
| :---: | :--- | :---: | :--- | :--- | :--- | :---: |
| **TC_AUTH_01** | Đăng nhập thành công vai trò Khách hàng (Customer) | Positive | 1. Mở trang `/login`<br>2. Nhập Email khách hàng hợp lệ<br>3. Nhập mật khẩu đúng<br>4. Nhấp nút "Đăng nhập" | `email`: `customer@gmail.com`<br>`password`: `123456` | Lưu session role `CUSTOMER`, chuyển hướng sang trang chủ `/home`. | `smoke` |
| **TC_AUTH_02** | Đăng nhập thành công vai trò Quản trị viên (Admin) | Positive | 1. Mở trang `/login`<br>2. Nhập Email admin hợp lệ<br>3. Nhập mật khẩu đúng<br>4. Nhấp nút "Đăng nhập" | `email`: `admin@gmail.com`<br>`password`: `admin123` | Lưu session role `ADMIN`, chuyển hướng sang trang `/admin/dashboard`. | `smoke` |
| **TC_AUTH_03** | Đăng nhập thất bại khi bỏ trống cả Email và Mật khẩu | Negative | 1. Mở trang `/login`<br>2. Để trống cả 2 trường<br>3. Nhấp nút "Đăng nhập" | `email`: `""`<br>`password`: `""` | Không chuyển trang, hiển thị thông báo "Sai email hoặc mật khẩu không đúng". | `regression` |
| **TC_AUTH_04** | Đăng nhập thất bại khi bỏ trống Mật khẩu | Negative | 1. Mở trang `/login`<br>2. Nhập email hợp lệ<br>3. Bỏ trống ô mật khẩu<br>4. Nhấp nút "Đăng nhập" | `email`: `customer@gmail.com`<br>`password`: `""` | Giữ lại email đã nhập, hiển thị thông báo "Sai email hoặc mật khẩu không đúng". | `regression` |
| **TC_AUTH_05** | Đăng nhập thất bại khi bỏ trống Email | Negative | 1. Mở trang `/login`<br>2. Bỏ trống ô email<br>3. Nhập mật khẩu<br>4. Nhấp nút "Đăng nhập" | `email`: `""`<br>`password`: `123456` | Hiển thị thông báo "Sai email hoặc mật khẩu không đúng". | `regression` |
| **TC_AUTH_06** | Đăng nhập thất bại với Email không tồn tại | Negative | 1. Mở trang `/login`<br>2. Nhập email chưa từng đăng ký<br>3. Nhập mật khẩu bất kỳ<br>4. Nhấp nút "Đăng nhập" | `email`: `nonexistent_user_999@gmail.com`<br>`password`: `123456` | Không cho đăng nhập, thông báo lỗi xác thực trên form. | `regression` |
| **TC_AUTH_07** | Đăng nhập thất bại khi nhập sai Mật khẩu | Negative | 1. Mở trang `/login`<br>2. Nhập email tài khoản có thật<br>3. Nhập sai mật khẩu<br>4. Nhấp nút "Đăng nhập" | `email`: `customer@gmail.com`<br>`password`: `WrongPassword@2026` | Đăng nhập không thành công, hiển thị thông báo lỗi. | `smoke` |
| **TC_AUTH_08** | Kiểm tra chống tấn công SQL Injection vào ô Đăng nhập | Negative | 1. Mở trang `/login`<br>2. Nhập chuỗi SQL Injection vào ô email<br>3. Nhập chuỗi bất kỳ vào password<br>4. Nhấp nút "Đăng nhập" | `email`: `' OR '1'='1`<br>`password`: `' OR '1'='1` | Hệ thống xử lý an toàn qua PreparedStatement, báo sai tài khoản, không bị vượt quyền. | `regression` |

---

### 2. MODULE XÁC THỰC: ĐĂNG KÝ TÀI KHOẢN (AUTHENTICATION - REGISTER)

| Test Case ID | Tên ca kiểm thử | Loại test | Các bước thực hiện | Dữ liệu kiểm thử (Test Data) | Kết quả mong đợi | Nhóm Test |
| :---: | :--- | :---: | :--- | :--- | :--- | :---: |
| **TC_REG_01** | Đăng ký tài khoản mới thành công | Positive | 1. Mở trang `/register`<br>2. Nhập đầy đủ thông tin hợp lệ<br>3. Nhấp nút "Đăng ký" | `name`: `Nguyễn Hoàng Trâm Anh`<br>`email`: `tramanh_new@gmail.com`<br>`phone`: `0987654321`<br>`password`: `Pass@123` | Thêm bản ghi mới vào CSDL, chuyển hướng người dùng sang trang `/login`. | `smoke` |
| **TC_REG_02** | Đăng ký thất bại khi Email đã tồn tại | Negative | 1. Mở trang `/register`<br>2. Nhập email đã có trong bảng Customer<br>3. Điền các trường khác hợp lệ<br>4. Nhấp nút "Đăng ký" | `name`: `Test User`<br>`email`: `customer@gmail.com`<br>`phone`: `0912345678`<br>`password`: `Pass@123` | Không lưu CSDL, giữ nguyên trang đăng ký và báo lỗi "Email này đã được sử dụng". | `regression` |
| **TC_REG_03** | Đăng ký thất bại khi bỏ trống Họ và tên | Negative | 1. Mở trang `/register`<br>2. Bỏ trống ô Họ tên<br>3. Điền các ô còn lại hợp lệ<br>4. Nhấp nút "Đăng ký" | `name`: `""`<br>`email`: `user_noname@gmail.com`<br>`phone`: `0912345678`<br>`password`: `Pass@123` | Form yêu cầu điền đầy đủ họ tên, không tiến hành submit. | `regression` |
| **TC_REG_04** | Đăng ký thất bại khi bỏ trống Email | Negative | 1. Mở trang `/register`<br>2. Bỏ trống ô Email<br>3. Điền các thông tin khác<br>4. Nhấp nút "Đăng ký" | `name`: `Trần Văn A`<br>`email`: `""`<br>`phone`: `0912345678`<br>`password`: `Pass@123` | Form hiển thị cảnh báo yêu cầu nhập Email. | `regression` |
| **TC_REG_05** | Đăng ký thất bại khi bỏ trống Mật khẩu | Negative | 1. Mở trang `/register`<br>2. Điền tên, email, phone<br>3. Bỏ trống ô mật khẩu<br>4. Nhấp nút "Đăng ký" | `name`: `Lê Thị B`<br>`email`: `lethib@gmail.com`<br>`phone`: `0912345678`<br>`password`: `""` | Form hiển thị cảnh báo yêu cầu nhập Mật khẩu. | `regression` |
| **TC_REG_06** | Đăng ký thất bại khi Số điện thoại sai định dạng | Negative | 1. Mở trang `/register`<br>2. Nhập số điện thoại chứa chữ hoặc ít hơn 10 chữ số<br>3. Nhấp nút "Đăng ký" | `name`: `Phạm C`<br>`email`: `phamc@gmail.com`<br>`phone`: `098ABC`<br>`password`: `Pass@123` | Báo lỗi định dạng số điện thoại không hợp lệ. | `regression` |

---

### 3. MODULE QUẢN TRỊ SẢN PHẨM (ADMIN PRODUCT CRUD)

| Test Case ID | Tên ca kiểm thử | Loại test | Các bước thực hiện | Dữ liệu kiểm thử (Test Data) | Kết quả mong đợi | Nhóm Test |
| :---: | :--- | :---: | :--- | :--- | :--- | :---: |
| **TC_PROD_01** | Hiển thị danh sách sản phẩm quản trị | Positive | 1. Đăng nhập quyền Admin<br>2. Truy cập `/admin/product`<br>3. Kiểm tra bảng hiển thị | Tài khoản Admin hợp lệ | Tải thành công bảng danh sách sản phẩm, đầy đủ các cột ID, Tên, Giá, Số lượng. | `smoke` |
| **TC_PROD_02** | Thêm mới sản phẩm thành công với thông tin hợp lệ | Positive | 1. Mở form `/admin/product?action=new`<br>2. Điền đầy đủ thông tin hợp lệ<br>3. Nhấp nút "Lưu / Thêm mới" | `productName`: `Son Kem Lì Black Rouge Air Fit`<br>`price`: `185000`<br>`stockQuantity`: `50`<br>`categoryID`: `1`<br>`image`: `son_blackrouge.png`<br>`description`: `Son lì mịn môi giữ màu lâu trôi` | Lưu bản ghi vào CSDL, chuyển hướng về `/admin/product`, sản phẩm mới xuất hiện trên bảng. | `smoke` |
| **TC_PROD_03** | Thêm mới thất bại khi để trống Tên sản phẩm | Negative | 1. Mở form thêm mới<br>2. Bỏ trống ô Tên sản phẩm<br>3. Điền giá, số lượng, loại hợp lệ<br>4. Nhấp nút "Lưu" | `productName`: `""`<br>`price`: `150000`<br>`stockQuantity`: `20`<br>`categoryID`: `1` | Ném ngoại lệ hoặc báo lỗi: "Tên sản phẩm không được để trống.", giữ lại dữ liệu trên form. | `regression` |
| **TC_PROD_04** | Thêm mới thất bại khi để trống Giá sản phẩm | Negative | 1. Mở form thêm mới<br>2. Nhập tên sản phẩm<br>3. Bỏ trống ô Giá<br>4. Nhấp nút "Lưu" | `productName`: `Kem Chống Nắng Anessa`<br>`price`: `""`<br>`stockQuantity`: `30`<br>`categoryID`: `2` | Báo lỗi: "Vui lòng nhập giá.", không lưu vào CSDL. | `regression` |
| **TC_PROD_05** | Thêm mới thất bại khi Giá bán bằng 0 (Biên giá dưới) | Negative | 1. Mở form thêm mới<br>2. Nhập tên sản phẩm<br>3. Nhập giá bằng 0<br>4. Nhấp nút "Lưu" | `productName`: `Nước Tẩy Trang Bioderma 500ml`<br>`price`: `0`<br>`stockQuantity`: `10`<br>`categoryID`: `3` | Ném ngoại lệ: "Giá phải lớn hơn 0.", không thêm vào CSDL. | `regression` |
| **TC_PROD_06** | Thêm mới thất bại khi Giá bán là số âm (Giá trị biên vi phạm) | Negative | 1. Mở form thêm mới<br>2. Nhập giá mang giá trị âm<br>3. Nhấp nút "Lưu" | `productName`: `Sữa Rửa Mặt CeraVe`<br>`price`: `-50000`<br>`stockQuantity`: `15`<br>`categoryID`: `3` | Ném ngoại lệ: "Giá phải lớn hơn 0.", không lưu vào CSDL. | `regression` |
| **TC_PROD_07** | Thêm mới thất bại khi Giá bán nhập chữ ký tự không hợp lệ | Negative | 1. Mở form thêm mới<br>2. Nhập ký tự chữ vào ô giá<br>3. Nhấp nút "Lưu" | `productName`: `Toner Hoa Cúc Kiehl's`<br>`price`: `abc_xyz`<br>`stockQuantity`: `10`<br>`categoryID`: `2` | Bắt `NumberFormatException`, thông báo lỗi: "Dữ liệu số không hợp lệ.". | `regression` |
| **TC_PROD_08** | Thêm mới thất bại khi để trống Số lượng tồn kho | Negative | 1. Mở form thêm mới<br>2. Bỏ trống ô Số lượng tồn kho<br>3. Nhấp nút "Lưu" | `productName`: `Mặt Nạ Giấy Innisfree Trà Xanh`<br>`price`: `25000`<br>`stockQuantity`: `""`<br>`categoryID`: `4` | Báo lỗi: "Vui lòng nhập số lượng.". | `regression` |
| **TC_PROD_09** | Thêm mới thất bại khi Số lượng tồn kho là số âm (Biên số lượng vi phạm) | Negative | 1. Mở form thêm mới<br>2. Nhập số lượng âm<br>3. Nhấp nút "Lưu" | `productName`: `Phấn Phủ Eglips Blur Powder`<br>`price`: `130000`<br>`stockQuantity`: `-1`<br>`categoryID`: `1` | Ném ngoại lệ: "Số lượng không được âm.", không lưu vào CSDL. | `regression` |
| **TC_PROD_10** | Thêm mới thất bại khi không chọn Loại sản phẩm (Category) | Negative | 1. Mở form thêm mới<br>2. Nhập tên, giá, số lượng hợp lệ<br>3. Không chọn danh mục loại<br>4. Nhấp nút "Lưu" | `productName`: `Xịt Khoáng Avene 300ml`<br>`price`: `280000`<br>`stockQuantity`: `40`<br>`categoryID`: `""` | Báo lỗi: "Vui lòng chọn loại sản phẩm.". | `regression` |
| **TC_PROD_11** | Cập nhật thông tin sản phẩm thành công | Positive | 1. Chọn sản phẩm có sẵn<br>2. Bấm Sửa (`action=edit`)<br>3. Sửa giá và tên sản phẩm<br>4. Nhấp nút "Lưu" | `productID`: `1`<br>`productName`: `Son Black Rouge Version 8 (Updated)`<br>`price`: `195000`<br>`stockQuantity`: `45` | Cập nhật thành công vào CSDL, bảng hiển thị dữ liệu mới. | `regression` |
| **TC_PROD_12** | Xóa sản phẩm thành công | Positive | 1. Truy cập danh sách sản phẩm<br>2. Bấm icon/nút Xóa tại sản phẩm mục tiêu<br>3. Xác nhận xóa | `action=delete&productID=...` | Bản ghi bị xóa khỏi CSDL, không còn hiển thị trên danh sách sản phẩm. | `regression` |
