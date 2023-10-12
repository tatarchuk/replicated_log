package com.tatarchuk.replicated.service;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class MessageService {

    private List<String> messageList = new LinkedList<>();

    public List<String> getMessageList() {
        return messageList;
    }

    public void appendMessageList(String message) {
        messageList.add(message);
    }

    public void init(List<String> messageList) {
        this.messageList = messageList;
    }
}
