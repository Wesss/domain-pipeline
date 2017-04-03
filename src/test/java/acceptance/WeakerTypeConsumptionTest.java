package acceptance;

import org.junit.Before;
import org.junit.Test;
import org.wesss.domain_pipeline.DomainPipeline;
import test_instantiation.basic.IntDomainObj;
import test_instantiation.basic.IntProducer;
import test_instantiation.basic.ObjConsumer;

import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;

public class WeakerTypeConsumptionTest {

    private IntProducer intProducer;
    private ObjConsumer objConsumer;

    @Before
    public void before() {
        intProducer = new IntProducer();
        objConsumer = new ObjConsumer();
        DomainPipeline minimalPipeline = DomainPipeline.createPipeline()
                .startingWith(intProducer)
                .thenConsumedBy(objConsumer)
                .build();

        minimalPipeline.start();
    }

    @Test
    public void sentDomainObjectsAreConsumedByAWeakerTypeConsumer() {
        for (int i = 0; i < 3; i++) {
            intProducer.emitDomainObject(i);
        }

        assertThat(objConsumer.getReceivedDomainObjects().stream()
                        .map(domainObj -> ((IntDomainObj) domainObj).getId())
                        .collect(Collectors.toList()),
                hasItems(0, 1, 2));
    }
}
