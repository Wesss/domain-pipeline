package test_instantiation.inheritance_based_consumption;

import org.wesss.domain_pipeline.Accepts;
import org.wesss.domain_pipeline.workers.Consumer;

import java.util.ArrayList;
import java.util.List;

public class InheritRedundantConsumer extends Consumer<DomainObjRoot> {

    private List<DomainConsumption> receivedDomainObjects;

    public InheritRedundantConsumer() {
        super(DomainObjRoot.class);
        receivedDomainObjects = new ArrayList<>();
    }

    @Override
    @Accepts(DomainObjRoot.class)
    public void acceptDomain(DomainObjRoot domainObj) {
        DomainConsumption consumption =
                new DomainConsumption(DomainObjRoot.class, domainObj.getClass());
        receivedDomainObjects.add(consumption);
    }

    public List<DomainConsumption> getReceivedDomainObjects() {
        return new ArrayList<>(receivedDomainObjects);
    }

}
