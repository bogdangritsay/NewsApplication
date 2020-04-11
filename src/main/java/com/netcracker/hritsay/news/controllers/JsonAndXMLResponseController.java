package com.netcracker.hritsay.news.controllers;


import com.netcracker.hritsay.news.models.News;
import com.netcracker.hritsay.news.services.NEWSAPINewsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/data")
public class JsonAndXMLResponseController {
    @Autowired
    NEWSAPINewsService newsapiNewsService;
    @Autowired
            NewsFromJSONConverter converter;
    private static final Logger logger = LogManager.getLogger(JsonAndXMLResponseController.class);



    @RequestMapping(value = "json", method = RequestMethod.GET,
                    produces = {"application/json"})
    public News getNewsInJSON() {

        String responseNews = newsapiNewsService.getResponseNews();
        News news = converter.convert(responseNews);
        logger.info("Response in JSON");
        return news;
    }

    @RequestMapping(value = "xml", method = RequestMethod.GET,
            produces = {"application/xml"})
    public News getNewsInXML() {
        String responseNews = newsapiNewsService.getResponseNews();
        News news = converter.convert(responseNews);
        logger.info("Response in XML");
        return news;
    }

}
