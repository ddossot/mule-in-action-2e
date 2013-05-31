import org.mule.tck.junit4.FunctionalTestCase;


//ToDo Remove this class
public class AggregateResponsesFunctionalTestCase extends FunctionalTestCase {

    @Override
    protected String getConfigResources() {
        return  "src/main/app/aggregateResponses.xml";
    }


    public void testCanAggregateResponses() throws Exception {
    }
}
