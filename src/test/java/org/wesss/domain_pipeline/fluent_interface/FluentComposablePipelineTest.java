package org.wesss.domain_pipeline.fluent_interface;

import org.junit.Before;
import org.junit.Test;
import org.wesss.domain_pipeline.DomainPipeline;
import org.wesss.domain_pipeline.workers.Consumer;
import org.wesss.domain_pipeline.workers.Producer;
import org.wesss.domain_pipeline.workers.Translator;
import test_instantiation.basic.IntDomainObj;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.wesss.test_utils.MockUtils.genericMock;

public class FluentComposablePipelineTest {

    private Producer<IntDomainObj> mockIntProducer;
    private Translator<IntDomainObj, IntDomainObj> mockIntTranslator;
    private Translator<IntDomainObj, IntDomainObj> mockIntTranslator2;
    private Consumer<IntDomainObj> mockIntConsumer;

    public FluentComposablePipelineTest() {
        mockIntProducer = genericMock(Producer.class);
        mockIntTranslator = genericMock(Translator.class);
        mockIntTranslator2 = genericMock(Translator.class);
        mockIntConsumer = genericMock(Consumer.class);
    }

    @Before
    public void before() {
        reset(mockIntProducer, mockIntTranslator, mockIntTranslator2, mockIntConsumer);
        when(mockIntTranslator.getAcceptedClass()).thenReturn(IntDomainObj.class);
        when(mockIntTranslator2.getAcceptedClass()).thenReturn(IntDomainObj.class);
        when(mockIntConsumer.getAcceptedClass()).thenReturn(IntDomainObj.class);
    }

    @Test
    public void producerCanBeComposed() {
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
        Translator<IntDomainObj, IntDomainObj> composedTranslator = DomainPipeline.createComposedTranslator()
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
        Consumer<IntDomainObj> composedConsumer = DomainPipeline.createComposedConsumer()
                .firstTranslatedBy(mockIntTranslator)
                .thenConsumedBy(mockIntConsumer)
                .build();

        assertThat(composedConsumer, not(nullValue()));

        verify(mockIntTranslator, never()).initAcceptor(any());
        verify(mockIntTranslator).initPasser(any());
        verify(mockIntConsumer).initAcceptor(any());
    }
}
