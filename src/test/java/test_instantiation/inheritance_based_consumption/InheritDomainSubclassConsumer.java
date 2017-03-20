package test_instantiation.inheritance_based_consumption;

import org.wesss.domain_pipeline.Accepts;

public class InheritDomainSubclassConsumer extends AbstractInheritConsumer {

    public static final String ACCEPT_ROOT_METHOD_NAME = "acceptDomain";
    public static final String ACCEPT_LEAF1_METHOD_NAME = "acceptLeaf1";

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
}
