package com.williamle.modulr.stipulator.logging;

import com.williamle.modulr.stipulator.models.LogSeverity;
import com.williamle.modulr.stipulator.Settings;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The main logging class for Modulr.Stipulator.
 * It has the responsibility of collecting printed output for saving to a file and reducing spam.
 */
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

    /**
     * Log a message to the console.
     * @param severity How important the message is.
     * @param message The message itself.
     */
    public static void log(LogSeverity severity, String message) {
        log.add(new LogPair(severity, message));
        if (severity.ordinal() <= Settings.LogLevel.ordinal()) {
            if (severity == LogSeverity.ERROR)
                errOutput.println(message);
            else
                mainOutput.println(message);
        }
    }

    /**
     * Get a <code>TesterPrintStream</code> which masquerades the default <code>PrintStream</code>s.
     * @return The main <code>TesterPrintStream</code>.
     */
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
    public static String dumpLogToFile(String file) throws IOException {
        var out = Paths.get(file);
        if (!Files.exists(out))
            Files.createFile(out);
        Files.writeString(out, log.stream().map(o -> "[" + o.getLogSeverity() + "] " + o.getMessage()).collect(Collectors.joining("\n")));
        return out.toAbsolutePath().normalize().toString();
    }
}
