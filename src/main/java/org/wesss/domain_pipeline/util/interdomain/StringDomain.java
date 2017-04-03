package org.wesss.domain_pipeline.util.interdomain;

import org.wesss.domain_pipeline.DomainObj;

import java.util.Objects;

public class StringDomain extends DomainObj {
    private String string;

    public StringDomain(String string) {
        Objects.requireNonNull(string);
        this.string = string;
    }

    @Override
    public String toString() {
        return string;
    }
}
