package com.rift.riftingester.application.port.out;

import com.rift.riftingester.domain.model.CatalogSnapshot;
import java.util.Optional;

/** Anti-corruption layer: application code knows no OpenRift HTTP DTOs. */
public interface CatalogSourcePort {
    FetchResult fetchCatalog(Optional<String> knownEtag);

    sealed interface FetchResult permits FetchResult.Modified, FetchResult.NotModified {
        record Modified(CatalogSnapshot snapshot, String etag) implements FetchResult { }
        record NotModified(String etag) implements FetchResult { }
    }
}
