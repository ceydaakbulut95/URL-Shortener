package org.example.urlshortener.repository;

import org.example.urlshortener.model.URLModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface URLRepository extends JpaRepository<URLModel, Long> {
    Optional<URLModel> findByShortUrl(String shortUrl);

    @Modifying
    @Transactional
    @Query("UPDATE URLModel u SET u.isActive = 'N' WHERE u.createdTime < :fiveYearsAgo")
    void deactivateOldUrls(LocalDateTime fiveYearsAgo);

    //hitCount to track the nr of times a URL is accessed.
    @Modifying
    @Transactional
    @Query("UPDATE URLModel u SET u.hitCount = u.hitCount + 1 WHERE u.shortUrl = :shortUrl")
    void incrementHitCount(String shortUrl);

}


