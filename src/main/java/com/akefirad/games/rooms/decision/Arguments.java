package com.akefirad.games.rooms.decision;

import com.akefirad.games.rooms.decision.impl.Argument;

import java.util.Collection;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

public final class Arguments {
    private Arguments () {
    }

    public static Map<String, ?> expandToMap (Collection<Argument<?>> collection) {
        return collection == null ? null :
                collection.stream().collect(toMap(Argument::getName, Argument::getValue));
    }
}
