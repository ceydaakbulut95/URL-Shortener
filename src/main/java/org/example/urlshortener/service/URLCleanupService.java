package org.example.urlshortener.service;

import org.example.urlshortener.repository.URLRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

@Service
public class URLCleanupService {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(URLEncodingService.class);

    @Autowired
    private URLRepository urlRepository;

    @Scheduled(cron = "0 0 0 * * ?")  //Daily at midnight
    public void cleanExpiredUrls() {

        LocalDateTime fiveYearsAgo = LocalDateTime.now().minusYears(5);
        urlRepository.deactivateOldUrls(fiveYearsAgo);

        logger.info("Expired URLs cleaned up!");
    }
}
