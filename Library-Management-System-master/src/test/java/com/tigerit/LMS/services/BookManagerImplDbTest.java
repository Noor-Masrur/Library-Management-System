package com.tigerit.LMS.services;

import com.tigerit.LMS.entities.Book;
import com.tigerit.LMS.entities.Genre;
import com.tigerit.LMS.error.BookNotFound;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testcontainers.containers.MySQLContainer;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

class BookManagerImplDbTest {

    private BookManager bookManager;
    private static final String CONNECTION_URL =
            "jdbc:mysql://localhost:3306/mylibrary";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "!tigerit36";
    java.util.Date now = new java.util.Date();
    private Book firstBook;
    private Book secondBook;
    private Book thirdBook;

    @BeforeEach
    void setUp() throws SQLException {
        bookManager = new BookManagerImplDb();
        firstBook = new Book(
                "To Kill a Mockingbird", // Title
                "Harper Lee",            // Author
                1L,                  // Book ID
                new Date(now.getTime()), // Publication date
                Genre.Fiction,      // Genre
                5                         // Number of copies
        );
        secondBook = new Book(
                "Atomic Habits", // Title
                "James Clear",            // Author
                2L,                  // Book ID
                new Date(now.getTime()), // Publication date
                Genre.Journal,      // Genre
                10                         // Number of copies
        );

        thirdBook = new Book(
                "Atomic Habits", // Title
                "James Clear",            // Author
                2L,                  // Book ID
                new Date(now.getTime()), // Publication date
                Genre.Journal,      // Genre
                10                         // Number of copies
        );

        try (Connection connection = DriverManager.getConnection(CONNECTION_URL, USERNAME, PASSWORD)) {
            String sql = "DELETE from borrowings";
            PreparedStatement statement = connection.prepareStatement(sql);
            int rowsAffected = statement.executeUpdate();

            sql = "DELETE from books";
            statement = statement = connection.prepareStatement(sql);
            rowsAffected = statement.executeUpdate();

            sql = "INSERT INTO books ( book_id, title, author, publication_date, genre, number_of_copies) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(sql);
            statement.setLong(1, firstBook.getBookId());
            statement.setString(2, firstBook.getTitle());
            statement.setString(3, firstBook.getAuthor());
            statement.setDate(4, firstBook.getPublicationDate());
            statement.setString(5,  firstBook.getGenre().toString());
            statement.setInt(6, firstBook.getNumberOfCopies());
            rowsAffected = statement.executeUpdate();

            sql = "INSERT INTO books ( book_id, title, author, publication_date, genre, number_of_copies) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(sql);
            statement.setLong(1, secondBook.getBookId());
            statement.setString(2, secondBook.getTitle());
            statement.setString(3, secondBook.getAuthor());
            statement.setDate(4, secondBook.getPublicationDate());
            statement.setString(5, secondBook.getGenre().toString());
            statement.setInt(6, secondBook.getNumberOfCopies());
            rowsAffected = statement.executeUpdate();

           if(rowsAffected == 0){
               throw new SQLException("Query not executed");
           }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert initial book",e);
        }
    }
    @Test
    void givenBookId_IfBookPresent_ReturnsBook(){
        Book book = bookManager.getBookById(1L);
        assertEquals(firstBook.getBookId(),book.getBookId());
    }

    @Test
    void givenBookId_IfBookNotPresent_ReturnsBook(){
        assertThrows(BookNotFound.class,
                () -> bookManager.getBookById(3L));
    }

    @Test
    void givenBookIdToDelete_IfBookPresent_DeletedSuccessfully(){
        try (Connection connection = DriverManager
                .getConnection(CONNECTION_URL, USERNAME, PASSWORD)) {
            bookManager.deleteBook(1L);
            String sql = "select count(book_id) from books";
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int countOfBooks = resultSet.getInt(1);

            assertEquals(1, countOfBooks);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    void givenBookIdToDelete_IfBookNotPresent_DeletedUnsuccessful(){

            assertThrows(RuntimeException.class,
                    () -> bookManager.deleteBook(1L));
    }

    @Test
    void givenBookIdToDelete_IfBookNotPresent_DeletedSuccessfully(){

        assertThrows(BookNotFound.class,
                () -> bookManager.deleteBook(3L));
    }

    @AfterEach
    void tearDown() throws SQLException{
        try (Connection connection = DriverManager.getConnection(CONNECTION_URL, USERNAME, PASSWORD)) {
            String sql = "delete from books";
            PreparedStatement statement = connection.prepareStatement(sql);
            int rowAffected = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Could not clear table");
        }
    }
}