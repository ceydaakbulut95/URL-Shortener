package org.example.urlshortener.controller;

import jakarta.validation.Valid;
import org.example.urlshortener.dto.UrlRequestDTO;
import org.example.urlshortener.model.URLModel;
import org.example.urlshortener.service.URLEncodingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class URLController {
    @Autowired
    private URLEncodingService urlEncodingService;

    @PostMapping("/shorten")
    public ResponseEntity<String> shortenUrl(@Valid @RequestBody UrlRequestDTO urlRequest) throws Exception {
        String shortUrl = urlEncodingService.shortenURL(urlRequest.getLongUrl());
        return ResponseEntity.ok(shortUrl);
    }

    @GetMapping("/expand/{shortUrl}")
    public ResponseEntity<String> expandUrl(@PathVariable String shortUrl) {
        String longUrl = urlEncodingService.expandURL(shortUrl);
        if (longUrl != null) {
            return ResponseEntity.ok(longUrl);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
