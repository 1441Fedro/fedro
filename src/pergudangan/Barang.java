/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pergudangan;

/**
 *
 * @author Fedro Maulana
 */
import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author Fedro Maulana
 */
@Entity
@Table(name = "tabel_barang")
public class Barang implements Serializable {
    @Id
    @Column(name = "kd_brg")
    private String kodeBarang;

    @Column(name = "nama")
    private String nama;

    @Column(name = "harga")
    private int harga;

    @Column(name = "stok")
    private int stok;

    @Column(name = "tanggal")
    private String tanggal;

    @Column(name = "status")
    private String status;

    @Column(name = "aktivitas")
    private String aktivitas;

    // Getter dan Setter
    public String getKodeBarang() {
        return kodeBarang;
    }

    public void setKodeBarang(String kodeBarang) {
        this.kodeBarang = kodeBarang;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAktivitas() {
        return aktivitas;
    }

    public void setAktivitas(String aktivitas) {
        this.aktivitas = aktivitas;
    }
}
