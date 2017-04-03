package org.wesss.domain_pipeline.workers.composable;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.Emitter;
import org.wesss.domain_pipeline.node_wrappers.DomainPasserNode;
import org.wesss.domain_pipeline.node_wrappers.ProducerNode;
import org.wesss.domain_pipeline.util.TranslatorAsConsumer;
import org.wesss.domain_pipeline.workers.Producer;

public class ComposedProducer<T extends DomainObj> extends Producer<T> {

    private ProducerNode<?> producer;
    private TranslatorAsConsumer<T> fakeConsumer;

    public ComposedProducer(ProducerNode<?> producer, TranslatorAsConsumer<T> fakeConsumer) {
        this.producer = producer;
        this.fakeConsumer = fakeConsumer;
    }

    @Override
    public void initPasser(Emitter<T> emitter) {
        super.initPasser(emitter);
        fakeConsumer.initPasser(emitter);
    }

    @Override
    protected void run() {
        producer.start();
    }
}
