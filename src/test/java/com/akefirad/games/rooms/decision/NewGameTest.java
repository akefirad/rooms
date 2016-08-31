package com.akefirad.games.rooms.decision;

import com.akefirad.games.rooms.context.impl.GameMenu;
import com.akefirad.games.rooms.decision.impl.*;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.akefirad.games.rooms.decision.impl.NewGame.NAME;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class NewGameTest {
    private Decision decision;

    @Before
    public void setUp () {
        decision = new NewGame();
    }

    @Test
    public void testName () {
        assertThat(decision.getName(), equalTo(NAME));
    }

    @Test
    public void testMake () throws Exception {
        List<Argument<?>> arguments = decision.getArguments();
        arguments.iterator().next().setValue("Rad");
        GameMenu game = (GameMenu) decision.make(arguments).getContext();
        assertTrue(game.hasDecisions());
        assertThat(game.listDecisions(), hasSize(3));
        assertThat(game.listDecisions().get(0).getName(), equalTo(ExitGame.NAME));
        assertThat(game.listDecisions().get(1).getName(), equalTo(StartGame.NAME_START));
        assertThat(game.listDecisions().get(2).getName(), equalTo(SaveGame.NAME));
    }
}
