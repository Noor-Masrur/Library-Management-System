package com.tigerit.LMS.services;

import com.tigerit.LMS.entities.Book;
import com.tigerit.LMS.entities.Borrowing;

import java.sql.Date;
import java.time.LocalDate;

public interface BorrowingManager {

    public void issueBook(Book book, Date issueDate, Date dueDate);
    public double calculateFine(Borrowing borrowing);

    public void returnBook(Long issueId);

}
