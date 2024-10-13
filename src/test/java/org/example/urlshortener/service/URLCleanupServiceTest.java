/*
package org.example.urlshortener.service;

import org.example.urlshortener.model.URLModel;
import org.example.urlshortener.repository.URLRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest // Full integration test with Spring
@Transactional
class URLCleanupServiceTest {

    @Autowired
    private URLCleanupService urlCleanupService;

    @Autowired
    private URLRepository urlRepository;

    @BeforeEach
    void setUp() {
        urlRepository.deleteAll();
    }

    @Test
    void testCleanExpiredUrls() {

        LocalDateTime expiredDate = LocalDateTime.now().minusYears(6);
        URLModel expiredUrl = new URLModel();
        expiredUrl.setShortUrl("shortUrl1");
        expiredUrl.setLongUrl("http://longurl.com/test1");
        expiredUrl.setCreatedTime(expiredDate);
        expiredUrl.setIsActive("Y");

        urlRepository.save(expiredUrl);

        urlCleanupService.cleanExpiredUrls();

        Optional<URLModel> retrievedUrlOptional = urlRepository.findByShortUrl("shortUrl1");

        assertTrue(retrievedUrlOptional.isPresent(), "The URL should be present in the repository.");

        URLModel retrievedUrl = retrievedUrlOptional.get();

        assertThat(retrievedUrl.getIsActive()).isEqualTo("N");
    }
}
*/