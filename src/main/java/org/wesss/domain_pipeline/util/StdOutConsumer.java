package org.wesss.domain_pipeline.util;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.workers.Consumer;

public class StdOutConsumer extends Consumer<DomainObj> {

    public StdOutConsumer() {
        super(DomainObj.class);
    }

    @Override
    public void acceptDomain(DomainObj domainObj) {
        System.out.println(domainObj);
    }
}
