package com.williamle.modulr.stipulator;

import com.williamle.modulr.stipulator.logging.Logger;
import com.williamle.modulr.stipulator.models.LogSeverity;

import java.io.FileDescriptor;
import java.util.Arrays;
import java.util.List;

public class Security extends SecurityManager {

    private final boolean allowRW;
    private final List<String> DEFAULT_PROPERTIES = Arrays.asList("java.locale.providers",
            "locale.resources.debug",
            "resource.bundle.debug",
            "jdk.proxy.ProxyGenerator.v49");
    private final List<String> READ_WRITE_PROPERTIES = Arrays.asList("user.dir");

    private Security(boolean allowRW) {
        super();
        this.allowRW = allowRW;
    }

    public static boolean hasSecurity() {
        return System.getSecurityManager() == null;
    }

    public static void enableSecurity(boolean allowRW) {
        System.setSecurityManager(new Security(allowRW));
    }

    @Override
    public void checkRead(String file) {
        Logger.log(LogSeverity.VERBOSE, "Attempted read on \"" + file + "\"");
        if (!allowRW || file.contains(".java") || file.contains(".class"))
            super.checkRead(file);
    }

    @Override
    public void checkRead(String file, Object context) {
        Logger.log(LogSeverity.VERBOSE, "Attempted read on \"" + file + "\"");
        if (!allowRW || file.contains(".java") || file.contains(".class"))
            super.checkRead(file, context);
    }

    @Override
    public void checkWrite(FileDescriptor fd) {
        if (!allowRW)
            super.checkWrite(fd);
    }

    @Override
    public void checkWrite(String file) {
        Logger.log(LogSeverity.VERBOSE, "Attempted write on \"" + file + "\"");
        if (!allowRW || file.contains(".java") || file.contains(".class"))
            super.checkWrite(file);
    }

    @Override
    public void checkDelete(String file) {
        Logger.log(LogSeverity.VERBOSE, "Attempted delete on \"" + file + "\"");
        if (!allowRW || file.contains(".java") || file.contains(".class"))
            super.checkDelete(file);
    }

    @Override
    public void checkPropertyAccess(String key) {
        Logger.log(LogSeverity.VERBOSE, "Attempted property access on \"" + key + "\"");
        if (DEFAULT_PROPERTIES.contains(key))
            return;
        if (allowRW && READ_WRITE_PROPERTIES.contains(key))
            return;
        super.checkPropertyAccess(key);
    }
}
