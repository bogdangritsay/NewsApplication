package com.netcracker.hritsay.news.services;

import com.netcracker.hritsay.news.controllers.NewsFromJSONConverter;
import com.netcracker.hritsay.news.models.News;

public class WordResponseService {
    public void createWordDocument(String content) {
        News news = NewsFromJSONConverter.parseNews(content);
        PatternWordFormatter patternWordFormatter = new PatternWordFormatter();
        patternWordFormatter.writeInDoc(news);
    }

}
