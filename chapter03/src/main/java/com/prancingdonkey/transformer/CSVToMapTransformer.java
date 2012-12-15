
package com.prancingdonkey.transformer;

import java.util.HashMap;

import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractTransformer;

public class CSVToMapTransformer extends AbstractTransformer
{

    @Override
    protected Object doTransform(final Object src, final String enc) throws TransformerException
    {
        // TODO Implement
        return new HashMap<Object, Object>();
    }
}
