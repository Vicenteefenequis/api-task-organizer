package com.vicente.task.organizer.domain.exceptions;

import com.vicente.task.organizer.domain.AggregateRoot;
import com.vicente.task.organizer.domain.Identifier;
import com.vicente.task.organizer.domain.validation.Error;

import java.util.Collections;
import java.util.List;

public class NotFoundException extends DomainException {
    protected NotFoundException(final String aMessage, final List<Error> anErrors) {
        super(aMessage, anErrors);
    }

    public static NotFoundException with(final Class<? extends AggregateRoot<?>> anAggregate, final Identifier anId) {
        final var anError = "%s with ID %s was not found".formatted(anAggregate.getSimpleName(), anId.getValue());
        return new NotFoundException(anError, Collections.emptyList());
    }
}