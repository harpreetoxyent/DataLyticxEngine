// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 8/25/2014 12:06:17 PM
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   MappingManager.java

package com.oxymedical.component.importcomponent.mappingmanager;

import com.oxymedical.component.db.IDBComponent;
import com.oxymedical.component.importcomponent.IMappingManager;
import com.oxymedical.component.importcomponent.exception.ImportComponentException;
import com.oxymedical.component.importcomponent.importconstant.ImportConstant;

import java.io.*;
import java.lang.reflect.Method;
import java.net.*;
import java.util.*;
import java.util.List;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import org.apache.commons.lang.WordUtils;
import org.hibernate.MappingException;
import org.hibernate.mapping.*;
import org.hibernate.property.Setter;
import org.hibernate.type.Type;

public class MappingManager
    implements IMappingManager
{
    private String header[];
    Method methods[];
    private List settersList;
    private List sortSettersList;
    private int index;
    private Class loadedClass;
    private String className;
    private Object obj;
    private URLClassLoader loader;
    private IDBComponent dbObj;

    public MappingManager(String className, String header[], IDBComponent dbObj)
    {
        index = 0;
        loadedClass = null;
        this.className = null;
        obj = null;
        loader = null;
        this.dbObj = null;
        System.out.println((new StringBuilder("class name = ")).append(className).toString());
        this.header = header;
        this.className = className;
        this.dbObj = dbObj;
    }

    public List getSortedSettersList()
        throws ImportComponentException
    {
        loader = loadJarAtRuntime();
        settersList = new ArrayList();
        sortSettersList = new ArrayList();
        if(loadedClass == null)
            return null;
        PersistentClass persistentClass = dbObj.getPersistentClass(loadedClass.getName());
        Iterator propertyIt = persistentClass.getPropertyIterator();
        boolean primaryKeysPresent = false;
        while(propertyIt.hasNext()) 
        {
            primaryKeysPresent = true;
            Property property = (Property)(Property)propertyIt.next();
            if(property.getValue().getType().isAssociationType())
            {
                Property property1 = persistentClass.getIdentifierProperty();
                Setter setter = property1.getSetter(persistentClass.getMappedClass());
                Class parameterTypes[] = setter.getMethod().getParameterTypes();
                try
                {
                    Method method = persistentClass.getMappedClass().getMethod(setter.getMethodName(), parameterTypes);
                    settersList.add(method);
                }
                catch(MappingException me)
                {
                    throw new ImportComponentException(me.getMessage());
                }
                catch(SecurityException se)
                {
                    throw new ImportComponentException(se.getMessage());
                }
                catch(NoSuchMethodException nsme)
                {
                    throw new ImportComponentException(nsme.getMessage());
                }
            }
            Setter setter = property.getSetter(persistentClass.getMappedClass());
            Class parameterTypes[] = setter.getMethod().getParameterTypes();
            try
            {
                Method method = persistentClass.getMappedClass().getMethod(setter.getMethodName(), parameterTypes);
                settersList.add(method);
            }
            catch(MappingException me)
            {
                throw new ImportComponentException(me.getMessage());
            }
            catch(SecurityException se)
            {
                throw new ImportComponentException(se.getMessage());
            }
            catch(NoSuchMethodException nsme)
            {
                throw new ImportComponentException(nsme.getMessage());
            }
        }
        if(!primaryKeysPresent)
        {
            Component identifier = (Component)persistentClass.getIdentifier();
            for(Iterator columnIterator = identifier.getPropertyIterator(); columnIterator.hasNext();)
            {
                Property property = (Property)(Property)columnIterator.next();
                Setter setter = property.getSetter(persistentClass.getMappedClass());
                Class parameterTypes[] = setter.getMethod().getParameterTypes();
                try
                {
                    Method method = persistentClass.getMappedClass().getMethod(setter.getMethodName(), parameterTypes);
                    settersList.add(method);
                }
                catch(MappingException me)
                {
                    throw new ImportComponentException(me.getMessage());
                }
                catch(SecurityException se)
                {
                    throw new ImportComponentException(se.getMessage());
                }
                catch(NoSuchMethodException nsme)
                {
                    throw new ImportComponentException(nsme.getMessage());
                }
            }

        }
        for(int i = 0; i < header.length; i++)
        {
            for(int j = 0; j < settersList.size(); j++)
            {
                Method SetMethod = (Method)settersList.get(j);
                String headerName = header[i].trim();
                //check if header contains _ as hibernate will remove it and make it caps
                if(null != headerName && headerName.indexOf("_")>0)
                {
                	//replace _ and make letter after _ caps
                	headerName=headerName.replaceFirst("_", "");
                }
                if((new StringBuilder("set")).append(headerName).toString().equalsIgnoreCase(SetMethod.getName().trim()))
                {
                    sortSettersList.add(index, SetMethod);
                    index++;
                }
            }

        }

        return sortSettersList;
    }

    URLClassLoader loadJarAtRuntime()
        throws ImportComponentException
    {
        URLClassLoader loader = null;
        ClassLoader parentClassLoader = getClass().getClassLoader();
        loader = (URLClassLoader)parentClassLoader;
        String jarFilePath = ImportConstant.jarFilePath;
        try
        {
            URL url = (new File(jarFilePath)).toURL();
            URL urlArr[] = new URL[1];
            urlArr[0] = url;
            loader = new URLClassLoader(urlArr, parentClassLoader);
            JarFile jarFile = new JarFile(jarFilePath);
            Enumeration entries = jarFile.entries();
            String classToLoad = (new StringBuilder(String.valueOf(className.trim()))).append(".class").toString();
            while(entries.hasMoreElements()) 
            {
                ZipEntry entry = (ZipEntry)(ZipEntry)entries.nextElement();
                String str = entry.getName();
                str = str.replace("/", ".");
                str.indexOf(".");
                if(str.trim().indexOf(classToLoad) == 0)
                {
                    loadedClass = loader.loadClass(className);
                    System.out.println((new StringBuilder(" loaded class ==============")).append(loadedClass).toString());
                    break;
                }
            }
        }
        catch(MalformedURLException mue)
        {
            throw new ImportComponentException(mue.getMessage());
        }
        catch(IOException ioe)
        {
            throw new ImportComponentException(ioe.getMessage());
        }
        catch(ClassNotFoundException cnf)
        {
            throw new ImportComponentException(cnf.getMessage());
        }
        return loader;
    }

    public String getClassName()
    {
        return className;
    }

}