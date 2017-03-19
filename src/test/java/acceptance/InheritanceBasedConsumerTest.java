package acceptance;

import org.junit.Before;
import org.junit.Test;
import org.wesss.domain_pipeline.DomainPipeline;
import org.wesss.domain_pipeline.builder.DomainPipelineBuilder;
import test_instantiation.inheritance_based_consumption.*;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class InheritanceBasedConsumerTest {

    // TODO erroneous cases: double annotation, annotated base accept method w/subclass accept
    // TODO support consumers that extend each other (super class has conflicting subclass annotations)

    private InheritProducer producer;
    private InheritRedundantConsumer redundantConsumer;
    private InheritRerouteConsumer rerouteConsumer;
    private InheritSubclassConsumer subclassConsumer;

    @Before
    public void before() {
        producer = null;
        redundantConsumer = null;
        rerouteConsumer = null;
        subclassConsumer = null;
    }

    public void setupRedundantPipeline() {
        producer = new InheritProducer();
        redundantConsumer = new InheritRedundantConsumer();
        DomainPipeline pipeline = new DomainPipelineBuilder()
                .startingWith(producer)
                .thenConsumedBy(redundantConsumer)
                .build();

        pipeline.start();
    }

    public void setupReroutePipeline() {
        producer = new InheritProducer();
        rerouteConsumer = new InheritRerouteConsumer();
        DomainPipeline pipeline = new DomainPipelineBuilder()
                .startingWith(producer)
                .thenConsumedBy(rerouteConsumer)
                .build();

        pipeline.start();
    }

    public void setupSubclassPipeline() {
        producer = new InheritProducer();
        subclassConsumer = new InheritSubclassConsumer();
        DomainPipeline pipeline = new DomainPipelineBuilder()
                .startingWith(producer)
                .thenConsumedBy(subclassConsumer)
                .build();

        pipeline.start();
    }

    /********** Test Cases **********/

    @Test
    public void redundantPipelineIsUnchanged() {
        setupRedundantPipeline();

        producer.emitRoot();
        producer.emitLeaf1();
        producer.emitLeaf1_1();
        producer.emitLeaf2();

        List<DomainConsumption> expected = new ArrayList<>();
        expected.add(new DomainConsumption(DomainObjRoot.class, DomainObjRoot.class));
        expected.add(new DomainConsumption(DomainObjRoot.class, DomainObjLeaf1.class));
        expected.add(new DomainConsumption(DomainObjRoot.class, DomainObjLeaf1_1.class));
        expected.add(new DomainConsumption(DomainObjRoot.class, DomainObjLeaf2.class));

        assertThat(redundantConsumer.getReceivedDomainObjects(), is(expected));
    }

    @Test
    public void reroutePipelineIsRerouted() {
        setupReroutePipeline();

        producer.emitRoot();
        producer.emitLeaf1();
        producer.emitLeaf1_1();
        producer.emitLeaf2();

        List<DomainConsumption> expected = new ArrayList<>();
        expected.add(new DomainConsumption(DomainObjRoot.class, DomainObjRoot.class));
        expected.add(new DomainConsumption(DomainObjRoot.class, DomainObjLeaf1.class));
        expected.add(new DomainConsumption(DomainObjRoot.class, DomainObjLeaf1_1.class));
        expected.add(new DomainConsumption(DomainObjRoot.class, DomainObjLeaf2.class));

        assertThat(rerouteConsumer.getReceivedDomainObjects(), is(expected));
    }

    @Test
    public void subclassPipelineDoesntRerouteRoot() {
        setupSubclassPipeline();

        producer.emitRoot();

        List<DomainConsumption> expected = new ArrayList<>();
        expected.add(new DomainConsumption(DomainObjRoot.class, DomainObjRoot.class));

        assertThat(subclassConsumer.getReceivedDomainObjects(), is(expected));
    }

    @Test
    public void subclassPipelineReroutesSpecifiedSubclass() {
        setupSubclassPipeline();

        producer.emitLeaf1();

        List<DomainConsumption> expected = new ArrayList<>();
        expected.add(new DomainConsumption(DomainObjLeaf1.class, DomainObjLeaf1.class));

        assertThat(subclassConsumer.getReceivedDomainObjects(), is(expected));
    }

    @Test
    public void subclassPipelineDoesNotRerouteUntouchedSubclass() {
        setupSubclassPipeline();

        producer.emitLeaf2();

        List<DomainConsumption> expected = new ArrayList<>();
        expected.add(new DomainConsumption(DomainObjRoot.class, DomainObjLeaf2.class));

        assertThat(subclassConsumer.getReceivedDomainObjects(), is(expected));
    }

    @Test
    public void subclassPipelineDoesRerouteSubclassToClosestReroutedParent() {
        setupSubclassPipeline();

        producer.emitLeaf1_1();

        List<DomainConsumption> expected = new ArrayList<>();
        expected.add(new DomainConsumption(DomainObjLeaf1.class, DomainObjLeaf1_1.class));

        assertThat(subclassConsumer.getReceivedDomainObjects(), is(expected));
    }
}
