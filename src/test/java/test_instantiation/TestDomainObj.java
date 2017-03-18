package test_instantiation;

import org.wesss.domain_pipeline.DomainObj;

public class TestDomainObj extends DomainObj {
    private Integer id;

    public TestDomainObj(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
