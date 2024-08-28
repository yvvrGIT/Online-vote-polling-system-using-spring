package com.election.project.component;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class ElectionEventPublisher {
    private final SimpMessagingTemplate template;

    public ElectionEventPublisher(SimpMessagingTemplate template) {
        this.template = template;
    }

    public void publishElectionEvent(String message) {
        template.convertAndSend("/topic/election", message);
    }
}
