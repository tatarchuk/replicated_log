package com.tatarchuk.replicated.controller;

import com.tatarchuk.replicated.service.MessageService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@ConditionalOnProperty(prefix = "master", name = "active", havingValue = "false")
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/sync")
public class SecondarySyncController {

    private MessageService messageService;

    @SneakyThrows
    @PostMapping("/appendList")
    public ResponseEntity appendMessageList(@RequestBody String message) {
        log.info("Secondary: Sync Append RQ is received with message {}", message);
        messageService.appendMessageList(message);
        Thread.sleep(15000);
        log.info("Secondary: Append RQ is finished for message {}", message);
        return ResponseEntity.status(CREATED).build();
    }
}
