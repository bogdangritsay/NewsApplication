package com.netcracker.hritsay.news.controllers;


import com.netcracker.hritsay.news.models.Article;
import com.netcracker.hritsay.news.models.News;
import com.netcracker.hritsay.news.services.NEWSAPINewsService;
import com.netcracker.hritsay.news.services.WordResponseService;
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
import java.util.List;
import java.util.concurrent.*;

@RestController
@RequestMapping(path = "/data")
public class ContentResponseController {
    @Autowired
    NEWSAPINewsService newsapiNewsService;
    @Autowired
    NewsFromJSONConverter converter;
    private static final Logger logger = LogManager.getLogger(ContentResponseController.class);
    @Autowired
    private WordResponseService wordResponseService;
    @Value("${word.path}")
    private String docFile;
    @Value("${request.threads}")
    private int n;


    @RequestMapping(path = "/word", method = RequestMethod.GET)
    public ResponseEntity<?> showData(@RequestParam MultiValueMap<String, String> queryMap) throws IOException {
        long before = System.currentTimeMillis();
        News news = handleRequest(queryMap);
        ResponseEntity respEntity = null;
        wordResponseService.createWordDocument(news);
        byte[] reportBytes = null;
        File result = new File(docFile);
        if (result.exists()) {
            InputStream inputStream = new FileInputStream(docFile);
            byte[] out = IOUtils.toByteArray(inputStream);
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("content-disposition", "attachment; filename=" + docFile);
            //responseHeaders.add("Content-Type", "docx");
            logger.info("Result exist. Response the file.");
            respEntity = new ResponseEntity(out, responseHeaders, HttpStatus.OK);
        } else {
            logger.warn("File not found.");
            respEntity = new ResponseEntity("File Not Found", HttpStatus.OK);
        }
        long after = System.currentTimeMillis();
        System.out.println("Work took " + (after - before) +" ms");
        return respEntity;
    }


    @RequestMapping(value = "json", method = RequestMethod.GET, produces = {"application/json"})
    public News getNewsInJSON(@RequestParam MultiValueMap<String, String> queryMap) {

        long before = System.currentTimeMillis();

        News news = handleRequest(queryMap);
        logger.info("Response in JSON");

        long after = System.currentTimeMillis();
        System.out.println("Work took " + (after - before) +" ms");
        return news;

    }


    @RequestMapping(value = "xml", method = RequestMethod.GET,
            produces = {"application/xml"})
    public News getNewsInXML(@RequestParam MultiValueMap<String, String> queryMap) {
        long before = System.currentTimeMillis();
        News news = handleRequest(queryMap);
        logger.info("Response in XML");
        long after = System.currentTimeMillis();
        System.out.println("Work took " + (after - before) +" ms");
        return news;
    }


    private News handleRequest(MultiValueMap<String, String> queryMap)  {
        String response = "";
        List<String> itemCountry = (queryMap.get("country") == null) ? new ArrayList<>() : queryMap.get("country");
        List<String> itemCategory = (queryMap.get("category") == null) ? new ArrayList<>() : queryMap.get("category");
        //setting default values for country and category
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
            i++;
        }
        service.shutdown();
        try {
            news = future.get();
        } catch (InterruptedException e) {
            logger.error("Thread was been interrupted!");
        } catch (ExecutionException e) {
            logger.error("There is execution!");
        }
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



