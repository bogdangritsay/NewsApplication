package com.netcracker.hritsay.news.controllers;

import com.netcracker.hritsay.news.models.News;
import com.netcracker.hritsay.news.services.NEWSAPINewsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/api")
public class NewsController {
    NEWSAPINewsService NEWSAPINewsService = new NEWSAPINewsService();

    @RequestMapping(path = "/news", method = RequestMethod.GET)
    public void showContent() {
        String responseNews = NEWSAPINewsService.getResponseNews();
        NEWSAPINewsService.createWordDocument(responseNews);
    }
}