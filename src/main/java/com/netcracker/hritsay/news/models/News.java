package com.netcracker.hritsay.news.models;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class News {
    private int results;
    ArrayList<Article> articles;

    @Autowired
    public News(String status, int results, ArrayList<Article> articles) {
        this.results = results;
        this.articles = articles;
    }

    public int getResults() {
        return results;
    }

    public void setResults(int results) {
        this.results = results;
    }

    public ArrayList<Article> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<Article> articles) {
        this.articles = articles;
    }

    @Override
    public String toString() {
        return "News{" +
                "results=" + results +
                ", articles=" + articles.toString() +
                '}';
    }
}
