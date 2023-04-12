package com.vicente.task.organizer.task;

import com.vicente.task.organizer.validation.Error;
import com.vicente.task.organizer.validation.ValidationHandler;
import com.vicente.task.organizer.validation.Validator;

public class TaskValidator extends Validator {

    private static final int NAME_MAX_LENGTH = 255;
    private static final int NAME_MIN_LENGTH = 1;
    private final Task task;

    public TaskValidator(Task task, final ValidationHandler aHandler) {
        super(aHandler);
        this.task = task;
    }

    @Override
    public void validate() {
        checkConstraints(task.getName(),"name");
        checkConstraints(task.getDescription(),"description");
    }

    private void checkConstraints(final String aConstraint,final String fieldName) {
        if (aConstraint == null) {
            this.validationHandler().append(new Error("'%s' should not be null".formatted(fieldName)));
            return;
        }
        if (aConstraint.isBlank()) {
            this.validationHandler().append(new Error("'%s' should not be empty".formatted(fieldName)));
            return;
        }

        final int length = aConstraint.trim().length();
        if (length > NAME_MAX_LENGTH || length < NAME_MIN_LENGTH) {
            this.validationHandler().append(new Error("'name' must be between 1 and 255 characteres"));
        }
    }


}
