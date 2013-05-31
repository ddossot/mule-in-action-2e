package com.prancingdonkey.service;


import com.prancingdonkey.model.Brew;

import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@WebService(endpointInterface = "com.prancingdonkey.service.BrewService", serviceName = "BrewService")
@Path("/brews")
public class BrewServiceImpl implements BrewService {

    @GET
    @Produces("application/json")
    public List<Brew> getBrews() {
        return Brew.findAll();
    }
}

