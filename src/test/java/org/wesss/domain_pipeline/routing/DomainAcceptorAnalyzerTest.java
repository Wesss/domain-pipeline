package org.wesss.domain_pipeline.routing;

import org.junit.Test;
import org.wesss.domain_pipeline.workers.Consumer;
import test_instantiation.annotated_consumption.*;
import test_instantiation.basic.IntConsumer;
import test_instantiation.basic.IntDomainObj;
import test_instantiation.inheritance_consumption.GenericConsumer;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

public class DomainAcceptorAnalyzerTest {

    @Test
    public void analyzeMinimalAcceptor() {
        IntConsumer consumer = new IntConsumer();
        PostAnalysisDomainAcceptor<IntDomainObj> analyzedDomainAcceptor =
                DomainAcceptorAnalyzer.analyzeDomainAcceptor(consumer);

        IntDomainObj domainObj = new IntDomainObj(0);
        analyzedDomainAcceptor.acceptDomain(domainObj);

        assertThat(consumer.getReceivedDomainObjects(), contains(0));
    }

    @Test
    public void analyzeAnnotatedAcceptor() {
        InheritDomainSubclassConsumer consumer = new InheritDomainSubclassConsumer();
        PostAnalysisDomainAcceptor<DomainObjRoot> analyzedDomainAcceptor =
                DomainAcceptorAnalyzer.analyzeDomainAcceptor(consumer);

        analyzedDomainAcceptor.acceptDomain(new DomainObjRoot());
        analyzedDomainAcceptor.acceptDomain(new DomainObjLeaf1());
        analyzedDomainAcceptor.acceptDomain(new DomainObjLeaf2());
        analyzedDomainAcceptor.acceptDomain(new DomainObjLeaf1_1());

        List<DomainConsumption> expected = new ArrayList<>();
        expected.add(new DomainConsumption(DomainObjRoot.class, DomainObjRoot.class));
        expected.add(new DomainConsumption(DomainObjLeaf1.class, DomainObjLeaf1.class));
        expected.add(new DomainConsumption(DomainObjRoot.class, DomainObjLeaf2.class));
        expected.add(new DomainConsumption(DomainObjLeaf1.class, DomainObjLeaf1_1.class));

        assertThat(consumer.getReceivedDomainObjects(), is(expected));
    }

    @Test
    public void analyzeGenericAcceptor() {
        GenericConsumer<IntDomainObj> genericConsumer = new GenericConsumer<>(IntDomainObj.class);
        PostAnalysisDomainAcceptor<IntDomainObj> analyzedDomainAcceptor =
                DomainAcceptorAnalyzer.analyzeDomainAcceptor(genericConsumer);

        IntDomainObj domainObj = new IntDomainObj(0);
        analyzedDomainAcceptor.acceptDomain(domainObj);

        assertThat(genericConsumer.getReceivedDomainObjects(), contains(domainObj));
    }
}
