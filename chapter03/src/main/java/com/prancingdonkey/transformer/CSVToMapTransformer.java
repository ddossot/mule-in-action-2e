package com.prancingdonkey.transformer;

import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractTransformer;

import java.util.HashMap;

public class CSVToMapTransformer extends AbstractTransformer {

    @Override
    protected Object doTransform(Object src, String enc) throws TransformerException {
        // ToDo Implement
        return new HashMap();
    }
}
