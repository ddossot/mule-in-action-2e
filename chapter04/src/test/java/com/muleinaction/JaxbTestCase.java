
package com.muleinaction;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;

import com.prancingdonkey.model.jaxb.Friend;

public class JaxbTestCase extends FunctionalTestCase
{

    @Override
    protected String getConfigResources()
    {
        return "jaxb.xml";
    }

    @Test
    public void testJaxb() throws Exception
    {
        MuleClient muleClient = new MuleClient(muleContext);

        Friend friend = new Friend();
        friend.setName("John");
        friend.setSurname("Doe");

        MuleMessage result = muleClient.send("vm://jaxb-simple.in", friend, null);
        assertThat(result, is(notNullValue()));
        assertThat(result.getPayload(), is(notNullValue()));
        assertThat(result.getPayload(), is(instanceOf(Friend.class)));
    }

}
