
package com.muleinaction;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.external.ExternalItem;
import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;

import com.prancingdonkey.model.json.Provider;

public class JsonTestCase extends FunctionalTestCase
{

    @Override
    protected String getConfigResources()
    {
        return "json.xml";
    }

    @Test
    public void testRegularJson() throws Exception
    {
        MuleClient muleClient = new MuleClient(muleContext);

        Provider payload = new Provider();
        payload.setName("test name");

        MuleMessage result = muleClient.send("vm://json-marshalling.in", payload, null);

        assertThat(result.getPayload(), is(instanceOf(Provider.class)));
    }

    @Test
    public void testMixinJson() throws Exception
    {
        MuleClient muleClient = new MuleClient(muleContext);

        ExternalItem payload = new ExternalItem();

        payload.setItemNumber("1234");
        payload.setUnwantedValue("notWanted");

        MuleMessage result = muleClient.send("vm://json-marshalling-mixin.in", payload, null);

        assertThat(result.getPayload(), is(instanceOf(ExternalItem.class)));
        assertThat(((ExternalItem) result.getPayload()).getUnwantedValue(), is(nullValue()));
    }

}
