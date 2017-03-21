package org.wesss.domain_pipeline.fluent_interface;

import org.junit.Before;
import org.junit.Test;
import org.wesss.domain_pipeline.DomainPipeline;
import org.wesss.domain_pipeline.workers.Consumer;
import org.wesss.domain_pipeline.workers.Producer;
import org.wesss.general_utils.exceptions.IllegalUseException;
import test_instantiation.basic.IntDomainObj;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.wesss.test_utils.MockUtils.genericMock;

public class FluentPipelineTest {

    private Producer<IntDomainObj> mockIntProducer;
    private Consumer<IntDomainObj> mockIntConsumer;

    public FluentPipelineTest() {
        mockIntProducer = genericMock(Producer.class);
        mockIntConsumer = genericMock(Consumer.class);
    }

    @Before
    public void before() {
        reset(mockIntProducer, mockIntConsumer);
    }

    @Test
    public void buildMinimumPipeline() {
        DomainPipeline domainPipeline = DomainPipeline.createPipeline()
                .startingWith(mockIntProducer)
                .thenConsumedBy(mockIntConsumer)
                .build();

        assertThat(domainPipeline, not(nullValue()));

        verify(mockIntProducer).init(any());
        verify(mockIntProducer, never()).start();
    }

    @Test
    public void callingStartingWithTwiceThrowsError() {
        FluentPipelinePostInitStage pipeline = DomainPipeline.createPipeline();
        pipeline.startingWith(mockIntProducer);
        try {
            pipeline.startingWith(mockIntProducer);
            fail();
        } catch (IllegalUseException ignored) {

        }
    }

    @Test
    public void callingThenConsumedByTwiceThrowsError() {
        FluentPipelinePostProducerStage preConsumerStage = DomainPipeline.createPipeline()
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
        FluentPipelinePostConsumerStage preBuildStage = DomainPipeline.createPipeline()
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
            DomainPipeline.createPipeline()
                .startingWith(null)
                .thenConsumedBy(mockIntConsumer)
                .build();

            fail();
        } catch (NullPointerException ignored) {

        }
    }
}
