package com.williamle.modulr.stipulator.logging;

import java.io.PrintStream;

public class TesterPrintStream extends PrintStream {

    private final StringBuffer buffer;

    public TesterPrintStream() {
        super(System.out);
        buffer = new StringBuffer();
    }

    @Override
    public void print(boolean b) {
        buffer.append(b);
    }

    @Override
    public void print(char c) {
        buffer.append(c);
    }

    @Override
    public void print(int i) {
        buffer.append(i);
    }

    @Override
    public void print(long l) {
        buffer.append(l);
    }

    @Override
    public void print(float f) {
        buffer.append(f);
    }

    @Override
    public void print(double d) {
        buffer.append(d);
    }

    @Override
    public void print(char[] s) {
        buffer.append(s);
    }

    @Override
    public void print(String s) {
        buffer.append(s);
    }

    @Override
    public void print(Object obj) {
        buffer.append(obj);
    }

    @Override
    public void println() {
        buffer.append('\n');
    }

    @Override
    public void println(boolean x) {
        this.print(x);
        this.println();
    }

    @Override
    public void println(char x) {
        this.print(x);
        this.println();
    }

    @Override
    public void println(int x) {
        this.print(x);
        this.println();
    }

    @Override
    public void println(long x) {
        this.print(x);
        this.println();
    }

    @Override
    public void println(float x) {
        this.print(x);
        this.println();
    }

    @Override
    public void println(double x) {
        this.print(x);
        this.println();
    }

    @Override
    public void println(char[] x) {
        this.print(x);
        this.println();
    }

    @Override
    public void println(String x) {
        this.print(x);
        this.println();
    }

    @Override
    public void println(Object x) {
        this.print(x);
        this.println();
    }

    public String getAndFlush() {
        var output = buffer.toString();
        buffer.setLength(0);
        super.flush();
        return output;
    }
}
