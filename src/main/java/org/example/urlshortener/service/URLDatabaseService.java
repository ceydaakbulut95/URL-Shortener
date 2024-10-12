package org.example.urlshortener.service;

import org.example.urlshortener.model.URLModel;
import org.example.urlshortener.repository.URLRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class URLDatabaseService {

    @Autowired
    private URLRepository urlRepository;

    private static final Logger logger = LoggerFactory.getLogger(URLEncodingService.class);

    public void saveUrl(String shortUrl, String longUrl) {
        logger.debug("Saving URL: shortUrl={}, longUrl={}", shortUrl, longUrl);
        URLModel url = new URLModel();
        url.setShortUrl(shortUrl);
        url.setLongUrl(longUrl);
        url.setCreatedTime(LocalDateTime.now());
        url.setIsActive("Y");
        urlRepository.save(url);
    }
}
