package com.williamle.modulr.stipulator;

import com.williamle.modulr.stipulator.logging.Logger;
import com.williamle.modulr.stipulator.models.LogSeverity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

// A public, all static class to hold state information.
public class Settings {
    public static boolean AllowRW = false;
    public static boolean UseToString = true;
    public static LogSeverity LogLevel = LogSeverity.VERBOSE;
    public static boolean WriteLogOnExit = false;
    public static boolean WriteInRealTime = false;

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
                    if (arguments.size() > 1)
                        UseToString = arguments.get(1).toLowerCase(Locale.ROOT).contains("t");
                    else
                        UseToString = true;
                    Logger.log(LogSeverity.VERBOSE, (UseToString ? "Enabled" : "Disabled") + " printing overridden toStrings");
                    break;
                case "--allow-rw":
                    if (arguments.size() > 1)
                        AllowRW = arguments.get(1).toLowerCase(Locale.ROOT).contains("t");
                    else
                        AllowRW = false;
                    Logger.log(LogSeverity.VERBOSE, (AllowRW ? "Enabled" : "Disabled") + " reading/writing to FS");
                    break;
                case "--write-log-on-exit":
                    if (arguments.size() > 1)
                        WriteLogOnExit = arguments.get(1).toLowerCase(Locale.ROOT).contains("t");
                    else
                        WriteLogOnExit = true;
                    Logger.log(LogSeverity.VERBOSE, (WriteLogOnExit ? "Enabled" : "Disabled") + " writing logs on exit");
                    break;
                case "--real-time":
                    if (arguments.size() > 1)
                        WriteInRealTime = arguments.get(1).toLowerCase(Locale.ROOT).contains("t");
                    else
                        WriteInRealTime = false;
                    Logger.log(LogSeverity.VERBOSE, (WriteInRealTime ? "Enabled" : "Disabled") + " real-time output");
                    break;
            }
        } catch (Exception ex) {
            Logger.log(LogSeverity.WARNING, "Unrecognized or bad start-up argument: \"" + String.join(" ", arguments) + "\"");
        }
    }
}
