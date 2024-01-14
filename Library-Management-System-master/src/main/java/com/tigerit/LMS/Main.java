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
import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;

public class Main {
    private static Logger logger = LogManager.getLogger(Main.class);
    public static void main(String[] args) throws IOException {


        //System.out.println("Hello world!");

        java.util.Date now = new java.util.Date();

        //Instance of a book class
        Book book1 = new Book(
                "To Kill a Mockingbird", // Title
                "Harper Lee",            // Author
                1L,                  // Book ID
                new Date(now.getTime()), // Publication date
                Genre.Fiction,      // Genre
                5                         // Number of copies
        );

        Book book2 = new Book(
                "Atomic Habits Kill", // Title
                "James Clear",            // Author
                2L,                  // Book ID
                new Date(now.getTime()), // Publication date
                Genre.Journal,      // Genre
                10                         // Number of copies
        );

        Book book3 = new Book(
                "Atomic Habits", // Title
                "James Clear",            // Author
                2L,                  // Book ID
                new Date(now.getTime()), // Publication date
                Genre.Journal,      // Genre
                10                         // Number of copies
        );
        // Print the book information using toString()
       // System.out.println(book1);
//
//        Member member = new Member(4L, "Tausif ", "masrur.bd.noor@gmail.com", "+8801703440379", LocalDate.now());
//        UserManager userManager = new UserManagerImpl();
//        userManager.registerMember(member);
//        logger.info(member);

//        Borrowing borrowing1 = new Borrowing(
//                1L, // Issued book ID
//                67890L, // Issue ID
//                // Issuer member ID
//                LocalDate.of(2024, 1, 2) // Issue date
//        );

        // Print the borrowing information
       // System.out.println(borrowing1);

        //BookManager bookManager = new BookManagerImpl();
        BookManager bookManager = new BookManagerImplDb();


//        try{
//            bookManager.addBook(book1);
//        } catch (BookAlreadyPresent e) {
//            logger.error("Error: " + e);
//        }
//        try{
//            bookManager.addBook(book2);
//        } catch (BookAlreadyPresent e) {
//            logger.error("Error: " + e);
//        }

//
//
//        System.out.println(bookManager.getBookById(1L));

//        bookManager.searchBookByTitle("How").forEach(book -> System.out.println(book));
//        bookManager.searchBookByAuthor("James").forEach(book -> System.out.println(book));
//        bookManager.getAllAvailableBooks().forEach(book -> System.out.println(book));

//        try{
//            bookManager.updateBook(book3);
//        } catch (BookNotFound e) {
//            logger.error("Error + " +e);
//        }
//
//        bookManager.getAllAvailableBooks().forEach(book -> System.out.println(book));
//        try{
//            bookManager.deleteBook(4L);
//        } catch (BookNotFound e) {
//            logger.error("Error + " +e);
//        }
//        bookManager.getAllAvailableBooks().forEach(book -> System.out.println(book));

        java.util.Date currentTime  = new java.util.Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentTime);
        calendar.add(Calendar.MONTH, 6);
        java.util.Date dueDateUpdate = calendar.getTime();

        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.set(2023,Calendar.MAY, 14, 0, 0, 0);
        calendar2.set(2023,Calendar.NOVEMBER, 14, 0, 0, 0);



        BorrowingManager borrowingManager = new BorrowingManagerImplDb(bookManager);

        borrowingManager.issueBook(book1, new Date(currentTime.getTime()) ,  new Date(dueDateUpdate.getTime()));
        borrowingManager.issueBook(book1, new Date(calendar1.getTime().getTime()),
                new Date(calendar2.getTime().getTime()));

        bookManager.getAllAvailableBooks().forEach(book -> System.out.println(book));

        try{
            borrowingManager.returnBook(7L);
        } catch (BorrowingNotFound e) {
            logger.error("Error : " + e);
        }
        //borrowingManager.returnBook(1L);
//
////
//        bookManager.getAllAvailableBooks().forEach(book -> System.out.println(book));
//
//
//        ReportGenerator reportGenerator = new ReportGeneratorImpl(bookManager,borrowingManager);
//        try{
//            reportGenerator.generateAllBooksReport();
//        } catch (RuntimeException e){
//            logger.error("Error: " + e);
//        }
//
//





    }
}