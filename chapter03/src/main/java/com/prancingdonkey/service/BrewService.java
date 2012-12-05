package com.prancingdonkey.service;

import com.prancingdonkey.model.Brew;

import javax.jws.WebService;
import java.util.List;

@WebService
public interface BrewService {

    List<Brew> getBrews();
}
