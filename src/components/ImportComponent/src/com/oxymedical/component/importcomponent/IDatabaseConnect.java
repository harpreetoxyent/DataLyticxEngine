// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 8/25/2014 12:06:15 PM
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   IDatabaseConnect.java

package com.oxymedical.component.importcomponent;

import com.oxymedical.component.db.DBComponent;
import com.oxymedical.component.importcomponent.exception.ImportDatabaseConnectException;
import com.oxymedical.component.importcomponent.parser.ConfigData;

public interface IDatabaseConnect
{

    public abstract void readMapping(ConfigData configdata, DBComponent dbcomponent)
        throws ImportDatabaseConnectException;

    public abstract void generateMappings(ConfigData configdata, DBComponent dbcomponent)
        throws ImportDatabaseConnectException;
}