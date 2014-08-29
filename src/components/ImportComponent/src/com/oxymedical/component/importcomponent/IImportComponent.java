// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 8/25/2014 12:06:16 PM
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   IImportComponent.java

package com.oxymedical.component.importcomponent;

import com.oxymedical.component.importcomponent.exception.ImportComponentException;
import com.oxymedical.component.importcomponent.exception.ImportDatabaseConnectException;
import com.oxymedical.core.commonData.IHICData;;

public interface IImportComponent
{

    public abstract void importCSV(String s, String s1, String s2)
        throws ImportComponentException;

    public abstract void importStart(String s, String s1, String s2)
        throws ImportDatabaseConnectException;

    public abstract IHICData getHicData();

    public abstract void setHicData(IHICData ieibdata);
}