// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 8/25/2014 12:06:16 PM
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   IImportProcess.java

package com.oxymedical.component.importcomponent;

import com.oxymedical.component.db.DBComponent;
import com.oxymedical.component.importcomponent.exception.ImportComponentException;

public interface IImportProcess
{

    public abstract void run()
        throws ImportComponentException;
	public Object importOneObjectFromCSV(String classtoload, DBComponent dbObj,
			String csvFileName, char seperator, boolean isOneRow) 
		throws ImportComponentException;
	public Object[] importAllObjectFromCSV(String classtoload, DBComponent dbObj,
			String csvFileName, char seperator, boolean isOneRow) 
		throws ImportComponentException;
}