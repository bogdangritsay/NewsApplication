package com.netcracker.hritsay.news.controllers;

import com.netcracker.hritsay.news.models.Article;
import org.apache.logging.log4j.LogManager;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
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
import java.util.List;

/**
 * It`s class for getting template and creating template for indicated quantity of news article.
 * Also here everybody can to write XWPFDocument into the file and replace default  values
 * in template by news data.
 *
 */
@Component
public class WordWriter {
    @Value("${word.path.template}")
    private String TEMPLATE;
    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(WordWriter.class);


    /**
     * Method that creates the file with news data
     * @param articles it`s news data
     * @return created XWPFDFile with news data
     */
    public  XWPFDocument createNewsDocFromTemplate(List<Article> articles) {
        try {
            XWPFDocument documentT = createNTemplateParagraphs(articles.size());
            InputStream is = writeToInputStream(documentT);
            XWPFDocument document = new XWPFDocument(is);
            List<XWPFParagraph> paragraphList = document.getParagraphs();
            for (int i = 0; i < articles.size(); i++) {
                XWPFParagraph par = paragraphList.get(i);
                List<XWPFRun> runList = par.getRuns();
                if (runList != null) {
                    for (XWPFRun run : runList) {
                        String textInRun = run.getText(0);
                        if (textInRun != null) {
                            textInRun = textInRun.replace("title", articles.get(i).getTitle() == null ? "-" : articles.get(i).getTitle());
                            textInRun = textInRun.replace("author", articles.get(i).getAuthor() == null ? "-" : articles.get(i).getAuthor());
                            textInRun = textInRun.replace("description", articles.get(i).getDescription() == null ? "-" : articles.get(i).getDescription());
                            textInRun = textInRun.replace("url", articles.get(i).getUrl() == null ? "-" : articles.get(i).getUrl());
                            run.setText(textInRun, 0);
                        }
                    }
                    XWPFRun imageRun = par.createRun();
                    addImageRun(imageRun, articles.get(i).getUrlToImage());
                }
                par.setSpacingBetween(1.3);
                par.setSpacingAfter(1000);
            }
            return document;
        } catch (IOException e) {
            logger.error("InputStream was not been found!");
        }
        return null;
    }

    private XWPFDocument createNTemplateParagraphs(int n) {
        XWPFDocument doc = null;
        try {
            doc = new XWPFDocument(new FileInputStream(TEMPLATE));
            XWPFParagraph templateParagraph = doc.getParagraphs().get(0);
            while(doc.getParagraphs().size() != n) {
                doc.createParagraph();
            }
            int position = 0;
            int s = doc.getParagraphs().size();
            for(XWPFParagraph paragraph : doc.getParagraphs()) {
                doc.setParagraph(templateParagraph, position++);
            }
        } catch (IOException e) {
            logger.error("File " + TEMPLATE + "was not been found!");
        }
        return doc;
    }

    private InputStream writeToInputStream(XWPFDocument document) {
        InputStream is = null;
        try {
            ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
            document.write(byteOutStream);
            is = new ByteArrayInputStream(byteOutStream.toByteArray());
            document.close();
        } catch (IOException e) {
            logger.error("IOException in creating NTEMPLATES module.");
        }
        return is;
    }

    private void addImageRun(XWPFRun imageRun, String urlToImage) {
        try {
            imageRun.addBreak();
            URL url = new URL(urlToImage);
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            connection.connect();
            BufferedImage img = ImageIO.read(connection.getInputStream());
            ByteArrayOutputStream imageStream = new ByteArrayOutputStream();
            if (img != null) {
                ImageIO.write(img, "jpg", imageStream);
                InputStream is = new ByteArrayInputStream(imageStream.toByteArray());
                imageRun.addPicture(is,
                        XWPFDocument.PICTURE_TYPE_JPEG, "image",
                        Units.toEMU(400), Units.toEMU(250));
            }
        } catch (IOException | InvalidFormatException e) {
            logger.error("Error with inserting image!");
        }
    }
}