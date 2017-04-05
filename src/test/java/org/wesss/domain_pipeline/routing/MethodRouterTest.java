package org.wesss.domain_pipeline.routing;

import org.junit.Test;
import org.wesss.domain_pipeline.util.interdomain.IntDomain;
import test_instantiation.annotated_consumption.*;
import test_instantiation.basic.IntConsumer;
import test_instantiation.inheritance_consumption.GenericConsumer;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

public class MethodRouterTest {

    @Test
    public void analyzeMinimalAcceptor() {
        IntConsumer consumer = new IntConsumer();
        MethodRouter<IntDomain> methodRouter =
                MethodRouterFactory.getMethodRouter(consumer);

        IntDomain domainObj = new IntDomain(0);
        methodRouter.acceptDomain(domainObj);

        assertThat(consumer.getReceivedDomainObjects(), contains(0));
    }

    @Test
    public void analyzeAnnotatedAcceptor() {
        InheritDomainSubclassConsumer consumer = new InheritDomainSubclassConsumer();
        MethodRouter<DomainObjRoot> methodRouter =
                MethodRouterFactory.getMethodRouter(consumer);

        methodRouter.acceptDomain(new DomainObjRoot());
        methodRouter.acceptDomain(new DomainObjLeaf1());
        methodRouter.acceptDomain(new DomainObjLeaf2());
        methodRouter.acceptDomain(new DomainObjLeaf1_1());

        List<DomainConsumption> expected = new ArrayList<>();
        expected.add(new DomainConsumption(DomainObjRoot.class, DomainObjRoot.class));
        expected.add(new DomainConsumption(DomainObjLeaf1.class, DomainObjLeaf1.class));
        expected.add(new DomainConsumption(DomainObjRoot.class, DomainObjLeaf2.class));
        expected.add(new DomainConsumption(DomainObjLeaf1.class, DomainObjLeaf1_1.class));

        assertThat(consumer.getReceivedDomainObjects(), is(expected));
    }

    @Test
    public void analyzeGenericAcceptor() {
        GenericConsumer<IntDomain> genericConsumer = new GenericConsumer<>(IntDomain.class);
        MethodRouter<IntDomain> methodRouter =
                MethodRouterFactory.getMethodRouter(genericConsumer);

        IntDomain domainObj = new IntDomain(0);
        methodRouter.acceptDomain(domainObj);

        assertThat(genericConsumer.getReceivedDomainObjects(), contains(domainObj));
    }
}
