package com.prancingdonkey.domain;


import java.io.Serializable;

public class LineItem implements Serializable {

    private Brew brew;
    private Integer quantity;

    public Brew getBrew() {
        return brew;
    }

    public void setBrew(Brew brew) {
        this.brew = brew;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
