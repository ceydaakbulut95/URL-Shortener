package org.example.urlshortener.service;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@Service
public class KeyGenerationService {

    private static final Logger logger = LoggerFactory.getLogger(KeyGenerationService.class);

    private final ZooKeeper zooKeeper;
    private static final String COUNTER_PATH = "/url_counter";

    public KeyGenerationService() throws Exception, IOException, KeeperException {
        zooKeeper = new ZooKeeper("zookeeper:2181", 2000, null);

        if (zooKeeper.exists(COUNTER_PATH, false) == null) {
            zooKeeper.create(COUNTER_PATH, "0".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            logger.info("ZooKeeper counter initialized.");
        }
    }

    //for testing
    public KeyGenerationService(ZooKeeper zooKeeper) {
        this.zooKeeper = zooKeeper;
    }

    public String generateShortURL() throws Exception, IOException, KeeperException {
        byte[] data = zooKeeper.getData(COUNTER_PATH, false, null);
        int counter = Integer.parseInt(new String(data));

        counter++;
        zooKeeper.setData(COUNTER_PATH, Integer.toString(counter).getBytes(), -1);

        String shortUrl = Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(String.valueOf(counter).getBytes());

        return padShortUrl(shortUrl);
    }

    public String generateUUIDShortUrl() {
        UUID uuid = UUID.randomUUID();
        String shortUrl = Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(uuid.toString().getBytes());

        return padShortUrl(shortUrl);
    }

    public String padShortUrl(String shortUrl) {
        while (shortUrl.length() < 7) {
            shortUrl += "0";
        }

        return shortUrl.substring(0, 7);
    }
}
