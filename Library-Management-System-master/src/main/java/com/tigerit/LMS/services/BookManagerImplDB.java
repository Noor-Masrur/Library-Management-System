package com.tigerit.LMS.services;

import com.tigerit.LMS.entities.Book;
import com.tigerit.LMS.entities.Genre;
import com.tigerit.LMS.error.BookAlreadyPresent;
import com.tigerit.LMS.error.BookNotFound;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookManagerImplDB implements BookManager {

    private static final String CONNECTION_URL =
            "dbc:mysql://localhost:3306/mylibrary";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "!tigerit36";


    public Book createBookFromResultSet(ResultSet resultSet) throws SQLException {
        Long bookId = resultSet.getLong("book_id");
        String title = resultSet.getString("title");
        String author = resultSet.getString("author");
        LocalDate publicationDate = resultSet.getObject("publication_date", LocalDate.class);
        Genre genre = Genre.valueOf(resultSet.getString("genre")); // Assuming genre is stored as a string in the database
        int numberOfCopies = resultSet.getInt("number_of_copies");

        return new Book(title, author, bookId, publicationDate, genre, numberOfCopies);
    }


    @Override
    public void addBook(Book book) {
        try (Connection conn = DriverManager.getConnection(CONNECTION_URL, USERNAME, PASSWORD)) {
            String sql = "INSERT INTO books (book_id, title, author, number_of_copies) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, book.getBookId());
            stmt.setString(2, book.getTitle());
            stmt.setString(3, book.getAuthor());
            stmt.setInt(4, book.getNumberOfCopies());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new BookAlreadyPresent("Book no: " + book.getBookId() + " already exists");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to add book", e);
        }
    }

    @Override
    public Book getBookById(Long bookId) {
        try (Connection connection = DriverManager
                .getConnection(CONNECTION_URL, USERNAME, PASSWORD)) {
            String sql = "SELECT * FROM books WHERE book_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, bookId);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return createBookFromResultSet(rs);
            } else {
                throw new BookNotFound("Book no: " + bookId + " not found");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get book", e);
        }
    }

    @Override
    public void updateBook(Book updatedBook) {
        try (Connection conn = DriverManager
                .getConnection(CONNECTION_URL, USERNAME, PASSWORD)) {
            String sql =
                    "UPDATE books SET title = ?, author = ?, number_of_copies = ?" +
                            " WHERE book_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, updatedBook.getTitle());
            stmt.setString(2, updatedBook.getAuthor());
            stmt.setInt(3, updatedBook.getNumberOfCopies());
            stmt.setLong(4, updatedBook.getBookId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new BookNotFound("Book no: " + updatedBook.getBookId() + " not found");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update book", e);
        }
    }

    @Override
    public void deleteBook(Long bookId) {
        try (Connection conn = DriverManager
                .getConnection(CONNECTION_URL, USERNAME, PASSWORD)) {
            String sql = "DELETE FROM books WHERE book_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, bookId);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new BookNotFound("Book no: " + bookId + " not found");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete book", e);
        }
    }

    @Override
    public List<Book> searchBookByTitle(String Keyword) {
        return null;
    }

    @Override
    public List<Book> searchBookByAuthor(String keyword) {
        List<Book> books = new ArrayList<>();

        try (Connection connection = DriverManager
                .getConnection(CONNECTION_URL, USERNAME, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM books WHERE LOWER(author) LIKE ?"
            );
            statement.setString(1, "%"
                    + keyword.toLowerCase() + "%");

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                books.add(createBookFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            // Handle database errors gracefully
            e.printStackTrace(); // For now, print the stack trace for debugging
            // You might want to log the error, throw a custom exception, or provide a user-friendly message
        }

        return books;
    }

    @Override
    public List<Book> getAllAvailableBooks() {
        return null;
    }

}

