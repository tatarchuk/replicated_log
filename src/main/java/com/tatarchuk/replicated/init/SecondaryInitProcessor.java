package com.tatarchuk.replicated.init;

import com.tatarchuk.replicated.client.SecondaryRestClient;
import com.tatarchuk.replicated.service.MessageService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@ConditionalOnProperty(prefix = "master", name = "active", havingValue = "false")
@Component
public class SecondaryInitProcessor implements ApplicationListener<ApplicationReadyEvent> {

    private MessageService messageService;
    private SecondaryRestClient secondaryRestClient;

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        messageService.init(secondaryRestClient.getInitialMessageList());
        return;
    }
}
