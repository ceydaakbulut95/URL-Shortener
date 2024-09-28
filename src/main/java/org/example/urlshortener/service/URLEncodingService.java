package org.example.urlshortener.service;

import org.example.exception.URLShorteningException;
import org.example.urlshortener.model.URLModel;
import org.example.urlshortener.repository.URLRepository;
import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Base64;

@Service
public class URLEncodingService {

    private static final Logger logger = LoggerFactory.getLogger(URLEncodingService.class);
    @Autowired
    private URLRepository urlRepository;

    private ZooKeeper zooKeeper;
    private static final String COUNTER_PATH = "/url_counter";

    public URLEncodingService() throws Exception {
        zooKeeper = new ZooKeeper("localhost:2181", 2000, event -> {
            if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
                System.out.println("ZooKeeper connected");
            }
        });
        if (zooKeeper.exists(COUNTER_PATH, false) == null) {
            zooKeeper.create(COUNTER_PATH, "0".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            logger.info("The counter is ready for ZooKeeper.");
        }
    }

    public String shortenURL(String longUrl) throws Exception {
        if (longUrl == null || longUrl.trim().isEmpty()) {
            logger.warn("Received empty or null longUrl");
            throw new URLShorteningException("Long URL can't be empty.");
        }
        try {
            logger.info("The shorter URL is being created.");
            byte[] data = zooKeeper.getData(COUNTER_PATH, false, null);
            int counter = Integer.parseInt(new String(data));

            counter++;
            zooKeeper.setData(COUNTER_PATH, Integer.toString(counter).getBytes(), -1);
            String shortUrl = Base64.getUrlEncoder().encodeToString(String.valueOf(counter).getBytes());

            LocalDateTime expirationDate = LocalDateTime.now().plusYears(5);
            URLModel url = new URLModel();
            url.setShortUrl(shortUrl);
            url.setLongUrl(longUrl);
            url.setCreatedTime(LocalDateTime.now());
            url.setExpirationDate(expirationDate);
            urlRepository.save(url);
            logger.info("The shorter URL has been created successfully.");

            return shortUrl;
        } catch (Exception e) {
            logger.error("Error while generating shorter URL", e);
            throw new URLShorteningException("Error while generating shorter URL" + e.getMessage(), e);
        }
    }

    public String expandUrl(String shortUrl) {
        URLModel urlModel = urlRepository.findByShortUrl(shortUrl);
        if (urlModel != null) {
            logger.info("The short URL has been expanded.");
            return urlModel.getLongUrl();
        } else {
            logger.warn("The short URL not found...");
            return null;
        }
    }
}
