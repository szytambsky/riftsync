package com.rift.riftingester.config;

import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("ingester.openrift")
public record OpenRiftProperties(String baseUrl, String userAgent, Duration connectTimeout) { }
