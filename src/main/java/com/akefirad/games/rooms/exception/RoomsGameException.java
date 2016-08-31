package com.akefirad.games.rooms.exception;

public class RoomsGameException extends RuntimeException {
    public RoomsGameException (String message) {
        super(message);
    }

    public RoomsGameException (Throwable cause) {
        super(cause);
    }
}
