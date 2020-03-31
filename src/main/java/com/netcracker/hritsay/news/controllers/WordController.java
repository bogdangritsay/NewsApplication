package com.netcracker.hritsay.news.controllers;

import com.netcracker.hritsay.news.services.NEWSAPINewsService;
import com.netcracker.hritsay.news.services.WordResponseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/data")
public class WordController implements DataResponseController {
    com.netcracker.hritsay.news.services.NEWSAPINewsService NEWSAPINewsService = new NEWSAPINewsService();
    com.netcracker.hritsay.news.services.WordResponseService wordResponseService = new WordResponseService();


    @RequestMapping(path = "/word", method = RequestMethod.GET)
    public ResponseEntity<?> showData() {
        String responseNews = NEWSAPINewsService.getResponseNews();
        wordResponseService.createWordDocument(responseNews);
        return new ResponseEntity<>("<h2>File with news was been saved!</h2>", HttpStatus.OK);
    }
}
