package com.netcracker.hritsay.news.services;

import com.netcracker.hritsay.news.models.News;

import javax.xml.parsers.ParserConfigurationException;

public interface ResponseService {
    public String getData(News news);
}
