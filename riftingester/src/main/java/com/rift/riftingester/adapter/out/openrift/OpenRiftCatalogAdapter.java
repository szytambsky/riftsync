package com.rift.riftingester.adapter.out.openrift;

import com.rift.riftingester.application.port.out.CatalogSourcePort;
import com.rift.riftingester.config.OpenRiftProperties;
import com.rift.riftingester.domain.model.CatalogSnapshot;
import java.time.Clock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

/**
 * ## OpenRift catalog client
 * Downloads the raw OpenRift catalog and translates HTTP cache semantics into the application
 * port. Provider-specific HTTP details deliberately remain in this adapter.
 */
@Component
class OpenRiftCatalogAdapter implements CatalogSourcePort {
    private final OpenRiftProperties properties;
    private final RestClient client;
    private final Clock clock;

    OpenRiftCatalogAdapter(OpenRiftProperties properties, RestClient openRiftRestClient, Clock clock) {
        this.properties = properties;
        this.client = openRiftRestClient;
        this.clock = clock;
    }

    @Override
    public FetchResult fetchCatalog(String knownEtag) {
        ResponseEntity<String> response = client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/catalog")
                        .queryParam("langs", properties.catalogLanguages())
                        .build())
                .headers(headers -> addRequestHeaders(headers, knownEtag))
                .retrieve()
                .toEntity(String.class);

        String etag = response.getHeaders().getETag();
        if (response.getStatusCode().value() == 304) {
            return new FetchResult.NotModified(etag != null ? etag : knownEtag);
        }

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null || etag == null) {
            throw new IllegalStateException("OpenRift catalog response is missing a successful body or ETag");
        }

        return new FetchResult.Modified(
                new CatalogSnapshot(
                        null,
                        response.getBody(),
                        clock.instant()),
                        etag
        );
    }

    private void addRequestHeaders(HttpHeaders headers, String knownEtag) {
        headers.set(HttpHeaders.USER_AGENT, properties.userAgent());
        if (knownEtag != null) {
            headers.setIfNoneMatch(knownEtag);
        }
    }
}
