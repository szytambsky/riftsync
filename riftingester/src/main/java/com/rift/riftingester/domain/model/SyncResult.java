package com.rift.riftingester.domain.model;

import java.time.Instant;

public record SyncResult(SyncStatus status, Instant completedAt, String detail) { }
