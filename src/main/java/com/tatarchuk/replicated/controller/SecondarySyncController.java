package com.tatarchuk.replicated.controller;

import com.tatarchuk.replicated.service.MessageService;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@ConditionalOnProperty(prefix = "master", name = "active", havingValue = "false")
@RestController
@RequestMapping("/api/v1/sync")
public class SecondarySyncController {

    private MessageService messageService;

    @SneakyThrows
    @PostMapping("/appendList")
    public ResponseEntity appendMessageList(@RequestBody String message) {
        messageService.appendMessageList(message);
        Thread.sleep(15000);
        return ResponseEntity.status(CREATED).build();
    }
}
