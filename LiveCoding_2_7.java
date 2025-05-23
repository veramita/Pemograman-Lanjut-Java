import java.util.Scanner;

interface BankOperations {
    void createAccount(String accountNumber, String name, double initialBalance) throws Exception;
    void deposit(String accountNumber, double amount) throws Exception;
    void withdraw(String accountNumber, double amount) throws Exception;
    double checkBalance(String accountNumber) throws Exception;
}

class Account {
    String accountNumber;
    String name;
    double balance;
    int transactionCount;

    public Account(String accountNumber, String name, double initialBalance) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.balance = initialBalance;
        this.transactionCount = 0;
    }
}

class Bank implements BankOperations {
    private Account[] accounts;
    private int accountCount;

    public Bank() {
        accounts = new Account[5];
        accountCount = 0;
    }

    @Override
    public void createAccount(String accountNumber, String name, double initialBalance) throws Exception {
        if (initialBalance < 0) throw new InvalidAmountException("ERROR: InvalidAmountException - Saldo awal tidak boleh negatif");
        if (accountCount >= 5) throw new BankFullException("ERROR: BankFullException - Bank telah mencapai batas maksimal 5 akun");
        for (Account account : accounts) {
            if (account != null && account.accountNumber.equals(accountNumber)) {
                throw new Exception("ERROR: Nomor akun " + accountNumber + " sudah digunakan");
            }
        }
        accounts[accountCount++] = new Account(accountNumber, name, initialBalance);
        System.out.println("Akun " + accountNumber + " berhasil dibuat untuk " + name + " dengan saldo " + initialBalance);
    }

    @Override
    public void deposit(String accountNumber, double amount) throws Exception {
        Account account = findAccount(accountNumber);
        if (account.transactionCount >= 3) throw new DailyLimitExceededException("ERROR: DailyLimitExceededException " + accountNumber + " telah mencapai batas 3 transaksi harian");
        if (amount <= 0) throw new InvalidAmountException("ERROR: InvalidAmountException - Jumlah deposit harus lebih dari 0");

        account.balance += amount;
        account.transactionCount++;
        System.out.println("Deposit ke " + accountNumber + " berhasil. Saldo sekarang: " + account.balance);
    }

    @Override
    public void withdraw(String accountNumber, double amount) throws Exception {
        Account account = findAccount(accountNumber);
        if (account.transactionCount >= 3) throw new DailyLimitExceededException("ERROR: DailyLimitExceededException - Akun " + accountNumber + " telah mencapai batas 3 transaksi harian");
        if (amount <= 0) throw new InvalidAmountException("ERROR: InvalidAmountException - Jumlah penarikan harus lebih dari 0");
        if (amount > 5000000) throw new InvalidAmountException("ERROR: InvalidAmountException - Jumlah penarikan maksimal 5000000.0");
        if (account.balance < amount) throw new InsufficientFundsException("ERROR: InsufficientFundsException - Saldo tidak mencukupi untuk penarikan sebesar " + amount);

        account.balance -= amount;
        account.transactionCount++;
        System.out.println("Penarikan dari " + accountNumber + " berhasil. Saldo sekarang: " + account.balance);
    }

    @Override
    public double checkBalance(String accountNumber) throws Exception {
        Account account = findAccount(accountNumber);
        return account.balance;
    }

    private Account findAccount(String accountNumber) throws Exception {
        for (Account account : accounts) {
            if (account != null && account.accountNumber.equals(accountNumber)) {
                return account;
            }
        }
        throw new Exception("ERROR: AccountNotFoundException - Akun " + accountNumber + " tidak ditemukan");
    }
}

// Custom exceptions
class InvalidAmountException extends Exception {
    public InvalidAmountException(String message) {
        super(message);
    }
}

class BankFullException extends Exception {
    public BankFullException(String message) {
        super(message);
    }
}

class DailyLimitExceededException extends Exception {
    public DailyLimitExceededException(String message) {
        super(message);
    }
}

class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}

public class LiveCoding_2_7 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Bank bank = new Bank();
        String input;

        while (true) {
            input = scanner.nextLine();
            String[] parts = input.split(" ");
            if (parts.length == 0) continue;

            try {
                int operationCode = Integer.parseInt(parts[0]);
                switch (operationCode) {
                    case 1:
                        if (parts.length != 4) throw new Exception("ERROR: Format tidak valid untuk membuat akun");
                        String accountNumber = parts[1];
                        String name = parts[2];
                        double initialBalance = Double.parseDouble(parts[3]);
                        bank.createAccount(accountNumber, name, initialBalance);
                        break;
                    case 2:
                        if (parts.length != 3) throw new Exception("ERROR: Format tidak valid untuk deposit");
                        accountNumber = parts[1];
                        double depositAmount = Double.parseDouble(parts[2]);
                        bank.deposit(accountNumber, depositAmount);
                        break;
                    case 3:
                        if (parts.length != 3) throw new Exception("ERROR: Format tidak valid untuk penarikan");
                        accountNumber = parts[1];
                        double withdrawAmount = Double.parseDouble(parts[2]);
                        bank.withdraw(accountNumber, withdrawAmount);
                        break;
                    case 4:
                        if (parts.length != 2) throw new Exception("ERROR: Format tidak valid untuk cek saldo");
                        accountNumber = parts[1];
                        double balance = bank.checkBalance(accountNumber);
                        System.out.println("Saldo " + accountNumber + ": " + balance);
                        break;
                    case 5:
                        System.out.println("Program selesai!");
                        scanner.close();
                        return;
                    default:
                        throw new Exception("ERROR: Kode operasi tidak valid");
                }
            } catch (NumberFormatException e) {
                System.out.println("ERROR: Kode operasi harus berupa angka");
            } catch (Exception e) {
                String exceptionName = e.getClass().getSimpleName();
                System.out.println("ERROR: " + exceptionName + " - " + e.getMessage().replace("ERROR: ", ""));
            }
        }
    }
}
