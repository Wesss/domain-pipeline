package test_instantiation.swappable_workers;

import org.wesss.domain_pipeline.util.interdomain.IntDomain;
import org.wesss.domain_pipeline.workers.Consumer;
import org.wesss.domain_pipeline.workers.swappable.SwappableConsumer;

import java.util.ArrayList;
import java.util.List;

public class SwappableIntConsumer extends SwappableConsumer<IntDomain> {

    private List<Integer> receivedDomainObjects;

    public SwappableIntConsumer() {
        super(IntDomain.class);
        receivedDomainObjects = new ArrayList<>();
    }

    @Override
    public void acceptDomain(IntDomain domainObj) {
        receivedDomainObjects.add(domainObj.getInt());
    }

    public List<Integer> getReceivedDomainObjects() {
        return receivedDomainObjects;
    }

    public void swapTo(Consumer<IntDomain> consumer) {
        swapper.swapTo(consumer);
    }
}
