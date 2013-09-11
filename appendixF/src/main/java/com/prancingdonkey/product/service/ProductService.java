package com.prancingdonkey.product.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.prancingdonkey.product.model.Product;


@Path("products")
public class ProductService {
	
	@PersistenceContext
	EntityManager entityManager;
		
	@GET
    @Produces("application/json")
	@SuppressWarnings("unchecked")
    public List<Product> getProducts() {			
		return entityManager.createQuery("SELECT product FROM Product product").getResultList();
    }
	

}
