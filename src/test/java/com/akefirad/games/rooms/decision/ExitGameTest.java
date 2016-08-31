package com.akefirad.games.rooms.decision;

import com.akefirad.games.rooms.context.Context;
import com.akefirad.games.rooms.decision.impl.ExitGame;
import org.junit.Before;
import org.junit.Test;

import static com.akefirad.games.rooms.decision.impl.ExitGame.NAME;
import static java.util.Collections.emptyList;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class ExitGameTest {
    private Decision decision;

    @Before
    public void setUp () {
        decision = new ExitGame();
    }

    @Test
    public void testName () {
        assertThat(decision.getName(), equalTo(NAME));
    }

    @Test
    public void testArguments () {
        assertThat(decision.getArguments(), empty());
    }

    @Test
    public void testMake () throws Exception {
        assertThat(decision.getArguments(), empty());
        Context context = decision.make(emptyList()).getContext();
        assertFalse(context.hasDecisions());
        assertThat(context.listDecisions(), empty());
    }
}
