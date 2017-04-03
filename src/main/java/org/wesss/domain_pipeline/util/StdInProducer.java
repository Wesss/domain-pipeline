package org.wesss.domain_pipeline.util;

import org.wesss.domain_pipeline.util.interdomain.StringDomain;
import org.wesss.domain_pipeline.workers.Producer;

import java.util.Scanner;

/**
 * Continually outputs lines fed from std in until the quit string is input.
 * The quit string is by default "quit".
 */
public class StdInProducer extends Producer<StringDomain> {

    private static final String DEFAULT_QUIT_STRING = "quit";

    private String quitString;

    public StdInProducer() {
        this(DEFAULT_QUIT_STRING);
    }

    public StdInProducer(String quitString) {
        this.quitString = quitString;
    }

    @Override
    protected void run() {
        boolean isRunning = true;
        while (isRunning) {
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();

            if (line.equals(quitString)) {
                isRunning = false;
            } else {
                emitter.emit(new StringDomain(line));
            }
        }
    }
}
