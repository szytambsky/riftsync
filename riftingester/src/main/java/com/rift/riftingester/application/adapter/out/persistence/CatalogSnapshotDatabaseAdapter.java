package com.rift.riftingester.application.adapter.out.persistence;

import com.rift.riftingester.application.port.out.CatalogSnapshotRepositoryPort;
import com.rift.riftingester.domain.model.CatalogSnapshot;
import java.time.Instant;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
class CatalogSnapshotDatabaseAdapter implements CatalogSnapshotRepositoryPort {
    private final CatalogSnapshotRepository snapshots;
    private final CatalogSyncRunRepository syncRuns;

    @Override
    public Optional<String> findLatestEtag() {
        CatalogSnapshotEntity catalogSnapshot = snapshots.findTopByOrderByFetchedAtDesc();
        return catalogSnapshot == null
                ? Optional.empty()
                : Optional.of(catalogSnapshot.etag);
    }

    @Transactional
    @Override
    public void save(CatalogSnapshot snapshot, String etag, Instant completedAt) {
        var entity = new CatalogSnapshotEntity();
        entity.fetchedAt = snapshot.fetchedAt();
        entity.etag = etag;
        entity.sourceVersion = snapshot.sourceVersion();
        entity.payload = snapshot.rawJson();
        snapshots.save(entity);
        record("SUCCEEDED", etag, "Catalog snapshot stored", completedAt);
    }

    @Transactional
    @Override
    public void recordUnchanged(String etag, Instant completedAt) {
        record("NOT_MODIFIED", etag, "Provider returned 304", completedAt);
    }

    @Transactional
    @Override
    public void recordFailure(String detail, Instant completedAt) {
        record("FAILED", null, detail, completedAt);
    }

    private void record(String status, String etag, String detail, Instant completedAt) {
        var run = new CatalogSyncRunEntity();
        run.status = status; run.etag = etag; run.detail = detail; run.completedAt = completedAt;
        syncRuns.save(run);
    }
}
