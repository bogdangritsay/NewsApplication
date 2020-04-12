package com.netcracker.hritsay.news.models;



import org.springframework.stereotype.Component;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;


@Component
@XmlRootElement
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
