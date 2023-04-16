package com.Vicarius.services;

import com.Vicarius.modules.BookStore;

public interface ElasticsearchService {

    void createIndex();

    String createDocument();

    String getDocument(String documentId);
}
