// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 8/25/2014 12:06:16 PM
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ImportConfigReadException.java

package com.oxymedical.component.importcomponent.exception;


// Referenced classes of package com.oxymedical.component.importcomponent.exception:
//            ImportComponentException

public class ImportConfigReadException extends ImportComponentException
{

    public ImportConfigReadException()
    {
    }

    public ImportConfigReadException(String message)
    {
        super(message);
    }

    public ImportConfigReadException(String messageException, Throwable causeException)
    {
        super(messageException);
    }

    public ImportConfigReadException(Throwable causeException)
    {
        super(causeException);
    }

    private static final long serialVersionUID = 1L;
}