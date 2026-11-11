package com.rift.riftingester.adapter.out.openrift;

import com.rift.riftingester.application.port.out.CatalogSourcePort;
import com.rift.riftingester.config.OpenRiftProperties;
import java.util.Optional;
import org.springframework.stereotype.Component;

/**
 * Outbound adapter template. Implement HTTP request, User-Agent and If-None-Match here only.
 * Map OpenRift's response DTO to CatalogSnapshot before crossing the port.
 */
@Component
class OpenRiftCatalogAdapter implements CatalogSourcePort {
    private final OpenRiftProperties properties;

    OpenRiftCatalogAdapter(OpenRiftProperties properties) {
        this.properties = properties;
    }

    @Override
    public FetchResult fetchCatalog(Optional<String> knownEtag) {
        throw new UnsupportedOperationException(
                "Template only: implement GET " + properties.baseUrl() + "/api/v1/catalog with ETag support");
    }
}
