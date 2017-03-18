package test_instantiation;

import org.wesss.domain_pipeline.pipeline_worker.Consumer;

import java.util.ArrayList;
import java.util.List;

public class TestConsumer extends Consumer<TestIntDomainObj> {

    private List<Integer> receivedDomainObjects;

    public TestConsumer() {
        super(TestIntDomainObj.class);
        receivedDomainObjects = new ArrayList<>();
    }

    @Override
    public void acceptDomainObject(TestIntDomainObj domainObj) {
        receivedDomainObjects.add(domainObj.getId());
    }

    public List<Integer> getReceivedDomainObjects() {
        return new ArrayList<>(receivedDomainObjects);
    }
}
