package com.netcracker.hritsay.news.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@Qualifier("news")
public class NewsService {
    public NewsService() {}
    public String getNews() {
        String url = "https://newsapi.org/v2/top-headlines?country=ua&apiKey=ba0b13a40fc94543a6ce6009633ec0d8";
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
                System.out.println(sb.toString());
                return sb.toString();
            } else {
                System.out.println("Fail: " + connection.getResponseCode() + ", " + connection.getResponseMessage());
                return null;
            }

        } catch (IOException e) {
            e.printStackTrace();
            //there is logs
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return  null;
    }
}
