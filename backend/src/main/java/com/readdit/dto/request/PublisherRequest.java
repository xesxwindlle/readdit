package com.readdit.dto.request;

import com.readdit.model.Publisher;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PublisherRequest {

    @NotBlank
    private String id;

    @NotBlank
    private String name;

    public PublisherRequest() {
    }

    public Publisher toPublisher() {
        Publisher publisher = new Publisher();
        publisher.setId(id);
        publisher.setName(name);
        return publisher;
    }
}
