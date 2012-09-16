
package com.muleinaction;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.mule.DefaultMuleMessage;
import org.mule.api.MuleMessage;
import org.mule.api.el.ExpressionLanguage;
import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;
import org.xml.sax.InputSource;

public class ExpressionsTestCase extends FunctionalTestCase
{
    @Override
    protected String getConfigResources()
    {
        return "expressions.xml";
    }

    @Test
    public void string() throws Exception
    {
        MuleClient muleClient = new MuleClient(muleContext);

        MuleMessage result = muleClient.send("vm://string-expression.in",
            "<payment />", null);

        String idValue = DocumentBuilderFactory.newInstance()
            .newDocumentBuilder()
            .parse(
                new InputSource(new StringReader(
                    result.getPayloadAsString())))
            .getDocumentElement()
            .getAttribute("tid");

        assertThat("Got: " + result.getPayloadAsString(),
            idValue.split("-").length, is(5));
    }

    @Test
    public void logger() throws Exception
    {
        MuleClient muleClient = new MuleClient(muleContext);

        muleClient.send("vm://logger.in", "<invoice id='123' />", null);
    }

    @Test
    public void ensureAttached() throws Exception
    {
        MuleClient muleClient = new MuleClient(muleContext);
        MuleMessage muleMessage = new DefaultMuleMessage("foo",
            muleContext);
        assertThat(muleClient.send("vm://ensure.attached", muleMessage)
            .getPayloadAsString(), is("ERROR: no attachment!"));

        muleMessage.addOutboundAttachment("data", "bar", "text/plain");
        assertThat(muleClient.send("vm://ensure.attached", muleMessage)
            .getPayloadAsString(), is("OK"));
    }

    @Test
    public void dynamicDispatcher() throws Exception
    {
        assertThat(
            new MuleClient(muleContext).send("vm://ticker.fetcher",
                "GOOG".getBytes(), null)
                .getPayloadAsString()
                .contains("Google"), is(true));
    }

    @Test
    public void evaluationFromCode() throws Exception
    {
        //<start id="lis_02_expressions-5"/>
        ExpressionLanguage mel = muleContext.getExpressionLanguage();
        String applicationName = mel.evaluate("app.name");
        //<end id="lis_02_expressions-5"/>
        assertThat(mel.isValid("'test'"), is(true));
        assertThat(StringUtils.isNotBlank(applicationName), is(true));
        String testEl = "targetDir = new java.io.File(server.tmpDir, 'target');targetDir.mkdir();targetDir";
        File targetDir = mel.evaluate(testEl);
        assertThat(targetDir, is(notNullValue()));
    }
}
