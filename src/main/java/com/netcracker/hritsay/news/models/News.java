package com.netcracker.hritsay.news.models;



import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement(name = "news")
public class News {
    private ArrayList<Article> articles;

    public News() {}

    public News(ArrayList<Article> articles) {
        this.articles = articles;
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
                "articles=" + articles +
                '}';
    }
}
