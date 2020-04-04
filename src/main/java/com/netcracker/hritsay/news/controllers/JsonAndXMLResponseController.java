package com.netcracker.hritsay.news.controllers;

import com.netcracker.hritsay.news.models.News;
import com.netcracker.hritsay.news.services.NEWSAPINewsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/data")
public class JsonAndXMLResponseController {
    NEWSAPINewsService newsapiNewsService = new NEWSAPINewsService();


    @RequestMapping(value = "json", method = RequestMethod.GET,
                    produces = {"application/json"})
    public News getNewsInJSON() {
        String responseNews = newsapiNewsService.getResponseNews();
        News news = NewsFromJSONConverter.parseNews(responseNews);
        return news;
    }

    @RequestMapping(value = "xml", method = RequestMethod.GET,
            produces = {"application/xml"})
    public News getNewsInXML() {
        String responseNews = newsapiNewsService.getResponseNews();
        News news = NewsFromJSONConverter.parseNews(responseNews);
        return news;
    }





/*
    @RequestMapping(value = "/employee", method = RequestMethod.GET,
            produces = { "application/json", "application/xml" })
    public Employee firstPage() {

        Employee emp = new Employee();
        emp.setName("emp1");
        emp.setDesignation("manager");
        emp.setEmpId("1");
        emp.setSalary(3000);

        return emp;
    }*/
}
