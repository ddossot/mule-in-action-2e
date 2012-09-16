
package com.prancingdonkey.transformers;

import org.mule.api.transformer.DiscoverableTransformer;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractTransformer;
import org.mule.transformer.types.DataTypeFactory;

import com.prancingdonkey.statistics.ActivityReport;

public class StringToActivityReport extends AbstractTransformer implements DiscoverableTransformer
{
    private int weighting = 1;

    public StringToActivityReport()
    {
        registerSourceType(DataTypeFactory.create(String.class));
        setReturnDataType(DataTypeFactory.create(ActivityReport.class));
    }

    @Override
    protected Object doTransform(Object src, String encoding) throws TransformerException
    {
        return new ActivityReport();
    }

    public int getPriorityWeighting()
    {
        return weighting;
    }

    public void setPriorityWeighting(int weighting)
    {
        this.weighting = weighting;
    }

}
