package org.wesss.domain_pipeline.node_wrappers;

import org.wesss.domain_pipeline.DomainObj;

public interface DomainTranslatorNode<T extends DomainObj, V extends DomainObj>
        extends DomainAcceptorNode<T>, DomainPasserNode<V> {
}
