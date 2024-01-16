package com.tigerit.LMS.services;

import com.tigerit.LMS.entities.Book;
import com.tigerit.LMS.entities.Genre;
import com.tigerit.LMS.error.BookAlreadyPresent;
import com.tigerit.LMS.error.BookNotFound;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class BookManagerImplTest {

    private BookManagerImpl bookManager;
    private Book firstBook;
    private Book secondBook;
    private Book thirdBook;
    private java.util.Date now;
    //private List<Book> books;

    @BeforeEach
    void setUp() {
        bookManager = new BookManagerImpl();
        now = new java.util.Date();
        firstBook =  new Book(
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
                "Doler Nam black dragon", // Title
                "Jafor Iqbal",            // Author
                3L,                  // Book ID
                new Date(now.getTime()), // Publication date
                Genre.Journal,      // Genre
                1                         // Number of copies
        );

        bookManager.addBook(firstBook);
        bookManager.addBook(secondBook);
        bookManager.addBook(thirdBook);

    }

    @AfterEach
    void tearDown() {
        bookManager.clearBooks();
    }

    @DisplayName("Successfully book added")
    @Test
    void addBook_shouldAddBookSuccessfully() {
        Book book = new Book();
        book.setBookId(4L);
        book.setTitle("Test book");

        bookManager.addBook(book);

        assertAll("Two assertions",
                () -> assertEquals(4L, bookManager.getBooks().size()),
                () -> assertTrue(bookManager.getBooks().contains(book))
        );
    }

    @DisplayName("If there is already a book, then it would throw BookAlreadyPresentException")
    @Test
    void addBook_ifBookIdExists_shouldThrowBookAlreadyPresentException(){

        Book bookWithSameId = new Book();
        bookWithSameId.setBookId(2L);

        assertThrows(BookAlreadyPresent.class, () -> bookManager.addBook(bookWithSameId));
    }

    @Test
    void givenBookId_IfBookPresent_ReturnsBook(){
        Book book = bookManager.getBookById(2L);
        assertEquals(secondBook,book);
    }

    @Test
    void givenBookId_IfBookNotPresent_ReturnsBookNotFoundException(){
        assertThrows(BookNotFound.class,
                () -> bookManager.getBookById(4L));
    }

    @Test
    void givenBookToUpdate_IfBookPresent_BookUpdatedSuccessfully(){

        Book bookToUpdate = new Book(
                "Doler Nam black dragon", // Title
                "Jafor Iqbal",            // Author
                3L,                  // Book ID
                new Date(now.getTime()), // Publication date
                Genre.Journal,      // Genre
                5                         // Number of copies
        );

        bookManager.updateBook(bookToUpdate);
        assertEquals(5, bookManager.getBookById(3L).getNumberOfCopies());
    }

    @Test
    void givenBookToUpdate_IfBookNotPresent_ReturnsBookNotFoundException(){
        Book bookToUpdate = new Book();
        bookToUpdate.setBookId(4L);
        assertThrows(BookNotFound.class,
                () -> bookManager.updateBook(bookToUpdate));
    }
    
    @Test
    void givenBookIdToDelete_IfBookPresent_DeletedSuccessfully(){
        bookManager.deleteBook(3L);
        assertEquals(2, bookManager.getBooks().size());
    }

    @Test
    void givenBookIdToDelete_IfBookNotPresent_DeleteUnsuccessful(){
        assertThrows(BookNotFound.class,
                () -> bookManager.deleteBook(7L)
        );
    }

    @Test
    void givenKeywordOfTitle_ReturnsListOfBooks(){
        List<Book> queryResult = bookManager.searchBookByTitle("Atomic");
        assertEquals(1, queryResult.size());
        assertEquals(2L, queryResult.get(0).getBookId());

    }

    @Test
    void givenKeywordOfAuthor_ReturnsListOfBooks(){
        List<Book> queryResult = bookManager.searchBookByAuthor("James");
        assertEquals(1, queryResult.size());
        assertEquals(2L, queryResult.get(0).getBookId());

    }

    @Test
    void allBooksReturnedSuccessfully(){
        List<Book> queryResult = bookManager.getAllAvailableBooks();
        assertEquals(3,queryResult.size());
    }






}