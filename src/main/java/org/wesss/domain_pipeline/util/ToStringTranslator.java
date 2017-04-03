package org.wesss.domain_pipeline.util;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.util.interdomain.StringDomain;
import org.wesss.domain_pipeline.workers.Translator;

public class ToStringTranslator extends Translator<DomainObj, StringDomain> {

    public ToStringTranslator() {
        super(DomainObj.class);
    }

    @Override
    public void acceptDomain(DomainObj domainObj) {
        emitter.emit(new StringDomain(domainObj.toString()));
    }
}
