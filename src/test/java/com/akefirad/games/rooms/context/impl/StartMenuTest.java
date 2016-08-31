package com.akefirad.games.rooms.context.impl;

import com.akefirad.games.rooms.decision.impl.*;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

public class StartMenuTest {
    private StartMenu start;

    @Before
    public void setUp () {
        start = new StartMenu();
    }

    @Test
    public void hasDecisions () throws Exception {
        assertTrue(start.hasDecisions());
    }

    @Test
    public void listDecisions () throws Exception {
        assertThat(start.listDecisions(), hasSize(3));
        assertThat(start.listDecisions().get(0).getName(), equalTo(ExitGame.NAME));
        assertThat(start.listDecisions().get(1).getName(), equalTo(NewGame.NAME));
        assertThat(start.listDecisions().get(2).getName(), equalTo(LoadGame.NAME));
    }
}