//<start id="lis_12_expressions-6"/>
package com.prancingdonkey.evaluator;

import java.util.ResourceBundle;

import org.mule.api.MuleMessage;
import org.mule.api.expression.ExpressionEvaluator;

public class MessageBundleEvaluator implements ExpressionEvaluator
{
    public Object evaluate(String expression, MuleMessage ignored)
    {
        return ResourceBundle.getBundle("prancing-donkey-messages")
            .getObject(expression);
    }

    public String getName()
    {
        return "message-bundle";
    }

    public void setName(String name)
    {
        throw new UnsupportedOperationException();
    }
}
//<end id="lis_12_expressions-6"/>