package com.tatarchuk.replicated.service;

import com.tatarchuk.replicated.client.MasterRestClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SyncService {

    private MasterRestClient masterRestClient;

    public List<ResponseEntity> syncMessageWithAllNodes(String message) {
        return masterRestClient.postMessageToAllSecondaryNodes(message);
    }
}
