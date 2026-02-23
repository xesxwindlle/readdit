package com.readdit.dto.request;

import com.readdit.model.Publisher;

public class PublisherRequest {

    private String id;

    private String name;

    public PublisherRequest() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Publisher toPublisher() {
        Publisher publisher = new Publisher();
        publisher.setId(id);
        publisher.setName(name);
        return publisher;
    }
}
