package test_instantiation.inheritance_based_consumption;

import org.wesss.domain_pipeline.Accepts;

public class InheritIllegalSignatureConsumer extends AbstractInheritConsumer {

    @Override
    public void acceptDomain(DomainObjRoot domainObj) {

    }

    @Accepts(DomainObjLeaf1.class)
    public void overrideAcceptDomain(DomainObjRoot domainObj) {

    }
}
