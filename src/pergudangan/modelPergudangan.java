package pergudangan;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class modelPergudangan {
    private Connection connection;

    public modelPergudangan() {
        try {
            String url = "jdbc:mysql://localhost:3306/db_gudang"; // Sesuaikan nama database
            String user = "root"; // Sesuaikan dengan username MySQL Anda
            String password = ""; // Isi password jika ada
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Koneksi berhasil!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Koneksi gagal!");
        }
    }

    public void tambahData(String kdBrg, String nama, int harga, int stok, String tanggal, String status, String aktivitas) {
        String query = "INSERT INTO tabel_barang (kd_brg, nama, harga, stok, tanggal, status, aktivitas) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, kdBrg);
            stmt.setString(2, nama);
            stmt.setInt(3, harga);
            stmt.setInt(4, stok);
            stmt.setString(5, tanggal);
            stmt.setString(6, status);
            stmt.setString(7, aktivitas);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getDataBarang() {
        String query = "SELECT * FROM tabel_barang";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            return stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // Metode untuk mendapatkan koneksi
    public Connection getConnection() {
        if (connection == null) {
            throw new IllegalStateException("Koneksi database tidak berhasil dibuat.");
        }
        return connection;
    }
}
