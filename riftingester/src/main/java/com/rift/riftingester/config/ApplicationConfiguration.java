package com.rift.riftingester.config;

import java.time.Clock;
import java.net.http.HttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
class ApplicationConfiguration {

    @Bean Clock clock() {
        return Clock.systemUTC();
    }

    /** Creates the only HTTP client used for the OpenRift catalog. */
    @Bean
    RestClient openRiftRestClient(OpenRiftProperties properties) {
        var httpClient = HttpClient.newBuilder()
                .connectTimeout(properties.connectTimeout())
                .build();
        var requestFactory = new JdkClientHttpRequestFactory(httpClient);
        requestFactory.setReadTimeout(properties.readTimeout());

        return RestClient.builder()
                .baseUrl(properties.baseUrl())
                .requestFactory(requestFactory)
                .build();
    }
}
