// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 8/25/2014 12:06:17 PM
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ConfigData.java

package com.oxymedical.component.importcomponent.parser;


public class ConfigData
{

    public ConfigData()
    {
        user = "";
        password = "";
        databaseurl = "";
    }

    public String getUser()
    {
        return user;
    }

    public void setUser(String user)
    {
        this.user = user;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getDatabaseUrl()
    {
        return databaseurl;
    }

    public void setDatabaseUrl(String databaseurl)
    {
        this.databaseurl = databaseurl;
    }

    String user;
    String password;
    String databaseurl;
}