import java.util.*;

abstract class Perjalanan {
    protected int jarak;

    public Perjalanan(int jarak) {
        this.jarak = jarak;
    }
    public abstract void waktuTempuh();
}
class Jalan extends Perjalanan {
    private int kecepatan;

    public Jalan(int jarak) {
        super(jarak);
        this.kecepatan = 5;
    }

    public void waktuTempuh() {
        float waktu = jarak/kecepatan;
        System.out.printf("%s %.2f %s", "Waktu tempuh:", waktu, "jam");
    }
}
class Kuda extends Perjalanan {
    private int kecepatan;

    public Kuda(int jarak, int kecepatan) {
        super(jarak);
        this.kecepatan = kecepatan;
    }

    public void waktuTempuh() {
        float waktu = jarak/kecepatan;
        System.out.printf("%s %.2f %s", "Waktu tempuh:", waktu, "jam");
    }
}
class Terbang extends Perjalanan {
    private int kecepatanNaga;
    private int kecepatanAngin;

    public Terbang(int jarak, int kecepatanNaga, int kecepatanAngin) {
        super(jarak);
        this.kecepatanNaga = kecepatanNaga;
        this.kecepatanAngin = kecepatanAngin;
    }

    public void waktuTempuh() {
        float kecepatan = kecepatanNaga + kecepatanAngin;
        if (kecepatan  <= 0) {
            System.out.println("Pahlawan tidak bisa terbang hari ini!");
        } else {
            float waktu = jarak/kecepatan;
            System.out.printf("%s %.2f %s", "Waktu tempuh:", waktu, "jam");
        }
    }
}

public class LiveCoding_2_2 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        
        int metode = scan.nextInt();
        if (metode == 1) {
            int jarak = scan.nextInt();
            Perjalanan jalan = new Jalan(jarak);
            jalan.waktuTempuh();
        } else if (metode == 2) {
            int jarak = scan.nextInt();
            int kecepatanKuda = scan.nextInt();
            Perjalanan jalan = new Kuda(jarak, kecepatanKuda);
            jalan.waktuTempuh();
        } else if (metode == 3) {
            int jarak = scan.nextInt();
            int kecepatanNaga = scan.nextInt();
            int kecepatanAngin = scan.nextInt();
            Perjalanan jalan = new Terbang(jarak, kecepatanNaga, kecepatanAngin);
            jalan.waktuTempuh();
        } else {
            System.out.println("Metode perjalanan tidak valid!");
        }
    }
}
