package com.tigerit.LMS.error;

public class BookAlreadyPresent extends RuntimeException{
    public BookAlreadyPresent(String msg){
        super(msg);
    }
    public BookAlreadyPresent(String msg,Long bookId){
        super(msg);
        String message = "Book no: " + bookId + " already exists";

    }

}
