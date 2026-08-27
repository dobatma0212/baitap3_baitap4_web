# Quản Lý Bán Hàng & Danh Mục (Java Servlet MVC + JPA Hibernate)

Dự án Web Application xây dựng theo mô hình **MVC** sử dụng **Jakarta EE Servlet/JSP** kết hợp **JPA (Hibernate 6.x)** và cơ sở dữ liệu **MySQL**. Ứng dụng hỗ trợ phân quyền người dùng (Admin, Manager, User) và chức năng CRUD danh mục (Category) kèm tính năng upload và hiển thị hình ảnh đại diện.

---

## 🚀 Công Nghệ Sử Dụng

- **Ngôn ngữ**: Java 17+ (Jakarta EE 10 / Servlet 6.0)
- **Web Server**: Apache Tomcat 10.1+ / 11.0+
- **ORM & Database Access**:
  - Jakarta Persistence API (JPA 3.x)
  - Hibernate ORM 6.4.4.Final
  - Hibernate Validator 8.0.1.Final (Bean Validation)
- **Cơ sở dữ liệu**: MySQL 8.x (`mysql-connector-j 8.3.0`)
- **View / Giao diện**:
  - JSP, JSTL 3.0 (`jakarta.tags.core`)
  - Bootstrap 4.6, FontAwesome 5
- **Công cụ build**: Apache Maven

---

## 📁 Cấu Trúc Dự Án

```
baitap1/
├── src/
│   └── main/
│       ├── java/
│       │   └── vn/iotstar/
│       │       ├── config/        # JPAConfig (EntityManager quản lý kết nối)
│       │       ├── controller/    # Servlet Controllers (Auth, User, Category, DownloadImage)
│       │       ├── dao/           # UserDao, CategoryDao & JPA Implementations
│       │       ├── model/         # JPA Entities (User, Category)
│       │       ├── service/       # Business Logic Services
│       │       └── util/          # Hằng số (Constant), đường dẫn upload
│       ├── resources/
│       │   └── META-INF/
│       │       └── persistence.xml # Cấu hình JPA & Hibernate Provider
│       └── webapp/
│           ├── WEB-INF/
│           │   └── web.xml
│           └── views/             # Các giao diện JSP (Auth, Admin, Manager, Category CRUD)
├── database.sql                   # Script khởi tạo CSDL MySQL và dữ liệu mẫu
├── pom.xml                        # Maven dependencies & build plugins
└── .gitignore                     # Cấu hình bỏ qua các file rác/build
```

---

## ⚙️ Hướng Dẫn Cài Đặt & Chạy Dự Án

### 1. Khởi tạo Cơ Sở Dữ Liệu
- Mở **MySQL Workbench** (hoặc phpMyAdmin / MySQL CLI).
- Mở và thực thi toàn bộ script trong file [`database.sql`](database.sql).
- CSDL `baitap1` sẽ được tạo cùng 2 bảng `User` và `Category` kèm dữ liệu mẫu.

### 2. Cấu hình Kết Nối CSDL
Mở file `src/main/resources/META-INF/persistence.xml` và chỉnh sửa mật khẩu MySQL (nếu khác `123456`):
```xml
<property name="jakarta.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/baitap1?useUnicode=true&amp;characterEncoding=UTF-8&amp;useSSL=false&amp;allowPublicKeyRetrieval=true" />
<property name="jakarta.persistence.jdbc.user" value="root" />
<property name="jakarta.persistence.jdbc.password" value="MẬT_KHẨU_CỦA_BẠN" />
```

### 3. Build & Chạy Ứng Dụng
- **Bằng Maven**:
  ```bash
  mvn clean package
  ```
- File `.war` sẽ được tạo tại `target/baitap1.war`.
- Deploy vào **Apache Tomcat 10+ / 11+** hoặc chạy trực tiếp bằng IDE (Eclipse / IntelliJ IDEA / VS Code Server connector).

---

## 🔐 Tài Khoản Kiểm Thử

| Quyền hạn | Username | Mật khẩu | Chức năng |
| :--- | :--- | :--- | :--- |
| **Admin** | `admin` | `123456` | Toàn quyền quản trị, Quản lý danh mục (CRUD) |
| **Manager** | `manager` | `123456` | Quản lý cửa hàng |
| **User** | `dobt` | `123456` | Khách hàng thông thường |

---

## 📌 Các Tính Năng Chính
1. **Xác thực người dùng**: Đăng nhập, Đăng ký, Ghi nhớ đăng nhập (Remember Me với Cookie), Đăng xuất.
2. **Phân quyền truy cập**: Điều hướng Dashboard tương ứng với Role (Admin, Manager, User).
3. **Quản lý danh mục (Category CRUD)**:
   - Xem danh sách danh mục có hình ảnh.
   - Tìm kiếm danh mục theo từ khóa.
   - Thêm mới danh mục có tải lên file ảnh (`@MultipartConfig`).
   - Sửa thông tin danh mục, tự động thay thế/xóa ảnh cũ khi tải ảnh mới.
   - Xóa danh mục kèm xóa file ảnh vật lý trên đĩa.
4. **Stream ảnh**: Servlet `/image?fname=...` đọc và truyền stream ảnh an toàn.
