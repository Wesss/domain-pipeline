package org.wesss.domain_pipeline;

import org.junit.Before;
import org.junit.Test;
import org.wesss.domain_pipeline.node_wrappers.ProducerNode;

import static org.mockito.Mockito.*;
import static org.wesss.test_utils.MockUtils.genericMock;

public class DomainPipelineTest {

    private ProducerNode<DomainObj> mockProducerNode;
    private DomainPipeline domainPipeline;

    public DomainPipelineTest() {
        mockProducerNode = genericMock(ProducerNode.class);
        domainPipeline = new DomainPipeline(mockProducerNode);
    }

    @Before
    public void before() {
        reset(mockProducerNode);
    }

    @Test
    public void startStartsGenerator() {
        domainPipeline.start();

        verify(mockProducerNode).start();
    }

    @Test
    public void noStartDoesNotStartGenerator() {
        verify(mockProducerNode, never()).start();
    }
}
