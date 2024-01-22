package com.tigerit.LMS.entities;


import java.sql.Date;

public class Book {
    private String title;
    private String author;
    private Long bookId;
    private Date publicationDate;
    private Genre genre;
    private Integer numberOfCopies;

    public Book(String title,
                String author,
                Long bookId,
                Date publicationDate,
                Genre genre,
                Integer numberOfCopies) {
        this.title = title;
        this.author = author;
        this.bookId = bookId;
        this.publicationDate = publicationDate;
        this.genre = genre;
        this.numberOfCopies = numberOfCopies;
    }

    public Book() {
    }

    public Book(Long bookId) {
        this.bookId = bookId;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Integer getNumberOfCopies() {
        return numberOfCopies;
    }

    public void setNumberOfCopies(Integer numberOfCopies) {
        this.numberOfCopies = numberOfCopies;
    }


    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", bookId=" + bookId +
                ", publicationDate=" + publicationDate +
                ", genre=" + genre +
                ", numberOfCopies=" + numberOfCopies +
                '}';
    }
}
