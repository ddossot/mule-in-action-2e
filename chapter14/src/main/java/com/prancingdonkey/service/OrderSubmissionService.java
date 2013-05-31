package com.prancingdonkey.service;


import com.prancingdonkey.model.Order;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService
public interface OrderSubmissionService {

    @WebResult(name = "summary")
    Order processOrder(@WebParam(name = "order") Order order);
}
