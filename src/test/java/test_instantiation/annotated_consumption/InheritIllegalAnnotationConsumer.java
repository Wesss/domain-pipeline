package test_instantiation.annotated_consumption;

import org.wesss.domain_pipeline.Accepts;
import org.wesss.domain_pipeline.DomainObj;
import test_instantiation.basic.IntDomainObj;

public class InheritIllegalAnnotationConsumer extends AbstractInheritConsumer {

    @Override
    public void acceptDomain(DomainObjRoot domainObj) {

    }

    // IntDomainObj is an unrelated sibling to DomainObjRoot
    @Accepts(IntDomainObj.class)
    public void acceptIntDomain(DomainObj obj) {

    }
}
