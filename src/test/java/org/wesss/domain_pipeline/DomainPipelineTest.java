package org.wesss.domain_pipeline;

import org.junit.Before;
import org.junit.Test;
import org.wesss.domain_pipeline.pipeline_worker.Consumer;
import org.wesss.domain_pipeline.pipeline_worker.Generator;

import static org.mockito.Mockito.*;
import static org.wesss.test_utils.MockUtils.genericMock;

public class DomainPipelineTest {

    private Generator<DomainObj> mockGenerator;
    private Consumer<DomainObj> mockConsumer;
    private DomainPipeline domainPipeline;

    public DomainPipelineTest() {
        mockGenerator = genericMock(Generator.class);
        mockConsumer = genericMock(Consumer.class);
        domainPipeline = new DomainPipeline(mockGenerator, mockConsumer);
    }

    @Before
    public void before() {
        reset(mockGenerator, mockConsumer);
    }

    @Test
    public void startStartsGenerator() {
        domainPipeline.start();

        verify(mockGenerator).start();
    }

    @Test
    public void noStartDoesNotStartGenerator() {
        verify(mockGenerator, never()).start();
    }
}
