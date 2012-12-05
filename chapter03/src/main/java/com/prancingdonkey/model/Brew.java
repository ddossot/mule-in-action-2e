package com.prancingdonkey.model;

import com.google.common.collect.ImmutableList;

import java.io.Serializable;
import java.util.List;

public class Brew implements Serializable {

    String name;

    String description;

    public Brew(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    static public List<Brew> findAll() {
        return ImmutableList.of(new Brew("Hobbit IPA", "Hobbit IPA"), new Brew("Frodo's Lager", "Frodo's Lager"));
    }


}
