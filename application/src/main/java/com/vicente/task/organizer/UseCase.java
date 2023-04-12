package com.vicente.task.organizer;

public abstract class UseCase<IN, OUT> {

    public abstract OUT execute(IN anIn);
}
