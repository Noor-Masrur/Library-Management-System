package com.tigerit.LMS.services;

import com.tigerit.LMS.entities.Book;
import com.tigerit.LMS.entities.Borrowing;
import com.tigerit.LMS.error.BorrowingNotFound;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class BorrowingManagerImpl implements BorrowingManager{
    private List<Borrowing> borrowings;
    private BookManager bookManager;
    private static Long countId = 0L;


    public BorrowingManagerImpl(BookManager bookManager) {
        this.borrowings = new ArrayList<>();
        this.bookManager = bookManager;
    }

    @Override
    public void issueBook(Book book, Date issueDate, Date dueDate) {
        if (book.getNumberOfCopies() > 0) {
            Borrowing borrowing = new Borrowing(
                    book.getBookId(), countId++,  issueDate , dueDate
            );
            borrowings.add(borrowing);
            book.setNumberOfCopies(book.getNumberOfCopies()-1);

            bookManager.updateBook(book);
        } else {
            //throw new IllegalStateException("No more copies of " + book.getTitle() + "available");
            System.out.println("No more copies of " + book.getTitle() + "available");
        }
    }

    @Override
    public double calculateFine(Borrowing borrowing) {
        double fine = 0.0;
        Date dueDate = borrowing.getDueDate();

        if (LocalDate.now().isAfter(dueDate.toLocalDate())) {
            long daysBetween = (LocalDate.now().toEpochDay() - dueDate.toLocalDate().toEpochDay());
            int dueDays = Math.toIntExact(daysBetween);
            fine = dueDays * 2.0;
        }

        return fine;
    }

    @Override
    public void returnBook(Long issueId) {
        Borrowing borrowing = borrowings.stream()
                .filter(b -> b.getIssueId()
                        .equals(issueId))
                .findFirst()
                .orElseThrow(() -> new BorrowingNotFound("No borrowing of issue id: "
                        + issueId
                        + " found"));
        System.out.println(borrowings);

        double fine = calculateFine(borrowing);
        System.out.println(fine);

        if (fine > 0.0) {
            System.out.println("Pay fine " + fine + "tk before returning the book");
        }

        Book updatedBook = bookManager.getBookById(borrowing.getIssuedBookId());
        updatedBook.setNumberOfCopies(updatedBook.getNumberOfCopies()+1);

        bookManager.updateBook(updatedBook);

        if (borrowings.indexOf(borrowing) != -1) {
            borrowings.remove(borrowing);
        } else {
            throw new BorrowingNotFound("No borrowing data available for issueId: "+issueId);
        }
    }
}
