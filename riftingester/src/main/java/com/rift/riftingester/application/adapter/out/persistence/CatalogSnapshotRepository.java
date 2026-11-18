package com.rift.riftingester.application.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

interface CatalogSnapshotRepository extends JpaRepository<CatalogSnapshotEntity, Long> {
    CatalogSnapshotEntity findTopByOrderByFetchedAtDesc();
}
