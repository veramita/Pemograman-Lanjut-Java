import java.util.*;

abstract class Kendaraan {
    private String merek;
    private String model;
    protected double tarifDasar;
    protected final String nomorRegistrasi;
    private static int jumlahKendaraan = 0;
    protected int lamaSewa;

    public Kendaraan(String merek, String model, double tarifDasar, int lamaSewa) {
        this.merek = merek;
        this.model = model;
        this.tarifDasar = tarifDasar;
        this.lamaSewa = lamaSewa;
        this.nomorRegistrasi = "VEH" + (++jumlahKendaraan);
    }

    public abstract double biayaSewa();

    public void tampilkanInfo() {
        System.out.println("Nomor Registrasi: " + nomorRegistrasi);
        System.out.println("Merek: " + merek);
        System.out.println("Model: " + model);
        System.out.printf("Tarif Dasar: Rp %.1f\n", tarifDasar);
    }
}
class Mobil extends Kendaraan {
    private int kapasitasPenumpang;

    public Mobil(String merek, String model, double tarifDasar, int kapasitasPenumpang, int lamaSewa) {
        super(merek, model, tarifDasar, lamaSewa);
        this.kapasitasPenumpang = kapasitasPenumpang;
    }

    @Override
    public double biayaSewa() {
        double harga = 0;
        if (kapasitasPenumpang > 4) {
            harga = super.tarifDasar * lamaSewa * 0.9;
        } else {
            harga = super.tarifDasar * lamaSewa;
        }
        return harga;
    }
    @Override
    public void tampilkanInfo() {
        super.tampilkanInfo();
        System.out.println("Kapasitas Penumpang: " + kapasitasPenumpang);
        System.out.printf("Biaya Sewa: Rp %.1f\n", biayaSewa());
    }
}
class Motor extends Kendaraan {
    private boolean denganDriver;

    public Motor(String merek, String model, double tarifDasar, boolean denganDriver, int lamaSewa) {
        super(merek, model, tarifDasar, lamaSewa);
        this.denganDriver = denganDriver;
    }

    @Override
    public double biayaSewa() {
        double harga = 0;
        if (denganDriver) {
            harga = (super.tarifDasar * lamaSewa) + (100000 * lamaSewa);
        } else {
            harga = super.tarifDasar * lamaSewa;
        }
        return harga;
    }
    @Override
    public void tampilkanInfo() {
        String driver = null;
        if (denganDriver) {
            driver = "Ya";
        } else {
            driver = "Tidak";
        }
        super.tampilkanInfo();
        System.out.println("Dengan Driver: " + driver);
        System.out.printf("Biaya Sewa: Rp %.1f\n", biayaSewa());
    }
}
public class LiveCoding_2_5 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int jumlahKendaraan = input.nextInt();
        input.nextLine();
        List<Kendaraan> daftarKendaraan = new ArrayList<>();
        for (int i = 0; i < jumlahKendaraan; i++) {
            int jenisKendaraan = input.nextInt();
            input.nextLine();
            switch (jenisKendaraan) {
                case 1:
                    String merekMobil = input.nextLine();
                    String modelMobil = input.nextLine();
                    double tarifMobil = input.nextDouble();
                    int kapasitasMobil = input.nextInt();
                    int lamaMobil = input.nextInt();
                    daftarKendaraan.add(new Mobil(merekMobil, modelMobil, tarifMobil, kapasitasMobil, lamaMobil));
                    break;
            
                case 2:
                    String merekMotor = input.nextLine();
                    String modelMotor = input.nextLine();
                    double tarifMotor = input.nextDouble();
                    boolean driverMotor = input.nextInt() == 1;
                    int lamaMotor = input.nextInt();
                    daftarKendaraan.add(new Motor(merekMotor, modelMotor, tarifMotor, driverMotor, lamaMotor));
                    break;
            }
        }
        for (Kendaraan kendaraan : daftarKendaraan) {
            kendaraan.tampilkanInfo();
            System.out.println("");
        }
        System.out.println("Total Kendaraan: " + jumlahKendaraan);
        input.close();
    }
}