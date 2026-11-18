package com.rift.riftingester.application.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

interface SpringDataCatalogSyncRunRepository extends JpaRepository<CatalogSyncRunEntity, Long> { }
