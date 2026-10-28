package QuanLyChiTieuCaNhan;

// Lớp cha trừu tượng (Thể hiện Tính Trừu Tượng & Tính Đóng Gói trong OOP)
abstract class GiaoDich {
    private String maGd;
    private String ngay;
    private double soTien;
    private String ghiChu;

    public GiaoDich() {
    }

    public GiaoDich(String maGd, String ngay, double soTien, String ghiChu) {
        this.maGd = maGd;
        this.ngay = ngay;
        this.soTien = soTien;
        this.ghiChu = ghiChu;
    }

    // Phương thức trừu tượng để lớp con ghi đè (Thể hiện Tính Đa Hình)
    public abstract String layThongTinChiTiet();

    // Getter và Setter (Tính Đóng Gói)
    public String getMaGd() { return maGd; }
    public void setMaGd(String maGd) { this.maGd = maGd; }

    public String getNgay() { return ngay; }
    public void setNgay(String ngay) { this.ngay = ngay; }

    public double getSoTien() { return soTien; }
    public void setSoTien(double soTien) { this.soTien = soTien; }

    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }
}

// Lớp con kế thừa từ GiaoDich (Thể hiện Tính Kế Thừa & Đa Hình)
public class KhoanChiTieu extends GiaoDich {
    private String loaiGd;
    private String danhMuc;

    public KhoanChiTieu() {
        super();
    }

    public KhoanChiTieu(String maGd, String ngay, String loaiGd, String danhMuc, double soTien, String ghiChu) {
        super(maGd, ngay, soTien, ghiChu);
        this.loaiGd = loaiGd;
        this.danhMuc = danhMuc;
    }

    public String getLoaiGd() { return loaiGd; }
    public void setLoaiGd(String loaiGd) { this.loaiGd = loaiGd; }

    public String getDanhMuc() { return danhMuc; }
    public void setDanhMuc(String danhMuc) { this.danhMuc = danhMuc; }

    @Override
    public String layThongTinChiTiet() {
        return "[" + loaiGd + "] " + danhMuc + " - Số tiền: " + getSoTien() + " VNĐ";
    }
}