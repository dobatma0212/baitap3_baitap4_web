CREATE DATABASE IF NOT EXISTS baitap1 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE baitap1;

DROP TABLE IF EXISTS `Product`;
DROP TABLE IF EXISTS `Category`;
DROP TABLE IF EXISTS `User`;

CREATE TABLE `User` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `email` VARCHAR(150) NULL,
    `username` VARCHAR(50) NOT NULL UNIQUE,
    `fullname` VARCHAR(150) NULL,
    `password` VARCHAR(255) NOT NULL,
    `phone` VARCHAR(20) NULL,
    `images` VARCHAR(255) NULL,
    `avatar` VARCHAR(255) NULL,
    `roleid` INT DEFAULT 5, -- 1: Admin, 2: Manager, 5: User/Khách hàng
    `status` INT DEFAULT 0, -- 0: Chưa kích hoạt, 1: Đã kích hoạt
    `code` VARCHAR(10) NULL, -- Mã OTP xác thực
    `createddate` DATE NULL
);

-- Hỗ trợ cập nhật bảng User nếu đã tồn tại CSDL từ trước:
-- ALTER TABLE `User` ADD COLUMN IF NOT EXISTS `status` INT DEFAULT 0;
-- ALTER TABLE `User` ADD COLUMN IF NOT EXISTS `code` VARCHAR(10) NULL;

CREATE TABLE `Category` (
    `cate_id` INT AUTO_INCREMENT PRIMARY KEY,
    `cate_name` VARCHAR(255) NOT NULL,
    `icons` VARCHAR(255) NULL
);

CREATE TABLE `Product` (
    `product_id` INT AUTO_INCREMENT PRIMARY KEY,
    `product_name` VARCHAR(255) NOT NULL,
    `description` TEXT NULL,
    `price` DOUBLE NOT NULL DEFAULT 0,
    `images` VARCHAR(255) NULL,
    `quantity` INT DEFAULT 0,
    `status` INT DEFAULT 1, -- 1: Đang kinh doanh, 0: Ngừng kinh doanh
    `cate_id` INT NOT NULL,
    CONSTRAINT `fk_product_category` FOREIGN KEY (`cate_id`) 
        REFERENCES `Category` (`cate_id`) 
        ON DELETE CASCADE 
        ON UPDATE CASCADE
);

INSERT INTO `User` (`email`, `username`, `fullname`, `password`, `phone`, `images`, `avatar`, `roleid`, `status`, `code`, `createddate`)
VALUES 
('admin@hcmute.edu.vn', 'admin', 'Quản Trị Viên', '123456', '0908617108', NULL, NULL, 1, 1, NULL, CURDATE()),
('manager@hcmute.edu.vn', 'manager', 'Quản Lý Cửa Hàng', '123456', '0901234567', NULL, NULL, 2, 1, NULL, CURDATE()),
('dobientan@hcmute.edu.vn', 'dobt', 'Biện Tấn Đô', '123456', '0908617108', NULL, NULL, 5, 1, NULL, CURDATE());

INSERT INTO `Category` (`cate_name`, `icons`)
VALUES 
('Quần Áo Nam', NULL),
('Quần Áo Nữ', NULL),
('Giày Dép', NULL);

INSERT INTO `Product` (`product_name`, `description`, `price`, `images`, `quantity`, `status`, `cate_id`)
VALUES 
('Áo Sơ Mi Nam Công Sở', 'Áo sơ mi trắng cotton cao cấp', 250000, NULL, 50, 1, 1),
('Quần Jean Nam Slimfit', 'Quần jean co giãn thời trang', 350000, NULL, 30, 1, 1),
('Đầm Nữ Dáng Xòe', 'Đầm dự tiệc thanh lịch phong cách Hàn Quốc', 420000, NULL, 20, 1, 2),
('Chân Váy Chữ A', 'Chân váy công sở dễ phối đồ', 180000, NULL, 45, 1, 2),
('Giày Sneaker Nam Nữ', 'Giày thể thao phong cách năng động, êm chân', 550000, NULL, 60, 1, 3);

SELECT * FROM `User`;
SELECT * FROM `Category`;
SELECT * FROM `Product`;