package org.wesss.domain_pipeline.workers.swappable;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.routing.swapper.ConsumerSwapper;
import org.wesss.domain_pipeline.workers.Consumer;
import org.wesss.domain_pipeline.workers.Producer;

/**
 * A Consumer that can be swapped out with other producers arbitrarily
 *
 * @param <T> the type of domain consumed by this consumer
 */
public abstract class SwappableConsumer<T extends DomainObj> extends Consumer<T> {

    protected ConsumerSwapper<T> swapper;

    /**
     * @param acceptedClazz the class that is accepted by this consumer
     */
    public SwappableConsumer(Class<T> acceptedClazz) {
        super(acceptedClazz);
    }

    public void initSwapper(ConsumerSwapper<T> swapper) {
        this.swapper = swapper;
    }
}
