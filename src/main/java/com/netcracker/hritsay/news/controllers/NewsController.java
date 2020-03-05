package com.netcracker.hritsay.news.controllers;

import com.netcracker.hritsay.news.models.News;
import com.netcracker.hritsay.news.models.NewsJsonParser;
import com.netcracker.hritsay.news.services.NewsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/api")
public class NewsController {
    NewsService newsService = new NewsService();

    @RequestMapping(path = "/news", method = RequestMethod.GET)
    public void showContent() {
        //System.out.println(newsService.getNews());
        String responseNews = newsService.getNews();
        News news = NewsJsonParser.parseNews(responseNews);
        System.out.println(news.toString());
    }
}