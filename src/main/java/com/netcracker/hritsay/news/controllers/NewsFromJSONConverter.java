package com.netcracker.hritsay.news.controllers;

import com.netcracker.hritsay.news.models.Article;
import com.netcracker.hritsay.news.models.News;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.ArrayList;

@Component
public class NewsFromJSONConverter implements Converter<String, News> {
    private static final Logger logger = LogManager.getLogger(NewsFromJSONConverter.class);

    @Override
    public  News convert(String responseNews) {
        ArrayList<Article> parsedArticles = new ArrayList<>();
        JSONObject newsJson = new JSONObject(responseNews);
        String status = newsJson.getString("status");
        int totalResults = newsJson.getInt("totalResults");

        JSONArray arr = newsJson.getJSONArray("articles");
        for (int i = 0; i < arr.length(); i++) {
            Object idObj = arr.getJSONObject(i).getJSONObject("source").get("id");
            String name = arr.getJSONObject(i).getJSONObject("source").getString("name");
            String source = name;
            Object authObj = arr.getJSONObject(i).get("author");
            String author = (!JSONObject.NULL.equals(authObj)) ? (String) authObj : null;
            String title = arr.getJSONObject(i).getString("title");
            Object descriptionObj = arr.getJSONObject(i).get("description");
            String description = (!JSONObject.NULL.equals(descriptionObj)) ? (String) descriptionObj : null;
            String url = arr.getJSONObject(i).getString("url");
            Object urlToImageObj = arr.getJSONObject(i).get("urlToImage");
            String urlToImage = (!JSONObject.NULL.equals(urlToImageObj)) ? (String) urlToImageObj : null;
            ZonedDateTime date = ZonedDateTime.parse(arr.getJSONObject(i).getString("publishedAt"));
            Object contObj = arr.getJSONObject(i).get("content");
            String content = null;//(!JSONObject.NULL.equals(contObj))? (String) contObj: null;
            parsedArticles.add(new Article(source, author, title, description, url, urlToImage, date, content));
        }
        logger.info("JSON was been converted into News data.");
        return new News(status, totalResults, parsedArticles);
    }
}
