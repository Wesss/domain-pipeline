package acceptance;

import org.junit.Before;
import org.junit.Test;
import org.wesss.domain_pipeline.DomainPipeline;
import org.wesss.domain_pipeline.fluent_interface.FluentPipelineInitProducerStage;
import org.wesss.general_utils.exceptions.IllegalUseException;
import test_instantiation.annotated_consumption.*;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;

public class AnnotatedConsumerTest {

    // TODO support consumers that extend each other (super class has conflicting subclass annotations)

    private InheritProducer producer;

    @Before
    public void before() {
        producer = new InheritProducer();
    }

    public AbstractInheritConsumer setupRedundantPipeline() {
        AbstractInheritConsumer redundantConsumer = new InheritRedundantConsumer();
        DomainPipeline pipeline = DomainPipeline.createPipeline()
                .startingWith(producer)
                .thenConsumedBy(redundantConsumer)
                .build();

        pipeline.start();
        return redundantConsumer;
    }

    public AbstractInheritConsumer setupReroutePipeline() {
        AbstractInheritConsumer rerouteConsumer = new InheritRerouteConsumer();
        DomainPipeline pipeline = new FluentPipelineInitProducerStage()
                .startingWith(producer)
                .thenConsumedBy(rerouteConsumer)
                .build();

        pipeline.start();
        return rerouteConsumer;
    }

    public AbstractInheritConsumer setupSubclassPipeline() {
        AbstractInheritConsumer subclassConsumer = new InheritDomainSubclassConsumer();
        DomainPipeline pipeline = new FluentPipelineInitProducerStage()
                .startingWith(producer)
                .thenConsumedBy(subclassConsumer)
                .build();

        pipeline.start();
        return subclassConsumer;
    }

    /********** Test Cases **********/

    @Test
    public void redundantPipelineIsUnchanged() {
        AbstractInheritConsumer consumer = setupRedundantPipeline();

        producer.emitRoot();
        producer.emitLeaf1();
        producer.emitLeaf1_1();
        producer.emitLeaf2();

        List<DomainConsumption> expected = new ArrayList<>();
        expected.add(new DomainConsumption(DomainObjRoot.class, DomainObjRoot.class));
        expected.add(new DomainConsumption(DomainObjRoot.class, DomainObjLeaf1.class));
        expected.add(new DomainConsumption(DomainObjRoot.class, DomainObjLeaf1_1.class));
        expected.add(new DomainConsumption(DomainObjRoot.class, DomainObjLeaf2.class));

        assertThat(consumer.getReceivedDomainObjects(), is(expected));
    }

    @Test
    public void reroutePipelineIsRerouted() {
        AbstractInheritConsumer consumer = setupReroutePipeline();

        producer.emitRoot();
        producer.emitLeaf1();
        producer.emitLeaf1_1();
        producer.emitLeaf2();

        List<DomainConsumption> expected = new ArrayList<>();
        expected.add(new DomainConsumption(DomainObjRoot.class, DomainObjRoot.class));
        expected.add(new DomainConsumption(DomainObjRoot.class, DomainObjLeaf1.class));
        expected.add(new DomainConsumption(DomainObjRoot.class, DomainObjLeaf1_1.class));
        expected.add(new DomainConsumption(DomainObjRoot.class, DomainObjLeaf2.class));

        assertThat(consumer.getReceivedDomainObjects(), is(expected));
    }

    @Test
    public void subclassPipelineDoesntRerouteRoot() {
        AbstractInheritConsumer consumer = setupSubclassPipeline();

        producer.emitRoot();

        List<DomainConsumption> expected = new ArrayList<>();
        expected.add(new DomainConsumption(DomainObjRoot.class, DomainObjRoot.class));

        assertThat(consumer.getReceivedDomainObjects(), is(expected));
    }

    @Test
    public void subclassPipelineReroutesSpecifiedSubclass() {
        AbstractInheritConsumer consumer = setupSubclassPipeline();

        producer.emitLeaf1();

        List<DomainConsumption> expected = new ArrayList<>();
        expected.add(new DomainConsumption(DomainObjLeaf1.class, DomainObjLeaf1.class));

        assertThat(consumer.getReceivedDomainObjects(), is(expected));
    }

    @Test
    public void subclassPipelineDoesNotRerouteUntouchedSubclass() {
        AbstractInheritConsumer consumer = setupSubclassPipeline();

        producer.emitLeaf2();

        List<DomainConsumption> expected = new ArrayList<>();
        expected.add(new DomainConsumption(DomainObjRoot.class, DomainObjLeaf2.class));

        assertThat(consumer.getReceivedDomainObjects(), is(expected));
    }

    @Test
    public void subclassPipelineDoesRerouteSubclassToClosestReroutedParent() {
        AbstractInheritConsumer consumer = setupSubclassPipeline();

        producer.emitLeaf1_1();

        List<DomainConsumption> expected = new ArrayList<>();
        expected.add(new DomainConsumption(DomainObjLeaf1.class, DomainObjLeaf1_1.class));

        assertThat(consumer.getReceivedDomainObjects(), is(expected));
    }

    @Test
    public void illegalSignatureConsumerThrowsError() {
        try {
            DomainPipeline.createPipeline()
                    .startingWith(producer)
                    .thenConsumedBy(new InheritIllegalSignatureConsumer())
                    .build();
            fail();
        } catch (IllegalUseException ignored) {

        }
    }

    @Test
    public void illegalDuplicationConsumerThrowsError() {
        try {
            DomainPipeline.createPipeline()
                    .startingWith(producer)
                    .thenConsumedBy(new InheritIllegalDuplicationConsumer())
                    .build();
            fail();
        } catch (IllegalUseException ignored) {

        }
    }

    @Test
    public void illegalAnnotationConsumerThrowsError() {
        try {
            DomainPipeline.createPipeline()
                    .startingWith(producer)
                    .thenConsumedBy(new InheritIllegalAnnotationConsumer())
                    .build();
            fail();
        } catch (IllegalUseException ignored) {

        }
    }
}
