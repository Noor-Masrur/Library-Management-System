package com.tigerit.LMS.services;

import com.tigerit.LMS.entities.Book;
import com.tigerit.LMS.error.BookNotFound;
import com.tigerit.LMS.services.BookManagerImplDb;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class BookManagerImplMockitoTest {

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private BookManagerImplDb bookManager;

    @Before
    public void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
    }

    @Test
    void givenBookId_IfBookPresent_ReturnsBook() throws SQLException {
        // Arrange
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong("book_id")).thenReturn(1L);
        when(resultSet.getString("title")).thenReturn("Test Book");
        when(resultSet.getString("author")).thenReturn("Test Author");
        when(resultSet.getObject("publication_date", java.sql.Date.class)).thenReturn(null);
        when(resultSet.getString("genre")).thenReturn("Fiction");
        when(resultSet.getInt("number_of_copies")).thenReturn(5);

        // Act
        Book book = bookManager.getBookById(1L);

        // Assert
        assertEquals(Optional.of(1L), book.getBookId());
        assertEquals("Test Book", book.getTitle());
        assertEquals("Test Author", book.getAuthor());
        // Add more assertions as needed
    }

    @Test
    void givenBookId_IfBookNotPresent_ReturnsBookNotFound() throws SQLException {
        // Arrange
        when(resultSet.next()).thenReturn(false);

        // Act and Assert
        assertThrows(BookNotFound.class, () -> bookManager.getBookById(3L));
    }
}
