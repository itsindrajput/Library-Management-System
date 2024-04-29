import java.sql.*;

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
            // Add books
            addBook("Introduction to Java", "John Doe");
            addBook("Data Structures and Algorithms", "Alice Smith");

            // Search for books
            System.out.println("Search Results:");
            searchBook("Java");

            // Borrow a book
            borrowBook(1);

            // Return a book
            returnBook(1);

            // Display borrowed books
            displayBorrowedBooks();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
    }

    public static void addBook(String title, String author) {
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
        } catch (SQLException e) {
            System.out.println("Error adding book: " + e.getMessage());
        }
    }

    public static void searchBook(String keyword) {
        String sql = "SELECT * FROM Books WHERE title LIKE ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, "%" + keyword + "%");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    System.out.println("Title: " + resultSet.getString("title")
                            + ", Author: " + resultSet.getString("author"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error searching for books: " + e.getMessage());
        }
    }

    public static void borrowBook(int bookId) {
        String sql = "UPDATE Books SET availability = false WHERE book_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, bookId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Book borrowed successfully.");
            } else {
                System.out.println("Book not available for borrowing.");
            }
        } catch (SQLException e) {
            System.out.println("Error borrowing book: " + e.getMessage());
        }
    }

    public static void returnBook(int bookId) {
        String sql = "UPDATE Books SET availability = true WHERE book_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, bookId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Book returned successfully.");
            } else {
                System.out.println("Book ID not found or already returned.");
            }
        } catch (SQLException e) {
            System.out.println("Error returning book: " + e.getMessage());
        }
    }

    public static void displayBorrowedBooks() {
        String sql = "SELECT * FROM Books WHERE availability = false";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            System.out.println("Borrowed Books:");
            while (resultSet.next()) {
                System.out.println("Title: " + resultSet.getString("title")
                        + ", Author: " + resultSet.getString("author"));
            }
        } catch (SQLException e) {
            System.out.println("Error displaying borrowed books: " + e.getMessage());
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
