package com.akefirad.games.rooms.util;

import java.util.regex.Pattern;

public final class Asserts {
    private Asserts () {
    }

    public static void assertNotNull (Object object, String name) {
        if (object == null)
            throw new IllegalArgumentException(name + " is null!");
    }

    public static void assertMatch (Pattern pattern, String string, String name) {
        if (!pattern.matcher(string).matches())
            throw new IllegalArgumentException(name + " does not match: " + string);
    }

    public static void assertInRange (int integer, int min, int max, String name) {
        if (integer < min || integer > max)
            throw new IllegalArgumentException(name + " has invalid value: " + integer);
    }
}
