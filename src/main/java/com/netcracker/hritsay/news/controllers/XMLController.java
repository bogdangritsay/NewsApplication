package com.netcracker.hritsay.news.controllers;


import com.netcracker.hritsay.news.models.News;
import com.netcracker.hritsay.news.services.NEWSAPINewsService;
import com.netcracker.hritsay.news.services.XMLResponseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/data")
public class XMLController implements DataResponseController {
    com.netcracker.hritsay.news.services.NEWSAPINewsService NEWSAPINewsService = new NEWSAPINewsService();

    @RequestMapping(path = "/xml", method = RequestMethod.GET)
    @Override
    public ResponseEntity<?> showData() {
        String responseNews = NEWSAPINewsService.getResponseNews();
        /*
         * Тут логика возвращения XML на страницу с помощью XMLResponseService
         */
        News news = NewsFromJSONConverter.parseNews(responseNews);
        XMLResponseService jsonService = new XMLResponseService();
        String data =  jsonService.getData(news);
        return new ResponseEntity<>(data, HttpStatus.OK);
}
}
