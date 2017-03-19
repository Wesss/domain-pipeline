package test_instantiation.inheritance_based_consumption;

import org.wesss.domain_pipeline.Accepts;
import org.wesss.domain_pipeline.workers.Consumer;

import java.util.ArrayList;
import java.util.List;

public class InheritRerouteConsumer extends Consumer<DomainObjRoot> {

    private List<DomainConsumption> receivedDomainObjects;

    public InheritRerouteConsumer() {
        receivedDomainObjects = new ArrayList<>();
    }

    @Override
    public void acceptDomain(DomainObjRoot domainObj) {
        throw new RuntimeException("default acceptDomain should be completely rerouted");
    }

    @Accepts(DomainObjRoot.class)
    public void overrideAcceptDomain(DomainObjRoot domainObj) {
        DomainConsumption consumption =
                new DomainConsumption(DomainObjRoot.class, domainObj.getClass());
        receivedDomainObjects.add(consumption);
    }

    public List<DomainConsumption> getReceivedDomainObjects() {
        return new ArrayList<>(receivedDomainObjects);
    }

}
