package com.prancingdonkey.transformer;

import com.prancingdonkey.model.Customer;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractTransformer;

import java.util.Map;

public class SalesforceQueryResultToCustomerTransformer extends AbstractTransformer {

    @Override
    protected Object doTransform(Object o, String s) throws TransformerException {
        if (o instanceof Map) {

            Map payload = (Map) o;
            Customer customer = new Customer();
            String[] nameComponents = ((String) payload.get("Name")).split(" ");
            customer.setFirstName(nameComponents[0]);
            customer.setLastName(nameComponents[1]);

            return customer;
        } else {
            throw new TransformerException(this, new ClassCastException());
        }

    }
}
