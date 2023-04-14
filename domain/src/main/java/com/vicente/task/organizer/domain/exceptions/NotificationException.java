package com.vicente.task.organizer.domain.exceptions;

import com.vicente.task.organizer.domain.validation.handler.Notification;

public class NotificationException extends DomainException{
    public NotificationException(String aMessage, final Notification notification) {
        super(aMessage,notification.getErrors());
    }
}
