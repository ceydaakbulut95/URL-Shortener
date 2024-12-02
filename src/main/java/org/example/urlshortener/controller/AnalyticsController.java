package org.example.urlshortener.controller;

import org.example.urlshortener.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping("/total-urls")
    public ResponseEntity<Long> getTotalUrlCount(){
        return ResponseEntity.ok(analyticsService.countTotalUrls());
    }

    @GetMapping("/top-accessed")
    public ResponseEntity<?> getTopAccessedUrls(){
        return ResponseEntity.ok(analyticsService.getMostAccessedUrls(10));
    }
}
