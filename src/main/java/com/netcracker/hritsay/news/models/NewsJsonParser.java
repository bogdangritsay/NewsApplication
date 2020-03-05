package com.netcracker.hritsay.news.models;

import org.json.JSONArray;
import org.json.JSONObject;
import java.time.ZonedDateTime;
import java.util.ArrayList;

public class NewsJsonParser {
    public static News parseNews(String responseNews) {
        ArrayList<Article> parsedArticles = new ArrayList<>();
        JSONObject newsJson = new JSONObject(responseNews);
        String status = newsJson.getString("status");
        int totalResults = newsJson.getInt("totalResults");

        JSONArray arr = newsJson.getJSONArray("articles");
        for (int i = 0; i < arr.length(); i++) {
            Object idObj = arr.getJSONObject(i).getJSONObject("source").get("id");
            String id = (!JSONObject.NULL.equals(idObj))? (String) idObj: null;
            String name = arr.getJSONObject(i).getJSONObject("source").getString("name");
            Source source = new Source(id, name);
            Object authObj = arr.getJSONObject(i).get("author");
            String author= (!JSONObject.NULL.equals(authObj))? (String) authObj: null;
            String title = arr.getJSONObject(i).getString("title");
            String description = arr.getJSONObject(i).getString("description");
            String url  = arr.getJSONObject(i).getString("url");
            String urlToImage = arr.getJSONObject(i).getString("urlToImage");
            ZonedDateTime date = ZonedDateTime.parse(arr.getJSONObject(i).getString("publishedAt"));
            Object contObj = arr.getJSONObject(i).get("content");
            String content = null;//(!JSONObject.NULL.equals(contObj))? (String) contObj: null;
            parsedArticles.add(new Article(source, author, title, description, url, urlToImage, date, content));
        }
        return new News(status, totalResults, parsedArticles);
    }
}
