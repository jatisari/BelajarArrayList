package net.agusharyanto.belajararraylist;

/**
 * Created by Lenovo on 29/01/2018.
 */

public class Mahasiswa {
    private String id = "";
    private String nim = "";
    private String nama = "";
    private String jurusan = "";

    @Override
    public String toString() {
        return "Mahasiswa{" +
                "id='" + id + '\'' +
                ", nim='" + nim + '\'' +
                ", nama='" + nama + '\'' +
                ", jurusan='" + jurusan + '\'' +
                '}';
    }

    public String getData(){
        return id +" "+ nim + " "+ nama + " "+jurusan;
    }
    public Mahasiswa(){
    }
    public Mahasiswa(String id, String nim, String nama, String jurusan) {
        this.id = id;
        this.nim = nim;
        this.nama = nama;
        this.jurusan = jurusan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }
}
