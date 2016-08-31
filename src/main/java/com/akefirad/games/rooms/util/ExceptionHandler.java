package com.akefirad.games.rooms.util;

import com.akefirad.games.rooms.exception.RoomsGameException;

import java.io.IOException;

public final class ExceptionHandler {
    private ExceptionHandler () {
    }

    /**
     * Calls the callable and returns the value. In case of
     * IOException, it catches it and throws a RoomsGameException.
     *
     * @param callable Callable to call
     * @param <T>      Type of return value
     * @return Result of callable
     */
    public static <T> T handleIOException (final IOExceptionCallable<T> callable) {
        try {
            return callable.call();
        }
        catch (IOException e) {
            throw new RoomsGameException(e);
        }
    }

    /**
     * Calls the runnable In case of IOException,
     * it catches it and throws a RoomsGameException.
     *
     * @param runnable Callable to call
     */
    public static void handleIOException (final IOExceptionRunnable runnable) {
        try {
            runnable.run();
        }
        catch (IOException e) {
            throw new RoomsGameException(e);
        }
    }

    @FunctionalInterface
    public interface IOExceptionCallable<V> {
        V call () throws IOException;
    }

    @FunctionalInterface
    public interface IOExceptionRunnable {
        void run () throws IOException;
    }
}