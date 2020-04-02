package com.netcracker.hritsay.news.services;

import com.netcracker.hritsay.news.models.Article;
import com.netcracker.hritsay.news.models.News;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.ArrayList;

public class XMLResponseService implements ResponseService {

    @Override
    public String getData(News news) {
    try {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();

        Element newsElement = document.createElement("news");
        newsElement.setAttribute("results", Integer.toString(news.getResults()));
        document.appendChild(newsElement);
        ArrayList<Article> articles = news.getArticles();

        for (int i = 0; i < articles.size(); i++) {
            Element article = document.createElement("article");
            newsElement.appendChild(article);
            article.setAttribute("source", articles.get(i).getSource());
            article.setAttribute("author", articles.get(i).getAuthor());
            article.setAttribute("title", articles.get(i).getTitle());
            article.setAttribute("description", articles.get(i).getDescription());
            article.setAttribute("url", articles.get(i).getUrl());
            article.setAttribute("urltoimage", articles.get(i).getUrlToImage());
            article.setAttribute("date", articles.get(i).getDate().toString());
            article.setAttribute("content", articles.get(i).getContent());
        }
        //?????
    return  null;

    } catch (ParserConfigurationException e) {
        e.printStackTrace();
    }
    return null;

    }




}
