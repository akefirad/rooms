package com.akefirad.games.rooms.exception;

public class EOFConsoleException extends RoomsGameException {
    public EOFConsoleException () {
        super("Unexpected End-Of-File");
    }
}
