
package com.muleinaction;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;

public class RawTransportTestCase
{
    @Test
    public void testCxfWsdlRawCall() throws Exception
    {
        //<start id="lis_12_raw-cxf"/>
        MuleClient muleClient = new MuleClient(true);//<co id="lis_12_raw-cxf_1"/>
        MuleMessage result = muleClient.send(
            "wsdl-cxf:http://www.webservicex.net/CurrencyConvertor.asmx?WSDL"
            +"&method=ConversionRate",
            new String[]{"CAD", "USD"},
            null,
            15000);//<co id="lis_12_raw-cxf_2"/>
        Double cadToUsd = (Double)result.getPayload();//<co id="lis_12_raw-cxf_3"/>
        muleClient.dispose();//<co id="lis_12_raw-cxf_4"/>
        //<end id="lis_12_raw-cxf"/>

        assertThat(cadToUsd, is(notNullValue()));
    }
}
