package org.example.urlshortener.repository;

import org.example.urlshortener.model.URLModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface URLRepository extends JpaRepository<URLModel, Long> {
    URLModel findByShortUrl(String shortUrl);

    List<URLModel> findAllByCreatedTimeBefore(LocalDateTime createdTime);
}


