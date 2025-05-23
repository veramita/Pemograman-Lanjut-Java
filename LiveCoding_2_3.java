import java.util.*;

class Kartu {
    private String name;
    String noKartu;
    private int pinKartu;
    private double saldo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNoKartu() {
        return noKartu;
    }

    public void setNoKartu(String noKartu) {
        this.noKartu = noKartu;
    }

    public int getPinKartu() {
        return pinKartu;
    }

    public void setPinKartu(int pinKartu) {
        this.pinKartu = pinKartu;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getMembership() {
        int prefix = Integer.parseInt(noKartu.substring(0, 2));
        if (prefix == 38) {
            return "silver";
        } else if (prefix == 56) {
            return "gold";
        } else if (prefix == 74) {
            return "platinum";
        } else {
            return "tidak ada membership";
        }
    }
}

class Verifikasi {
    private int percobaanInput = 0;
    private boolean terblokir = false;

    public boolean verifikasiPin(Kartu kartu, Scanner input) {
        if (terblokir) {
            System.out.println("Kartu anda terblokir");
            return false;
        }
        
        for (int i = 0; i < 3; i++) {
            int inputPin = input.nextInt();
            if (inputPin == kartu.getPinKartu()) {
                percobaanInput = 0;
                return true;
            } else {
                System.out.println("Pin salah");
                percobaanInput++;
                if (percobaanInput >= 3) {
                    terblokir = true;
                    System.out.println("Kartu anda terblokir");
                    return false;
                }
            }
        }
        return false;
    }

    public boolean isTerblokir() {
        return terblokir;
    }
}

class Transaksi {
    private Verifikasi verifikasi;

    public Transaksi(Verifikasi verifikasi) {
        this.verifikasi = verifikasi;
    }

    public void topup(Kartu kartu, Scanner input) {
        if (!verifikasi.verifikasiPin(kartu, input)){
            return;
        } else {
            double jumlah = input.nextDouble();
            kartu.setSaldo(kartu.getSaldo() + jumlah);
            System.out.println("Topup berhasil, saldo akhir: " + kartu.getSaldo());
        }
    }

    public void pembelian(Kartu kartu, Scanner input) {
        if (!verifikasi.verifikasiPin(kartu, input)){
            return;
        } else {
            double jumlah = input.nextDouble();
            double diskon = 0;
            switch (kartu.getMembership()) {
                case "silver": 
                    diskon = 0.03; 
                    break;
                case "gold": 
                    diskon = 0.05; 
                    break;
                case "platinum": 
                    diskon = 0.07; 
                    break;
            }
            double total = jumlah - (jumlah * diskon);
            if (kartu.getSaldo() >= total) {
                kartu.setSaldo(kartu.getSaldo() - total);
                System.out.println("Pembelian berhasil, sisa saldo: " + kartu.getSaldo());
            } else {
                System.out.println("Saldo tidak mencukupi untuk transaksi ini");
            }
        }
    }

    public void cekSaldo(Kartu kartu) {
        System.out.println("Nama: " + kartu.getName());
        System.out.println("Nomor: " + kartu.getNoKartu());
        System.out.println("Saldo: " + kartu.getSaldo());
        System.out.println("Akun " + kartu.getMembership());
    }
}

public class LiveCoding_2_3 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String nama = input.nextLine();
        String nomor = input.nextLine();
        int pin = Integer.parseInt(input.nextLine());
        double saldo = Double.parseDouble(input.nextLine());

        Kartu kartu = new Kartu();
        kartu.setName(nama);
        kartu.setNoKartu(nomor);
        kartu.setPinKartu(pin);
        kartu.setSaldo(saldo);

        Verifikasi verifikasi = new Verifikasi();
        Transaksi transaksi = new Transaksi(verifikasi);

        System.out.println("Akun berhasil dibuat");
        
        while (true) {
            String perintah = input.next();
            switch (perintah) {
                case "pembelian" :
                    if (verifikasi.isTerblokir()) {
                        System.out.println("Kartu anda terblokir");
                        break;
                    }
                    transaksi.pembelian(kartu, input);
                    break;
                case "topup" :
                    if (verifikasi.isTerblokir()) {
                        System.out.println("Kartu anda terblokir");
                        break;
                    }
                    transaksi.topup(kartu, input);
                    break;
                case "cek" :
                    transaksi.cekSaldo(kartu);
                    break;
                case "exit" :
                    System.out.println("Terimakasih telah berbelanja");
                    input.close();
                    return;
            }
        }
    }
}
