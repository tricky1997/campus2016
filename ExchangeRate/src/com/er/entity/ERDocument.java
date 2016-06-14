package com.er.entity;

import org.jsoup.nodes.Document;

/**
 * Created by lenovo on 2016/6/8.
 */
public class ERDocument {

    public Document getDoc() {
        return doc;
    }

    public void setDoc(Document doc) {
        this.doc = doc;
    }

    private Document doc;

    public ERDocument(Document doc) {
        this.doc = doc;
    }
}
