
package com.prancingdonkey.transformer;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import au.com.bytecode.opencsv.CSVReader;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractTransformer;

public class CSVToListOfMapsTransformer extends AbstractTransformer {

    @Override
    protected Object doTransform(final Object src, final String enc) throws TransformerException {

        List<Map> products = new ArrayList<Map>();

        CSVReader reader = new CSVReader(new StringReader((String) src));

        String[] nextLine;
        try {
            while ((nextLine = reader.readNext()) != null) {
                Map<String,String> product = new HashMap<String,String>();
                product.put("name",nextLine[0]);
                product.put("acv",nextLine[1]);
                product.put("cost",nextLine[2]);
                product.put("description",nextLine[3]);
                products.add(product);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return products;
    }
}
