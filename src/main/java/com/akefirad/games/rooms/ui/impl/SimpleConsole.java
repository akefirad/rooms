package com.akefirad.games.rooms.ui.impl;

import com.akefirad.games.rooms.decision.Decision;
import com.akefirad.games.rooms.decision.impl.Argument;
import com.akefirad.games.rooms.exception.EOFConsoleException;

import java.io.*;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import static com.akefirad.games.rooms.decision.Decision.DRAWING_HEIGHT;
import static com.akefirad.games.rooms.util.Asserts.assertInRange;
import static com.akefirad.games.rooms.util.StringUtils.center;
import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.parseInt;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

public class SimpleConsole implements com.akefirad.games.rooms.ui.Console {
    private static final Logger logger = Logger.getLogger(SimpleConsole.class.getSimpleName());
    private static final String DIVIDER = "    ";

    private final PrintWriter writer;
    private final Scanner scanner;

    public SimpleConsole () {
        this(new OutputStreamWriter(System.out), new InputStreamReader(System.in));
    }

    public SimpleConsole (Writer writer, Reader reader) {
        this.writer = new PrintWriter(writer);
        this.scanner = new Scanner(reader);
    }

    @Override
    public void showDecisions (List<Decision> decisions) {
        StringBuilder sb = new StringBuilder();
        List<String[]> blocks = decisions.stream()
                .map(Decision::getIcon)
                .collect(toList());

        printIcons(sb, blocks);
        printLabels(decisions, sb, blocks);

        sb.append("\n");
        showMessage(sb.toString());
    }

    @Override
    public Integer selectDecision (int size) {
        assertInRange(size, 1, MAX_VALUE, "Maximum decision number");

        int max = size - 1;
        String message = "What do you want to do (enter the number between 0 ~ " + max + "): ";
        String error = "Decision number must be between 0 ~ " + max + "!";
        Integer integer;
        while ((integer = readInteger(message)) != null && integer < 0 && integer > max)
            showMessage(error);

        return integer;
    }

    @Override
    public void readArguments (List<Argument<?>> arguments) {
        arguments.forEach(argument -> readArgument(argument));
    }

    @Override
    public void showError (String message, Exception e) {
        showMessage(message);
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        logger.fine(message + "\n" + sw.toString());
    }

    @Override
    public void showMessage (String message) {
        print(message + "\n");
    }

    private void printLabels (List<Decision> decisions, StringBuilder sb, List<String[]> blocks) {
        range(0, decisions.size())
                .forEach(idx -> sb
                        .append(center(idx + ": " + decisions.get(idx).getName(), blocks.get(idx)[0].length()))
                        .append(DIVIDER));
    }

    private void printIcons (StringBuilder sb, List<String[]> blocks) {
        range(0, DRAWING_HEIGHT)
                .forEach(idx -> {
                    blocks.forEach(block -> sb.append(block[idx]).append(DIVIDER));
                    sb.append("\n");
                });
    }

    private void readArgument (Argument<?> argument) {
        boolean successful = false;
        while (!successful) {
            try {
                String message = argument.getMessage();
                argument.setValue(readNonEmptyString(message));
                successful = true;
            }
            catch (IllegalArgumentException e) {
                showMessage(e.getClass().getSimpleName() + ": " + e.getMessage());
            }
        }
    }

    private String readNonEmptyString (String message) {
        String line = readLine(message);
        while (line != null && line.trim().isEmpty())
            line = readLine(message);

        if (line == null)
            throw new EOFConsoleException();

        return line;
    }

    private String readLine (String message) {
        print(message);
        return scanner.hasNextLine() ? scanner.nextLine() : null;
    }

    private Integer readInteger (String message) {
        print(message);
        return scanner.hasNextLine() ? parseInt(scanner.nextLine()) : null;
    }

    private void print (String message) {
        writer.print(message);
        writer.flush();
    }
}
