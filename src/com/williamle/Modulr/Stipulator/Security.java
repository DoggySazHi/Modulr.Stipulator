package com.williamle.Modulr.Stipulator;

import java.io.FileDescriptor;

public class Security extends SecurityManager {

    private final boolean allowRW;

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
        if (!allowRW || file.contains(".java") || file.contains(".class"))
            super.checkRead(file);
    }

    @Override
    public void checkRead(String file, Object context) {
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
        if (!allowRW || file.contains(".java") || file.contains(".class"))
            super.checkWrite(file);
    }

    @Override
    public void checkDelete(String file) {
        if (!allowRW || file.contains(".java") || file.contains(".class"))
            super.checkDelete(file);
    }
}
