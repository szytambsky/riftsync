package com.rift.riftingester.application.service;

import com.rift.riftingester.application.port.in.SynchronizeCatalogUseCase;
import com.rift.riftingester.application.port.out.CatalogSnapshotRepositoryPort;
import com.rift.riftingester.application.port.out.CatalogSourcePort;
import com.rift.riftingester.domain.model.SyncResult;
import com.rift.riftingester.domain.model.SyncStatus;
import java.time.Clock;
import java.time.Instant;
import org.springframework.stereotype.Service;

/** Coordinates a catalog synchronization use case. */
@Service
public class CatalogSynchronizationService implements SynchronizeCatalogUseCase {
    private final CatalogSourcePort source;
    private final CatalogSnapshotRepositoryPort repository;
    private final Clock clock;

    public CatalogSynchronizationService(CatalogSourcePort source,
                                         CatalogSnapshotRepositoryPort repository,
                                         Clock clock) {
        this.source = source;
        this.repository = repository;
        this.clock = clock;
    }

    @Override
    public SyncResult synchronize() {
        Instant completedAt = clock.instant();
        try {
            var result = source.fetchCatalog(repository.findLatestEtag().orElse(null));
            if (result instanceof CatalogSourcePort.FetchResult.Modified modified) {
                repository.save(modified.snapshot(), modified.etag(), completedAt);
                return new SyncResult(SyncStatus.SUCCEEDED, completedAt, "Catalog snapshot stored");
            }
            var notModified = (CatalogSourcePort.FetchResult.NotModified) result;
            repository.recordUnchanged(notModified.etag(), completedAt);
            return new SyncResult(SyncStatus.NOT_MODIFIED, completedAt, "Provider returned 304 Not Modified");
        } catch (RuntimeException exception) {
            repository.recordFailure(exception.getMessage(), completedAt);
            throw exception;
        }
    }
}
