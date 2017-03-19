package test_instantiation.basic;

import org.wesss.domain_pipeline.workers.Consumer;

import java.util.ArrayList;
import java.util.List;

public class IntConsumer extends Consumer<IntDomainObj> {

    private List<Integer> receivedDomainObjects;

    public IntConsumer() {
        super(IntDomainObj.class);
        receivedDomainObjects = new ArrayList<>();
    }

    @Override
    public void acceptDomain(IntDomainObj domainObj) {
        receivedDomainObjects.add(domainObj.getId());
    }

    public List<Integer> getReceivedDomainObjects() {
        return new ArrayList<>(receivedDomainObjects);
    }
}
