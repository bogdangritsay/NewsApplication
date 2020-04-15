package com.netcracker.hritsay.news.controllers;


import com.netcracker.hritsay.news.services.NEWSAPINewsService;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;



@RestController
@RequestMapping(path = "/data")
public class WordController {
    @Autowired
    com.netcracker.hritsay.news.services.NEWSAPINewsService NEWSAPINewsService;
    @Autowired
    private com.netcracker.hritsay.news.services.WordResponseService wordResponseService;
    @Value("${word.path}")
    private String docFile;

    private static final Logger logger = LogManager.getLogger(WordController.class);

/*
    @RequestMapping(path = "/worvvd", method = RequestMethod.GET)
    public ResponseEntity<?> showData(
            @RequestParam(name = "country", defaultValue = "ua") String country,
            @RequestParam(name = "category", defaultValue = "business") String category
    ) throws IOException {

    ResponseEntity respEntity = null;
    String responseNews = NEWSAPINewsService.getResponseNews(country, category);
    wordResponseService.createWordDocument(responseNews);

    byte[] reportBytes = null;

    File result = new File(docFile);
    if(result.exists()){
        InputStream inputStream = new FileInputStream(docFile);

        byte[]out= IOUtils.toByteArray(inputStream);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("content-disposition", "attachment; filename=" + docFile);
        //responseHeaders.add("Content-Type", "docx");

        logger.info("Result exist. Response the file.");
        respEntity = new ResponseEntity(out, responseHeaders,HttpStatus.OK);
    } else {
        logger.warn("File not found.");
        respEntity = new ResponseEntity ("File Not Found", HttpStatus.OK);
    }
    return respEntity;
    }*/
}
