CREATE TABLE catalog_snapshots (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    fetched_at TIMESTAMP WITH TIME ZONE NOT NULL,
    etag VARCHAR(255) NOT NULL,
    source_version VARCHAR(255),
    payload JSONB NOT NULL
);

CREATE INDEX catalog_snapshots_fetched_at_idx ON catalog_snapshots (fetched_at DESC);

CREATE TABLE catalog_sync_runs (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    completed_at TIMESTAMP WITH TIME ZONE NOT NULL,
    status VARCHAR(32) NOT NULL,
    etag VARCHAR(255),
    detail TEXT
);

CREATE INDEX catalog_sync_runs_completed_at_idx ON catalog_sync_runs (completed_at DESC);
