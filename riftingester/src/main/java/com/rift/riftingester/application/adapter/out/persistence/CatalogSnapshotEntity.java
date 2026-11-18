package com.rift.riftingester.application.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;


@Entity
@Table(name = "catalog_snapshots")
class CatalogSnapshotEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    String id;
    @Column(nullable = false)
    Instant fetchedAt;
    @Column(nullable = false)
    String etag;
    String sourceVersion;
    @Column(nullable = false, columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    String payload;
}
