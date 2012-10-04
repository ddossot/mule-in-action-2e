package com.prancingdonkey.service;


import com.prancingdonkey.model.Order;

import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.UUID;

@WebService(endpointInterface = "com.prancingdonkey.service.OrderSubmissionService", serviceName = "OrderSubmission")
public class OrderSubmissionServiceImpl implements OrderSubmissionService {

    @Override
    public Order processOrder(@WebParam(name = "order") Order order) {

        order.setOrderId(UUID.randomUUID().toString());

        return order;
    }
}
