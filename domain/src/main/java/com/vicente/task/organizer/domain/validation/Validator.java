package com.vicente.task.organizer.domain.validation;

public abstract class Validator {
    private final ValidationHandler handler;


    public Validator(final ValidationHandler aHandler) {
        this.handler = aHandler;
    }

    public  abstract  void validate();

    protected ValidationHandler validationHandler() {
        return this.handler;
    }
}
