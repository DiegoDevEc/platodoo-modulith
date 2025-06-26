package com.playtodoo.modulith.notifications;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    public void send(String message) {
        messagingTemplate.convertAndSend("/topic/notifications", message);
    }
}
