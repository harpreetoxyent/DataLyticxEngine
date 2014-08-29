// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 8/25/2014 12:06:17 PM
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ImportUtil.java

package com.oxymedical.component.importcomponent.util;

import com.oxymedical.component.importcomponent.ICSVParser;
import com.oxymedical.component.importcomponent.exception.ImportComponentException;
import com.oxymedical.component.importcomponent.parser.CSVParser;
import java.io.*;
import java.util.List;

public class ImportUtil
{

    public ImportUtil(String filePath, char seperator)
        throws ImportComponentException
    {
        try
        {
            reader = new CSVParser(new FileReader(filePath), seperator);
        }
        catch(FileNotFoundException fnfe)
        {
            throw new ImportComponentException(fnfe.getMessage());
        }
    }

    public String[] read()
        throws ImportComponentException
    {
        String headerList[] = (String[])null;
        try
        {
            headerList = reader.readNext();
        }
        catch(IOException ioe)
        {
            throw new ImportComponentException(ioe.getMessage());
        }
        return headerList;
    }

    public List readAll()
        throws ImportComponentException
    {
        List list = null;
        try
        {
            list = reader.readAll();
            if(list == null)
                return null;
        }
        catch(IOException ex)
        {
            throw new ImportComponentException(ex.getMessage());
        }
        return list;
    }

    private static final char DEFAULT_QUOTE_CHARACTER = 0;
    private ICSVParser reader;
}