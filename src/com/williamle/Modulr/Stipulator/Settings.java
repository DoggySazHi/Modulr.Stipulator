package com.williamle.Modulr.Stipulator;

import com.williamle.Modulr.Stipulator.Logging.Logger;
import com.williamle.Modulr.Stipulator.Models.LogSeverity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

// A public, all static class to hold state information.
public class Settings {
    public static boolean AllowRW = false;
    public static boolean UseToString = false;
    public static LogSeverity LogLevel = LogSeverity.VERBOSE;

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

    private static void interpret(List<String> arguments) {
        try {
            switch (arguments.get(0).toLowerCase(Locale.ROOT)) {
                case "--log-level":
                    LogLevel = LogSeverity.valueOf(arguments.get(1).toUpperCase(Locale.ROOT));
                    Logger.log(LogSeverity.VERBOSE, "Set log level to " + LogLevel);
                    break;
                case "--use-to-string":
                    UseToString = arguments.get(1).toLowerCase(Locale.ROOT).contains("t");
                    Logger.log(LogSeverity.VERBOSE, (UseToString ? "Enabled" : "Disabled") + " printing overridden toStrings");
                    break;
                case "--allow-rw":
                    if (arguments.size() > 1)
                        AllowRW = arguments.get(1).toLowerCase(Locale.ROOT).contains("t");
                    else
                        AllowRW = true;
                    Logger.log(LogSeverity.VERBOSE, (UseToString ? "Enabled" : "Disabled") + " reading/writing to FS");
                    break;
            }
        } catch (Exception ex) {
            Logger.log(LogSeverity.WARNING, "Unrecognized or bad start-up argument: \"" + String.join(" ", arguments) + "\"");
        }
    }
}
