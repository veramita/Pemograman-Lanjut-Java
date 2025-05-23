public class LiveCoding_2_9 {

    public static boolean isValid(String a, String b) {
        // 1. Panjang harus sama
        if (a.length() != b.length()) {
            return false;
        }

        // 2. Validasi hanya huruf (alfabet, tanpa spasi/simbol/angka)
        if (!a.matches("[a-zA-Z]+") || !b.matches("[a-zA-Z]+")) {
            return false;
        }

        // 3. Jumlah vokal harus lebih banyak dari konsonan (pada string a)
        if (!hasMoreVowelsThanConsonants(a)) {
            return false;
        }

        // 4. Hitung jumlah perbedaan karakter (case-sensitive)
        int differences = 0;
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i)) {
                differences++;
                if (differences > 1) {
                    return false; // terlalu banyak perbedaan
                }
            }
        }

        return true;
    }

    public static boolean hasMoreVowelsThanConsonants(String str) {
        int vowels = 0;
        int consonants = 0;

        for (char c : str.toCharArray()) {
            // Asumsikan sudah pasti huruf (karena sudah divalidasi sebelumnya)
            char lower = Character.toLowerCase(c);
            if ("aeiou".indexOf(lower) >= 0) {
                vowels++;
            } else {
                consonants++;
            }
        }

        return vowels > consonants;
    }

    public static void main(String[] args) {
        System.out.println(isValid("AeioB", "AeioC")); // true
        System.out.println(isValid("AeioB", "aeioB")); // false (huruf kapital beda)
        System.out.println(isValid("Hello", "Helli")); // false (vokal tidak lebih dari konsonan)
        System.out.println(isValid("aeio#", "aeioB")); // false (ada simbol)
        System.out.println(isValid("aeiou", "aeiou")); // true
    }
}
