package com.UrlShortner.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

@Repository
public class UrlRepository {
    private final Jedis jedis;
    private final String idKey;
    private final String urlKey;

    private static final Logger LOGGER = LoggerFactory.getLogger(UrlRepository.class);
    //class constructor
    public UrlRepository() {
        this.jedis = new Jedis();
        this.idKey = "id";
        this.urlKey = "url:";
    }
    //class constructor
    public UrlRepository(Jedis jedis, String idKey, String urlKey) {
        this.jedis = jedis;
        this.idKey = idKey;
        this.urlKey = urlKey;
    }
    // set the id increment
    public Long incrementID() {
        Long id = jedis.incr(idKey);
        LOGGER.info("Incrementing ID: {}", id-1);
        return id - 1;
    }
    // save the url
    public void saveUrl(String key, String longUrl) {
        LOGGER.info("Saving: {} at {}", longUrl, key);
        jedis.hset(urlKey, key, longUrl);
    }
    // get the url by id
    public String getUrl(Long id) throws Exception {
        LOGGER.info("Retrieving at {}", id);
        String url = jedis.hget(urlKey, "url:"+id);
        LOGGER.info("Retrieved {} at {}", url ,id);
        if (url == null) {
            throw new Exception("URL at key" + id + " does not exist");
        }
        return url;
    }

    // set to 0 the quantity clicks
    public void setZeroHits(Long id) {
        LOGGER.info("Set Zero Hits: id {}", id);
        jedis.hset(urlKey, "metrics-" + id.toString(), "0");
    }

    // Get the quantity clicks by Id
    public String getHits(Long id) {
        LOGGER.info("Retrieving hits for id: {}", id);
        String hits = jedis.hget(urlKey, "metrics-" + id.toString());
        return hits;
    }

    // Set the quantity clicks by Id
    public void setHits(Long id, Long hits) {
        LOGGER.info("Update Hits: id {} has {} hits", id, hits);
        jedis.hset(urlKey, "metrics-" + id.toString(), hits.toString());
    }
}
