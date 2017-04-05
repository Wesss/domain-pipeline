package test_instantiation.basic;

import org.wesss.domain_pipeline.util.interdomain.IntDomain;
import org.wesss.domain_pipeline.workers.Consumer;

import java.util.ArrayList;
import java.util.List;

public class IntRecursiveConsumer extends Consumer<IntDomain> {
    private List<Integer> receivedDomainObjects;

    public IntRecursiveConsumer() {
        super(IntDomain.class);
        receivedDomainObjects = new ArrayList<>();
    }

    @Override
    public void acceptDomain(IntDomain domainObj) {
        int val = domainObj.getInt();
        receivedDomainObjects.add(val);
        if (val < 0) {
            recursiveEmitter.emit(new IntDomain(val + 10));
        }
    }

    public List<Integer> getReceivedDomainObjects() {
        return new ArrayList<>(receivedDomainObjects);
    }
}
