package org.wesss.domain_pipeline.util.interdomain;

import org.wesss.domain_pipeline.DomainObj;

public class CharDomain extends DomainObj {
    private char character;

    public CharDomain(char id) {
        this.character = id;
    }

    public char getChar() {
        return character;
    }
}
