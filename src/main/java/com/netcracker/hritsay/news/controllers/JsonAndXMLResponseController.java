package com.netcracker.hritsay.news.controllers;


import com.netcracker.hritsay.news.models.Article;
import com.netcracker.hritsay.news.models.News;
import com.netcracker.hritsay.news.services.NEWSAPINewsService;
import com.netcracker.hritsay.news.services.WordResponseService;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/data")
public class JsonAndXMLResponseController {
    @Autowired
    NEWSAPINewsService newsapiNewsService;
    @Autowired
    NewsFromJSONConverter converter;
    private static final Logger logger = LogManager.getLogger(JsonAndXMLResponseController.class);
    @Autowired
    private WordResponseService wordResponseService;
    @Value("${word.path}")
    private String docFile;




    @RequestMapping(path = "/word", method = RequestMethod.GET)
    public ResponseEntity<?> showData(@RequestParam MultiValueMap<String, String> queryMap) throws IOException {
        News news = handleRequest(queryMap);
        ResponseEntity respEntity = null;
        wordResponseService.createWordDocument(news);
        byte[] reportBytes = null;
        File result = new File(docFile);
        if(result.exists()){
            InputStream inputStream = new FileInputStream(docFile);

            byte[]out= IOUtils.toByteArray(inputStream);

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("content-disposition", "attachment; filename=" + docFile);
            //responseHeaders.add("Content-Type", "docx");

            logger.info("Result exist. Response the file.");
            respEntity = new ResponseEntity(out, responseHeaders, HttpStatus.OK);
        } else {
            logger.warn("File not found.");
            respEntity = new ResponseEntity ("File Not Found", HttpStatus.OK);
        }
        return respEntity;
    }



    @RequestMapping(value = "json", method = RequestMethod.GET, produces = {"application/json"})
    public News getNewsInJSON(@RequestParam MultiValueMap<String, String> queryMap) {

        News news = handleRequest(queryMap);
        logger.info("Response in JSON");
        return news;

    }




    @RequestMapping(value = "xml", method = RequestMethod.GET,
            produces = {"application/xml"})
    public News getNewsInXML(@RequestParam MultiValueMap<String, String> queryMap) {
        News news = handleRequest(queryMap);
        logger.info("Response in XML");
        return news;
    }


    //@RequestMapping(value = "tmp", method = RequestMethod.GET, produces = {"application/json"})
    public News handleRequest(/*@RequestParam*/ MultiValueMap<String, String> queryMap) {
        String response = "";

        String newsAPIResponse;
        List<String> itemCountry = (queryMap.get("country") == null)? new ArrayList<>() : queryMap.get("country");
        List<String> itemCategory = (queryMap.get("category") == null)? new ArrayList<>(): queryMap.get("category");
        News news = new News();
        int i = 0;
        while (true) {
            String category = "";
            String country = "";
            String tmpString = "";
            if((i > (itemCategory.size() - 1)) && (i > (itemCountry.size() - 1))) {
                break;
            } else if(i > (itemCategory.size() - 1)){
                category = "";
                country = itemCountry.get(i);
            } else if(i > (itemCountry.size() - 1)) {
                country = "";
                category = itemCategory.get(i);
            } else {
                category = itemCategory.get(i);
                country = itemCountry.get(i);
            }

            newsAPIResponse = newsapiNewsService.getResponseNews(country, category);
            News tmpNews = converter.convert(newsAPIResponse);
            if(news.getArticles() == null) {
                news = tmpNews;
            } else {
                for(Article article : tmpNews.getArticles()) {
                    news.getArticles().add(article);
                }
            }
            i++;
        }
        return news;
    }



}
