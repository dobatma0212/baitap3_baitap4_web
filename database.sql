CREATE DATABASE IF NOT EXISTS baitap1 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE baitap1;

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
    `createddate` DATE NULL
);

CREATE TABLE `Category` (
    `cate_id` INT AUTO_INCREMENT PRIMARY KEY,
    `cate_name` VARCHAR(255) NOT NULL,
    `icons` VARCHAR(255) NULL
);

INSERT INTO `User` (`email`, `username`, `fullname`, `password`, `phone`, `images`, `avatar`, `roleid`, `createddate`)
VALUES 
('admin@hcmute.edu.vn', 'admin', 'Quản Trị Viên', '123456', '0908617108', NULL, NULL, 1, CURDATE()),
('manager@hcmute.edu.vn', 'manager', 'Quản Lý Cửa Hàng', '123456', '0901234567', NULL, NULL, 2, CURDATE()),
('dobientan@hcmute.edu.vn', 'dobt', 'Biện Tấn Đô', '123456', '0908617108', NULL, NULL, 5, CURDATE());

INSERT INTO `Category` (`cate_name`, `icons`)
VALUES 
('Quần Áo Nam', NULL),
('Quần Áo Nữ', NULL),
('Giày Dép', NULL);

SELECT * FROM `User`;
SELECT * FROM `Category`;