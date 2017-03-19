package org.wesss.domain_pipeline;

import org.junit.Before;
import org.junit.Test;
import org.wesss.domain_pipeline.pipeline_worker.Consumer;
import org.wesss.domain_pipeline.pipeline_worker.Producer;

import static org.mockito.Mockito.*;
import static org.wesss.test_utils.MockUtils.genericMock;

public class DomainPipelineTest {

    private Producer<DomainObj> mockProducer;
    private Consumer<DomainObj> mockConsumer;
    private DomainPipeline domainPipeline;

    public DomainPipelineTest() {
        mockProducer = genericMock(Producer.class);
        mockConsumer = genericMock(Consumer.class);
        domainPipeline = new DomainPipeline(mockProducer, mockConsumer);
    }

    @Before
    public void before() {
        reset(mockProducer, mockConsumer);
    }

    @Test
    public void startStartsGenerator() {
        domainPipeline.start();

        verify(mockProducer).start();
    }

    @Test
    public void noStartDoesNotStartGenerator() {
        verify(mockProducer, never()).start();
    }
}
