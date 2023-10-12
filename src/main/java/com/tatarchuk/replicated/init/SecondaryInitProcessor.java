package com.tatarchuk.replicated.init;

import com.tatarchuk.replicated.client.SecondaryRestClient;
import com.tatarchuk.replicated.service.MessageService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@ConditionalOnProperty(prefix = "master", name = "active", havingValue = "false")
@Component
public class SecondaryInitProcessor{

    private MessageService messageService;
    private SecondaryRestClient secondaryRestClient;

    @EventListener
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        messageService = event.getApplicationContext().getBean(MessageService.class);
        secondaryRestClient = event.getApplicationContext().getBean(SecondaryRestClient.class);
        messageService.init(secondaryRestClient.getInitialMessageList());
        return;
    }
}
