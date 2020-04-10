package com.netcracker.hritsay.news.services;

import com.netcracker.hritsay.news.controllers.NewsFromJSONConverter;
import com.netcracker.hritsay.news.models.News;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WordResponseService {
    @Value("${word.path}")
    private String output;

    public void createWordDocument(String content) {
        News news = NewsFromJSONConverter.parseNews(content);
        PatternWordFormatter patternWordFormatter = new PatternWordFormatter();
        patternWordFormatter.writeInDoc(news, output);

    }
}
