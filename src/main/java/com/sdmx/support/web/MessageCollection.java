package com.sdmx.support.web;

import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MessageCollection {

//    private List<Message> messages = new ArrayList<>();
    private List<Message> messages = new LinkedList<>();

    public void error(String message, String... args) {
        messages.add(new Message(message, Message.Type.DANGER, args));
    }

    public void info(String message, String... args) {
        messages.add(new Message(message, Message.Type.INFO, args));
    }

    public void warning(String message, String... args) {
        messages.add(new Message(message, Message.Type.WARNING, args));
    }

    public void success(String message, String... args) {
        messages.add(new Message(message, Message.Type.SUCCESS, args));
    }

    public List<Message> getMessages() {
        return messages;
    }
}
