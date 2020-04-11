package com.netcracker.hritsay.news.services;

import com.netcracker.hritsay.news.controllers.NewsFromJSONConverter;
import com.netcracker.hritsay.news.controllers.WordController;
import com.netcracker.hritsay.news.models.News;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WordResponseService {
    @Value("${word.path}")
    private String output;
    @Autowired
    private PatternWordFormatter patternWordFormatter;
    @Autowired
    NewsFromJSONConverter converter;
    private static final Logger logger = LogManager.getLogger(WordResponseService.class);

    public void createWordDocument(String responseNews) {
        News news = converter.convert(responseNews);
        patternWordFormatter.writeInDoc(news, output);
        logger.info("Document for response was been created");

    }
}
