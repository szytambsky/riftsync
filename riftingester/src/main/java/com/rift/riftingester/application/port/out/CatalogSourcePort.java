package com.rift.riftingester.application.port.out;

import com.rift.riftingester.domain.model.CatalogSnapshot;

/** Anti-corruption layer: application code knows no OpenRift HTTP DTOs. */
public interface CatalogSourcePort {
    FetchResult fetchCatalog(String knownEtag);

    sealed interface FetchResult permits FetchResult.Modified, FetchResult.NotModified {
        record Modified(CatalogSnapshot snapshot, String etag) implements FetchResult { }
        record NotModified(String etag) implements FetchResult { }
    }
}
