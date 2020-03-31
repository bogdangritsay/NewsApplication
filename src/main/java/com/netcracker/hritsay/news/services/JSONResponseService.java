package com.netcracker.hritsay.news.services;

import com.google.gson.Gson;
import com.netcracker.hritsay.news.models.News;

public class JSONResponseService implements ResponseService {


    @Override
    public String getData(News news) {
        Gson gson = new Gson();
        String data = gson.toJson(news, News.class);
        return data;
    }
}
