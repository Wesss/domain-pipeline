package org.wesss.domain_pipeline;

import org.junit.Before;
import org.wesss.domain_pipeline.pipeline_workers.Consumer;
import org.wesss.domain_pipeline.pipeline_workers.Generator;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;

public class DomainPipelineTest {

    private Generator<DomainObj> mockGenerator;
    private Consumer<DomainObj> mockConsumer;
    private DomainPipeline domainPipeline;

    public DomainPipelineTest() {
        mockConsumer = genericMock(Consumer.class);
        mockGenerator = genericMock(Generator.class);
        domainPipeline = new DomainPipeline(mockGenerator, mockConsumer);
    }

    @Before
    public void before() {
        reset(mockGenerator, mockConsumer);
    }

    // TODO move to test utils
    @SuppressWarnings("unchecked")
    static <T> T genericMock(Class<? super T> classToMock) { return (T)mock(classToMock); }
}
