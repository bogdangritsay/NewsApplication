package com.netcracker.hritsay.news.services;


import com.netcracker.hritsay.news.controllers.WordController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@Qualifier("news")
public class NEWSAPINewsService implements NewsService {
    @Value("${newsapi.url.ua.ua}")
    private String url;
    private static final Logger logger = LogManager.getLogger(NEWSAPINewsService.class);


    @Override
    public String getResponseNews() {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();

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
