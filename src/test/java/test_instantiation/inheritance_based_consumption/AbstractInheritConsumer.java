package test_instantiation.inheritance_based_consumption;

import org.wesss.domain_pipeline.workers.Consumer;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractInheritConsumer extends Consumer<DomainObjRoot> {

    protected List<DomainConsumption> receivedDomainObjects;

    public AbstractInheritConsumer() {
        super(DomainObjRoot.class);
        receivedDomainObjects = new ArrayList<>();
    }

    public List<DomainConsumption> getReceivedDomainObjects() {
        return new ArrayList<>(receivedDomainObjects);
    }
}
