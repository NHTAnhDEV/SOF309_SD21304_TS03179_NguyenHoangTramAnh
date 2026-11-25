# Nhật ký AI: Viết Unit Test Controller với JUnit 5 nâng cao

* **Giai đoạn:** Tuần 2 (09/11 - 15/11/2026)  
* **Sinh viên thực hiện:** Nguyễn Hoàng Trâm Anh  
* **Mã số sinh viên:** TS03179  
* **Lớp:** SD21304  
* **Mục tiêu nghiệm thu:** Hoàn thành Yêu cầu Y3 (Unit Test Controller) & Y6 (Minh chứng AI Điểm A)  

---

## 1. Ngữ cảnh & Mục tiêu
- **Ngữ cảnh:** Dự án Web Mỹ phẩm *CosmeticsStore* có các Controller xử lý luồng xác thực (`LoginController`) và nghiệp vụ quản lý sản phẩm (`ProductServlet`).
- **Mục tiêu:** Sử dụng AI hỗ trợ xây dựng mã nguồn Unit Test bằng JUnit 5 nâng cao, áp dụng đầy đủ các kỹ thuật cốt lõi:
  1. Kiểm thử tham số hóa (`@ParameterizedTest` + `@CsvSource`) để kiểm tra dữ liệu biên form đăng nhập và sản phẩm.
  2. Kiểm thử ngoại lệ (`assertThrows`) để bắt các vi phạm nghiệp vụ (giá sản phẩm $\le 0$, sai định dạng số).
  3. Kiểm thử thời gian thực thi (`@Timeout`) đảm bảo hiệu năng xử lý dưới 2 giây.

---

## 2. Câu lệnh đã gửi cho AI (Prompt)

> **Prompt:**  
> *"Tôi đang làm dự án kiểm thử SOF309 cho Website Bán Mỹ Phẩm CosmeticsStore. Dưới đây là logic nghiệp vụ của tôi:*  
> *- Chức năng Đăng nhập (LoginController): kiểm tra email và password, phân quyền Customer (redirect /home) và Admin (redirect /admin/dashboard), sai thì báo lỗi.*  
> *- Chức năng Sản phẩm (ProductServlet): kiểm tra tên không được rỗng, giá phải lớn hơn 0 (ném Exception nếu <= 0), số lượng không được âm (ném Exception nếu < 0), giá nhập chữ văng NumberFormatException.*  
>  
> *Hãy hướng dẫn tôi viết mã nguồn Unit Test hoàn chỉnh bằng JUnit 5 nâng cao cho 2 class `ProductControllerTest` và `SampleControllerTest`. Yêu cầu có đầy đủ: @ParameterizedTest quét các giá trị biên form qua @CsvSource, assertThrows bắt ngoại lệ giá âm, và @Timeout dưới 2 giây."*

---

## 3. Kết quả do AI sinh ra (Raw Output)
AI đã đề xuất cấu trúc mã nguồn gồm:
- Các ca kiểm thử tham số hóa `@ParameterizedTest` với bảng dữ liệu `@CsvSource`.
- Các hàm kiểm thử ngoại lệ `assertThrows(Exception.class, ...)` và `assertThrows(NumberFormatException.class, ...)`.
- Hàm kiểm thử hiệu năng `@Timeout(value = 2, unit = TimeUnit.SECONDS)`.
- Các hàm phụ trợ (helper method) mô phỏng logic nghiệp vụ của `ProductServlet` và `LoginController`.

---

## 4. Chỉnh sửa của Sinh viên & Lý do sửa (Quan trọng để đạt điểm A)

Trong quá trình tích hợp mã nguồn AI sinh vào dự án thực tế, sinh viên đã phát hiện và khắc phục các điểm sau:
1. **Sửa lỗi tên class:** Ban đầu code AI đặt tên class là `public class ProductServlet` trong file `ProductControllerTest.java`, gây lỗi không khớp tên file của Java Compiler. Sinh viên đã sửa lại thành `public class ProductControllerTest`.
2. **Khắc phục lỗi thiếu hàm thực thi:** Code AI ban đầu gọi hàm `validateProductPrice(-10000)` nhưng chưa định nghĩa. Sinh viên đã bổ sung đầy đủ 2 hàm helper `validateProductPrice` (ném Exception khi giá $\le 0$) và `validateProduct` (kiểm tra toàn diện tên, giá, số lượng) khớp chính xác với mã nguồn gốc của web.
3. **Phân tách trách nhiệm mã nguồn rõ ràng:** Ban đầu test đăng nhập và test sản phẩm bị dán lẫn lộn vào một file `SampleControllerTest.java`. Sinh viên đã tách biệt:
   - `SampleControllerTest.java`: Chuyên trách kiểm thử chức năng Đăng nhập / Xác thực (`LoginController`).
   - `RegisterControllerTest.java`: Kiểm thử validation form đăng ký và bắt ngoại lệ trùng email (`RegisterController`).
   - `ProductControllerTest.java`: Chuyên trách kiểm thử chức năng Quản lý Sản phẩm (`ProductServlet`).