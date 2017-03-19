package org.wesss.domain_pipeline.builder;

import org.junit.Before;
import org.junit.Test;
import org.wesss.domain_pipeline.DomainPipeline;
import org.wesss.domain_pipeline.workers.Consumer;
import org.wesss.domain_pipeline.workers.Producer;
import test_instantiation.TestIntDomainObj;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.wesss.test_utils.MockUtils.genericMock;

public class DomainPipelineBuilderTest {

    private Producer<TestIntDomainObj> mockIntProducer;
    private Consumer<TestIntDomainObj> mockIntConsumer;
    private DomainPipelineBuilder builder;

    public DomainPipelineBuilderTest() {
        mockIntProducer = genericMock(Producer.class);
        mockIntConsumer = genericMock(Consumer.class);
    }

    @Before
    public void before() {
        reset(mockIntProducer, mockIntConsumer);
        builder = new DomainPipelineBuilder();
    }

    @Test
    public void buildMinimumPipeline() {
        DomainPipeline domainPipeline = builder.startingWith(mockIntProducer)
                .thenConsumedBy(mockIntConsumer)
                .build();

        assertThat(domainPipeline, not(nullValue()));

        verify(mockIntProducer).init(any());
        verify(mockIntProducer, never()).start();
    }

    @Test
    public void callingStartingWithTwiceThrowsError() {
        builder.startingWith(mockIntProducer);
        try {
            builder.startingWith(mockIntProducer);
            fail();
        } catch (Exception ignored) {

        }
    }

    @Test
    public void callingThenConsumedByTwiceThrowsError() {
        DomainPipelineBuilderPostProducerStage preConsumerStage =
                builder.startingWith(mockIntProducer);
        preConsumerStage.thenConsumedBy(mockIntConsumer);
        try {
            preConsumerStage.thenConsumedBy(mockIntConsumer);
            fail();
        } catch (Exception ignored) {

        }
    }

    @Test
    public void callingBuildTwiceThrowsError() {
        DomainPipelineBuilderPostConsumerStage preBuildStage =
                builder.startingWith(mockIntProducer).thenConsumedBy(mockIntConsumer);
        preBuildStage.build();
        try {
            preBuildStage.build();
            fail();
        } catch (Exception ignored) {

        }
    }
}
