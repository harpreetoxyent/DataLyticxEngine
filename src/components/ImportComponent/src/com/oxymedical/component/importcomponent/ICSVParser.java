// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 8/25/2014 12:06:15 PM
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ICSVParser.java

package com.oxymedical.component.importcomponent;

import com.oxymedical.component.importcomponent.exception.ImportComponentException;
import java.io.IOException;
import java.util.List;

public interface ICSVParser
{

    public abstract List readAll()
        throws IOException, ImportComponentException;

    public abstract String[] readNext()
        throws IOException, ImportComponentException;

    public abstract void close()
        throws IOException, ImportComponentException;

    public static final char DEFAULT_SEPARATOR = 44;
    public static final char DEFAULT_QUOTE_CHARACTER = 34;
    public static final int DEFAULT_SKIP_LINES = 0;
}