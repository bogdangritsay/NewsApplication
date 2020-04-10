package com.netcracker.hritsay.news.services;


import com.netcracker.hritsay.news.models.Article;
import com.netcracker.hritsay.news.models.News;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

import java.net.URL;
import java.net.URLConnection;

import java.util.ArrayList;

public class PatternWordFormatter {

    private XWPFDocument document = new XWPFDocument();

    public void writeInDoc(News news, String output) {
        try {
            formatAll(news);
            FileOutputStream out = new FileOutputStream(output);
            document.write(out);
            out.close();
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void formatAll(News news) {
        ArrayList<Article> articles = news.getArticles();
        for (Article article : articles) {
            format(article);
        }
    }

    private void format(Article article) {
        XWPFParagraph titleNew = document.createParagraph();
        titleNew.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun titleRun = titleNew.createRun();
        titleRun.setText(article.getTitle());
        titleRun.setBold(true);
        titleRun.setFontSize(16);
        titleRun.setFontFamily("Times New Roman");
        titleRun.addBreak();

        XWPFParagraph image = document.createParagraph();
        image.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun imageRun = image.createRun();
        imageRun.setTextPosition(20);
        try {
            URL url = new URL(article.getUrlToImage());
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            connection.connect();
            BufferedImage img = ImageIO.read(connection.getInputStream());
            String pathTmpImage = "\\downloaded.jpg";
            File file = new File(pathTmpImage);
            if(img != null) {
                ImageIO.write(img, "jpg", file);
                if (article.getUrlToImage() != null) {
                    InputStream is = new FileInputStream(file);
                    imageRun.addPicture(is,
                            XWPFDocument.PICTURE_TYPE_JPEG, file.getName(),
                            Units.toEMU(400), Units.toEMU(250));
                }
            }
        } catch (InvalidFormatException | IOException e) {
            e.printStackTrace();
            System.out.println("Error is there: " + article.getUrlToImage());
        }
        imageRun.addBreak();


        XWPFParagraph authorNew = document.createParagraph();
        XWPFRun authorRun = titleNew.createRun();
        authorRun.setText("Автор новини: " + article.getAuthor());
        authorRun.setItalic(true);
        titleRun.setFontSize(14);
        titleRun.setFontFamily("Times New Roman");
        authorRun.addBreak();

        XWPFParagraph descriptionNew = document.createParagraph();
        XWPFRun descriptionRun = titleNew.createRun();
        descriptionRun.setText(article.getDescription());
        descriptionRun.setFontSize(12);
        descriptionRun.setFontFamily("Times New Roman");
        descriptionRun.addBreak();

        XWPFParagraph urlToImageNew = document.createParagraph();
        XWPFRun urlImageRun = titleNew.createRun();
        urlImageRun.setText(article.getUrlToImage());
        urlImageRun.addBreak();

        XWPFParagraph urlNew = document.createParagraph();
        XWPFRun urlRun = titleNew.createRun();
        urlRun.setText("Посилання на новину: " + article.getUrl());
        authorRun.setItalic(true);
        titleRun.setFontSize(14);
        titleRun.setFontFamily("Calibri");
        urlRun.addBreak();





    }
}
