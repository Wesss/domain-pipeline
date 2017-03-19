package test_instantiation.inheritance_based_consumption;

import org.wesss.domain_pipeline.Accepts;
import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.workers.Consumer;

import java.util.ArrayList;
import java.util.List;

public class InheritDomainSubclassConsumer extends Consumer<DomainObjRoot> {

    public static final String ACCEPT_ROOT_METHOD_NAME = "acceptDomain";
    public static final String ACCEPT_LEAF1_METHOD_NAME = "acceptLeaf1";

    private List<DomainConsumption> receivedDomainObjects;

    public InheritDomainSubclassConsumer() {
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
