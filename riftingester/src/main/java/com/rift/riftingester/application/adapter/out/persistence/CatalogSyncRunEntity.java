package com.rift.riftingester.application.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "catalog_sync_runs")
class CatalogSyncRunEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    String id;
    @Column(nullable = false)
    Instant completedAt;
    @Column(nullable = false)
    String status;
    String etag;
    String detail;
}
