package QuanLyChiTieuCaNhan;

	import javax.swing.*;
	import javax.swing.border.TitledBorder;
	import javax.swing.table.DefaultTableModel;
	import java.awt.*;
	import java.awt.event.MouseAdapter;
	import java.awt.event.MouseEvent;
	import java.text.DecimalFormat;
	import java.util.List;

	public class QuanLyChiTieuApp extends JFrame {
	    private static final long serialVersionUID = 1L;

	    private JTextField txtMaGd, txtNgay, txtSoTien, txtGhiChu, txtTimKiem;
	    private JComboBox<String> cbLoaiGd, cbDanhMuc, cbLocLoai;
	    private JTable table;
	    private DefaultTableModel tableModel;
	    private JLabel lblTongThu, lblTongChi, lblSoDu, lblStatus;
	    private ChartPanel chartPanel;

	    private KhoanChiTieuDAO dao = new KhoanChiTieuDAO();
	    private DecimalFormat df = new DecimalFormat("#,###");

	    public QuanLyChiTieuApp() {
	        setTitle("Phần Mềm Quản Lý Chi Tiêu Cá Nhân - Đồ Án OOP Java");
	        setSize(980, 680);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLocationRelativeTo(null);
	        setLayout(new BorderLayout(10, 10));

	        // ================= 1. PANEL TIÊU ĐỀ =================
	        JLabel lblTitle = new JLabel("PHẦN MỀM QUẢN LÝ CHI TIÊU CÁ NHÂN", JLabel.CENTER);
	        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
	        lblTitle.setForeground(new Color(25, 118, 210));
	        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
	        add(lblTitle, BorderLayout.NORTH);

	        // ================= 2. PANEL TRUNG TÂM =================
	        JPanel mainCenter = new JPanel(new BorderLayout(10, 10));
	        mainCenter.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

	        // --- 2.1. Khu vực phía trên: Form nhập liệu (Trái) + Thống kê & Biểu đồ (Phải) ---
	        JPanel topSection = new JPanel(new GridLayout(1, 2, 10, 10));

	        // Form nhập thông tin
	        JPanel formPanel = new JPanel(new GridLayout(6, 2, 8, 8));
	        formPanel.setBorder(BorderFactory.createTitledBorder(
	                BorderFactory.createEtchedBorder(), "Thông Tin Giao Dịch", TitledBorder.LEFT, TitledBorder.TOP,
	                new Font("Arial", Font.BOLD, 13)));

	        txtMaGd = new JTextField();
	        txtNgay = new JTextField("18/10/2026");
	        cbLoaiGd = new JComboBox<>(new String[]{"THU", "CHI"});
	        cbDanhMuc = new JComboBox<>(new String[]{
	                "Lương / Thưởng", "Ăn uống", "Nhà cửa / Điện nước",
	                "Di chuyển", "Mua sắm", "Học tập", "Giải trí", "Khác"
	        });
	        cbDanhMuc.setEditable(true);
	        txtSoTien = new JTextField();
	        txtGhiChu = new JTextField();

	        formPanel.add(new JLabel(" Mã giao dịch (*):"));
	        formPanel.add(txtMaGd);
	        formPanel.add(new JLabel(" Ngày (dd/MM/yyyy):"));
	        formPanel.add(txtNgay);
	        formPanel.add(new JLabel(" Loại giao dịch:"));
	        formPanel.add(cbLoaiGd);
	        formPanel.add(new JLabel(" Danh mục:"));
	        formPanel.add(cbDanhMuc);
	        formPanel.add(new JLabel(" Số tiền (VNĐ):"));
	        formPanel.add(txtSoTien);
	        formPanel.add(new JLabel(" Ghi chú:"));
	        formPanel.add(txtGhiChu);

	        // Bảng Thống kê & Biểu đồ đồ họa
	        JPanel statAndChartPanel = new JPanel(new BorderLayout(5, 5));
	        statAndChartPanel.setBorder(BorderFactory.createTitledBorder(
	                BorderFactory.createEtchedBorder(), "Thống Kê & Biểu Đồ Trực Quan", TitledBorder.LEFT, TitledBorder.TOP,
	                new Font("Arial", Font.BOLD, 13)));

	        JPanel statsTextPanel = new JPanel(new GridLayout(3, 1, 5, 5));
	        statsTextPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
	        lblTongThu = new JLabel("Tổng Thu: 0 VNĐ");
	        lblTongThu.setFont(new Font("Arial", Font.BOLD, 14));
	        lblTongThu.setForeground(new Color(46, 125, 50));

	        lblTongChi = new JLabel("Tổng Chi: 0 VNĐ");
	        lblTongChi.setFont(new Font("Arial", Font.BOLD, 14));
	        lblTongChi.setForeground(new Color(198, 40, 40));

	        lblSoDu = new JLabel("Số Dư Hiện Tại: 0 VNĐ");
	        lblSoDu.setFont(new Font("Arial", Font.BOLD, 15));
	        lblSoDu.setForeground(new Color(21, 101, 192));

	        statsTextPanel.add(lblTongThu);
	        statsTextPanel.add(lblTongChi);
	        statsTextPanel.add(lblSoDu);

	        chartPanel = new ChartPanel();
	        statAndChartPanel.add(statsTextPanel, BorderLayout.NORTH);
	        statAndChartPanel.add(chartPanel, BorderLayout.CENTER);

	        topSection.add(formPanel);
	        topSection.add(statAndChartPanel);

	        // --- 2.2. Khu vực các nút chức năng (Thêm, Sửa, Xóa, Tìm kiếm) ---
	        JPanel actionBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
	        actionBar.setBorder(BorderFactory.createEtchedBorder());

	        JButton btnThem = new JButton("Thêm mới");
	        btnThem.setBackground(new Color(46, 125, 50));
	        btnThem.setForeground(Color.WHITE);

	        JButton btnSua = new JButton("Cập nhật (Sửa)");
	        btnSua.setBackground(new Color(2, 119, 189));
	        btnSua.setForeground(Color.WHITE);

	        JButton btnXoa = new JButton("Xóa");
	        btnXoa.setBackground(new Color(198, 40, 40));
	        btnXoa.setForeground(Color.WHITE);

	        JButton btnLamMoi = new JButton("Làm mới ô nhập");

	        txtTimKiem = new JTextField(14);
	        cbLocLoai = new JComboBox<>(new String[]{"TẤT CẢ", "THU", "CHI"});
	        JButton btnTimKiem = new JButton("Tìm kiếm");
	        JButton btnHienTatCa = new JButton("Hiện tất cả");

	        actionBar.add(btnThem);
	        actionBar.add(btnSua);
	        actionBar.add(btnXoa);
	        actionBar.add(btnLamMoi);
	        actionBar.add(new JLabel("  |  Từ khóa:"));
	        actionBar.add(txtTimKiem);
	        actionBar.add(cbLocLoai);
	        actionBar.add(btnTimKiem);
	        actionBar.add(btnHienTatCa);

	        JPanel northWrapper = new JPanel(new BorderLayout(5, 5));
	        northWrapper.add(topSection, BorderLayout.CENTER);
	        northWrapper.add(actionBar, BorderLayout.SOUTH);

	        mainCenter.add(northWrapper, BorderLayout.NORTH);

	        // --- 2.3. Khu vực Bảng dữ liệu ---
	        tableModel = new DefaultTableModel(
	                new String[]{"Mã GD", "Ngày", "Loại", "Danh Mục", "Số Tiền (VNĐ)", "Ghi Chú"}, 0) {
	            private static final long serialVersionUID = 1L;
	            @Override
	            public boolean isCellEditable(int row, int column) {
	                return false;
	            }
	        };
	        table = new JTable(tableModel);
	        table.setRowHeight(24);
	        JScrollPane scrollPane = new JScrollPane(table);
	        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh Sách Giao Dịch Chi Tiêu"));
	        mainCenter.add(scrollPane, BorderLayout.CENTER);

	        add(mainCenter, BorderLayout.CENTER);

	        // ================= 3. THANH TRẠNG THÁI =================
	        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	        lblStatus = new JLabel("Đang kết nối CSDL...");
	        lblStatus.setFont(new Font("Arial", Font.BOLD, 12));
	        bottomPanel.add(lblStatus);
	        add(bottomPanel, BorderLayout.SOUTH);

	        // ================= 4. SỰ KIỆN TƯƠNG TÁC =================
	        table.addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseClicked(MouseEvent e) {
	                int row = table.getSelectedRow();
	                if (row >= 0) {
	                    txtMaGd.setText(tableModel.getValueAt(row, 0).toString());
	                    txtMaGd.setEditable(false);
	                    txtNgay.setText(tableModel.getValueAt(row, 1).toString());
	                    cbLoaiGd.setSelectedItem(tableModel.getValueAt(row, 2).toString());
	                    cbDanhMuc.setSelectedItem(tableModel.getValueAt(row, 3).toString());
	                    String tienStr = tableModel.getValueAt(row, 4).toString().replace(",", "").replace(".", "");
	                    txtSoTien.setText(tienStr);
	                    txtGhiChu.setText(tableModel.getValueAt(row, 5) != null ? tableModel.getValueAt(row, 5).toString() : "");
	                }
	            }
	        });

	        btnThem.addActionListener(e -> xuLyThem());
	        btnSua.addActionListener(e -> xuLySua());
	        btnXoa.addActionListener(e -> xuLyXoa());
	        btnLamMoi.addActionListener(e -> lamMoiForm());
	        btnTimKiem.addActionListener(e -> xuLyTimKiem());
	        btnHienTatCa.addActionListener(e -> taiDuLieuLenBang());

	        taiDuLieuLenBang();
	    }

	    private void taiDuLieuLenBang() {
	        try {
	            List<KhoanChiTieu> ds = dao.layTatCa();
	            hienThiDanhSach(ds);
	            lblStatus.setText("CSDL: Đã kết nối MySQL thành công (Port 3306)");
	            lblStatus.setForeground(new Color(0, 128, 0));
	        } catch (Exception e) {
	            lblStatus.setText("Lỗi kết nối CSDL: " + e.getMessage());
	            lblStatus.setForeground(Color.RED);
	        }
	    }

	    private void hienThiDanhSach(List<KhoanChiTieu> ds) {
	        tableModel.setRowCount(0);
	        double tongThu = 0;
	        double tongChi = 0;

	        for (KhoanChiTieu k : ds) {
	            tableModel.addRow(new Object[]{
	                    k.getMaGd(),
	                    k.getNgay(),
	                    k.getLoaiGd(),
	                    k.getDanhMuc(),
	                    df.format(k.getSoTien()),
	                    k.getGhiChu()
	            });
	            if ("THU".equalsIgnoreCase(k.getLoaiGd())) {
	                tongThu += k.getSoTien();
	            } else {
	                tongChi += k.getSoTien();
	            }
	        }

	        double soDu = tongThu - tongChi;
	        lblTongThu.setText("Tổng Thu: " + df.format(tongThu) + " VNĐ");
	        lblTongChi.setText("Tổng Chi: " + df.format(tongChi) + " VNĐ");

	        // XỬ LÝ HIỂN THỊ CẢNH BÁO KHI CHI TIÊU QUÁ MỨC LÀM SỐ DƯ BỊ ÂM
	        if (soDu < 0) {
	            lblSoDu.setText("Số Dư: " + df.format(soDu) + " VNĐ (CẢNH BÁO: ÂM NGÂN SÁCH!)");
	            lblSoDu.setForeground(Color.RED);
	        } else {
	            lblSoDu.setText("Số Dư Hiện Tại: " + df.format(soDu) + " VNĐ");
	            lblSoDu.setForeground(new Color(21, 101, 192));
	        }

	        chartPanel.capNhatDuLieu(tongThu, tongChi);
	    }

	    private void xuLyThem() {
	        try {
	            String ma = txtMaGd.getText().trim();
	            String ngay = txtNgay.getText().trim();
	            String loai = cbLoaiGd.getSelectedItem().toString();
	            String danhMuc = cbDanhMuc.getSelectedItem().toString().trim();
	            String tienStr = txtSoTien.getText().trim().replace(",", "");
	            String ghiChu = txtGhiChu.getText().trim();

	            if (ma.isEmpty() || ngay.isEmpty() || tienStr.isEmpty()) {
	                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ Mã GD, Ngày và Số tiền!");
	                return;
	            }
	            double soTien = Double.parseDouble(tienStr);

	            // 1. Chặn nhập số tiền âm hoặc bằng 0
	            if (soTien <= 0) {
	                JOptionPane.showMessageDialog(this,
	                        "Số tiền giao dịch phải lớn hơn 0! Không được nhập số âm hoặc bằng 0.",
	                        "Lỗi nhập liệu", JOptionPane.WARNING_MESSAGE);
	                return;
	            }

	            // 2. Kiểm tra nếu khoản CHI mới vượt quá số dư hiện có (Chi tiêu quá mức)
	            if ("CHI".equalsIgnoreCase(loai)) {
	                List<KhoanChiTieu> dsHienTai = dao.layTatCa();
	                double thu = 0, chi = 0;
	                for (KhoanChiTieu item : dsHienTai) {
	                    if ("THU".equalsIgnoreCase(item.getLoaiGd())) thu += item.getSoTien();
	                    else chi += item.getSoTien();
	                }
	                double soDuDuKien = thu - (chi + soTien);
	                if (soDuDuKien < 0) {
	                    int chon = JOptionPane.showConfirmDialog(this,
	                            "CẢNH BÁO CHI TIÊU QUÁ MỨC!\n" +
	                            "Khoản chi này sẽ làm Số dư bị âm (" + df.format(soDuDuKien) + " VNĐ).\n" +
	                            "Bạn có chắc chắn vẫn muốn lưu khoản chi này không?",
	                            "Cảnh báo vượt ngân sách",
	                            JOptionPane.YES_NO_OPTION,
	                            JOptionPane.WARNING_MESSAGE);
	                    if (chon != JOptionPane.YES_OPTION) {
	                        return;
	                    }
	                }
	            }

	            KhoanChiTieu k = new KhoanChiTieu(ma, ngay, loai, danhMuc, soTien, ghiChu);
	            if (dao.them(k)) {
	                JOptionPane.showMessageDialog(this, "Thêm giao dịch thành công!\n" + k.layThongTinChiTiet());
	                lamMoiForm();
	                taiDuLieuLenBang();
	            }
	        } catch (NumberFormatException ex) {
	            JOptionPane.showMessageDialog(this, "Số tiền phải là chữ số hợp lệ!");
	        } catch (Exception ex) {
	            JOptionPane.showMessageDialog(this, "Lỗi khi thêm (Có thể trùng Mã GD): " + ex.getMessage());
	        }
	    }

	    private void xuLySua() {
	        try {
	            String ma = txtMaGd.getText().trim();
	            if (ma.isEmpty()) {
	                JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 dòng trên bảng để sửa!");
	                return;
	            }
	            String ngay = txtNgay.getText().trim();
	            String loai = cbLoaiGd.getSelectedItem().toString();
	            String danhMuc = cbDanhMuc.getSelectedItem().toString().trim();
	            double soTien = Double.parseDouble(txtSoTien.getText().trim().replace(",", ""));
	            String ghiChu = txtGhiChu.getText().trim();

	            // 1. Chặn sửa thành số tiền âm hoặc bằng 0
	            if (soTien <= 0) {
	                JOptionPane.showMessageDialog(this,
	                        "Số tiền cập nhật phải lớn hơn 0! Không được nhập số âm.",
	                        "Lỗi nhập liệu", JOptionPane.WARNING_MESSAGE);
	                return;
	            }

	            // 2. Kiểm tra nếu sửa khoản CHI làm tổng chi vượt tổng thu
	            List<KhoanChiTieu> dsHienTai = dao.layTatCa();
	            double thu = 0, chi = 0;
	            for (KhoanChiTieu item : dsHienTai) {
	                if (!item.getMaGd().equalsIgnoreCase(ma)) {
	                    if ("THU".equalsIgnoreCase(item.getLoaiGd())) thu += item.getSoTien();
	                    else chi += item.getSoTien();
	                }
	            }
	            if ("THU".equalsIgnoreCase(loai)) thu += soTien;
	            else chi += soTien;

	            double soDuDuKien = thu - chi;
	            if (soDuDuKien < 0) {
	                int chon = JOptionPane.showConfirmDialog(this,
	                        "CẢNH BÁO CHI TIÊU QUÁ MỨC!\n" +
	                        "Việc cập nhật giao dịch này sẽ làm Số dư bị âm (" + df.format(soDuDuKien) + " VNĐ).\n" +
	                        "Bạn có chắc chắn vẫn muốn cập nhật không?",
	                        "Cảnh báo vượt ngân sách",
	                        JOptionPane.YES_NO_OPTION,
	                        JOptionPane.WARNING_MESSAGE);
	                if (chon != JOptionPane.YES_OPTION) {
	                    return;
	                }
	            }

	            KhoanChiTieu k = new KhoanChiTieu(ma, ngay, loai, danhMuc, soTien, ghiChu);
	            if (dao.sua(k)) {
	                JOptionPane.showMessageDialog(this, "Cập nhật giao dịch thành công!");
	                lamMoiForm();
	                taiDuLieuLenBang();
	            }
	        } catch (NumberFormatException ex) {
	            JOptionPane.showMessageDialog(this, "Số tiền phải là chữ số hợp lệ!");
	        } catch (Exception ex) {
	            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật: " + ex.getMessage());
	        }
	    }

	    private void xuLyXoa() {
	        String ma = txtMaGd.getText().trim();
	        if (ma.isEmpty()) {
	            JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 giao dịch trên bảng để xóa!");
	            return;
	        }
	        int confirm = JOptionPane.showConfirmDialog(this,
	                "Bạn có chắc chắn muốn xóa giao dịch [" + ma + "] không?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
	        if (confirm == JOptionPane.YES_OPTION) {
	            try {
	                if (dao.xoa(ma)) {
	                    JOptionPane.showMessageDialog(this, "Đã xóa giao dịch thành công!");
	                    lamMoiForm();
	                    taiDuLieuLenBang();
	                }
	            } catch (Exception ex) {
	                JOptionPane.showMessageDialog(this, "Lỗi khi xóa: " + ex.getMessage());
	            }
	        }
	    }

	    private void xuLyTimKiem() {
	        try {
	            String tuKhoa = txtTimKiem.getText().trim();
	            String loaiLoc = cbLocLoai.getSelectedItem().toString();
	            List<KhoanChiTieu> ketQua = dao.timKiem(tuKhoa, loaiLoc);
	            hienThiDanhSach(ketQua);
	        } catch (Exception ex) {
	            JOptionPane.showMessageDialog(this, "Lỗi tìm kiếm: " + ex.getMessage());
	        }
	    }

	    private void lamMoiForm() {
	        txtMaGd.setText("");
	        txtMaGd.setEditable(true);
	        txtNgay.setText("18/10/2026");
	        cbLoaiGd.setSelectedIndex(0);
	        cbDanhMuc.setSelectedIndex(0);
	        txtSoTien.setText("");
	        txtGhiChu.setText("");
	        table.clearSelection();
	    }

	    // Lớp nội bộ vẽ Biểu đồ đồ họa 2D so sánh Thu - Chi
	    class ChartPanel extends JPanel {
	        private static final long serialVersionUID = 1L;
	        private double thu = 0;
	        private double chi = 0;

	        public void capNhatDuLieu(double thu, double chi) {
	            this.thu = thu;
	            this.chi = chi;
	            repaint();
	        }

	        @Override
	        protected void paintComponent(Graphics g) {
	            super.paintComponent(g);
	            Graphics2D g2 = (Graphics2D) g;
	            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

	            int width = getWidth() - 40;
	            int yStart = 18;
	            double max = Math.max(thu, chi);
	            if (max == 0) max = 1;

	            int barWidthThu = (int) ((thu / max) * (width - 80));
	            int barWidthChi = (int) ((chi / max) * (width - 80));

	            // Vẽ thanh THU
	            g2.setColor(new Color(67, 160, 71));
	            g2.fillRoundRect(60, yStart, Math.max(barWidthThu, 5), 22, 8, 8);
	            g2.setColor(Color.BLACK);
	            g2.drawString("THU", 15, yStart + 16);

	            // Vẽ thanh CHI
	            g2.setColor(new Color(229, 57, 53));
	            g2.fillRoundRect(60, yStart + 32, Math.max(barWidthChi, 5), 22, 8, 8);
	            g2.setColor(Color.BLACK);
	            g2.drawString("CHI", 15, yStart + 48);

	            // Hiển thị cảnh báo trực tiếp trên biểu đồ nếu Chi > Thu
	            if (chi > thu) {
	                g2.setColor(Color.RED);
	                g2.setFont(new Font("Arial", Font.BOLD, 12));
	                g2.drawString("⚠ CẢNH BÁO: Tổng Chi đang vượt quá Tổng Thu!", 60, yStart + 72);
	            }
	        }
	    }

	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(() -> {
	            new QuanLyChiTieuApp().setVisible(true);
	        });
	    }
	}
