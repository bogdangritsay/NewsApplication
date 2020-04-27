package com.netcracker.hritsay.news.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@Qualifier("news")
@EnableCaching(proxyTargetClass = true)
public class NEWSAPINewsService implements NewsService {
    @Value("${newsapi.url.ua.ua}")
    private String url;
    @Value("${newsapi.key}")
    private String key;
    private static final Logger logger = LogManager.getLogger(NEWSAPINewsService.class);


    @Override
    @Cacheable(cacheNames = "newsCache", key="{#country, #category}")
    public String getResponseNews(String country, String category) {
        HttpURLConnection connection = null;
        String allUrl = url + "country=" + country + "&category=" + category +  key;
        try {
            connection = (HttpURLConnection) new URL(allUrl).openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            StringBuilder sb = new StringBuilder();
            if(HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String result;
                while((result = in.readLine()) != null) {
                   sb.append(result);
                }
                logger.info("Connection-status: OK");
                return sb.toString();
            } else {
                logger.error("Fail: " + connection.getResponseCode() + ", " + connection.getResponseMessage());
                return null;
            }
        } catch (IOException e) {
            logger.error("I/OException");
        } finally {
            if (connection != null) {
                connection.disconnect();
                logger.info("Connection was been closed.");
            }
        }
        return  null;
    }
}
