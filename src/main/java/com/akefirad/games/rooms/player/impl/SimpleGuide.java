package com.akefirad.games.rooms.player.impl;


import com.akefirad.games.rooms.context.Room;
import com.akefirad.games.rooms.player.Guide;

import static com.akefirad.games.rooms.util.Asserts.assertNotNull;

public class SimpleGuide implements Guide {
    private final Room main;

    public SimpleGuide (Room main) {
        assertNotNull(main, "Main room");

        this.main = main;
    }

    @Override
    public Room mainRoom () {
        return main;
    }
}
