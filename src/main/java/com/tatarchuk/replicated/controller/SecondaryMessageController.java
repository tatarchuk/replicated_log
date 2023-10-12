package com.tatarchuk.replicated.controller;

import com.tatarchuk.replicated.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@ConditionalOnProperty(prefix = "master", name = "active", havingValue = "false")
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/message")
public class SecondaryMessageController {

    private MessageService messageService;

    @GetMapping("/getList")
    public ResponseEntity<List<String>> getMessageList() {
        return ResponseEntity.ok(messageService.getMessageList());
    }
}
