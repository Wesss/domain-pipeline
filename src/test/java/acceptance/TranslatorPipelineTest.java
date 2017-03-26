package acceptance;

import org.junit.Before;
import org.junit.Test;
import org.wesss.domain_pipeline.DomainPipeline;
import test_instantiation.basic.CharConsumer;
import test_instantiation.basic.IntProducer;
import test_instantiation.basic.IntToCharTranslator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;

public class TranslatorPipelineTest {

    private IntProducer intProducer;
    private IntToCharTranslator intToCharTranslator;
    private CharConsumer charConsumer;

    @Before
    public void before() {
        intProducer = new IntProducer();
        intToCharTranslator = new IntToCharTranslator();
        charConsumer = new CharConsumer();
        DomainPipeline translatorPipeline = DomainPipeline.createPipeline()
                .startingWith(intProducer)
                .thenTranslatedBy(intToCharTranslator)
                .thenConsumedBy(charConsumer)
                .build();

        translatorPipeline.start();
    }

    /********** Test Cases **********/

    @Test
    public void generatingNothingDoesNothing() {
        assertThat(charConsumer.getReceivedDomainObjects(), empty());
    }

    @Test
    public void sentDomainObjectIsConsumed() {
        intProducer.emitDomainObject(0);

        assertThat(charConsumer.getReceivedDomainObjects(), contains('0'));
    }

    @Test
    public void manySentDomainObjectAreConsumedInOrder() {
        for (int i = 0; i < 5; i++) {
            intProducer.emitDomainObject(i);
        }

        assertThat(charConsumer.getReceivedDomainObjects(), contains('0', '1', '2', '3', '4'));
    }
}
