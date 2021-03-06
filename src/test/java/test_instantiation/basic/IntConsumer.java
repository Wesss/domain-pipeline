package test_instantiation.basic;

import org.wesss.domain_pipeline.util.interdomain.IntDomain;
import org.wesss.domain_pipeline.workers.Consumer;

import java.util.ArrayList;
import java.util.List;

public class IntConsumer extends Consumer<IntDomain> {

    private List<Integer> receivedDomainObjects;

    public IntConsumer() {
        super(IntDomain.class);
        receivedDomainObjects = new ArrayList<>();
    }

    @Override
    public void acceptDomain(IntDomain domainObj) {
        receivedDomainObjects.add(domainObj.getInt());
    }

    public List<Integer> getReceivedDomainObjects() {
        return new ArrayList<>(receivedDomainObjects);
    }
}
