package test_instantiation.annotated_consumption;

import org.wesss.domain_pipeline.Accepts;

public class InheritRerouteConsumer extends AbstractInheritConsumer {

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
}
