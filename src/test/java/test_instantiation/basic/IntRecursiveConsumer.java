package test_instantiation.basic;

import org.wesss.domain_pipeline.workers.Consumer;

import java.util.ArrayList;
import java.util.List;

public class IntRecursiveConsumer extends Consumer<IntDomainObj> {
    private List<Integer> receivedDomainObjects;

    public IntRecursiveConsumer() {
        super(IntDomainObj.class);
        receivedDomainObjects = new ArrayList<>();
    }

    @Override
    public void acceptDomain(IntDomainObj domainObj) {
        int val = domainObj.getId();
        receivedDomainObjects.add(val);
        if (val < 0) {
            recursiveEmitter.emit(new IntDomainObj(val + 10));
        }
    }

    public List<Integer> getReceivedDomainObjects() {
        return new ArrayList<>(receivedDomainObjects);
    }
}
