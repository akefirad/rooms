package com.akefirad.games.rooms.decision.impl;

import java.util.Objects;

import static com.akefirad.games.rooms.util.Asserts.assertNotNull;

public class Argument<T> {
    private final String name;
    private final String message;
    private final Converter<T> converter;

    private T value;

    /**
     * Create an argument with the give information
     *
     * @param name      argument name
     * @param message   argument message
     * @param converter string converter (must not throw any exception)
     */
    public Argument (String name, String message, Converter<T> converter) {
        assertNotNull(name, "Argument name");
        assertNotNull(message, "Argument message");
        assertNotNull(converter, "Argument converter");

        this.name = name;
        this.message = message;
        this.converter = converter;
    }

    public String getName () {
        return name;
    }

    public String getMessage () {
        return message;
    }

    public T getValue () {
        return value;
    }

    public void setValue (String string) {
        this.value = converter.convert(string);
    }

    @Override
    public boolean equals (Object that) {
        return this == that || that != null && getClass() == that.getClass() &&
                Objects.equals(name, ((Argument<?>) that).name);
    }

    @Override
    public int hashCode () {
        return name.hashCode();
    }

    interface Converter<T> {
        T convert (String string) throws IllegalArgumentException;
    }
}
