package com.akefirad.games.rooms.interaction.impl;

import com.akefirad.games.rooms.context.Room;
import com.akefirad.games.rooms.decision.Decision;
import com.akefirad.games.rooms.decision.impl.OpenDoor;
import com.akefirad.games.rooms.interaction.Interactable;
import com.akefirad.games.rooms.player.Player;

import java.util.List;

import static com.akefirad.games.rooms.util.Asserts.assertNotNull;
import static java.util.Collections.singletonList;

public class Door implements Interactable {
    private final Room to;

    public Door (Room to) {
        assertNotNull(to, "Room");

        this.to = to;
    }

    @Override
    public List<Decision> listDecisions (Player player) {
        assertNotNull(player, "Player");

        return singletonList(new OpenDoor(player, to));
    }

    public Room getTo () {
        return to;
    }
}
