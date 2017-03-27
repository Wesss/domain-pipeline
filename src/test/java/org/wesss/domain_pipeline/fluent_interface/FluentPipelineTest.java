package org.wesss.domain_pipeline.fluent_interface;

import org.junit.Before;
import org.junit.Test;
import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.DomainPipeline;
import org.wesss.domain_pipeline.workers.Consumer;
import org.wesss.domain_pipeline.workers.Producer;
import org.wesss.domain_pipeline.workers.Translator;
import org.wesss.general_utils.exceptions.IllegalUseException;
import test_instantiation.basic.IntDomainObj;
import test_instantiation.basic.IntProducer;
import test_instantiation.basic.ObjConsumer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.wesss.test_utils.MockUtils.genericMock;

public class FluentPipelineTest {

    private Producer<IntDomainObj> mockIntProducer;
    private Translator<IntDomainObj, IntDomainObj> mockIntTranslator;
    private Consumer<IntDomainObj> mockIntConsumer;
    private Consumer<DomainObj> mockObjConsumer;

    public FluentPipelineTest() {
        mockIntProducer = genericMock(Producer.class);
        mockIntTranslator = genericMock(Translator.class);
        mockIntConsumer = genericMock(Consumer.class);
        mockObjConsumer = genericMock(Consumer.class);
    }

    @Before
    public void before() {
        reset(mockIntProducer, mockIntTranslator, mockIntConsumer);
        when(mockIntTranslator.getAcceptedClass()).thenReturn(IntDomainObj.class);
        when(mockIntConsumer.getAcceptedClass()).thenReturn(IntDomainObj.class);
        when(mockObjConsumer.getAcceptedClass()).thenReturn(DomainObj.class);
    }

    @Test
    public void minimumPipelineIsBuilt() {
        DomainPipeline domainPipeline = DomainPipeline.createPipeline()
                .startingWith(mockIntProducer)
                .thenConsumedBy(mockIntConsumer)
                .build();

        assertThat(domainPipeline, not(nullValue()));

        verify(mockIntProducer).initPasser(any());
        verify(mockIntConsumer).initAcceptor(any());
        verify(mockIntProducer, never()).start();
    }

    @Test
    public void translatorPipelineIsBuilt() {
        DomainPipeline domainPipeline = DomainPipeline.createPipeline()
                .startingWith(mockIntProducer)
                .thenTranslatedBy(mockIntTranslator)
                .thenConsumedBy(mockIntConsumer)
                .build();

        assertThat(domainPipeline, not(nullValue()));

        verify(mockIntProducer).initPasser(any());
        verify(mockIntTranslator).initAcceptor(any());
        verify(mockIntTranslator).initPasser(any());
        verify(mockIntConsumer).initAcceptor(any());
        verify(mockIntProducer, never()).start();
    }

    @Test
    public void weakerConsumerPipelineIsBuilt() {
        DomainPipeline domainPipeline = DomainPipeline.createPipeline()
                .startingWith(mockIntProducer)
                .thenConsumedBy(mockObjConsumer)
                .build();

        assertThat(domainPipeline, not(nullValue()));

        verify(mockIntProducer).initPasser(any());
        verify(mockObjConsumer).initAcceptor(any());
        verify(mockIntProducer, never()).start();
    }

    @Test
    public void callingStartingWithTwiceThrowsError() {
        FluentPipelineInitProducerStage pipeline = DomainPipeline.createPipeline();
        pipeline.startingWith(mockIntProducer);
        try {
            pipeline.startingWith(mockIntProducer);
            fail();
        } catch (IllegalUseException ignored) {

        }
    }

    @Test
    public void callingThenConsumedByTwiceThrowsError() {
        FluentPipelineAddWorkerStage<IntDomainObj> preConsumerStage = DomainPipeline.createPipeline()
                .startingWith(mockIntProducer);
        preConsumerStage.thenConsumedBy(mockIntConsumer);
        try {
            preConsumerStage.thenConsumedBy(mockIntConsumer);
            fail();
        } catch (IllegalUseException ignored) {

        }
    }

    @Test
    public void callingBuildTwiceThrowsError() {
        FluentPipelineFinalizeStage preBuildStage = DomainPipeline.createPipeline()
                .startingWith(mockIntProducer)
                .thenConsumedBy(mockIntConsumer);
        preBuildStage.build();
        try {
            preBuildStage.build();
            fail();
        } catch (IllegalUseException ignored) {

        }
    }

    @Test
    public void passingInNullPipelineWorkerThrowsError() {
        try {
            IntProducer nullProducer = null;
            DomainPipeline.createPipeline()
                    .startingWith(nullProducer)
                    .thenConsumedBy(mockIntConsumer)
                    .build();

            fail();
        } catch (NullPointerException ignored) {

        }
    }
}
