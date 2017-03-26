package test_instantiation.annotated_consumption;

import org.wesss.domain_pipeline.Accepts;

public class InheritRedundantConsumer extends AbstractInheritConsumer {

    @Override
    @Accepts(DomainObjRoot.class)
    public void acceptDomain(DomainObjRoot domainObj) {
        DomainConsumption consumption =
                new DomainConsumption(DomainObjRoot.class, domainObj.getClass());
        receivedDomainObjects.add(consumption);
    }
}
