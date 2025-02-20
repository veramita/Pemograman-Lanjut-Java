import java.util.*;

class Dosen {
    private int nim;
    private String nama;
    private String penelitian;

    public Dosen(int nim, String nama, String penelitian){
        this.nim = nim;
        this.nama = nama;
        this.penelitian = penelitian;
    }
    public String getDataDosen(){
        return nim + ",'" + nama + "','" + penelitian + "'";
    }
}
class MataKuliah {
    private String kodemk;
    private String nama;
    private int sks;

    public MataKuliah(String kodemk, String nama, int sks) {
            this.kodemk = kodemk;
            this.nama = nama;
            this.sks = sks;
        }
        public String getKodemk() {
            return this.kodemk;
        }
        public String getNama() {
            return this.nama;
        }
        public int getSks() {
            return this.sks;
        }
        public void dataMatkul() {
            System.out.println("'" + kodemk + "','" + nama + "'," + sks);
        }
    }
    class Ruangan {
        private String kode;
        private String nama;
        private char gedung;

        public Ruangan(String kode, String nama, char gedung){
            this.kode = "";
            this.nama = "";
            this.gedung = ' ';
        }
        public void setKode(String kode) {
            this.kode = kode;
        } public void setNama(String nama) {
            this.nama = nama;
        } public void setGedung(char gedung) {
            this.gedung = gedung;
        }
        public String getRuangan() {
            return "'" + kode + "','" + nama + "'," + gedung + "'";
        }
    }
    
    public class ClassDanObject {
        public static void main(String[] args) {
            Dosen Dosen1 = new Dosen(111, "Pak Wayan", "Optimalisasi");
            Dosen Dosen2 = new Dosen(222, "Pak Yudhis", "Deep Learning");
            Dosen Dosen3 = new Dosen(333, "Pak Arwan", "RPL");
    
            System.out.println(Dosen1.getDataDosen());
            System.out.println(Dosen2.getDataDosen());
            System.out.println(Dosen3.getDataDosen());
    
            MataKuliah MK1 = new MataKuliah("C1", "Pemograman Lanjut", 5);
            MataKuliah MK2 = new MataKuliah("C2", "AI", 3);

            MK1.dataMatkul();
            MK2.dataMatkul();

            Ruangan F26 = new Ruangan("F26", "F2.6", 'F');
            Ruangan F25 = new Ruangan("F25", "F2.5", 'F');

            System.out.println(F26.getRuangan());
            System.out.println(F25.getRuangan());
    }
}
