package test_instantiation.annotated_consumption;

import org.wesss.domain_pipeline.Accepts;

public class InheritIllegalDuplicationConsumer extends AbstractInheritConsumer {

    @Override
    public void acceptDomain(DomainObjRoot domainObj) {

    }

    @Accepts(DomainObjRoot.class)
    public void overrideAcceptDomain(DomainObjRoot domainObj) {

    }

    @Accepts(DomainObjRoot.class)
    public void overrideAcceptDomain2(DomainObjRoot domainObj) {

    }
}
