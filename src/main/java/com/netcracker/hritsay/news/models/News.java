package com.netcracker.hritsay.news.models;

import jdk.net.SocketFlow;

import java.util.ArrayList;

public class News {
    private String status;
    private int results;
    ArrayList<Article> articles;

    public News(String status, int results, ArrayList<Article> articles) {
        this.status = status;
        this.results = results;
        this.articles = articles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
}
