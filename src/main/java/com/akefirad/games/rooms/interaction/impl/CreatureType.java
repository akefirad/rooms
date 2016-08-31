package com.akefirad.games.rooms.interaction.impl;

public enum CreatureType {
    Wolf,
    Scorpion,
    Man,
    Unknown;

    public static CreatureType of (String value) {
        try {
            return valueOf(value);
        }
        catch (IllegalArgumentException e) {
            return Unknown;
        }
    }
}
