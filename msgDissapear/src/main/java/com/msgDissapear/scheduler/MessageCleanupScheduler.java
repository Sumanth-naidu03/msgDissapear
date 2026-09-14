package com.msgDissapear.scheduler;

import com.msgDissapear.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MessageCleanupScheduler {

    private static final Logger log = LoggerFactory.getLogger(MessageCleanupScheduler.class);

    private final MessageService messageService;

    public MessageCleanupScheduler(MessageService messageService) {
        this.messageService = messageService;
    }

    @Scheduled(cron = "${message.cleanup.cron:0/60 * * * * *}")
    public void purgeExpiredMessages() {
        log.debug("Running expired message cleanup...");
        int deleted = messageService.purgeExpired();
        log.debug("Cleanup complete. Deleted: {}", deleted);
    }
}
