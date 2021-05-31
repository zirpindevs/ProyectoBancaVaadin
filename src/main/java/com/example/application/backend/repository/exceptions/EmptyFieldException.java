package com.example.application.backend.repository.exceptions;

public class EmptyFieldException extends Exception{

    @Override
    public String getMessage(){

        return "All fields required";
    }
}
