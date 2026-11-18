package com.rift.riftingester.config;

import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * ## OpenRift connection settings
 * Keeps the provider URL, contact-bearing user agent, timeout and imported languages outside
 * application code.
 */
@ConfigurationProperties("ingester.openrift")
public record OpenRiftProperties(
        String baseUrl,
        String userAgent,
        Duration connectTimeout,
        Duration readTimeout,
        String catalogLanguages
) {}
