package com.tatarchuk.replicated.controller;

import com.tatarchuk.replicated.service.MessageService;
import com.tatarchuk.replicated.service.SyncService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@ConditionalOnProperty(prefix = "master", name = "active", havingValue =  "true")
@RestController
@RequestMapping("/api/v1/message")
public class MasterMessageController {

    private MessageService messageService;
    private SyncService syncService;

    @GetMapping("/getList")
    public ResponseEntity<List<String>> getMessageList() {
        return ResponseEntity.ok(messageService.getMessageList());
    }

    @PostMapping("/appendList")
    public ResponseEntity appendMessageList(@RequestBody String message) {
        messageService.appendMessageList(message);
        syncService.syncMessageWithAllNodes(message);
        return ResponseEntity.status(CREATED).build();
    }
}
