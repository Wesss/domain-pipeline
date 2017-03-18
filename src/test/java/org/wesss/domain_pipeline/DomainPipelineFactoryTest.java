package org.wesss.domain_pipeline;

import org.junit.Before;
import org.junit.Test;
import org.wesss.domain_pipeline.pipeline_worker.Consumer;
import org.wesss.domain_pipeline.pipeline_worker.Generator;
import test_instantiation.TestCharDomainObj;
import test_instantiation.TestIntDomainObj;

import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;
import static org.wesss.test_utils.MockUtils.genericMock;
import static org.wesss.test_utils.matchers.IsSuchThat.argSuchThat;

public class DomainPipelineFactoryTest {

    private Generator<TestIntDomainObj> mockIntGenerator;
    private Consumer<TestIntDomainObj> mockIntConsumer;
    private Consumer<TestCharDomainObj> mockCharConsumer;

    public DomainPipelineFactoryTest() {
        mockIntGenerator = genericMock(Generator.class);
        mockIntConsumer = genericMock(Consumer.class);
        mockCharConsumer = genericMock(Consumer.class);
    }

    @Before
    public void before() {
        reset(mockIntGenerator, mockIntConsumer, mockCharConsumer);
        when(mockIntGenerator.getDomainObjClass()).thenReturn(TestIntDomainObj.class);
        when(mockIntConsumer.getDomainObjClass()).thenReturn(TestIntDomainObj.class);
        when(mockCharConsumer.getDomainObjClass()).thenReturn(TestCharDomainObj.class);
    }

    @Test
    public void instantiateMinimalPipeline() {
        DomainPipeline domainPipeline =
                DomainPipelineFactory.getDomainPipeline(mockIntGenerator, mockIntConsumer);

        assertThat(domainPipeline, not(nullValue()));
        verify(mockIntGenerator).init(argSuchThat(Objects::nonNull));
    }

    @Test
    public void instantiatingIncorrectMinimalPipelineThrowsError() {
        try {
            DomainPipelineFactory.getDomainPipeline(mockIntGenerator, mockCharConsumer);
            fail();
        } catch (Exception ignored) {

        }
    }
}
