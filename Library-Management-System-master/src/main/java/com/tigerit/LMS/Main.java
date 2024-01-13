package com.tigerit.LMS;

import com.tigerit.LMS.entities.Book;
import com.tigerit.LMS.entities.Genre;
import com.tigerit.LMS.entities.Member;
import com.tigerit.LMS.error.BookAlreadyPresent;
import com.tigerit.LMS.error.BookNotFound;
import com.tigerit.LMS.error.BorrowingNotFound;
import com.tigerit.LMS.services.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.IOException;
import java.time.LocalDate;

public class Main {
    private static Logger logger = LogManager.getLogger(Main.class);
    public static void main(String[] args) throws IOException {


        //System.out.println("Hello world!");


        //Instance of a book class
        Book book1 = new Book(
                "To Kill a Mockingbird", // Title
                "Harper Lee",            // Author
                1L,                  // Book ID
                LocalDate.of(1960, 7, 11), // Publication date
                Genre.Fiction,      // Genre
                5                         // Number of copies
        );

        Book book2 = new Book(
                "Atomic Habits Kill", // Title
                "James Clear",            // Author
                2L,                  // Book ID
                LocalDate.of(1980, 5, 12), // Publication date
                Genre.Journal,      // Genre
                10                         // Number of copies
        );

        Book book3 = new Book(
                "Atomic Habits", // Title
                "James Clear",            // Author
                5L,                  // Book ID
                LocalDate.of(1980, 5, 12), // Publication date
                Genre.Journal,      // Genre
                10                         // Number of copies
        );
        // Print the book information using toString()
       // System.out.println(book1);
        Member member = new Member(1L, "John Doe", "johndoe@example.com", "+1234567890", LocalDate.now());
        UserManager userManager = new UserManagerImpl();
        userManager.registerMember(member);

//        Borrowing borrowing1 = new Borrowing(
//                1L, // Issued book ID
//                67890L, // Issue ID
//                // Issuer member ID
//                LocalDate.of(2024, 1, 2) // Issue date
//        );

        // Print the borrowing information
       // System.out.println(borrowing1);

        BookManager bookManager = new BookManagerImpl();

        try{
            bookManager.addBook(book1);
        } catch (BookAlreadyPresent e) {
            logger.error("Error: " + e);
        }
        try{
            bookManager.addBook(book2);
        } catch (BookAlreadyPresent e) {
            logger.error("Error: " + e);
        }



        System.out.println(bookManager.getBookById(2L));

        bookManager.searchBookByTitle("How").forEach(book -> System.out.println(book));
        bookManager.searchBookByAuthor("James").forEach(book -> System.out.println(book));
        bookManager.getAllAvailableBooks().forEach(book -> System.out.println(book));

        try{
            bookManager.updateBook(book3);
        } catch (BookNotFound e) {
            logger.error("Error + " +e);
        }

        bookManager.getAllAvailableBooks().forEach(book -> System.out.println(book));
        try{
            bookManager.deleteBook(2L);
        } catch (BookNotFound e) {
            logger.error("Error + " +e);
        }
        bookManager.getAllAvailableBooks().forEach(book -> System.out.println(book));


        BorrowingManager borrowingManager = new BorrowingManagerImpl(bookManager);
        borrowingManager.issueBook(book1, LocalDate.now() ,  LocalDate.now().plusMonths(6));
        borrowingManager.issueBook(book1, LocalDate.of(2023, 5, 14),LocalDate.now().plusMonths(2));
//
        bookManager.getAllAvailableBooks().forEach(book -> System.out.println(book));
//
        try{
            borrowingManager.returnBook(110L);
        } catch (BorrowingNotFound e) {
            logger.error("Error : " + e);
        }
      //  borrowingManager.returnBook(1L);

//
        bookManager.getAllAvailableBooks().forEach(book -> System.out.println(book));


        ReportGenerator reportGenerator = new ReportGeneratorImpl(bookManager,borrowingManager);
        try{
            reportGenerator.generateAllBooksReport();
        } catch (RuntimeException e){
            logger.error("Error: " + e);
        }







    }
}