package com.akefirad.games.rooms.context;

import org.junit.*;

import static com.akefirad.games.rooms.context.Rooms.newRoom;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class RoomsTest {
    @Test
    public void testNewRoom () {
        Room room = newRoom("Some_Room1");
        assertThat(room.getName(), equalTo("Some_Room1"));
        assertThat(room.getPlayer(), nullValue());
    }
    @Test (expected = IllegalArgumentException.class)
    public void testNullRoomName () {
        newRoom(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testEmptyRoomName () {
        newRoom("");
    }

    @Test (expected = IllegalArgumentException.class)
    public void testWithSpaceRoomName () {
        newRoom("Some Room");
    }

}
