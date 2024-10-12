package org.example.urlshortener.service;

import org.apache.zookeeper.KeeperException;
import org.example.urlshortener.exception.URLNotFoundException;
import org.example.urlshortener.exception.URLShorteningException;
import org.example.urlshortener.model.URLModel;
import org.example.urlshortener.repository.URLRepository;
import org.example.urlshortener.service.URLDatabaseService;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;

@Service
public class URLEncodingService {

    private static final Logger logger = LoggerFactory.getLogger(URLEncodingService.class);

    @Autowired
    private URLRepository urlRepository;

    @Autowired
    private URLDatabaseService urlDatabaseService;

    private final ZooKeeper zooKeeper;
    private static final String COUNTER_PATH = "/url_counter";

    public URLEncodingService() throws Exception, IOException, KeeperException {

        zooKeeper = new ZooKeeper("zookeeper:2181", 2000, null);

        if (zooKeeper.exists(COUNTER_PATH, false) == null) {
            zooKeeper.create(COUNTER_PATH, "0".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            logger.info("The counter is ready for ZooKeeper.");
        }
    }

    public String shortenURL(String longUrl) throws Exception, IOException, KeeperException {
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
