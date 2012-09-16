package com.pracingdonkey.service;

import com.pracingdonkey.model.Brew;

import javax.jws.WebService;
import java.util.List;

@WebService
public interface BrewService {

    List<Brew> getBrews();
}
