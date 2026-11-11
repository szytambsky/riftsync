package com.rift.riftingester.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

interface SpringDataCatalogSyncRunRepository extends JpaRepository<CatalogSyncRunEntity, Long> { }
