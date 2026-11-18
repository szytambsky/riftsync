package com.rift.riftingester.application.port.out;

import com.rift.riftingester.domain.model.CatalogSnapshot;
import java.time.Instant;

/** Persistence port. Its implementation owns the transactional database schema. */
public interface CatalogSnapshotRepositoryPort {
    String findLatestEtag();
    void save(CatalogSnapshot snapshot, String etag, Instant completedAt);
    void recordUnchanged(String etag, Instant completedAt);
    void recordFailure(String detail, Instant completedAt);
}
