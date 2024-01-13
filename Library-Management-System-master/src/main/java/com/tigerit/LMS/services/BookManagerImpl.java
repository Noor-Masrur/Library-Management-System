package com.tigerit.LMS.services;

import com.tigerit.LMS.entities.Book;
import com.tigerit.LMS.error.BookAlreadyPresent;
import com.tigerit.LMS.error.BookNotFound;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookManagerImpl implements BookManager{

    private List<Book> books;

    public BookManagerImpl() {
        this.books = new ArrayList<>();
    }

    @Override
    public void addBook(Book book) {

            if (books.stream().anyMatch(b -> b.getBookId()
                    .equals(book.getBookId()))) {
                throw new BookAlreadyPresent("Book no: "
                        + book.getBookId()
                        + " titled \""
                        + book.getTitle()
                        + "\" is already present");
            }

            books.add(book);
    }

    @Override
    public Book getBookById(Long bookId) {
        return books.stream()
                .filter(b->b.getBookId().equals(bookId))
                .findFirst().orElseThrow(() -> new BookNotFound("Book no: "
                        + bookId
                        + " not found"));
    }

    @Override
    public void updateBook(Book updatedBook) {
        int index = books.indexOf(getBookById(updatedBook.getBookId()));

        if (index!=-1) {
            books.set(index,updatedBook);
        } else {
            throw new BookNotFound("Book no: "
                    + updatedBook.getBookId()
                    +" not found");
        }
    }

    @Override
    public void deleteBook(Long bookId) {
        if (!books.remove(getBookById(bookId))) {
            throw new BookNotFound("Book no: "
                    + bookId
                    +" not found");
        }
    }

    @Override
    public List<Book> searchBookByTitle(String keyword) {
        return books.stream()
                .filter(b->b.getTitle()
                        .toLowerCase()
                        .contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> searchBookByAuthor(String keyword) {
        return books.stream()
                .filter(b -> b.getAuthor()
                        .toLowerCase()
                        .contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> getAllAvailableBooks() {
        return books.stream()
                .filter(b -> b.getNumberOfCopies() > 0)
                .collect(Collectors.toList());
    }


}
