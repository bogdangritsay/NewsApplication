package com.netcracker.hritsay.news.controllers;


import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;



@RestController
@RequestMapping(path = "/data")
public class WordController {
    @Autowired
    com.netcracker.hritsay.news.services.NEWSAPINewsService NEWSAPINewsService;
    @Autowired
    private com.netcracker.hritsay.news.services.WordResponseService wordResponseService;
    @Value("${word.path}")
    private String docFile;


    @RequestMapping(path = "/word", method = RequestMethod.GET)
    public ResponseEntity<?> showData() throws IOException {

    ResponseEntity respEntity = null;
    String responseNews = NEWSAPINewsService.getResponseNews();
    wordResponseService.createWordDocument(responseNews);

    byte[] reportBytes = null;

    File result = new File(docFile);
    if(result.exists()){
        InputStream inputStream = new FileInputStream(docFile);
        //String type = result.toURL().openConnection().guessContentTypeFromName(docFile);

        byte[]out= IOUtils.toByteArray(inputStream);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("content-disposition", "attachment; filename=" + docFile);
        //responseHeaders.add("Content-Type", "docx");

        respEntity = new ResponseEntity(out, responseHeaders,HttpStatus.OK);
    }else{
        respEntity = new ResponseEntity ("File Not Found", HttpStatus.OK);
    }
    return respEntity;
    }
}
