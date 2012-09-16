package com.prancingdonkey.service;

import java.util.List;

import javax.jws.WebService;

import com.prancingdonkey.model.Brew;

@WebService(endpointInterface="com.prancingdonkey.service.BrewService",
	serviceName = "BrewService")
public class BrewServiceImpl implements BrewService {

    public List<Brew> getBrews() {
        return Brew.findAll();
    }
}

