package com.si_pmu.pmu.factories;

import java.util.Random;
import java.util.UUID;

public class TestUtils {

    private static final Random random = new Random();
    public static String randomString(final String input) {
        return String.format("%s-%s", input, UUID.randomUUID());
    }

    public static int randomInt() {
        return random.nextInt();
    }
}
