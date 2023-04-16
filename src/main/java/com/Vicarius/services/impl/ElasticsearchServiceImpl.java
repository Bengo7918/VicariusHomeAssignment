package com.Vicarius.services.impl;

import com.Vicarius.controllers.advice.exceptions.BookStoreNotFoundException;
import com.Vicarius.controllers.advice.exceptions.BookStoreServerErrorException;
import com.Vicarius.modules.BookStore;
import com.Vicarius.services.ElasticsearchService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
public class ElasticsearchServiceImpl implements ElasticsearchService {

    private final RestClient client;
    private final String INDEX_NAME = "store_007";
    private final ObjectMapper mapper;

    @Autowired
    public ElasticsearchServiceImpl(RestClient client, ObjectMapper mapper) {
        this.client = client;
        this.mapper = mapper;
    }

    @Override
    public void createIndex() {
        try {
            Request request = new Request("PUT", "/" + this.INDEX_NAME);

            Response response = this.client.performRequest(request);
            if (response.getStatusLine().getStatusCode() != 200) {
                throw new BookStoreServerErrorException("Error with creating an index. Status code: " + response.getStatusLine().getStatusCode());
            }
        } catch (Exception ex) {
            log.error("Failed to create new index", ex);
            throw new BookStoreServerErrorException(ex.getMessage());
        }
    }

    @Override
    public String createDocument() {
        try {
            String docId = UUID.randomUUID().toString();
            /*
            Normally when opening a store we don't have books, but for the purpose of this exercise,
            lets assume we buy an existing book store which can hold already purchased books in a range of 1 to 100 books.
             */
            BookStore store = BookStore.builder().docId(docId).name("Ben & co books").totalBooks(ThreadLocalRandom.current().nextInt(1, 100)).build();
            Request request = new Request("PUT", "/" + this.INDEX_NAME + "/_doc/" + docId);
            request.setJsonEntity(this.mapper.writeValueAsString(store));

            Response response = this.client.performRequest(request);
            if (response.getStatusLine().getStatusCode() == 201) {
                return docId;
            }
            throw new BookStoreServerErrorException("Error with creating a document. Status code: " + response.getStatusLine().getStatusCode());
        } catch (Exception ex) {
            log.error("Failed to create new BookStore", ex);
            throw new BookStoreServerErrorException(ex.getMessage());
        }
    }

    @Override
    public BookStore getDocument(String documentId) {
        try {
            Request request = new Request("GET", "/" + this.INDEX_NAME + "/_doc/" + documentId);

            Response response = this.client.performRequest(request);
            if (response.getStatusLine().getStatusCode() == 200) {
                return this.mapper.readValue(response.getEntity().getContent(), BookStore.class);
            } else {
                String msg = "book store with id: " + documentId + " not found";
                log.warn(msg);
                throw new BookStoreNotFoundException(msg);
            }
        } catch (Exception ex) {
            log.error("Failed to get BookStore with id: {}", documentId, ex);
            throw new BookStoreServerErrorException(ex.getMessage());
        }
    }
}
