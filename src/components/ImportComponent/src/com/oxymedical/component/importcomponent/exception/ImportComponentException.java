// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 8/25/2014 12:06:16 PM
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ImportComponentException.java

package com.oxymedical.component.importcomponent.exception;

import com.oxymedical.component.baseComponent.exception.ComponentException;

public class ImportComponentException extends ComponentException
{

    public ImportComponentException()
    {
    }

    public ImportComponentException(String message)
    {
        super(message);
    }

    public ImportComponentException(String messageException, Throwable causeException)
    {
        super(messageException);
    }

    public ImportComponentException(Throwable causeException)
    {
        super(causeException);
    }

    private static final long serialVersionUID = 1L;
}