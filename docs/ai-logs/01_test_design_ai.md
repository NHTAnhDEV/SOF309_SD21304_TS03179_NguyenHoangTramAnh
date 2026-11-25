# Nhật ký AI: Thiết kế Kế hoạch kiểm thử & Bộ kịch bản kiểm thử (Test Plan & Test Cases)

* **Giai đoạn:** Tuần 1 (02/11 - 08/11/2026)  
* **Sinh viên thực hiện:** Nguyễn Hoàng Trâm Anh  
* **Mã số sinh viên:** TS03179  
* **Lớp:** SD21304  
* **Mục tiêu:** Đáp ứng tiêu chí minh chứng AI (Y6 - Hạng điểm A)  

---

## 1. Ngữ cảnh & Mục tiêu
* **Ngữ cảnh:** Dự án Website Bán Mỹ Phẩm Trực Tuyến (*CosmeticsStore*) xây dựng trên nền tảng Jakarta Servlet 6.0, JSP, JDBC/Hibernate kết nối CSDL SQL Server.
* **Mục tiêu:** Cung cấp tài liệu đặc tả nghiệp vụ `docs/SPECIFICATIONS.md` cho mô hình AI (GenAI RAG) nhằm sinh tự động Kế hoạch kiểm thử chi tiết (`docs/TEST_PLAN.md`) và Bộ kịch bản kiểm thử (`docs/TEST_CASES.md`) bao phủ đầy đủ các kỹ thuật Phân tích giá trị biên (Boundary Value Analysis) và Phân vùng tương đương (Equivalence Partitioning).

---

## 2. Câu lệnh đã gửi cho AI (Prompt)

> **Prompt:**  
> *"Bạn là một QA Lead và Test Architect chuyên nghiệp trong dự án Java Web. Dưới đây là nội dung tài liệu đặc tả nghiệp vụ chi tiết của dự án Website Bán Mỹ Phẩm CosmeticsStore của tôi:*  
> *[Nội dung file docs/SPECIFICATIONS.md: gồm thông tin dự án, quy tắc nghiệp vụ Đăng nhập LoginController, Đăng ký RegisterController, Quản trị Sản phẩm ProductServlet và các trường dữ liệu tên, giá > 0, số lượng tồn kho >= 0, categoryID].*  
>  
> *Hãy thực hiện 2 nhiệm vụ sau:*  
> *1. Xây dựng Kế hoạch kiểm thử chuẩn (Test Plan) bao gồm: Mục tiêu, phạm vi in-scope/out-of-scope, chiến lược kiểm thử đa tầng (JUnit 5, Selenium POM, TestNG Groups/DataProvider, CI/CD GitHub Actions), môi trường và tiêu chí nghiệm thu.*  
> *2. Áp dụng kỹ thuật Phân vùng tương đương (EP) và Phân tích giá trị biên (BVA) để thiết kế bảng Ma trận Test Cases chi tiết cho 3 module chính: Authentication Login, Register và Admin Product CRUD. Yêu cầu có đầy đủ Test Case ID, Loại test (Positive/Negative), các bước thực hiện, Test Data cụ thể cho mỹ phẩm, kết quả mong đợi và nhóm test (smoke/regression)."*

---

## 3. Kết quả do AI sinh ra (Raw Output)

* **AI đã phân tích ngữ cảnh và đề xuất:**
  * Khung Test Plan theo chuẩn IEEE 829 với 5 phần chính: Giới thiệu, Phạm vi, Chiến lược 3 tầng, Môi trường thử nghiệm và Tiêu chí Pass/Fail.
  * Phân tích các miền giá trị biên:
    * Giá sản phẩm: Giá trị âm (`-50000`), Giá trị 0 (`0`), Giá trị biên nhỏ nhất hợp lệ (`1000`), Giá trị chuỗi (`abc`).
    * Số lượng tồn kho: Số lượng âm (`-1`), Số lượng bằng 0 (`0`), Số lượng hợp lệ (`50`).
  * Bảng kịch bản 26 Test Cases chi tiết bao gồm 9 ca kiểm thử Positive (luồng chính) và 17 ca kiểm thử Negative (quét các trường hợp biên, dữ liệu rỗng, sai định dạng và bảo mật chống SQL Injection).

---

## 4. Chỉnh sửa của Sinh viên & Lý do sửa

* **Những điểm AI làm chưa sát hoặc cần cải tiến:**
  1. *Thiếu khớp nối với cấu trúc mã nguồn thực tế:* AI ban đầu đề xuất test trường `username`, trong khi `LoginController.java` của dự án thực tế sử dụng `email` để đăng nhập cho cả Khách hàng và Admin.
  2. *Thiếu phân quyền Role trong luồng Login:* AI chỉ đề xuất 1 ca test đăng nhập thành công. Sinh viên đã tách thành 2 ca riêng biệt: `TC_AUTH_01` (Khách hàng redirect `/home`) và `TC_AUTH_02` (Admin redirect `/admin/dashboard`) đúng với logic trong mã nguồn `LoginController`.
  3. *Bổ sung nhóm kiểm thử (TestNG Groups):* Sinh viên gắn nhãn rõ ràng cột `Nhóm Test` (`smoke` cho các luồng then chốt và `regression` cho các luồng validation biên) để chuẩn bị cho cấu hình file `testng.xml` ở Tuần 4 và pipeline GitHub Actions ở Tuần 5.
* **Tài liệu hoàn thiện sau khi chỉnh sửa:**
  * Đã lưu vào [`docs/SPECIFICATIONS.md`](../SPECIFICATIONS.md)
  * Đã lưu vào [`docs/TEST_PLAN.md`](../TEST_PLAN.md)
  * Đã lưu vào [`docs/TEST_CASES.md`](../TEST_CASES.md)
