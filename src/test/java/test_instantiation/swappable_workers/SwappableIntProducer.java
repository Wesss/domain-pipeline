package test_instantiation.swappable_workers;

import org.wesss.domain_pipeline.util.interdomain.IntDomain;
import org.wesss.domain_pipeline.workers.Producer;
import org.wesss.domain_pipeline.workers.swappable.SwappableProducer;

public class SwappableIntProducer extends SwappableProducer<IntDomain> {

    @Override
    public void run() {

    }

    public void emitDomainObject(int id) {
        emitter.emit(new IntDomain(id));
    }

    public void swapTo(Producer<IntDomain> producer) {
        swapper.swapTo(producer);
    }
}
