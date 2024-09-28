package org.example.urlshortener.service;

import org.example.urlshortener.model.URLModel;
import org.example.urlshortener.repository.URLRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class URLCleanupService {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(URLEncodingService.class);

    @Autowired
    private URLRepository urlRepository;

    @Scheduled(cron = "0 0 0 * * ?")  //Daily at midnight
    public void cleanExpiredUrls() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expirationThreshold = now.minusYears(5);

        List<URLModel> expiredUrls = urlRepository.findAllByCreatedTimeBefore(expirationThreshold);

        for (URLModel url : expiredUrls) {
            urlRepository.delete(url);
        }

        logger.info("Expired URLs cleaned up!");
    }
}
