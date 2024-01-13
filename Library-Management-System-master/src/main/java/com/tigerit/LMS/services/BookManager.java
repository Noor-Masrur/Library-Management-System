package com.tigerit.LMS.services;

import com.tigerit.LMS.entities.Book;

import java.util.ArrayList;
import java.util.List;

public interface BookManager {


    public void addBook(Book book);
    public Book getBookById(Long bookId);
    public void updateBook(Book updatedBook);
    public void deleteBook(Long bookId);
    public List<Book> searchBookByTitle(String Keyword);
    public List<Book> searchBookByAuthor(String Keyword);
    public List<Book> getAllAvailableBooks();

}
