package com.tatarchuk.replicated.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@ConditionalOnProperty(prefix = "master", name = "active", havingValue = "true")
@Service
public class MasterRestClient {

    @Value("${secondary}")
    private List<String> nodeList;

    private RestTemplate restTemplate = new RestTemplate();

    public List<ResponseEntity> postMessageToAllSecondaryNodes(String message) {
        return nodeList.parallelStream()
                .map(node -> postMessageToSecondaryNode(node, message))
                .toList();
    }

    private ResponseEntity postMessageToSecondaryNode(String url, String message) {
        log.info("Master: message {} is posting to node {}", message, url);
        restTemplate.postForLocation(url + "/api/v1/sync/appendList", message);
        log.info("Master: message {} successfully posted to node {}", message, url);
        return ResponseEntity.status(CREATED).build();
    }

}
