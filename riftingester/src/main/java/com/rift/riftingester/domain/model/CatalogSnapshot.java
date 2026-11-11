package com.rift.riftingester.domain.model;

import java.time.Instant;
import java.util.Objects;

/**
 * Immutable representation of a successful provider response.
 */
public record CatalogSnapshot(String sourceVersion, String rawJson, Instant fetchedAt) {

    public CatalogSnapshot {
        Objects.requireNonNull(rawJson, "rawJson must not be null");
        Objects.requireNonNull(fetchedAt, "fetchedAt must not be null");
    }
}
