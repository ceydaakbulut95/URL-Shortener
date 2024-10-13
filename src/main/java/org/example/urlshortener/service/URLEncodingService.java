package org.example.urlshortener.service;

import org.apache.zookeeper.KeeperException;
import org.example.urlshortener.exception.URLNotFoundException;
import org.example.urlshortener.exception.URLShorteningException;
import org.example.urlshortener.model.URLModel;
import org.example.urlshortener.repository.URLRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class URLEncodingService {

    private static final Logger logger = LoggerFactory.getLogger(URLEncodingService.class);

    @Autowired
    private URLRepository urlRepository;

    @Autowired
    private URLDatabaseService urlDatabaseService;

    @Autowired
    private KeyGenerationService keyGenerationService;

    public String shortenURL(String longUrl) throws Exception, IOException, KeeperException {
        if (longUrl == null || longUrl.trim().isEmpty()) {
            logger.warn("Received empty or null longUrl");
            throw new URLShorteningException("Long URL can't be empty.");
        }

        try {
            logger.info("The shorter URL is being created.");

            String shortUrl;
            boolean isUnique;

            do {

                shortUrl = keyGenerationService.generateShortURL();

                isUnique = urlRepository.findByShortUrl(shortUrl).isEmpty();

                if (!isUnique) {
                    logger.warn("Generated a duplicate short URL. Trying UUID as a fallback.");
                    shortUrl = keyGenerationService.generateUUIDShortUrl();
                    isUnique = urlRepository.findByShortUrl(shortUrl).isEmpty();
                }
            } while (!isUnique);

            urlDatabaseService.saveUrl(shortUrl, longUrl);

            logger.info("The shorter URL has been created successfully.");

            return shortUrl;
        } catch (Exception e) {
            logger.error("Error while generating shorter URL", e);
            throw new URLShorteningException("Error while generating shorter URL: " + e.getMessage(), e);
        }
    }

    public String expandURL(String shortUrl) {
        return urlRepository.findByShortUrl(shortUrl)
                .map(URLModel::getLongUrl)
                .orElseThrow(() -> new URLNotFoundException("Short URL not found: " + shortUrl));
    }
}
