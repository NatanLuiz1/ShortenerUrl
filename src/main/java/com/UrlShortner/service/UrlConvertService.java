package com.UrlShortner.service;

import com.UrlShortner.models.IdConvert;
import com.UrlShortner.repository.UrlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class UrlConvertService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UrlConvertService.class);
    private final UrlRepository urlRepository;

    @Autowired
    public UrlConvertService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    // function to short a url
    public String shortenURL(String localURL, String longUrl) {
        LOGGER.info("Shortening {}", longUrl);
        Long id = urlRepository.incrementID();
        String uniqueID = IdConvert.createUniqueID(id);
        urlRepository.saveUrl("url:"+id, longUrl);
        String baseString = formatLocalURLFromShortener(localURL);
        String shortenedURL = baseString + uniqueID;
        urlRepository.setZeroHits(id);
        return shortenedURL;
    }
    // function to get a true url by id
    public String getLongURLFromID(String uniqueID) throws Exception {
        Long dictionaryKey = IdConvert.getDictionaryKeyFromUniqueID(uniqueID);
        String longUrl = urlRepository.getUrl(dictionaryKey);
        LOGGER.info("Converting shortened URL back to {}", longUrl);
        return longUrl;
    }
    // function to format a original url from the short url
    private String formatLocalURLFromShortener(String localURL) {
        String[] addressComponents = localURL.split("/");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < addressComponents.length - 1; ++i) {
            sb.append(addressComponents[i]);
        }
        sb.append('/');
        return sb.toString();
    }
    //function to get a statistics link by id
    public String getUrlmetricsFromId(Long id) {
        return urlRepository.getHits(id);
    }
    //function to set a statistics value by id
    public void setMetricsValue(Long id) {
        Long hits = Long.parseLong(getUrlmetricsFromId(id));
        hits = hits + 1;
        urlRepository.setHits(id, hits);
    }

}
