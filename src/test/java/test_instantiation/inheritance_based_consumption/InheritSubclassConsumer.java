package test_instantiation.inheritance_based_consumption;

import org.wesss.domain_pipeline.Accepts;
import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.workers.Consumer;

import java.util.ArrayList;
import java.util.List;

public class InheritSubclassConsumer extends Consumer<DomainObjRoot> {

    private List<DomainConsumption> receivedDomainObjects;

    public InheritSubclassConsumer() {
        receivedDomainObjects = new ArrayList<>();
    }

    @Override
    @Accepts(DomainObjRoot.class)
    public void acceptDomain(DomainObjRoot domainObj) {
        DomainConsumption consumption =
            new DomainConsumption(DomainObjRoot.class, domainObj.getClass());
        receivedDomainObjects.add(consumption);
    }

    @Accepts(DomainObjLeaf1.class)
    public void acceptLeaf1(DomainObjLeaf1 domainObj) {
        DomainConsumption consumption =
                new DomainConsumption(DomainObjLeaf1.class, domainObj.getClass());
        receivedDomainObjects.add(consumption);
    }

    public List<DomainConsumption> getReceivedDomainObjects() {
        return new ArrayList<>(receivedDomainObjects);
    }

}
