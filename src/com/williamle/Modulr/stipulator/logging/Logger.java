package com.williamle.modulr.stipulator.logging;

import com.williamle.modulr.stipulator.models.LogSeverity;
import com.williamle.modulr.stipulator.Settings;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Logger {
    static final List<LogPair> log;
    static final PrintStream mainOutput;
    static final PrintStream errOutput;
    static final TesterPrintStream redirectOutput;

    static {
        log = new LinkedList<>();
        mainOutput = System.out;
        errOutput = System.err;
        redirectOutput = new TesterPrintStream();
        System.setOut(redirectOutput);
        System.setErr(redirectOutput);
    }

    public static void log(LogSeverity severity, String message) {
        log.add(new LogPair(severity, message));
        if (severity.ordinal() <= Settings.LogLevel.ordinal()) {
            if (severity == LogSeverity.ERROR)
                errOutput.println(message);
            else
                mainOutput.println(message);
        }
    }

    public static TesterPrintStream getSystemRedirect() {
        return redirectOutput;
    }

    /**
     * Dump the contents of the log into a file.
     *
     * You might wonder why we don't just continuously write the log.
     * Mainly, because by default, this is running in a container; we just want the output printed. Modulr can handle that.
     *
     * Requires <code>Settings.AllowRW</code> to be <code>true</code>, otherwise it'll probably throw an <code>SecurityException</code>.
     * @param file A filename/path to dump to.
     */
    public static void dumpLogToFile(String file) throws IOException {
        var out = Paths.get(file);
        if (!Files.exists(out))
            Files.createFile(out);
        Files.writeString(out, log.stream().map(o -> "[" + o.getLogSeverity() + "] " + o.getMessage()).collect(Collectors.joining()), StandardOpenOption.APPEND);
    }
}