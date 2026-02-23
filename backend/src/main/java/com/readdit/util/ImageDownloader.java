package com.readdit.util;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ImageDownloader {

    public static byte[] downloadImage(String url) {

        byte[] imageBytes = new byte[] {};
        
        try {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        HttpResponse<byte[]> response = client.send(request,HttpResponse.BodyHandlers.ofByteArray());
        imageBytes = response.body();
        System.out.println("Downloaded bytes: " + imageBytes.length);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return imageBytes;        
    }
}
