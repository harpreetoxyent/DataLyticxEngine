// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 8/25/2014 12:06:16 PM
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ImportComponent.java

package com.oxymedical.component.importcomponent;
import com.oxymedical.component.baseComponent.IComponent;
import com.oxymedical.component.baseComponent.annotations.EventSubscriber;
import com.oxymedical.component.baseComponent.exception.ComponentException;
import com.oxymedical.component.db.DBComponent;
import com.oxymedical.component.db.exception.DBComponentException;

import com.oxymedical.component.importcomponent.db.DatabaseConnect;
import com.oxymedical.component.importcomponent.exception.ImportComponentException;
import com.oxymedical.component.importcomponent.exception.ImportDatabaseConnectException;
import com.oxymedical.component.importcomponent.importconstant.ImportConstant;
import com.oxymedical.component.importcomponent.parser.ConfigData;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.maintenanceData.IMaintenanceData;
import com.oxymedical.framework.objectbroker.annotations.InjectNew;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

// Referenced classes of package com.oxymedical.component.importcomponent:
//            IImportComponent, IDatabaseConnect, IImportProcess, IMappingManager

public class ImportComponent
    implements IImportComponent, IComponent
{
	//public static final String CSVFilePathLocation = "C:/datalyticx_git/DataLytixEngine/documents/data structures/Material Master/";
	public static final String CSVFilePathLocation = "/home/oxyent/DatalyticxEngine/";
    private String csvFileName;
    private String sep;
    private String tableName;
    private String classToLoad;
//    private long importedData;
    private IImportProcess importProcess;
    private IDatabaseConnect databaseConnect;
    private ConfigData importConfigData;
    private IHICData eibData;	
	@InjectNew
	static public DBComponent dbComponent;

    public ImportComponent()
    {
    	try
    	{
			if(System.getenv("TOMCAT_HOME") == null)
			{
				System.setProperty("TOMCAT_HOME",ImportConstant.TOMCAT_HOME);
			}   	
	        csvFileName = null;
	        sep = null;
	        tableName = null;
	        classToLoad = null;
	        importProcess = null;
	        databaseConnect = null;
	        importConfigData = null;
	        eibData = null;
	        databaseConnect = new DatabaseConnect();
	        importStart("root", "root", "jdbc:mysql://localhost:3306/datalyticx");
    	}
    	catch(Exception exp)
    	{
    		exp.printStackTrace();
    	}
    }
    public void importCSV(String csvFileName, String sep, String tableName)
    throws ImportComponentException
    {
    	importCSV(csvFileName, sep, tableName,null);
    }
    public void importCSV(String csvFileName, String sep, String tableName, List<String[]> containerList)
        throws ImportComponentException
    {
        if(csvFileName == null || csvFileName == "" || sep == null || sep == "" || tableName == null || tableName == "")
        {
            return;
        } else
        {
            //tableName = convertTableNameUtil(tableName);
            this.csvFileName = csvFileName;
            this.sep = sep;
            this.tableName = tableName;
            readConfigurations(tableName);
            char seperator = this.sep.charAt(0);
            importProcess = new ImportProcess(classToLoad, dbComponent, csvFileName, seperator, containerList);
            return;
        }
    }
    
    public Object importCSVOneRowAndReturnObject(String csvFileName, String sep, String tableName)
    throws ImportComponentException
{
    if(csvFileName == null || csvFileName == "" || sep == null || sep == "" || tableName == null || tableName == "")
    {
        return null;
    } else
    {
        //tableName = convertTableNameUtil(tableName);
        this.csvFileName = csvFileName;
        this.sep = sep;
        this.tableName = tableName;
        readConfigurations(tableName);
        char seperator = this.sep.charAt(0);
        importProcess = new ImportProcess();
        Object obj = importProcess.importOneObjectFromCSV(classToLoad, dbComponent, csvFileName, seperator,true);
        return obj;
    }
}
    public Object[] importCSVAllRowAndReturnObject(String csvFileName, String sep, String tableName)
    throws ImportComponentException
{
    if(csvFileName == null || csvFileName == "" || sep == null || sep == "" || tableName == null || tableName == "")
    {
        return null;
    } else
    {
        //tableName = convertTableNameUtil(tableName);
        this.csvFileName = csvFileName;
        this.sep = sep;
        this.tableName = tableName;
        readConfigurations(tableName);
        char seperator = this.sep.charAt(0);
        importProcess = new ImportProcess();
        Object[] obj = importProcess.importAllObjectFromCSV(classToLoad, dbComponent, csvFileName, seperator,true);
        return obj;
    }
}

    private void readConfigurations(String tableName)
        throws ImportDatabaseConnectException
    {
        classToLoad = (new StringBuilder(ImportConstant.GENERATED_CLASS_PATH+".")).append(tableName).toString();
        databaseConnect.readMapping(importConfigData, dbComponent);
    }

    private String convertTableNameUtil(String tableName)
    {
        String tableToLower = tableName.toLowerCase();
        String str = (new StringBuilder(String.valueOf(tableToLower.charAt(0)))).toString();
        String str1 = (new StringBuilder(String.valueOf(str.toUpperCase()))).append(tableToLower.substring(1, tableToLower.length())).toString();
        return str1;
    }

    public void maintenance(IMaintenanceData imaintenancedata)
    {
    }

    public void start(Hashtable hashtable)
    {
    }
    
    public void importStart(String Database_UserName, String Database_Password, String Database_URL)
        throws ImportDatabaseConnectException
    {
    	try
    	{
	        if(Database_UserName == null || Database_Password == null || Database_URL == null || "".equals(Database_UserName) || "".equals(Database_Password) || "".equals(Database_URL))
	            return;
	        setConfigData(Database_UserName, Database_Password, Database_URL);
	        if(dbComponent != null)
	            databaseConnect.generateMappings(importConfigData, dbComponent);
    	}
    	catch (Exception exp)
    	{
    		exp.printStackTrace();
    	}
    }

    public void destroy()
    {
    }

    public void run()
    {
    }

    public void stop()
    {
    }

    private ConfigData setConfigData(String userName, String password, String databaseURL)
    {
        importConfigData = new ConfigData();
        importConfigData.setUser(userName);
        importConfigData.setPassword(password);
        importConfigData.setDatabaseUrl(databaseURL);
        return importConfigData;
    }
    
	@EventSubscriber(topic = "importCSVAllRowsAndSaveInDatabase")
	public synchronized void importCSVAllRowsAndSaveInDatabase(IHICData hicData) throws ComponentException
	{
		if (hicData == null)
		{
			throw new DBComponentException("input hicData is null in import component");
		}	
		if(databaseConnect == null)
			databaseConnect = new DatabaseConnect();
        importStart("root", "root", "jdbc:mysql://localhost:3306/datalyticx");
		com.oxymedical.core.commonData.IData data = hicData.getData();
		if(data!=null && data.getFormPattern()!=null && data.getFormPattern().getFormValues() !=null)
		{
			String csvFileNameToImport = (String) data.getFormPattern().getFormValues().get("csvFileNameToImport");
			String tableName = (String) data.getFormPattern().getFormValues().get("tableName");
			importCSV(csvFileNameToImport, ",", tableName);
		}
	}
	
	@EventSubscriber(topic = "importCSVAllRowsAndContainerSave")
	public synchronized void importCSVAllRowsAndContainerSave(IHICData hicData) throws ComponentException
	{
		if (hicData == null)
		{
			throw new DBComponentException("input hicData is null in import component");
		}	
		if(databaseConnect == null)
			databaseConnect = new DatabaseConnect();
        importStart("root", "root", "jdbc:mysql://localhost:3306/datalyticx");
		com.oxymedical.core.commonData.IData data = hicData.getData();
		if(data!=null && data.getFormPattern()!=null && data.getFormPattern().getFormValues() !=null)
		{
			String csvFileNameToImport = (String) data.getFormPattern().getFormValues().get("csvFileNameToImport");
			String tableName = (String) data.getFormPattern().getFormValues().get("tableName");
			String header[] = new String[1];
			String value[] = new String[1];
			header[0] = (String) data.getFormPattern().getFormValues().get("containerHeader");
			value[0] = (String) data.getFormPattern().getFormValues().get("containerID");
			List<String[]> containerList = new ArrayList<String[]>();
			containerList.add(0, header);
			containerList.add(1, value);
			importCSV(csvFileNameToImport, ",", tableName, containerList);
		}
	}

    public static void main(String a[])
        throws ComponentException, ImportComponentException
    {
        ImportComponent o = new ImportComponent();
        o.dbComponent = new DBComponent();
        o.importStart("root", "root", "jdbc:mysql://localhost:3306/datalyticx");
        o.importCSV("Material_Master.csv", ",", "MaterialMaster");
        /*
        ImportComponent o1 = new ImportComponent();
        o1.importDBComponent = new DBComponent();
        o1.importStart("root", "root", "jdbc:mysql://localhost:3306/datalyticx");
        o1.importCSV("Material_Master_Data.csv", ",", "MaterialMasterData");
        */ //For Actual Data
        
    }

    public IHICData getHicData()
    {
        return eibData;
    }

    public void setHicData(IHICData eibData)
    {
        this.eibData = eibData;
    }

}