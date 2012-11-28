
package com.muleinaction;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;

import com.prancingdonkey.statistics.ActivityReport;

import org.junit.Test;

public class XStreamTestCase extends FunctionalTestCase
{

    @Override
    protected String getConfigResources()
    {
        return "xstream.xml";
    }

    @Test
    public void testRegular() throws Exception
    {
        MuleClient muleClient = new MuleClient(muleContext);

        ActivityReport payload = new ActivityReport();
        payload.setValue1("1");
        payload.setValue2("2");

        MuleMessage result = muleClient.send("vm://xstream_xml-to-object-regular.in", payload, null);
        assertThat(result, is(notNullValue()));
        assertThat(result.getPayload(), is(notNullValue()));
        assertThat(result.getPayload(), is(instanceOf(ActivityReport.class)));
    }

    @Test
    public void testAlias() throws Exception
    {
        MuleClient muleClient = new MuleClient(muleContext);

        ActivityReport payload = new ActivityReport();
        payload.setValue1("1");
        payload.setValue2("2");

        MuleMessage result = muleClient.send("vm://xstream_xml-to-object-alias.in", payload, null);
        assertThat(result, is(notNullValue()));
        assertThat(result.getPayload(), is(notNullValue()));
        assertThat(result.getPayload(), is(instanceOf(ActivityReport.class)));
    }

    @Test
    public void testMuleMessage() throws Exception
    {
        MuleClient muleClient = new MuleClient(muleContext);

        MuleMessage result = muleClient.send("vm://xstream_xml-to-object-mule-message.in", "dummy", null);
        assertThat(result, is(notNullValue()));
        assertThat(result.getPayload(), is(notNullValue()));
        assertThat(result.getPayload(), is(instanceOf(String.class)));
    }

}
