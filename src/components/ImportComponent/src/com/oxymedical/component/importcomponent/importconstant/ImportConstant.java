// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 8/25/2014 12:06:16 PM
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ImportConstant.java

package com.oxymedical.component.importcomponent.importconstant;


public class ImportConstant
{

    public ImportConstant()
    {

    }
    public static String TOMCAT_HOME="/home/oxyent/tomcat";
    public static String JAR_PATH = (new StringBuilder(String.valueOf(TOMCAT_HOME))).append("/lib/").toString();
    public static String jarFilePath = (new StringBuilder(String.valueOf(TOMCAT_HOME))).append("/lib/resource_datalyticx.jar").toString();
    public static final String JAR_EXTENSION = ".jar";
    public static final String SET = "set";
    public static final String SET_ID = "setId";
    public static final String CSV_FILENAME = "fileName.csv";
    public static final String ROOT_DIR = (new StringBuilder(String.valueOf(TOMCAT_HOME))).append("/lib/").toString();
    public static final String CLASS_PATH = "com.oxymedical.component.render";//Anjali
    public static final String GENERATED_CLASS_PATH = "com.oxymedical.component.render.resource_datalyticx";
    public static final String IMPORT_CONFIG_TABLE = "table";
    public static final String IMPORT_CONFIG_NAME = "name";
    public static final String DATABASE_USERNAME = "user";
    public static final String DATABASE_PASSWORD = "password";
    public static final String DATABASE_CONNECT_URL = "DatabaseUrl";
    public static final String JAR_NAME = "resource_datalyticx";

}