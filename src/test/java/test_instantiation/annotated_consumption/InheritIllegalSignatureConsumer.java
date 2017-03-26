package test_instantiation.annotated_consumption;

import org.wesss.domain_pipeline.Accepts;

public class InheritIllegalSignatureConsumer extends AbstractInheritConsumer {

    @Override
    public void acceptDomain(DomainObjRoot domainObj) {

    }

    @Accepts(DomainObjLeaf1.class)
    public void overrideAcceptDomain(DomainObjRoot domainObj) {

    }
}
