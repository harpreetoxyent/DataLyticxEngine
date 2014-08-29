// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 8/25/2014 12:06:16 PM
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   DatabaseConnect.java

package com.oxymedical.component.importcomponent.db;

import com.oxymedical.component.db.DBComponent;
import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.importcomponent.IDatabaseConnect;
import com.oxymedical.component.importcomponent.exception.ImportDatabaseConnectException;
import com.oxymedical.component.importcomponent.importconstant.ImportConstant;
import com.oxymedical.component.importcomponent.parser.ConfigData;

public class DatabaseConnect
    implements IDatabaseConnect
{

    public DatabaseConnect()
    {
    }

    public void readMapping(ConfigData configData, DBComponent dbComp)
        throws ImportDatabaseConnectException
    {
        if(configData == null || dbComp == null)
            return;
        try
        {
            dbComp.setUpDataConfiguration(configData.getUser(), configData.getPassword(), configData.getDatabaseUrl(), "com.oxymedical.component.importcomponent", ImportConstant.ROOT_DIR, "resource_import");
            dbComp.loadResourcesJarInLoader(ImportConstant.JAR_PATH, ImportConstant.JAR_NAME+".jar");
            dbComp.createDBConfiguration();
        }
        catch(DBComponentException dbce)
        {
            throw new ImportDatabaseConnectException(dbce.getMessage());
        }
    }

    public void generateMappings(ConfigData configData, DBComponent dbComp)
        throws ImportDatabaseConnectException
    {
        if(configData == null || dbComp == null)
            return;
        try
        {
        	// ANJALI MAKING CHANGES TO AVOID IMPORT JAR CREATION
        	dbComp.setUpDataConfiguration(configData.getUser(), configData.getPassword(), configData.getDatabaseUrl(), ImportConstant.CLASS_PATH, ImportConstant.ROOT_DIR, ImportConstant.JAR_NAME);
            //dbComp.setUpDataConfiguration(configData.getUser(), configData.getPassword(), configData.getDatabaseUrl(), "com.oxymedical.component.importcomponent", ImportConstant.ROOT_DIR, "resource_import");
            dbComp.registerDBAndGenerateMappings();
        }
        catch(DBComponentException dbce)
        {
            throw new ImportDatabaseConnectException(dbce.getMessage());
        }
    }
}