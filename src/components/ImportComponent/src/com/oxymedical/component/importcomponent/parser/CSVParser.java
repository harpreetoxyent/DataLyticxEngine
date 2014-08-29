// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 8/25/2014 12:06:17 PM
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   CSVParser.java

package com.oxymedical.component.importcomponent.parser;

import com.oxymedical.component.importcomponent.ICSVParser;
import com.oxymedical.component.importcomponent.exception.ImportComponentException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVParser
    implements ICSVParser
{

    public CSVParser(Reader reader, char separator)
    {
        this(reader, separator, '"');
    }

    public CSVParser(Reader reader, char separator, char quotechar)
    {
        this(reader, separator, quotechar, 0);
    }

    public CSVParser(Reader reader, char separator, char quotechar, int line)
    {
        hasNext = true;
        br = new BufferedReader(reader);
        this.separator = separator;
        this.quotechar = quotechar;
        skipLines = line;
    }

    public List readAll()
        throws ImportComponentException
    {
        List allElements = new ArrayList();
        while(hasNext) 
        {
            String nextLineAsTokens[] = readNext();
            if(nextLineAsTokens != null)
                allElements.add(nextLineAsTokens);
        }
        return allElements;
    }

    public String[] readNext()
        throws ImportComponentException
    {
        String nextLine = getNextLine();
        return hasNext ? parseLine(nextLine) : null;
    }

    private String getNextLine()
        throws ImportComponentException
    {
        try
        {
            if(!linesSkiped)
            {
                for(int i = 0; i < skipLines; i++)
                    br.readLine();

                linesSkiped = true;
            }
            String nextLine = br.readLine();
            if(nextLine == null)
                hasNext = false;
            return hasNext ? nextLine : null;
        }
        catch(IOException ioe)
        {
            throw new ImportComponentException(ioe.getMessage());
        }
    }

    private String[] parseLine(String nextLine)
        throws ImportComponentException
    {
        if(nextLine == null)
            return null;
        List tokensOnThisLine = new ArrayList();
        StringBuffer sb = new StringBuffer();
        boolean inQuotes = false;
        do
        {
            if(inQuotes)
            {
                sb.append("\n");
                nextLine = getNextLine();
                if(nextLine == null)
                    break;
            }
            for(int i = 0; i < nextLine.length(); i++)
            {
                char c = nextLine.charAt(i);
                if(c == quotechar)
                {
                    if(inQuotes && nextLine.length() > i + 1 && nextLine.charAt(i + 1) == quotechar)
                    {
                        sb.append(nextLine.charAt(i + 1));
                        i++;
                    } else
                    {
                        inQuotes = !inQuotes;
                        if(i > 2 && nextLine.charAt(i - 1) != separator && nextLine.length() > i + 1 && nextLine.charAt(i + 1) != separator)
                            sb.append(c);
                    }
                } else
                if(c == separator && !inQuotes)
                {
                    tokensOnThisLine.add(sb.toString());
                    sb = new StringBuffer();
                } else
                {
                    sb.append(c);
                }
            }

        } while(inQuotes);
        tokensOnThisLine.add(sb.toString());
        return (String[])tokensOnThisLine.toArray(new String[0]);
    }

    public void close()
        throws ImportComponentException
    {
        try
        {
            br.close();
        }
        catch(IOException ioe)
        {
            throw new ImportComponentException(ioe.getMessage());
        }
    }

    private BufferedReader br;
    private boolean hasNext;
    private char separator;
    private char quotechar;
    private int skipLines;
    private boolean linesSkiped;
}