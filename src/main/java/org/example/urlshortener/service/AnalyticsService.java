package org.example.urlshortener.service;

import org.example.urlshortener.model.URLModel;
import org.example.urlshortener.repository.URLRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnalyticsService {

    @Autowired
    private URLRepository urlRepository;


    public List<URLModel> getMostAccessedUrls(int topN){
        return urlRepository.findAll()
                .stream()
                .sorted((u1, u2) -> Integer.compare(u2.getHitCount(), u1.getHitCount()))
                .limit(topN)
                .collect(Collectors.toList());
    }

    public long countTotalUrls(){
        return urlRepository.count();
    }
}
