package com.netcracker.hritsay.news.controllers;

import com.netcracker.hritsay.news.models.News;
import com.netcracker.hritsay.news.services.JSONResponseService;
import com.netcracker.hritsay.news.services.NEWSAPINewsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/data")
public class JSONController implements DataResponseController  {
    com.netcracker.hritsay.news.services.NEWSAPINewsService NEWSAPINewsService = new NEWSAPINewsService();

   @RequestMapping(path = "/json", method = RequestMethod.GET)
    public ResponseEntity<?> showData() {
       String responseNews = NEWSAPINewsService.getResponseNews();
       News news = NewsFromJSONConverter.parseNews(responseNews);
       JSONResponseService jsonService = new JSONResponseService();
       String data = jsonService.getData(news);
       return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
