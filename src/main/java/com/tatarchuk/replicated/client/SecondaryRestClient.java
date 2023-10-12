package com.tatarchuk.replicated.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@ConditionalOnProperty(prefix = "master", name = "active", havingValue = "false")
@Service
public class SecondaryRestClient {

    @Value("${master.server}")
    private String masterServer;

    @Value("${master.port}")
    private String masterPort;

    private RestTemplate restTemplate = new RestTemplate();

    public List<String> getInitialMessageList() {
        log.info("getInitialMessageList: server {} port {}", masterServer, masterPort);
        ResponseEntity<String[]> response
                = restTemplate.getForEntity(
                        masterServer + ":"+ masterPort + "/api/v1/message/getList", String[].class);
        List<String> result = new LinkedList<>();
        Collections.addAll(result, response.getBody());
        return result;

    }
}
