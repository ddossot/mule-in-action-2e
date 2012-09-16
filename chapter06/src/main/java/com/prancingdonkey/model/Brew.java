package com.prancingdonkey.model;

import java.util.Date;
import java.util.List;

import com.google.common.collect.ImmutableList;

public class Brew {

    String name;

    String description;

    Date processedTime;
    
    public Brew() {
    }

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
    
    public Date getProcessedTime() {
		return processedTime;
	}

	public void setProcessedTime(Date processedTime) {
		this.processedTime = processedTime;
	}

	static public List<Brew> findAll() {
        return ImmutableList.of(new Brew("Hobbit IPA", "Hobbit IPA"), new Brew("Frodo's Lager", "Frodo's Lager"));
    }

}
