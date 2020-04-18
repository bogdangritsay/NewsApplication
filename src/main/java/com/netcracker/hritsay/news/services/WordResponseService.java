package com.netcracker.hritsay.news.services;

import com.netcracker.hritsay.news.controllers.NewsFromJSONConverter;
import com.netcracker.hritsay.news.controllers.WordWriter;
import com.netcracker.hritsay.news.models.News;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WordResponseService {
    @Value("${word.path}")
    private String output;
    @Autowired
    NewsFromJSONConverter converter;
    private static final Logger logger = LogManager.getLogger(WordResponseService.class);
    @Autowired
    WordWriter wordWriter;

    public void createWordDocument(News news) {
        if (news != null) {
            XWPFDocument doc = wordWriter.createNewsDocFromTemplate(news.getArticles());
            wordWriter.writeToFile(doc, output);
            logger.info("Document for response was been created");
        } else {
            logger.info("Document for response was not been created because response news is empty!");
        }
    }
}
