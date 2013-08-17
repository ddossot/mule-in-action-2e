package com.prancingdonkey.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.prancingdonkey.model.Brew;

@Path("/brews")
public class BrewServiceImpl implements BrewService {

    @GET
    @Produces("application/json")
    @Path("/list")
    public List<Brew> getBrews() {
        return Brew.findAll();
    }
}

