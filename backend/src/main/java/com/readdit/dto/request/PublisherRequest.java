package com.readdit.dto.request;

import com.readdit.model.Publisher;

public class PublisherRequest {

    private String name;

    public PublisherRequest() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Publisher toPublisher() {
        Publisher publisher = new Publisher();
        publisher.setName(name);
        return publisher;
    }
}
