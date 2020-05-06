package com.netcracker.hritsay.news.controllers;


import com.netcracker.hritsay.news.models.Article;
import com.netcracker.hritsay.news.models.News;
import com.netcracker.hritsay.news.services.NEWSAPINewsService;
import com.netcracker.hritsay.news.services.WordResponseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@RestController
@RequestMapping(path = "/news")
public class ContentResponseController {
    @Autowired
    private NEWSAPINewsService newsapiNewsService;
    @Autowired
    private NewsFromJSONConverter converter;
    private static final Logger logger = LogManager.getLogger(ContentResponseController.class);
    @Autowired
    private WordResponseService wordResponseService;
    @Value("${word.out.filename}")
    private String docFile;
    @Value("${request.threads}")
    private int n;


    @RequestMapping(path = "/word", method = RequestMethod.GET)
    public ResponseEntity<?> showData(@RequestParam MultiValueMap<String, String> queryMap) {
        long before = System.currentTimeMillis();
        News news = handleRequest(queryMap);
        ResponseEntity respEntity = null;
        byte[] out = wordResponseService.createWordDocument(news);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("content-disposition", "attachment; filename=" + docFile);
        logger.info("Result exist. Response the file.");
        respEntity = new ResponseEntity(out, responseHeaders, HttpStatus.OK);
        long after = System.currentTimeMillis();
        System.out.println("Work took " + (after - before) +" ms");
        return respEntity;
    }


    @RequestMapping(value = "/data", method = RequestMethod.GET,
                    produces = { "application/json", "application/xml" })
    public ResponseEntity<News> getNews(@RequestParam MultiValueMap<String, String> queryMap) {
        long before = System.currentTimeMillis();
        News news = handleRequest(queryMap);
        long after = System.currentTimeMillis();
        System.out.println("Work took " + (after - before) +" ms");
        return new ResponseEntity<>(news, HttpStatus.OK);
    }

    private News handleRequest(MultiValueMap<String, String> queryMap)  {
        List<String> itemCountry = (queryMap.get("country") == null) ? new ArrayList<>() : queryMap.get("country");
        List<String> itemCategory = (queryMap.get("category") == null) ? new ArrayList<>() : queryMap.get("category");
        if (itemCategory.size() == 0) itemCategory.add("health");
        if (itemCountry.size() == 0) itemCountry.add("ua");
        News news = new News();
        int i = 0;
        ExecutorService service = Executors.newFixedThreadPool(n);
        Future<News> future = null;
        while (true) {
            String category = "";
            String country = "";
            String tmpString = "";
            if ((i > (itemCategory.size() - 1)) && (i > (itemCountry.size() - 1))) {
                break;
            } else if (i > (itemCategory.size() - 1)) {
                category = "";
                country = itemCountry.get(i);
            } else if (i > (itemCountry.size() - 1)) {
                country = "";
                category = itemCategory.get(i);
            } else {
                category = itemCategory.get(i);
                country = itemCountry.get(i);
            }

            String finalCountry = country;
            String finalCategory = category;
            News finalNews = news;
            future = service.submit(new Callable<News>() {
                @Override
                public News call() throws Exception {
                    String newsAPIResponse = newsapiNewsService.getResponseNews(finalCountry, finalCategory);
                    News nNews = getNewNews(newsAPIResponse, finalNews);
                    return nNews;
                }
            });

            try {
                news = future.get();
            } catch (InterruptedException e) {
                logger.error("Thread was been interrupted!");
            } catch (ExecutionException e) {
                logger.error("There is execution!");
            }

            i++;
        }
        service.shutdown();
        return news;
    }

    private News getNewNews(String response, News news) {
        News tmpNews = converter.convert(response);
        if (news.getArticles() == null) {
            news = tmpNews;
        } else {
            for (Article article : tmpNews.getArticles()) {
                news.getArticles().add(article);
            }
        }
        return news;
    }
}



