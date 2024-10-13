package org.example.urlshortener.service;

import org.apache.zookeeper.ZooKeeper;
import org.example.urlshortener.service.KeyGenerationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Base64;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class KeyGenerationServiceTest {

    private KeyGenerationService keyGenerationService;
    private ZooKeeper mockZooKeeper;

    @BeforeEach
    void setUp() throws Exception {
        mockZooKeeper = Mockito.mock(ZooKeeper.class);
        keyGenerationService = new KeyGenerationService(mockZooKeeper);
        assertNotNull(keyGenerationService);
    }

    @Test
    void testGenerateShortURL() throws Exception {
        when(mockZooKeeper.getData("/url_counter", false, null)).thenReturn("0".getBytes());
        when(mockZooKeeper.setData("/url_counter", "1".getBytes(), -1)).thenReturn(null);

        keyGenerationService = new KeyGenerationService(mockZooKeeper);

        String shortUrl = keyGenerationService.generateShortURL();

        String expectedShortUrl = Base64.getUrlEncoder().withoutPadding().encodeToString("1".getBytes());

        if (expectedShortUrl.length() < 7) {
            expectedShortUrl = expectedShortUrl + "00000";
        }

        assertEquals(expectedShortUrl, shortUrl);
    }

    @Test
    void testGenerateUUIDShortUrl() {
        String uuidShortUrl = keyGenerationService.generateUUIDShortUrl();
        String expectedUUID = Base64.getUrlEncoder().withoutPadding().encodeToString(UUID.randomUUID().toString().getBytes()).substring(0, 7);

        assertEquals(7, uuidShortUrl.length());
    }

}
