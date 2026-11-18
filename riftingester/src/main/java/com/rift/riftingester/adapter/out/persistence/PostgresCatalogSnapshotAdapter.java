package com.rift.riftingester.adapter.out.persistence;

import com.rift.riftingester.application.port.out.CatalogSnapshotRepositoryPort;
import com.rift.riftingester.domain.model.CatalogSnapshot;
import java.time.Instant;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
class PostgresCatalogSnapshotAdapter implements CatalogSnapshotRepositoryPort {
    private final SpringDataCatalogSnapshotRepository snapshots;
    private final SpringDataCatalogSyncRunRepository syncRuns;

    PostgresCatalogSnapshotAdapter(SpringDataCatalogSnapshotRepository snapshots, SpringDataCatalogSyncRunRepository syncRuns) {
        this.snapshots = snapshots;
        this.syncRuns = syncRuns;
    }

    @Override
    public String findLatestEtag() {
        CatalogSnapshotEntity catalogSnapshot = snapshots.findTopByOrderByFetchedAtDesc();
        return catalogSnapshot.etag;
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
