package com.UrlShortner.controller;

import com.UrlShortner.models.IdConvert;
import com.UrlShortner.models.UrlValidator;
import com.UrlShortner.service.UrlConvertService;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;


import java.io.IOException;
import java.net.URISyntaxException;

@RestController
public class UrlController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UrlController.class);
    private final UrlConvertService urlConverterService;

    public UrlController(UrlConvertService urlConverterService) {
        this.urlConverterService = urlConverterService;
    }

    //Route to receive a original url and return the short url
    @RequestMapping(value = "/shortener", method=RequestMethod.POST, consumes = {"application/json"})
    public String shortenUrl(@RequestBody @Valid final ShortenRequest shortenRequest, HttpServletRequest request) throws Exception {
        LOGGER.info("Received url to shorten: " + shortenRequest.getUrl());
        String longURL = shortenRequest.getUrl();
        if (UrlValidator.INSTANCE.validateURL(longURL)) {
            String localURL = request.getRequestURL().toString();
            LOGGER.info("Current params: " + localURL + " and " + longURL);
            LOGGER.info(urlConverterService.toString());
            String shortenedUrl = urlConverterService.shortenURL(localURL, longURL);
            LOGGER.info("Shortened url to: " + shortenedUrl);
            return shortenedUrl;
        }
        throw new Exception("Please enter a valid URL");
    }

    //Route to convert a short url to a original url
    @RequestMapping(value = "/{id}", method=RequestMethod.GET)
    public RedirectView redirectUrl(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws IOException, URISyntaxException, Exception {
        LOGGER.debug("Received shortened url to redirect: " + id);
        String redirectUrlString = urlConverterService.getLongURLFromID(id);
        urlConverterService.setMetricsValue(IdConvert.getDictionaryKeyFromUniqueID(id));
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://" + redirectUrlString);
        return redirectView;
    }

    //Route to get a value of statistics about a specific id
    @RequestMapping(value = "/metrics/{id}", method=RequestMethod.GET)
    public String metricsUrl(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws IOException, URISyntaxException, Exception {
        LOGGER.info("Received id for metrics: " + id);
        String urlMetricsString = urlConverterService.getUrlmetricsFromId(Long.parseLong(id));
        LOGGER.info("Metrics: " + urlMetricsString);
        return urlMetricsString;
    }
}

class ShortenRequest{
    private String url;

    @JsonCreator
    public ShortenRequest() {

    }

    @JsonCreator
    public ShortenRequest(@JsonProperty("url") String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
