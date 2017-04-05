package test_instantiation.annotated_consumption;

import org.wesss.domain_pipeline.Accepts;
import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.util.interdomain.IntDomain;

public class InheritIllegalAnnotationConsumer extends AbstractInheritConsumer {

    @Override
    public void acceptDomain(DomainObjRoot domainObj) {

    }

    // IntDomain is an unrelated sibling to DomainObjRoot
    @Accepts(IntDomain.class)
    public void acceptIntDomain(DomainObj obj) {

    }
}
