package test_instantiation;

import org.wesss.domain_pipeline.DomainObj;

public class TestCharDomainObj extends DomainObj {
    private char id;

    public TestCharDomainObj(char id) {
        this.id = id;
    }

    public char getId() {
        return id;
    }
}
