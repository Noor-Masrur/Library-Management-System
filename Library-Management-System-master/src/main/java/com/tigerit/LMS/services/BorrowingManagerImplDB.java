package com.tigerit.LMS.services;

import com.tigerit.LMS.entities.Book;
import com.tigerit.LMS.entities.Borrowing;
import com.tigerit.LMS.error.BorrowingNotFound;

import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class BorrowingManagerImplDB implements BorrowingManager {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/mylibrary";
    private static final String DB_USER = "username";
    private static final String DB_PASSWORD = "password";

    private BookManager bookManager;

    private Borrowing createBorrowingFromResultSet(ResultSet resultSet) throws SQLException {
        Long issueId = resultSet.getLong("issue_id");
        Long bookId = resultSet.getLong("book_id");
        LocalDate issueDate = resultSet.getDate("issue_date").toLocalDate();
        LocalDate dueDate = resultSet.getDate("due_date").toLocalDate();

        // Potentially retrieve other fields if present in your database table

        return new Borrowing(issueId, bookId, issueDate, dueDate);
    }

    public BorrowingManagerImplDB(BookManager bookManager) {
        this.bookManager = bookManager;
    }

    @Override
    public void issueBook(Book book, LocalDate issueDate, LocalDate dueDate) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO borrowings (book_id, issue_date, due_date) VALUES (?, ?, ?)"
            )) {
                statement.setLong(1, book.getBookId());
                statement.setDate(2, Date.valueOf(issueDate));
                statement.setDate(3, Date.valueOf(dueDate));

                int affectedRows = statement.executeUpdate();
                if (affectedRows == 1) {
                    book.setNumberOfCopies(book.getNumberOfCopies() - 1);
                    bookManager.updateBook(book);
                } else {
                    throw new SQLException("Failed to issue book");
                }
            }
        } catch (SQLException e) {
            // Handle database errors gracefully
            e.printStackTrace(); // For now, print the stack trace for debugging
            // You might want to log the error, throw a custom exception, or provide a user-friendly message
        }
    }

    @Override
    public double calculateFine(Borrowing borrowing) {
        // Calculation logic remains the same as in-memory implementation
        return 0.0; // Replace with actual fine calculation
    }

    @Override
    public void returnBook(Long issueId) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM borrowings WHERE issue_id = ?"
            )) {
                statement.setLong(1, issueId);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    Borrowing borrowing = createBorrowingFromResultSet(resultSet);
                    double fine = calculateFine(borrowing);

                    try (PreparedStatement updateStatement = connection.prepareStatement(
                            "DELETE FROM borrowings WHERE issue_id = ?"
                    )) {
                        updateStatement.setLong(1, issueId);
                        int affectedRows = updateStatement.executeUpdate();
                        if (affectedRows == 1) {
                            Book updatedBook = bookManager.getBookById(borrowing.getIssuedBookId());
                            updatedBook.setNumberOfCopies(updatedBook.getNumberOfCopies() + 1);
                            bookManager.updateBook(updatedBook);
                        } else {
                            throw new SQLException("Failed to delete borrowing");
                        }
                    }

                    if (fine > 0.0) {
                        System.out.println("Pay fine " + fine + "tk before returning the book");
                    }
                } else {
                    throw new BorrowingNotFound("No borrowing found for issueId: " + issueId);
                }
            }
        } catch (SQLException e) {
            // Handle database errors gracefully
            e.printStackTrace(); // For now, print the stack trace for debugging
            // You might want to log the error, throw a custom exception, or provide a user-friendly message
        }
    }
}

// Helper method to create a
