package com.tigerit.LMS.services;

import com.tigerit.LMS.entities.Book;
import com.tigerit.LMS.entities.Genre;
import com.tigerit.LMS.error.BookAlreadyPresent;
import com.tigerit.LMS.error.BookNotFound;
import com.tigerit.LMS.services.BookManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookManagerImplDb implements BookManager {

    private static final String CONNECTION_URL =
            "jdbc:mysql://localhost:3306/mylibrary";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "!tigerit36";

    public BookManagerImplDb() {

    }

    public Connection connect(String url, String name, String pass) throws SQLException {
        Connection connection = DriverManager.getConnection(url, name, pass);
        return connection;
    }
    public Book createBookFromResultSet(ResultSet resultSet) throws SQLException {
        Long bookId = resultSet.getLong("book_id");
        String title = resultSet.getString("title");
        String author = resultSet.getString("author");
        Date publicationDate = resultSet.getObject("publication_date", Date.class);
        Genre genre = Genre.valueOf(resultSet.getString("genre"));
        int numberOfCopies = resultSet.getInt("number_of_copies");

        return new Book(title, author, bookId, publicationDate, genre, numberOfCopies);
    }

    @Override
    public void addBook(Book book) {
        try (Connection connection = connect(CONNECTION_URL, USERNAME, PASSWORD)) {
            String sql = "INSERT INTO books ( book_id, title, author, publication_date, genre, number_of_copies) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, book.getBookId());
            statement.setString(2, book.getTitle());
            statement.setString(3, book.getAuthor());
            statement.setDate(4, book.getPublicationDate());
            statement.setString(5,  book.getGenre().toString());
            statement.setInt(6, book.getNumberOfCopies());

            int rowsAffected = statement.executeUpdate();

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
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, bookId);
            ResultSet rs = statement.executeQuery();

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
        try (Connection connection = DriverManager
                .getConnection(CONNECTION_URL, USERNAME, PASSWORD)) {
            String sql =
                    "UPDATE books SET " +
                            "title = ?, " +
                            "author = ?," +
                            "publication_date = ?, " +
                            "genre = ?, " +
                            "number_of_copies = ?" +
                            " WHERE book_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, updatedBook.getTitle());
            statement.setString(2, updatedBook.getAuthor());
            statement.setDate(3, updatedBook.getPublicationDate());
            statement.setString(4,  updatedBook.getGenre().toString());
            statement.setInt(5, updatedBook.getNumberOfCopies());
            statement.setLong(6, updatedBook.getBookId());
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) {
                throw new BookNotFound("Book no: " + updatedBook.getBookId() + " not found");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update book", e);
        }
    }

    @Override
    public void deleteBook(Long bookId) {
        try (Connection connection = DriverManager
                .getConnection(CONNECTION_URL, USERNAME, PASSWORD)) {
            String sql = "DELETE FROM books WHERE book_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, bookId);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) {
                throw new BookNotFound("Book no: " + bookId + " not found");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete book", e);
        }
    }

    @Override
    public List<Book> searchBookByTitle(String Keyword) {
        try (Connection connection = DriverManager
                .getConnection(CONNECTION_URL, USERNAME, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM books WHERE LOWER(title) LIKE ?");
            statement.setString(1, "%" + Keyword.toLowerCase() + "%");
            ResultSet resultSet = statement.executeQuery();

            List<Book> books = new ArrayList<>();

            while (resultSet.next()) {
                Book book = createBookFromResultSet(resultSet);  // Assuming you have a Book constructor that takes values from ResultSet
                books.add(book);
            }

            return books;
        } catch (SQLException e) {
            throw new RuntimeException("Error searching books", e);
        }
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
        try (Connection connection = DriverManager
                .getConnection(CONNECTION_URL, USERNAME, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM books");
            ResultSet resultSet = statement.executeQuery();

            List<Book> books = new ArrayList<>();

            while (resultSet.next()) {
                Book book = createBookFromResultSet(resultSet);  // Assuming you have a Book constructor that takes values from ResultSet
                books.add(book);
            }

            return books;
        } catch (SQLException e) {
            throw new RuntimeException("Error searching books", e);
        }
    }

}

