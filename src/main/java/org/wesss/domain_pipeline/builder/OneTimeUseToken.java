package org.wesss.domain_pipeline.builder;

/**
 * A token that can be used once.
 * On attempting to use a second time, an exception is thrown
 */
public class OneTimeUseToken {
    // TODO move to general utilities

    private boolean isUsed;

    public OneTimeUseToken() {
        isUsed = false;
    }

    public void use() {
        if (isUsed) {
            throw new IllegalUseException();
        }

        isUsed = true;
    }

    public boolean isUsed() {
        return isUsed;
    }

    // TODO extract into own class in utils
    public class IllegalUseException extends RuntimeException {

    }
}
