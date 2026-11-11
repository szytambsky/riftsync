package com.rift.riftingester.adapter.out.persistence;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

interface SpringDataCatalogSnapshotRepository extends JpaRepository<CatalogSnapshotEntity, Long> {
    Optional<CatalogSnapshotEntity> findTopByOrderByFetchedAtDesc();
}
