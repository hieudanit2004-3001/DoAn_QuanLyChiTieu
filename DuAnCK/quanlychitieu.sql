-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th10 28, 2026 lúc 05:19 AM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `quanlychitieu`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `khoan_chi_tieu`
--

CREATE TABLE `khoan_chi_tieu` (
  `ma_gd` varchar(20) NOT NULL,
  `ngay` varchar(20) NOT NULL,
  `loai_gd` varchar(20) NOT NULL,
  `danh_muc` varchar(100) NOT NULL,
  `so_tien` double NOT NULL,
  `ghi_chu` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `khoan_chi_tieu`
--

INSERT INTO `khoan_chi_tieu` (`ma_gd`, `ngay`, `loai_gd`, `danh_muc`, `so_tien`, `ghi_chu`) VALUES
('GD01', '18/10/2026', 'THU', 'Lương / Thưởng', 10000000, 'Lương tháng 10'),
('GD03', '20/10/2026', 'CHI', 'Nhà cửa / Điện nước', 2500000, 'Tiền phòng trọ'),
('GD04', '19/10/2026', 'CHI', 'Ăn uống', 2000000, 'An nha hang'),
('GD05', '19/10/2026', 'CHI', 'Mua sắm', 10000000, 'Mua hang');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `khoan_chi_tieu`
--
ALTER TABLE `khoan_chi_tieu`
  ADD PRIMARY KEY (`ma_gd`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
