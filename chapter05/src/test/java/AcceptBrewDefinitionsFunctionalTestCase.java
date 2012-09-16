import com.prancingdonkey.domain.Brew;
import org.junit.Test;
import org.mule.tck.junit4.FunctionalTestCase;

public class AcceptBrewDefinitionsFunctionalTestCase extends FunctionalTestCase {

    @Override
    protected String getConfigResources() {
        return "src/main/app/acceptBrewDefinitions.xml";
    }


    @Test
    public void testCanSubmitBrewDefinition() throws Exception {
        Brew brew = new Brew();
        muleContext.getClient().dispatch("jms://brew.definitions", brew, null);
    }
}

