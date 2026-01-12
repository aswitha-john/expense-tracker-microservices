package com.expense.expense_service.exception;

public class InvalidInputException  extends RuntimeException{

    public InvalidInputException(String message){
        super(message);
    }
}
