package com.rift.riftingester.application.port.in;

import com.rift.riftingester.domain.model.SyncResult;

/**
 * Application boundary used by HTTP, scheduler, CLI or a message consumer.
 * */
public interface SynchronizeCatalogUseCase {
    SyncResult synchronize();
}
