import java.util.*;

class Buku {
    private String judul;
    private String penulis;
    private int tahun;
    private String isbn;
    private int jumlahHalaman;

    public Buku(String judul, String penulis) {
        this.judul = judul;
        this.penulis = penulis;
        this.tahun = 0;
        this.isbn = "Unknown";
        this.jumlahHalaman = 0;
    }

    public Buku(String judul, String penulis, int tahun, String isbn, int jumlahHalaman) {
        this.judul = judul;
        this.penulis = penulis;
        this.tahun = tahun;
        this.isbn = isbn;
        this.jumlahHalaman = jumlahHalaman;
    }

    public Buku(String judul) {
        this.judul = judul;
        this.penulis = "Unknown";
        this.tahun = 0;
        this.isbn = "Unknown";
        this.jumlahHalaman = 0;
    }

    public String getKategoriHalaman() {
        String kategori;
        if (jumlahHalaman > 300) {
            kategori = "Panjang";
        } else {
            kategori = "Pendek";
        }

        return kategori;
    }
    public void tampilkanInfo() {
        System.out.println("Judul: " + judul);
        System.out.println("Penulis: " + penulis);
        System.out.println("Tahun Terbit: " + tahun);
        System.out.println("ISBN: " + isbn);
        System.out.printf("%s %d %c%s%c\n","Jumlah Halaman:", jumlahHalaman, '(', getKategoriHalaman(), ')');
        System.out.println("==========================");
    }
}

public class LiveCoding_2_1 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int jumlahBuku = scan.nextInt();
        scan.nextLine();
        List<Buku> daftarBuku = new ArrayList<>();
        for (int i = 0; i < jumlahBuku; i++) {
            int program = scan.nextInt();
            scan.nextLine();
            switch (program) {
                case 1:
                    String judul1 = scan.nextLine();
                    String penulis1 = scan.nextLine();
                    Buku buku1 = new Buku(judul1, penulis1);
                    daftarBuku.add(buku1);
                    break;
            
                case 2:
                    String judul2 = scan.nextLine();
                    String penulis2 = scan.nextLine();
                    int tahunTerbit = scan.nextInt();
                    scan.nextLine();
                    String isbn = scan.nextLine();
                    int jumlahHalamanBuku = scan.nextInt();
                    scan.nextLine();
                    Buku buku2 = new Buku(judul2, penulis2, tahunTerbit, isbn, jumlahHalamanBuku);
                    daftarBuku.add(buku2);
                    break;

                case 3:
                    String judul3 = scan.nextLine();
                    Buku buku3 = new Buku(judul3);
                    daftarBuku.add(buku3);
                    break;
            }
        }
        for (Buku buku : daftarBuku) {
            buku.tampilkanInfo();
        }
    }
}
