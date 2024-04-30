import java.sql.*;
import java.util.*;

public class Main {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/library_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Rishabh@124";
    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    public static void main(String[] args) {
        try {
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            createTables();
            startLibraryManagementSystem();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            closeResources();
        }
    }

    public static void createTables() throws SQLException {
        String createBooksTableSQL = "CREATE TABLE IF NOT EXISTS Books (" +
                "book_id INT PRIMARY KEY AUTO_INCREMENT," +
                "title VARCHAR(255)," +
                "author VARCHAR(255)," +
                "availability BOOLEAN" +
                ")";
        String createUsersTableSQL = "CREATE TABLE IF NOT EXISTS Users (" +
                "user_id INT PRIMARY KEY AUTO_INCREMENT," +
                "name VARCHAR(255)," +
                "address VARCHAR(255)," +
                "contact_info VARCHAR(255)" +
                ")";
        String createTransactionsTableSQL = "CREATE TABLE IF NOT EXISTS Transactions (" +
                "transaction_id INT PRIMARY KEY AUTO_INCREMENT," +
                "book_id INT," +
                "user_id INT," +
                "transaction_type ENUM('borrow', 'return')," +
                "transaction_date TIMESTAMP," +
                "FOREIGN KEY (book_id) REFERENCES Books(book_id)," +
                "FOREIGN KEY (user_id) REFERENCES Users(user_id)" +
                ")";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(createBooksTableSQL);
            statement.executeUpdate(createUsersTableSQL);
            statement.executeUpdate(createTransactionsTableSQL);
            System.out.println("Tables created successfully.");
        }
    }

    public static void startLibraryManagementSystem() {
        try {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Library Management System");
                System.out.println("1. Add Book");
                System.out.println("2. Search Book");
                System.out.println("3. Borrow Book");
                System.out.println("4. Return Book");
                System.out.println("5. Show Available Books");
                System.out.println("6. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        System.out.print("Enter title: ");
                        String title = scanner.nextLine();
                        System.out.print("Enter author: ");
                        String author = scanner.nextLine();
                        addBook(title, author);
                        break;
                    case 2:
                        System.out.print("Enter search query: ");
                        String query = scanner.nextLine();
                        searchBook(query);
                        break;
                    case 3:
                        System.out.print("Enter book ID to borrow: ");
                        int borrowBookId = scanner.nextInt();
                        borrowBook(borrowBookId);
                        break;
                    case 4:
                        System.out.print("Enter book ID to return: ");
                        int returnBookId = scanner.nextInt();
                        returnBook(returnBookId);
                        break;
                    case 5:
                        showAvailableBooks();
                        break;
                    case 6:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void addBook(String title, String author) throws SQLException {
        String sql = "INSERT INTO Books (title, author, availability) VALUES (?, ?, true)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, author);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Book added successfully.");
            } else {
                System.out.println("Failed to add book.");
            }
        }
    }

    public static void searchBook(String keyword) throws SQLException {
        String sql = "SELECT * FROM Books WHERE title LIKE ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, "%" + keyword + "%");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    System.out.println("Title: " + resultSet.getString("title")
                            + ", Author: " + resultSet.getString("author"));
                }
            }
        }
    }

    public static void borrowBook(int bookId) throws SQLException {
        String sql = "UPDATE Books SET availability = false WHERE book_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, bookId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Book borrowed successfully.");
            } else {
                System.out.println("Book not available for borrowing.");
            }
        }
    }

    public static void returnBook(int bookId) throws SQLException {
        String sql = "UPDATE Books SET availability = true WHERE book_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, bookId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Book returned successfully.");
            } else {
                System.out.println("Book ID not found or already returned.");
            }
        }
    }

    public static void showAvailableBooks() throws SQLException {
        String sql = "SELECT * FROM Books WHERE availability = true";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            System.out.println("Available Books:");
            while (resultSet.next()) {
                System.out.println("Title: " + resultSet.getString("title")
                        + ", Author: " + resultSet.getString("author"));
            }
        }
    }

    public static void closeResources() {
        try {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            System.out.println("Error closing resources: " + e.getMessage());
        }
    }
}
