package org.example.urlshortener.service;

import org.example.urlshortener.model.URLModel;
import org.example.urlshortener.repository.URLRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class URLDatabaseService {

    @Autowired
    private URLRepository urlRepository;

    public void saveUrl(String shortUrl, String longUrl) {
        URLModel url = new URLModel();
        url.setShortUrl(shortUrl);
        url.setLongUrl(longUrl);
        url.setCreatedTime(LocalDateTime.now());
        urlRepository.save(url);
    }
}
