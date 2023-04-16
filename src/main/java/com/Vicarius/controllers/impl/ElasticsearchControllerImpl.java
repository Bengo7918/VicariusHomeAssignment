package com.Vicarius.controllers.impl;

import com.Vicarius.controllers.ElasticsearchController;
import com.Vicarius.modules.BookStore;
import com.Vicarius.services.ElasticsearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ElasticsearchControllerImpl implements ElasticsearchController {

    @Autowired
    private ElasticsearchService elasticsearchService;

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public void createIndex() {
        log.info("Received request to create an index");
        this.elasticsearchService.createIndex();
        log.info("Created index successfully");
    }

    @Override
    public ResponseEntity<String> createDocument() {
        log.info("Received request to create a document");
        String docId = this.elasticsearchService.createDocument();
        log.info("Created document with id: {} successfully", docId);
        return new ResponseEntity<>(docId, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<String> getDocument(String documentId) {
        log.info("Received request to get a document");
        String doc = this.elasticsearchService.getDocument(documentId);
        log.info("Got document: {} successfully", doc);
        return new ResponseEntity<>(doc, HttpStatus.OK);
    }
}
