package com.readdit.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Table
public class Publisher implements Persistable<String> {
    @Id
    private String id; 
    private String name;

    public Publisher() {}

    public Publisher(String id, String name) {
        this.id = id;
        this.name = name;
        this.isNew = true;
    }

    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Transient
    @JsonIgnore
    private boolean isNew = true;

    @JsonIgnore
    public boolean isNew() {
        return isNew;
    }

    @JsonIgnore
    public void setNew(boolean isNew) {
        this.isNew = isNew;
    }
    
    @Override
    public String getId() {
        return this.id;
    }
    
}
