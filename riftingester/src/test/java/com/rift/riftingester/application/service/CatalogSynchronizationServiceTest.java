package com.rift.riftingester.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.rift.riftingester.application.port.out.CatalogSnapshotRepositoryPort;
import com.rift.riftingester.application.port.out.CatalogSourcePort;
import com.rift.riftingester.domain.model.CatalogSnapshot;
import com.rift.riftingester.domain.model.SyncStatus;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class CatalogSynchronizationServiceTest {
    @Test
    void storesSnapshotWhenProviderHasNewCatalog() {
        var source = (CatalogSourcePort) etag -> new CatalogSourcePort.FetchResult.Modified(
                new CatalogSnapshot("v1", "{}", Instant.EPOCH), "etag-1");
        var repository = new RecordingRepository();
        var service = new CatalogSynchronizationService(source, repository, Clock.fixed(Instant.EPOCH, ZoneOffset.UTC));

        var result = service.synchronize();

        assertEquals(SyncStatus.SUCCEEDED, result.status());
        assertEquals("etag-1", repository.savedEtag);
    }

    private static final class RecordingRepository implements CatalogSnapshotRepositoryPort {
        String savedEtag;
        public Optional<String> findLatestEtag() { return Optional.empty(); }
        public void save(CatalogSnapshot snapshot, String etag, Instant completedAt) { savedEtag = etag; }
        public void recordUnchanged(String etag, Instant completedAt) { }
        public void recordFailure(String detail, Instant completedAt) { }
    }
}
