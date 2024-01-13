package com.tigerit.LMS.error;

public class BookNotFound extends RuntimeException{
    public BookNotFound (String message){
        super(message);
    }
}
