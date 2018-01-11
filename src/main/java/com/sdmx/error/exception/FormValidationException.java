package com.sdmx.error.exception;

import java.util.LinkedList;
import java.util.List;

public class FormValidationException extends RuntimeException {
    List<String> messages = new LinkedList<>();

    public FormValidationException(String message) {
        pushMessage(message);
    }

    public void pushMessage(String message) {
        messages.add(message);
    }

    public List<String> getMessages() {
        return messages;
    }
}
