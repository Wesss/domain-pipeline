package org.wesss.domain_pipeline;

import org.junit.Before;
import org.junit.Test;
import org.wesss.domain_pipeline.pipeline_worker.Consumer;
import org.wesss.domain_pipeline.pipeline_worker.Producer;
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

    private Producer<TestIntDomainObj> mockIntProducer;
    private Consumer<TestIntDomainObj> mockIntConsumer;
    private Consumer<TestCharDomainObj> mockCharConsumer;

    public DomainPipelineFactoryTest() {
        mockIntProducer = genericMock(Producer.class);
        mockIntConsumer = genericMock(Consumer.class);
        mockCharConsumer = genericMock(Consumer.class);
    }

    @Before
    public void before() {
        reset(mockIntProducer, mockIntConsumer, mockCharConsumer);
        when(mockIntProducer.getEmittedDomainClass()).thenReturn(TestIntDomainObj.class);
        when(mockIntConsumer.getAcceptedDomainClass()).thenReturn(TestIntDomainObj.class);
        when(mockCharConsumer.getAcceptedDomainClass()).thenReturn(TestCharDomainObj.class);
    }

    @Test
    public void instantiateMinimalPipeline() {
        DomainPipeline domainPipeline =
                DomainPipelineFactory.getDomainPipeline(mockIntProducer, mockIntConsumer);

        assertThat(domainPipeline, not(nullValue()));
        verify(mockIntProducer).init(argSuchThat(Objects::nonNull));
    }

    @Test
    public void instantiatingIncorrectMinimalPipelineThrowsError() {
        try {
            DomainPipelineFactory.getDomainPipeline(mockIntProducer, mockCharConsumer);
            fail();
        } catch (Exception ignored) {

        }
    }
}
