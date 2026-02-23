package com.readdit.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table
public class Genre {

    @Id
    private int id;
    
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Genre() {
        this.id = 0;
        this.name = "";
    }

    public Genre(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
