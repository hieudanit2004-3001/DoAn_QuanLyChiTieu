
package QuanLyChiTieuCaNhan;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KhoanChiTieuDAO {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/quanlychitieu?useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    // 1. Lấy toàn bộ danh sách từ MySQL
    public List<KhoanChiTieu> layTatCa() throws SQLException {
        List<KhoanChiTieu> ds = new ArrayList<>();
        String sql = "SELECT * FROM khoan_chi_tieu";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ds.add(new KhoanChiTieu(
                        rs.getString("ma_gd"),
                        rs.getString("ngay"),
                        rs.getString("loai_gd"),
                        rs.getString("danh_muc"),
                        rs.getDouble("so_tien"),
                        rs.getString("ghi_chu")
                ));
            }
        }
        return ds;
    }

    // 2. Thêm giao dịch mới
    public boolean them(KhoanChiTieu k) throws SQLException {
        String sql = "INSERT INTO khoan_chi_tieu (ma_gd, ngay, loai_gd, danh_muc, so_tien, ghi_chu) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, k.getMaGd());
            pstmt.setString(2, k.getNgay());
            pstmt.setString(3, k.getLoaiGd());
            pstmt.setString(4, k.getDanhMuc());
            pstmt.setDouble(5, k.getSoTien());
            pstmt.setString(6, k.getGhiChu());
            return pstmt.executeUpdate() > 0;
        }
    }

    // 3. Cập nhật (Sửa) giao dịch
    public boolean sua(KhoanChiTieu k) throws SQLException {
        String sql = "UPDATE khoan_chi_tieu SET ngay=?, loai_gd=?, danh_muc=?, so_tien=?, ghi_chu=? WHERE ma_gd=?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, k.getNgay());
            pstmt.setString(2, k.getLoaiGd());
            pstmt.setString(3, k.getDanhMuc());
            pstmt.setDouble(4, k.getSoTien());
            pstmt.setString(5, k.getGhiChu());
            pstmt.setString(6, k.getMaGd());
            return pstmt.executeUpdate() > 0;
        }
    }

    // 4. Xóa giao dịch
    public boolean xoa(String maGd) throws SQLException {
        String sql = "DELETE FROM khoan_chi_tieu WHERE ma_gd=?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, maGd);
            return pstmt.executeUpdate() > 0;
        }
    }

    // 5. Tìm kiếm và lọc dữ liệu
    public List<KhoanChiTieu> timKiem(String tuKhoa, String loaiLoc) throws SQLException {
        List<KhoanChiTieu> ds = new ArrayList<>();
        String sql = "SELECT * FROM khoan_chi_tieu WHERE (ma_gd LIKE ? OR danh_muc LIKE ? OR ghi_chu LIKE ? OR ngay LIKE ?)";
        if (!loaiLoc.equals("TẤT CẢ")) {
            sql += " AND loai_gd = ?";
        }
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String key = "%" + tuKhoa + "%";
            pstmt.setString(1, key);
            pstmt.setString(2, key);
            pstmt.setString(3, key);
            pstmt.setString(4, key);
            if (!loaiLoc.equals("TẤT CẢ")) {
                pstmt.setString(5, loaiLoc);
            }
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    ds.add(new KhoanChiTieu(
                            rs.getString("ma_gd"),
                            rs.getString("ngay"),
                            rs.getString("loai_gd"),
                            rs.getString("danh_muc"),
                            rs.getDouble("so_tien"),
                            rs.getString("ghi_chu")
                    ));
                }
            }
        }
        return ds;
    }
}