package com.williamle.modulr.stipulator;

import com.williamle.modulr.stipulator.logging.Logger;
import com.williamle.modulr.stipulator.models.LogSeverity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

// A public, all static class to hold state information.
public class Settings {
    public static boolean AllowRW = false;
    public static boolean UseToString = true;
    public static LogSeverity LogLevel = LogSeverity.INFO;
    public static boolean WriteLogOnExit = false;
    public static boolean WriteInRealTime = true;
    public static boolean GenerateCSV = false;
    public static boolean PrintFailMessages = true;

    /**
     * Set variables based on an input string.
     * @param args The startup arguments, usually from <code>main(String[] args)</code>.
     */
    public static void loadFromCommandLine(String[] args) {
        final var currentArg = new ArrayList<String>();
        for (var arg : args) {
            if (arg.startsWith("--") && !currentArg.isEmpty()) {
                interpret(currentArg);
                currentArg.clear();
            }
            currentArg.add(arg);
        }
        if (!currentArg.isEmpty())
            interpret(currentArg);
    }

    /**
     * Act upon a specific argument.
     * Why switch statements? Because I don't know if a callback is any better.
     * Let alone using a map of arguments versus explicitly labeling out each individual typed variable.
     * @param arguments A list of arguments for a given command, ex. ["--use-to-string", "FALSE"]
     */
    private static void interpret(List<String> arguments) {
        try {
            switch (arguments.get(0).toLowerCase(Locale.ROOT)) {
                case "--log-level":
                    LogLevel = LogSeverity.valueOf(arguments.get(1).toUpperCase(Locale.ROOT));
                    Logger.log(LogSeverity.VERBOSE, "Set log level to " + LogLevel);
                    break;
                case "--use-to-string":
                    UseToString = getBoolean(arguments);
                    Logger.log(LogSeverity.VERBOSE, (UseToString ? "Enabled" : "Disabled") + " printing overridden toStrings");
                    break;
                case "--allow-rw":
                    AllowRW = getBoolean(arguments);
                    Logger.log(LogSeverity.VERBOSE, (AllowRW ? "Enabled" : "Disabled") + " reading/writing to FS");
                    break;
                case "--write-log-on-exit":
                    WriteLogOnExit = getBoolean(arguments);
                    Logger.log(LogSeverity.VERBOSE, (WriteLogOnExit ? "Enabled" : "Disabled") + " writing logs on exit");
                    break;
                case "--real-time":
                    WriteInRealTime = getBoolean(arguments);
                    Logger.log(LogSeverity.VERBOSE, (WriteInRealTime ? "Enabled" : "Disabled") + " real-time output");
                    break;
                case "--generate-csv":
                    GenerateCSV = getBoolean(arguments);
                    Logger.log(LogSeverity.VERBOSE, (GenerateCSV ? "Enabled" : "Disabled") + " real-time output");
                    break;
                case "--no-fail-messages":
                    PrintFailMessages = !getBoolean(arguments);
                    Logger.log(LogSeverity.VERBOSE, (GenerateCSV ? "Enabled" : "Disabled") + " printing failure messages");
                    break;
            }
        } catch (Exception ex) {
            Logger.log(LogSeverity.WARNING, "Unrecognized or bad start-up argument: \"" + String.join(" ", arguments) + "\"");
        }
    }

    /**
     * For boolean parameters, set their value based on a user's input.
     * @param arguments The list of arguments found in <code>interpret(List<String> arguments)</code>
     * @return The boolean that the user inputted.
     */
    private static boolean getBoolean(List<String> arguments) {
        if (arguments.size() > 1)
            return arguments.get(1).toLowerCase(Locale.ROOT).contains("t");
        return true;
    }

    /**
     * Merge the rest of the strings in a list into one value.
     * @param arguments A list of values for a given argument.
     * @param startAt An zero-based index (other items will be disregarded) to start joining strings from.
     * @return A string, concatenating the items in the given list based on the parameters.
     */
    private static String remainder(List<String> arguments, int... startAt) {
        if (arguments == null)
            return null;
        if (arguments.size() > 0)
            arguments.remove(0); // Remove the command itself (e.g. --log-path)
        if (startAt == null || startAt.length == 0)
            return String.join(" ", arguments);
        return arguments.stream().skip(startAt[0]).collect(Collectors.joining(" "));
    }
}
