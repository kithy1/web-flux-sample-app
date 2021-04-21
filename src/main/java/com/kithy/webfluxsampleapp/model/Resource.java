package com.kithy.webfluxsampleapp.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Resource {

    private String id;
    private String content;

    public Resource() {
    }

    public Resource(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
