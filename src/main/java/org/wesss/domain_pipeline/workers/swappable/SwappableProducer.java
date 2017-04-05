package org.wesss.domain_pipeline.workers.swappable;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.routing.swapper.ProducerSwapper;
import org.wesss.domain_pipeline.workers.Producer;

/**
 * A Producer that can be swapped out with other producers arbitrarily
 *
 * @param <T> the type of domain produced by this producer
 */
public abstract class SwappableProducer<T extends DomainObj> extends Producer<T> {

    protected ProducerSwapper<T> swapper;

    public void initSwapper(ProducerSwapper<T> swapper) {
        this.swapper = swapper;
    }
}
