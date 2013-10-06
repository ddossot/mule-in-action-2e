package com.prancingdonkey.product.event;

import com.prancingdonkey.product.model.Product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductIngestCompleted implements Serializable {

    Date date;

    List<Product> products = new ArrayList<Product>();

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
