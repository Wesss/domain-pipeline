package org.wesss.domain_pipeline.fluent_interface;

import org.junit.Before;
import org.junit.Test;
import org.wesss.domain_pipeline.DomainPipeline;
import org.wesss.domain_pipeline.workers.Consumer;
import org.wesss.domain_pipeline.workers.Producer;
import org.wesss.domain_pipeline.workers.Translator;
import org.wesss.general_utils.exceptions.IllegalUseException;
import test_instantiation.basic.IntDomainObj;
import test_instantiation.basic.IntProducer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.wesss.test_utils.MockUtils.genericMock;

public class FluentPipelineTest {

    // TODO error case: add a pipeline worker into more than 1 pipeline worker slot (in same or different pipelines)

    private Producer<IntDomainObj> mockIntProducer;
    private Translator<IntDomainObj, IntDomainObj> mockIntTranslator;
    private Translator<IntDomainObj, IntDomainObj> mockIntTranslator2;
    private Consumer<IntDomainObj> mockIntConsumer;

    public FluentPipelineTest() {
        mockIntProducer = genericMock(Producer.class);
        mockIntTranslator = genericMock(Translator.class);
        mockIntTranslator2 = genericMock(Translator.class);
        mockIntConsumer = genericMock(Consumer.class);
    }

    @Before
    public void before() {
        reset(mockIntProducer, mockIntTranslator, mockIntTranslator2, mockIntConsumer);
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
    public void consumerCanBeComposed() {
        Producer<IntDomainObj> composedProducer = DomainPipeline.createComposedProducer()
                .startingWith(mockIntProducer)
                .thenTranslatedBy(mockIntTranslator)
                .build();

        assertThat(composedProducer, not(nullValue()));

        verify(mockIntProducer).initPasser(any());
        verify(mockIntTranslator).initAcceptor(any());
        verify(mockIntTranslator, never()).initPasser(any());
    }

    @Test
    public void translatorCanBeComposed() {
        Translator<IntDomainObj, IntDomainObj> composedTranslator = DomainPipeline.createdComposedTranslator()
                .firstTranslatedBy(mockIntTranslator)
                .thenTranslatedBy(mockIntTranslator2)
                .build();

        assertThat(composedTranslator, not(nullValue()));

        verify(mockIntTranslator, never()).initAcceptor(any());
        verify(mockIntTranslator).initPasser(any());
        verify(mockIntTranslator2).initAcceptor(any());
        verify(mockIntTranslator2, never()).initPasser(any());
    }

    @Test
    public void consumerCanBeComposed() {
        Consumer<IntDomainObj> composedConsumer = DomainPipeline.createdComposedConsumer()
                .firstTranslatedBy(mockIntTranslator)
                .thenConsumedBy(mockIntConsumer)
                .build();

        assertThat(composedConsumer, not(nullValue()));

        verify(mockIntTranslator, never()).initAcceptor(any());
        verify(mockIntTranslator).initPasser(any());
        verify(mockIntConsumer).initAcceptor(any());
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
        FluentPipelineAddWorkersStage<IntDomainObj> preConsumerStage = DomainPipeline.createPipeline()
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
