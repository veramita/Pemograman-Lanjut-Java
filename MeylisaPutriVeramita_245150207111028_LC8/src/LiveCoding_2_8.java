import java.io.*;

public class LiveCoding_2_8 {

    static String[] namaProduk = new String[100];
    static int[] totalUnit = new int[100];
    static int[] totalPendapatan = new int[100];
    static int jumlahProduk = 0;

    public static void main(String[] args) throws IOException {
        bacaFile("penjualan.txt");
        tulisLaporan("LaporanAkhir.txt");
    }

    public static void bacaFile(String namaFile) {
        File fileDibaca = new File(namaFile);

        if (fileDibaca.exists() && fileDibaca.canRead()) {
            try (BufferedReader pembaca = new BufferedReader(new FileReader(fileDibaca))) {
                String baris;
                while ((baris = pembaca.readLine()) != null) {
                    String[] data = baris.split(",");
                    if (data.length == 4) {
                        String produk = data[1];
                        int jumlah = Integer.parseInt(data[2]);
                        int total = Integer.parseInt(data[3]);
                        prosesData(produk, jumlah, total);
                    }
                }
            } catch (IOException e) {
                System.err.println("Gagal membaca file : " + namaFile);
            }
        } else {
            System.out.println("File " + namaFile + " tidak ditemukan atau tidak bisa dibaca.");
        }
    }

    public static void prosesData(String produk, int jumlah, int total) {
        int index = cariProduk(produk);
        if (index == -1) {
            namaProduk[jumlahProduk] = produk;
            totalUnit[jumlahProduk] = jumlah;
            totalPendapatan[jumlahProduk] = total;
            jumlahProduk++;
        } else {
            totalUnit[index] += jumlah;
            totalPendapatan[index] += total;
        }
    }

    public static int cariProduk(String produk) {
        for (int i = 0; i < jumlahProduk; i++) {
            if (namaProduk[i].equalsIgnoreCase(produk)) {
                return i;
            }
        }
        return -1;
    }

    public static void tulisLaporan(String namaFile) {
        try (BufferedWriter penulis = new BufferedWriter(new FileWriter(namaFile))) {
            penulis.write("Laporan Penjualan:\n");

            for (int i = 0; i < jumlahProduk; i++) {
                penulis.write(namaProduk[i] + ": " + totalUnit[i] + " unit, Rp" + totalPendapatan[i] + "\n");
            }

            // Produk paling laris
            int maxUnit = totalUnit[0];
            for (int i = 1; i < jumlahProduk; i++) {
                if (totalUnit[i] > maxUnit) {
                    maxUnit = totalUnit[i];
                }
            }

            StringBuilder palingLaris = new StringBuilder();
            for (int i = 0; i < jumlahProduk; i++) {
                if (totalUnit[i] == maxUnit) {
                    if (palingLaris.length() > 0) palingLaris.append(", ");
                    palingLaris.append(namaProduk[i]).append(" (").append(totalUnit[i]).append(" unit)");
                }
            }

            // Harga rata-rata tertinggi
            double maxRata = (double) totalPendapatan[0] / totalUnit[0];
            int indexMaxRata = 0;
            for (int i = 1; i < jumlahProduk; i++) {
                double rata = (double) totalPendapatan[i] / totalUnit[i];
                if (rata > maxRata) {
                    maxRata = rata;
                    indexMaxRata = i;
                }
            }

            // Produk paling tidak laku
            int minUnit = totalUnit[0];
            for (int i = 1; i < jumlahProduk; i++) {
                if (totalUnit[i] < minUnit) {
                    minUnit = totalUnit[i];
                }
            }

            StringBuilder tidakLaku = new StringBuilder();
            for (int i = 0; i < jumlahProduk; i++) {
                if (totalUnit[i] == minUnit) {
                    tidakLaku.append("- ").append(namaProduk[i]).append(" (").append(totalUnit[i]).append(" unit)\n");
                }
            }

            // Total pendapatan
            int totalPendapatanToko = 0;
            for (int i = 0; i < jumlahProduk; i++) {
                totalPendapatanToko += totalPendapatan[i];
            }

            penulis.write("\nProduk paling laris: " + palingLaris + "\n");
            penulis.write("Produk dengan harga rata-rata tertinggi: " + namaProduk[indexMaxRata] +
                    " (Rp" + ((int) maxRata) + " per unit)\n");

            penulis.write("\nProduk yang paling tidak laku:\n" + tidakLaku);
            penulis.write("\nTotal pendapatan toko: Rp" + totalPendapatanToko + "\n");

            System.out.println("Laporan berhasil ditulis ke " + namaFile);
        } catch (IOException e) {
            System.err.println("Gagal menulis file laporan.");
        }
    }
}
