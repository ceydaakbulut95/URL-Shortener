package org.example.urlshortener.service;
import org.example.urlshortener.service.URLEncodingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
public class URLEncodingServiceIntegrationTest {

    @Container
    public static GenericContainer<?> zookeeper = new GenericContainer<>("zookeeper:3.8.1")
            .withExposedPorts(2181);

    @Autowired
    private URLEncodingService urlEncodingService;

    @Test
    void testShortenUrl() throws Exception {
        String zookeeperHost = zookeeper.getHost();
        Integer zookeeperPort = zookeeper.getFirstMappedPort();

        System.setProperty("zookeeper.host", zookeeperHost);
        System.setProperty("zookeeper.port", zookeeperPort.toString());

        String longUrl = "https://example.com";
        String shortUrl = urlEncodingService.shortenURL(longUrl);

        System.out.println(shortUrl);
        assertThat(shortUrl).isNotNull();

    }
}
