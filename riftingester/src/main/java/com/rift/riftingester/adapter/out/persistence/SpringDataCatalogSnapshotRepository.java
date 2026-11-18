package com.rift.riftingester.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

interface SpringDataCatalogSnapshotRepository extends JpaRepository<CatalogSnapshotEntity, Long> {
    CatalogSnapshotEntity findTopByOrderByFetchedAtDesc();
}
