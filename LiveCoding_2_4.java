import java.util.*;

class  Pegawai {
    private String nama;
    private String ktp;
    private String lahir;
    private double gaji;
    private boolean ultah;

    public Pegawai(String nama, String ktp, String lahir, double gaji, boolean ultah) {
        this.nama = nama;
        this.ktp = ktp;
        this.lahir = lahir;
        this.gaji = gaji;
        this.ultah = ultah;
    }

    public String getNama() {
        return nama;
    }

    public String getKtp() {
        return ktp;
    }

    public String getLahir() {
        return lahir;
    }

    public double getGaji() {
        return gaji;
    }

    public boolean isUltah() {
        return ultah;
    }
    public void cetakInfo(boolean truncated) {
        System.out.println(nama);
        System.out.println(ktp);

        if (truncated) {
            
        } else {
            System.out.println(lahir);
            System.out.printf("%.1f\n", gaji);
        }
    }
}
class Manager extends Pegawai {
    private double tunjangan;

    public Manager(String nama, String ktp, String lahir, double gaji, boolean ultah, double tunjangan) {
        super(nama, ktp, lahir, gaji, ultah);
        this.tunjangan = tunjangan;
    }

    public double getGajiAkhir() {
        double bonus = 0;
        if (isUltah()) {
            bonus = getGaji() * 0.1;
        }
        return getGaji() + tunjangan + bonus;
    }
}
class StaffTetap extends Pegawai {
    private double jamKerja;
    private double upahPerJam;

    public StaffTetap(String nama, String ktp, String lahir, double gaji, boolean ultah, double jamKerja,
            double upahPerJam) {
        super(nama, ktp, lahir, gaji, ultah);
        this.jamKerja = jamKerja;
        this.upahPerJam = upahPerJam;
    }

    public double getGajiAkhir() {
        return getGaji() + (jamKerja * upahPerJam);
    }
}
class StaffKontrak extends Pegawai {
    private double jumlahProyek;
    private double hargaPerProyek;

    public StaffKontrak(String nama, String ktp, String lahir, double gaji, boolean ultah, double jumlahProyek, double hargaPerProyek) {
        super(nama, ktp, lahir, gaji, ultah);
        this.jumlahProyek = jumlahProyek;
        this.hargaPerProyek = hargaPerProyek;
    }

    public double getGajiAkhir() {
        return getGaji() + (jumlahProyek * hargaPerProyek);
    }
}
class StaffMagang extends Pegawai {
    private double lamaMagang;

    public StaffMagang(String nama, String ktp, String lahir, double gaji, boolean ultah, double lamaMagang) {
        super(nama, ktp, lahir, gaji, ultah);
        this.lamaMagang = lamaMagang;
    }

    public double getGajiAkhir() {
        double gaji = 0;
        if(isUltah()) {
            gaji = (getGaji() * lamaMagang) + 10; 
        } else {
            gaji = getGaji() * lamaMagang;
        }
        return gaji;
    }
}
public class LiveCoding_2_4 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int jumlahPegawai = input.nextInt();

        List<Pegawai> listPegawai = new ArrayList<>();

        for (int i = 0; i < jumlahPegawai; i++) {
            if (!input.hasNext()) break;

            String nama = input.next();
            String ktp = input.next();
            String lahir = input.next();
            double gaji = input.nextDouble();
            boolean ultah = input.nextBoolean();
            int jenis = input.nextInt();
            if (input.hasNextLine()) input.nextLine();

            switch (jenis) {
                case 1:
                    double tunjangan = input.nextDouble();
                    if (input.hasNextLine()) input.nextLine();
                    listPegawai.add(new Manager(nama, ktp, lahir, gaji, ultah, tunjangan));
                    break;
                case 2:
                    double jamKerja =input.nextDouble();
                    double upahPerJam = input.nextDouble();
                    if (input.hasNextLine()) input.nextLine();
                    listPegawai.add(new StaffTetap(nama, ktp, lahir, gaji, ultah, jamKerja, upahPerJam));
                    break;
                case 3:
                    double jumlahProyek = input.nextDouble();
                    double hargaPerProyek = input.nextDouble();
                    if (input.hasNextLine()) input.nextLine();
                    listPegawai.add(new StaffKontrak(nama, ktp, lahir, gaji, ultah, jumlahProyek, hargaPerProyek));
                    break;
                case 4:
                    double lamaMagang = input.nextDouble();
                    if (input.hasNextLine()) input.nextLine();
                    listPegawai.add(new StaffMagang(nama, ktp, lahir, gaji, ultah, lamaMagang));
                    break;
                case 5:
                    System.out.println("Null");
                    break;
            }
        }
        for (Pegawai pegawai : listPegawai) {
            pegawai.cetakInfo(false);
            double gajiAkhir;
            if (pegawai instanceof Manager) {
                gajiAkhir = ((Manager) pegawai).getGajiAkhir();
                System.out.printf("%.1f\n", gajiAkhir);
            } else if (pegawai instanceof StaffTetap) {
                gajiAkhir = ((StaffTetap) pegawai).getGajiAkhir();
                System.out.printf("%.1f\n", gajiAkhir);
            } else if (pegawai instanceof StaffKontrak) {
                gajiAkhir = ((StaffKontrak) pegawai).getGajiAkhir();
                System.out.printf("%.1f\n", gajiAkhir);
            } else if (pegawai instanceof StaffMagang) {
                gajiAkhir = ((StaffMagang) pegawai).getGajiAkhir();
                System.out.printf("%.1f\n", gajiAkhir);
            }
        }
    }
}
