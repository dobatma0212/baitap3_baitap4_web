# Quản Lý Bán Hàng & Danh Mục (Java Servlet MVC + JPA Hibernate + SiteMesh)

Dự án Web Application xây dựng theo mô hình **MVC** sử dụng **Jakarta EE Servlet/JSP** kết hợp **JPA (Hibernate 6.x)**, **SiteMesh 3 Layout Decorator** và cơ sở dữ liệu **MySQL**. Ứng dụng hỗ trợ phân quyền người dùng (Admin, Manager, User), chức năng CRUD danh mục (Category) và trang Quản lý Hồ sơ cá nhân (Profile) kèm tính năng upload và stream hình ảnh vật lý.

---

## 🚀 Công Nghệ Sử Dụng

- **Ngôn ngữ**: Java 17+ / 21+ (Jakarta EE 10 / Servlet 6.0)
- **Web Server**: Apache Tomcat 10.1+ / 11.0+
- **Layout & Template Decorator**:
  - SiteMesh 3 (`org.sitemesh:sitemesh:3.2.3`)
  - Bộ lọc: `ConfigurableSiteMeshFilter`
  - Cấu hình layout: `WEB-INF/decorators.xml`
- **ORM & Database Access**:
  - Jakarta Persistence API (JPA 3.x)
  - Hibernate ORM 6.4.4.Final
  - Hibernate Validator 8.0.1.Final (Bean Validation)
- **Cơ sở dữ liệu**: MySQL 8.x (`mysql-connector-j 8.3.0`)
- **View / Giao diện**:
  - JSP, JSTL 3.0 (`jakarta.tags.core`)
  - Bootstrap 4.6, FontAwesome 5
- **Công cụ build**: Apache Maven 3.9+

---

## 📁 Cấu Trúc Dự Án

```
baitap1/
├── src/
│   └── main/
│       ├── java/
│       │   └── vn/iotstar/
│       │       ├── config/        # JPAConfig (EntityManager quản lý kết nối CSDL)
│       │       ├── controller/    # Servlet Controllers:
│       │       │   ├── LoginController, RegisterController, LogoutController, WaitingController
│       │       │   ├── HomeController, ProfileController
│       │       │   ├── CategoryListController, CategoryAddController, CategoryEditController, CategoryDeleteController
│       │       │   └── DownloadImageController (Stream ảnh vật lý)
│       │       ├── dao/           # UserDao, CategoryDao & JPA Implementations
│       │       ├── model/         # JPA Entities (User, Category)
│       │       ├── service/       # Business Logic Services (UserService, CategoryService)
│       │       └── util/          # Hằng số (Constant), đường dẫn upload C:\upload
│       ├── resources/
│       │   └── META-INF/
│       │       └── persistence.xml # Cấu hình JPA & Hibernate Provider
│       └── webapp/
│           ├── common/            # Các thành phần giao diện dùng chung
│           │   ├── web/           # header.jsp, footer.jsp cho giao diện người dùng
│           │   └── admin/         # header.jsp, footer.jsp cho giao diện quản trị
│           ├── decorators/        # Layout chính của SiteMesh
│           │   ├── web.jsp        # Layout chính cho người dùng (Header + Body + Footer)
│           │   ├── admin.jsp      # Layout cho khu vực Admin
│           │   └── manager.jsp    # Layout cho khu vực Manager
│           ├── WEB-INF/
│           │   ├── web.xml        # Cấu hình Webapp & khai báo SiteMesh Filter
│           │   └── decorators.xml # Cấu hình quy tắc URL áp dụng layout SiteMesh
│           └── views/             # Các trang nội dung JSP:
│               ├── login.jsp, register.jsp
│               ├── home.jsp, profile.jsp
│               ├── admin/         # home.jsp, list-category.jsp, add-category.jsp, edit-category.jsp
│               └── manager/       # home.jsp
├── database.sql                   # Script khởi tạo CSDL MySQL và dữ liệu mẫu
├── pom.xml                        # Maven dependencies (JPA, MySQL, SiteMesh 3, Validator, JSTL)
└── README.md                      # Tài liệu hướng dẫn dự án
```

---

## ⚙️ Hướng Dẫn Cài Đặt & Chạy Dự Án

### 1. Khởi tạo Cơ Sở Dữ Liệu
- Mở **MySQL Workbench** (hoặc phpMyAdmin / MySQL CLI).
- Mở và thực thi toàn bộ script trong file [`database.sql`](database.sql).
- CSDL `baitap1` sẽ được tạo cùng 2 bảng `User` (có đầy đủ `fullname`, `phone`, `images`, `avatar`) và `Category` kèm dữ liệu mẫu.

> **Nếu đã có sẵn bảng `User` từ trước**, chạy thêm các lệnh sau để cập nhật:
> ```sql
> ALTER TABLE `User` ADD COLUMN IF NOT EXISTS `fullname` VARCHAR(150) NULL;
> ALTER TABLE `User` ADD COLUMN IF NOT EXISTS `phone` VARCHAR(20) NULL;
> ALTER TABLE `User` ADD COLUMN IF NOT EXISTS `images` VARCHAR(255) NULL;
> ALTER TABLE `User` ADD COLUMN IF NOT EXISTS `status` INT DEFAULT 0;
> ALTER TABLE `User` ADD COLUMN IF NOT EXISTS `code` VARCHAR(10) NULL;
> ```

### 2. Cấu hình Kết Nối CSDL, Email & Thư Mục Upload
1. **Kết nối CSDL**: Mở file `src/main/resources/META-INF/persistence.xml` và chỉnh sửa mật khẩu MySQL (nếu khác `123456`):
   ```xml
   <property name="jakarta.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/baitap1?useUnicode=true&amp;characterEncoding=UTF-8&amp;useSSL=false&amp;allowPublicKeyRetrieval=true" />
   <property name="jakarta.persistence.jdbc.user" value="root" />
   <property name="jakarta.persistence.jdbc.password" value="MẬT_KHẨU_CỦA_BẠN" />
   ```
2. **Cấu hình Email gửi OTP**: Mở file `src/main/java/vn/iotstar/util/Constant.java` tại class `Constant.Mail`:
   - Thay đổi `USERNAME` (địa chỉ Gmail người gửi).
   - Thay đổi `PASSWORD` (Mật khẩu ứng dụng 16 ký tự của Gmail - App Password).
   - *Lưu ý*: Khi kiểm thử trong môi trường localhost hoặc chưa cấu hình App Password, hệ thống **tự động in mã OTP ra màn hình Console/Terminal** (`[OTP SYSTEM LOG] MÃ OTP XÁC THỰC LÀ: [xxxxxx]`) để có thể kiểm tra và kích hoạt ngay lập tức.
3. **Thư mục Upload**: File upload mặc định được lưu vào `C:\upload` (định nghĩa tại [`Constant.DIR`](src/main/java/vn/iotstar/util/Constant.java)). Hệ thống sẽ tự động tạo thư mục con `C:\upload\category` và `C:\upload\user`.

### 3. Build & Chạy Ứng Dụng
- **Bằng Maven**:
  ```bash
  mvn clean package
  ```
- File `.war` sẽ được tạo tại `target/baitap1.war`.
- Deploy vào **Apache Tomcat 10+ / 11+** hoặc chạy trực tiếp bằng IDE (Eclipse / IntelliJ IDEA / VS Code Server connector).

---

## 🔐 Tài Khoản Kiểm Thử

| Quyền hạn | Username | Mật khẩu | Quyền / Chức năng chính |
| :--- | :--- | :--- | :--- |
| **Admin** | `admin` | `123456` | Toàn quyền quản trị, Quản lý danh mục (CRUD), Dashboard Admin |
| **Manager** | `manager` | `123456` | Quản lý bán hàng & danh mục |
| **User** | `dobt` | `123456` | Khách hàng thông thường, Cập nhật hồ sơ cá nhân |

---

## 📌 Các Tính Năng Chính

### 1. Xác thực & Phân quyền Người dùng (Authentication & Authorization)
- **Đăng ký kèm xác thực OTP qua Email**:
  - Khi người dùng đăng ký, hệ thống tự động sinh mã OTP 6 chữ số ngẫu nhiên.
  - Tài khoản được lưu với trạng thái chờ kích hoạt (`status = 0`) và lưu mã OTP vào trường `code`.
  - Hệ thống gửi email chứa mã OTP kích hoạt đến địa chỉ email đăng ký (Jakarta Mail + Angus Mail qua SMTP Gmail).
  - Tự động in mã OTP ra Server Console (`[OTP SYSTEM LOG]`) để thuận tiện kiểm thử khi chưa cấu hình mật khẩu ứng dụng Gmail.
  - Người dùng được chuyển hướng đến trang `/verify-otp` để nhập mã xác thực.
  - Hỗ trợ tính năng **Gửi lại mã OTP (Resend OTP)** khi mã hết hạn hoặc chưa nhận được thư.
  - Sau khi xác thực đúng mã OTP, tài khoản được kích hoạt (`status = 1`, `code = null`) và chuyển về trang Đăng nhập.
- **Đăng nhập an toàn & Kiểm tra kích hoạt**:
  - Tự động kiểm tra trạng thái tài khoản: Nếu tài khoản chưa kích hoạt (`status = 0`), hệ thống chặn đăng nhập và hướng dẫn chuyển tiếp sang màn hình xác thực OTP.
- **Ghi nhớ đăng nhập (Remember Me)**: Lưu thông tin tài khoản an toàn qua Cookie.
- **Phân quyền tự động**: Sau đăng nhập, `WaitingController` tự động điều hướng người dùng về đúng trang quản trị theo vai trò (Role 1: Admin, Role 2: Manager, Role 5: User).

### 2. Trang trí Bố Cục Tự Động với SiteMesh 3 (Layout Decorator Pattern)
- Tách biệt rõ ràng các tầng giao diện:
  - **Header & Navigation**: Chứa logo, liên kết trang, thông tin đăng nhập và menu dropdown.
  - **Footer**: Bản quyền, liên kết nhanh, thông tin liên hệ.
  - **Body**: Vị trí nhúng tự động nội dung của từng trang con (`<sitemesh:write property="body"/>`).
- Cấu hình qua `WEB-INF/decorators.xml` giúp tự động gắn layout mà không cần include thủ công ở từng trang con; loại trừ layout cho các trang đăng nhập/đăng ký/stream ảnh.

### 3. Quản Lý Hồ Sơ Cá Nhân (User Profile Management)
- Xem thông tin cá nhân: Tên đăng nhập, Email, Họ tên, Số điện thoại và Ảnh đại diện.
- Cập nhật thông tin thông qua form `enctype="multipart/form-data"`.
- Upload ảnh đại diện mới kèm tính năng **xem trước ảnh trực tiếp (Live Preview)** trước khi lưu.
- Xử lý đa luồng `@MultipartConfig` tại `ProfileController`, lưu ảnh vào ổ đĩa và cập nhật CSDL qua JPA `EntityManager.merge()`.
- Tự động làm mới dữ liệu trong `Session` ngay sau khi cập nhật thành công.

### 4. Quản Lý Danh Mục (Category CRUD)
- Xem danh sách danh mục kèm hình ảnh đại diện dạng lưới/bảng.
- Tìm kiếm danh mục theo từ khóa tức thì.
- Thêm mới danh mục có tải lên hình ảnh (`@MultipartConfig`).
- Sửa danh mục, hỗ trợ giữ lại ảnh cũ hoặc thay thế ảnh mới.
- Xóa danh mục kèm xử lý xóa file ảnh vật lý trên đĩa.

### 5. Stream Ảnh Độc Lập
- Servlet `/image?fname=...` đọc và truyền stream ảnh an toàn từ ổ đĩa vật lý về trình duyệt, tự động nhận diện MIME type.
