package org.wesss.domain_pipeline.util.domain;

import org.wesss.domain_pipeline.DomainObj;

import java.util.Objects;

public class StringDomainObj extends DomainObj {
    private String string;

    public StringDomainObj(String string) {
        Objects.requireNonNull(string);
        this.string = string;
    }

    @Override
    public String toString() {
        return string;
    }
}
