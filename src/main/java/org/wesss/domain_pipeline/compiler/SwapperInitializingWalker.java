package org.wesss.domain_pipeline.compiler;

import org.wesss.domain_pipeline.DomainObj;
import org.wesss.domain_pipeline.node_wrappers.ConsumerNode;
import org.wesss.domain_pipeline.node_wrappers.ProducerNode;
import org.wesss.domain_pipeline.node_wrappers.TranslatorNode;
import org.wesss.domain_pipeline.routing.swapper.ConsumerSwapper;
import org.wesss.domain_pipeline.routing.swapper.ProducerSwapper;
import org.wesss.domain_pipeline.routing.swapper.TranslatorSwapper;
import org.wesss.domain_pipeline.workers.Consumer;
import org.wesss.domain_pipeline.workers.Producer;
import org.wesss.domain_pipeline.workers.Translator;
import org.wesss.domain_pipeline.workers.swappable.SwappableConsumer;
import org.wesss.domain_pipeline.workers.swappable.SwappableProducer;
import org.wesss.domain_pipeline.workers.swappable.SwappableTranslator;

public class SwapperInitializingWalker extends PipelineWalker {

    @Override
    public <T extends DomainObj> void visit(ProducerNode<T> producerNode) {
        super.visit(producerNode);

        initProducerSwapper(producerNode);
    }

    public static <T extends DomainObj> void initProducerSwapper(ProducerNode<T> producerNode) {
        Producer<T> producer = producerNode.getProducer();
        if (producer instanceof SwappableProducer) {
            SwappableProducer<T> swappableProducer = (SwappableProducer<T>) producer;
            swappableProducer.initSwapper(new ProducerSwapper<>(producerNode));
        }
    }

    @Override
    public <T extends DomainObj, V extends DomainObj> void visit(TranslatorNode<T, V> translatorNode) {
        super.visit(translatorNode);

        initTranslatorSwapper(translatorNode);
    }

    public static <T extends DomainObj, V extends DomainObj> void
    initTranslatorSwapper(TranslatorNode<T, V> translatorNode) {
        Translator<T, V> translator = translatorNode.getTranslator();
        if (translator instanceof SwappableTranslator) {
            SwappableTranslator<T, V> swappableTranslator = (SwappableTranslator<T, V>) translator;
            swappableTranslator.initSwapper(new TranslatorSwapper<>(translatorNode));
        }
    }

    @Override
    public <T extends DomainObj> void visit(ConsumerNode<T> consumerNode) {
        super.visit(consumerNode);

        initConsumerSwapper(consumerNode);
    }

    public static <T extends DomainObj> void initConsumerSwapper(ConsumerNode<T> consumerNode) {
        Consumer<T> consumer = consumerNode.getConsumer();
        if (consumer instanceof SwappableConsumer) {
            SwappableConsumer<T> swappableConsumer = (SwappableConsumer<T>) consumer;
            swappableConsumer.initSwapper(new ConsumerSwapper<>(consumerNode));
        }
    }
}
