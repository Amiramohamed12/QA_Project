package database;
import java.sql.Connection;
import java.sql.Statement;

public class DBInit {
    public static void initialize() {
        try (Connection conn = DBConnection.connect();
             Statement stmt = conn.createStatement()) {

            // USERS TABLE
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS users (
                    user_id INTEGER PRIMARY KEY AUTOINCREMENT,
                    first_name TEXT NOT NULL,
                    last_name TEXT NOT NULL,
                    ssn TEXT UNIQUE NOT NULL,
                    email TEXT UNIQUE NOT NULL,
                    password TEXT NOT NULL,
                    role TEXT CHECK(role IN ('client', 'admin')) NOT NULL
                );
            """);

            // ACCOUNTS TABLE
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS accounts (
                    account_id INTEGER PRIMARY KEY AUTOINCREMENT,
                    account_number TEXT UNIQUE,
                    user_id INTEGER ,
                    account_type TEXT CHECK(account_type IN ('saving', 'current')) NOT NULL,
                    balance REAL NOT NULL DEFAULT 0,
                    FOREIGN KEY (user_id) REFERENCES users(user_id)
                );
            """);

            // TRANSACTIONS TABLE
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS transactions (
                    transaction_id INTEGER PRIMARY KEY AUTOINCREMENT,
                    from_account_id INTEGER,
                    to_account_id INTEGER,
                    transaction_type TEXT CHECK(transaction_type IN ('deposit', 'withdraw', 'transfer')) NOT NULL,
                    amount REAL NOT NULL CHECK(amount BETWEEN 1000 AND 5000),
                    performed_by INTEGER NOT NULL,
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (from_account_id) REFERENCES accounts(account_id),
                    FOREIGN KEY (to_account_id) REFERENCES accounts(account_id),
                    FOREIGN KEY (performed_by) REFERENCES users(user_id)
                );
            """);

            System.out.println("Tables created successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}