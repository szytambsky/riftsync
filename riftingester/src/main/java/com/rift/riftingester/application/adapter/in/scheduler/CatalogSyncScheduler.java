package com.rift.riftingester.application.adapter.in.scheduler;

import com.rift.riftingester.application.port.in.SynchronizeCatalogUseCase;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "ingester.sync.scheduler-enabled", havingValue = "true")
class CatalogSyncScheduler {
    private final SynchronizeCatalogUseCase synchronizeCatalog;

    CatalogSyncScheduler(SynchronizeCatalogUseCase synchronizeCatalog) {
        this.synchronizeCatalog = synchronizeCatalog;
    }

    @Scheduled(cron = "${ingester.sync.cron}")
    void synchronize() {
        synchronizeCatalog.synchronize();
    }
}
