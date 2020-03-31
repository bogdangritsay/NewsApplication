package com.netcracker.hritsay.news.controllers;


import com.netcracker.hritsay.news.models.News;
import com.netcracker.hritsay.news.services.NEWSAPINewsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/data")
public class XMLController implements DataResponseController {
    com.netcracker.hritsay.news.services.NEWSAPINewsService NEWSAPINewsService = new NEWSAPINewsService();

    @RequestMapping(path = "/json", method = RequestMethod.GET)
    @Override
    public void showData() {
        String responseNews = NEWSAPINewsService.getResponseNews();
        News news = NewsFromJSONConverter.parseNews(responseNews);
        /*
         * Тут логика возвращения XML на страницу с помощью XMLResponseService
         */

    }
}
