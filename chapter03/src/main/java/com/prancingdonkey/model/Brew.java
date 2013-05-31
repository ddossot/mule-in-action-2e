
package com.prancingdonkey.model;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.ImmutableList;

public class Brew implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String name;
    private String description;

    public Brew(final String name, final String description)
    {
        this.name = name;
        this.description = description;
    }

    public String getName()
    {
        return name;
    }

    public void setName(final String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(final String description)
    {
        this.description = description;
    }

    static public List<Brew> findAll()
    {
        return ImmutableList.of(new Brew("Hobbit IPA", "Hobbit IPA"), new Brew("Frodos Lager",
            "Frodos Lager"));
    }

}
