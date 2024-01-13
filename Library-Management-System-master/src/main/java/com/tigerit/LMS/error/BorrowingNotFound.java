package com.tigerit.LMS.error;

public class BorrowingNotFound extends RuntimeException{
    public BorrowingNotFound (String message){
        super(message);
    }
}
