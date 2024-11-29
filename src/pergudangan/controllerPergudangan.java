package pergudangan;

import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import javax.swing.JOptionPane;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import org.hibernate.Query;
import org.hibernate.Session;


public class controllerPergudangan {
    private final viewPergudangan view;
    private final modelPergudangan model;
    private final DefaultTableModel tableModel;

    /**
     *
     * @param view
     */
    public controllerPergudangan(viewPergudangan view) {
        this.view = view;
        this.model = new modelPergudangan();
        this.tableModel = (DefaultTableModel) view.getTable().getModel();
    }

    // Metode untuk menambah data ke database dan tabel
    private boolean validasiInput(String kdBrg, String nama, String hargaText, String stokText, String tanggal) {
        if (kdBrg.isEmpty() || nama.isEmpty() || hargaText.isEmpty() || stokText.isEmpty() || tanggal.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Semua field harus diisi.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            Integer.parseInt(hargaText);
            Integer.parseInt(stokText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Harga dan Stok harus berupa angka.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private Barang buatBarang(String kdBrg, String nama, int harga, int stok, String tanggal, String status, String aktivitas) {
        Barang barang = new Barang();
        barang.setKodeBarang(kdBrg);
        barang.setNama(nama);
        barang.setHarga(harga);
        barang.setStok(stok);
        barang.setTanggal(tanggal);
        barang.setStatus(status);
        barang.setAktivitas(aktivitas);
        return barang;
    }

    public void addEntry() {
        String kdBrg = view.getKodeBarangField().getText().trim();
        String nama = view.getNamaField().getText().trim();
        String hargaText = view.getHargaField().getText().trim();
        String stokText = view.getStokField().getText().trim();
        String tanggal = view.getTanggalField().getText().trim();
        String status = (String) view.getStatusField().getSelectedItem();
        String aktivitas = (String) view.getAktivitasField().getSelectedItem();

        if (!validasiInput(kdBrg, nama, hargaText, stokText, tanggal)) {
            return;
        }

        int harga, stok;
        try {
            harga = Integer.parseInt(hargaText);
            stok = Integer.parseInt(stokText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Harga dan Stok harus berupa angka.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Barang barang = buatBarang(kdBrg, nama, harga, stok, tanggal, status, aktivitas);

        // Gunakan try-finally untuk memastikan session selalu ditutup
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(barang);
            session.getTransaction().commit();
            JOptionPane.showMessageDialog(view, "Data berhasil disimpan!", "Informasi", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            if (session != null && session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Terjadi kesalahan saat menyimpan data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        Object[] rowData = {kdBrg, nama, harga, stok, tanggal, status, aktivitas};
        tableModel.addRow(rowData);
    }


    // Metode untuk memuat data dari database ke tabel
    public void loadData() {
        tableModel.setRowCount(0);
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from Barang"); // Query tanpa generics
            List<Barang> list = query.list();
            for (Barang barang : list) {
                Object[] rowData = {
                    barang.getKodeBarang(),
                    barang.getNama(),
                    barang.getHarga(),
                    barang.getStok(),
                    barang.getTanggal(),
                    barang.getStatus(),
                    barang.getAktivitas()
                };
                tableModel.addRow(rowData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    public void showReport() {
        Session session = null;
        try {
            // Jalur file .jasper yang sudah dikompilasi
            String reportPath = "C:/Users/Fedro Maulana/OneDrive/Documents/NetBeansProjects/pergudangan/src/pergudangan/BarangReport.jasper";

            // Ambil data dari database menggunakan Hibernate
            session = HibernateUtil.getSessionFactory().openSession();
            System.out.println("Session Hibernate berhasil dibuat");

            List<Barang> listBarang = session.createQuery("from Barang").list();
            System.out.println("Jumlah data: " + (listBarang != null ? listBarang.size() : "null"));
            for (Barang barang : listBarang) {
                System.out.println("Kode Barang: " + barang.getKodeBarang());
                System.out.println("Nama: " + barang.getNama());
                System.out.println("Harga: " + barang.getHarga());
                System.out.println("Stok: " + barang.getStok());
                System.out.println("Tanggal: " + barang.getTanggal());
                System.out.println("Status: " + barang.getStatus());
                System.out.println("Aktivitas: " + barang.getAktivitas());
            }
            
            // Buat data source untuk laporan
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listBarang);

            // Isi laporan dengan data
            Map<String, Object> parameters = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath, parameters, dataSource);

            // Tampilkan laporan
            JasperViewer.viewReport(jasperPrint, false);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Terjadi kesalahan saat membuka laporan: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }   
        }
    }
}
