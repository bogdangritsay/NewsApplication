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

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class WordResponseService {
    @Autowired
    NewsFromJSONConverter converter;
    private static final Logger logger = LogManager.getLogger(WordResponseService.class);
    @Autowired
    WordWriter wordWriter;

    public byte[] createWordDocument(News news) {
        byte[] out = null;
        try {
            if (news != null) {
                XWPFDocument doc = wordWriter.createNewsDocFromTemplate(news.getArticles());
                ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
                doc.write(byteOutStream);
                out = byteOutStream.toByteArray();
                logger.info("Document for response was been created");
            } else {
                logger.info("Document for response was not been created because response news is empty!");
            }
        } catch (IOException e) {
            logger.error("Here is IOException.");
        }
        return out;
    }
}
