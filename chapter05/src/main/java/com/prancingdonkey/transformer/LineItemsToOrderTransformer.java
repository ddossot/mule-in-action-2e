package com.prancingdonkey.transformer;

import com.prancingdonkey.domain.LineItem;
import com.prancingdonkey.domain.Order;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractTransformer;

import java.util.List;

public class LineItemsToOrderTransformer extends AbstractTransformer {

    @Override
    protected Object doTransform(Object src, String enc) throws TransformerException {
        List<LineItem> lineItems = (List<LineItem>) src;
        Order order = new Order();
        order.setLineItems(lineItems);
        return order;
    }
}

