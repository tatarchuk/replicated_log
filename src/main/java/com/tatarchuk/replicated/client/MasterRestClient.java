package com.tatarchuk.replicated.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@ConditionalOnProperty(prefix = "master", name = "active", havingValue = "true")
@Service
public class MasterRestClient {

    @Value("${secondary}")
    List<String> nodeList;

    private RestTemplate restTemplate = new RestTemplate();

    public List<ResponseEntity> postMessageToAllSecondaryNodes(String message) {
        return nodeList.stream()
                .map(node -> postMessageToSecondaryNode(node, message))
                .toList();
    }

    private ResponseEntity postMessageToSecondaryNode(String url, String message) {
        restTemplate.postForLocation(url + "/api/v1/sync/appendList", message);
        return ResponseEntity.status(CREATED).build();
    }

}
