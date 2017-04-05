package test_instantiation.swappable_workers;

import org.wesss.domain_pipeline.util.interdomain.IntDomain;
import org.wesss.domain_pipeline.workers.Translator;
import org.wesss.domain_pipeline.workers.swappable.SwappableTranslator;

public class SwappableIntTranslator extends SwappableTranslator<IntDomain, IntDomain> {

    public SwappableIntTranslator() {
        super(IntDomain.class);
    }

    @Override
    public void acceptDomain(IntDomain domainObj) {
        emitter.emit(domainObj);
    }

    public void swapTo(Translator<IntDomain, IntDomain> translator) {
        swapper.swapTo(translator);
    }
}
