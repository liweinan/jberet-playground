package io.weli.jberet;

import java.util.Random;

public class Commons {
    private static final Random RANDOM = new Random();
    private static final int MS_IN_SECOND = 1000;


    public static void sleepRandomSeconds(int bound) {
        sleepSeconds(RANDOM.nextInt(bound));
    }

    public static void sleepSeconds(int seconds) {
        try {
            Thread.sleep(seconds * MS_IN_SECOND);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

}
