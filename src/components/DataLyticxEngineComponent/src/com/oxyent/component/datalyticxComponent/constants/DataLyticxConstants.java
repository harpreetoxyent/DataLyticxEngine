package com.oxyent.component.datalyticxComponent.constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.oxymedical.core.propertyUtil.PropertyUtil;

public class DataLyticxConstants 
{
	
	public static final String AuthenticateUserInLDAP = "AuthenticateUserInLDAP";
	public static final String UserPresentInUserAdmin_KEY = "UserPresentInUserAdmin";
	public static final String HibernatePath = "/com/oxymedical/component/datalyticxComponent/model/hibernate.cfg.xml";
	//public static final String USER_NAME = "root";
	//public static final String PASSWORD = "root";
	public static final String SERVER_NAME = "jdbc:mysql://localhost:3306/datalyticx";
	public static final String DBNAME = "datalyticx";
	public static final String PACKAGE_NAME = "com.oxymedical.component.datalyticx.model"; 
	public static final String BASEDIR = PropertyUtil.setUpProperties("GLASSFISH_HOME") + "\\lib";
	public static final String DATABASESETTING = "data/datasettings.xml";
	public static final String MATERIAL_MASTER ="MaterialMaster";
	public static final String MATERIAL_MASTER_DEFINITION ="MaterialMasterDefinition";
	
	
}
