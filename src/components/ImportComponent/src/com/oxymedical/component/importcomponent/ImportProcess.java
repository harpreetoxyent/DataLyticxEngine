package com.oxymedical.component.importcomponent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLClassLoader;
import java.util.List;
import java.util.jar.JarFile;

import com.oxymedical.component.db.DBComponent;
import com.oxymedical.component.db.IDBComponent;
import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.db.utilities.ConvertDataType;
import com.oxymedical.component.importcomponent.exception.ImportComponentException;
import com.oxymedical.component.importcomponent.mappingmanager.MappingManager;
import com.oxymedical.component.importcomponent.util.ImportUtil;

public class ImportProcess implements IImportProcess {

	int totalRecords;
	private IDBComponent dbObj;
	List recordsList;
	IMappingManager mappingManager;
	List methodList;
	JarFile jarFile;
	URLClassLoader loader;
	int save;
	private ConvertDataType cnvtDataType;
	private long importedData;

	public ImportProcess()
	{
		
	}
	public void run() throws ImportComponentException {
		try {
			int row = 0;
			totalRecords = recordsList.size();
			for (row = 0; row < totalRecords; row++) {
				String recordObj[] = (String[]) (String[]) recordsList.get(row);
				Object obj = dbObj.createObject(mappingManager.getClassName());
				Object _tmp = null;
				obj.getClass();
				for (int col = 0; col < recordObj.length; col++) {
					Object fieldObj[] = { recordObj[col].trim() };
					Method setMethod = (Method) (Method) methodList.get(col);
					Class parameterTypes[] = setMethod.getParameterTypes();
					Class _tmp1 = parameterTypes[0];
					Object fieldValue = cnvtDataType.setObjectValue(
							fieldObj[0], parameterTypes[0].getName());
					setMethod.invoke(obj, new Object[] { fieldValue });
				}

				dbObj.saveObject(obj);
			}

			setImportedData(row);
		} catch (IndexOutOfBoundsException iobe) {
			throw new ImportComponentException(iobe.getMessage());
		} catch (DBComponentException dbe) {
			throw new ImportComponentException(dbe.getMessage());
		} catch (IllegalAccessException iae) {
			throw new ImportComponentException(iae.getMessage());
		} catch (IllegalArgumentException iae) {
			throw new ImportComponentException(iae.getMessage());
		} catch (InvocationTargetException ite) {
			throw new ImportComponentException(ite.getMessage());
		} catch (SecurityException e) {
			throw new ImportComponentException(e.getMessage());
		}
	}

	public Object importOneRowNoSave() throws ImportComponentException {
		Object obj = null;
		try {
			totalRecords = recordsList.size();
			if (totalRecords > 0) {
				String recordObj[] = (String[]) (String[]) recordsList.get(0);
				obj = dbObj.createObject(mappingManager.getClassName());
				Object _tmp = null;
				obj.getClass();
				for (int col = 0; col < recordObj.length; col++) {
					Object fieldObj[] = { recordObj[col].trim() };
					Method setMethod = (Method) (Method) methodList.get(col);
					Class parameterTypes[] = setMethod.getParameterTypes();
					Class _tmp1 = parameterTypes[0];
					Object fieldValue = cnvtDataType.setObjectValue(
							fieldObj[0], parameterTypes[0].getName());
					setMethod.invoke(obj, new Object[] { fieldValue });
				}
				dbObj.saveObject(obj);
			}
			setImportedData(0);

		} catch (IndexOutOfBoundsException iobe) {
			throw new ImportComponentException(iobe.getMessage());
		} catch (DBComponentException dbe) {
			throw new ImportComponentException(dbe.getMessage());
		} catch (IllegalAccessException iae) {
			throw new ImportComponentException(iae.getMessage());
		} catch (IllegalArgumentException iae) {
			throw new ImportComponentException(iae.getMessage());
		} catch (InvocationTargetException ite) {
			throw new ImportComponentException(ite.getMessage());
		} catch (SecurityException e) {
			throw new ImportComponentException(e.getMessage());
		}
		return obj;
	}

	public ImportProcess(String classtoload, DBComponent dbObj,
			String csvFileName, char seperator) throws ImportComponentException {
		super();
		totalRecords = 1;
		this.dbObj = null;
		recordsList = null;
		mappingManager = null;
		methodList = null;
		jarFile = null;
		loader = null;
		save = 0;
		cnvtDataType = new ConvertDataType();
		this.dbObj = dbObj;
//		csvFileName = (new StringBuilder(ImportComponent.CSVFilePathLocation))
//				.append(csvFileName).toString();
		ImportUtil importUtil = new ImportUtil(csvFileName, seperator);
		recordsList = importUtil.readAll();
		String headerNames[] = (String[]) (String[]) recordsList.get(0);
		recordsList.remove(0);
		mappingManager = new MappingManager(classtoload, headerNames, dbObj);
		methodList = mappingManager.getSortedSettersList();
		if (methodList.size() == headerNames.length)
			run();
		else
			throw new ImportComponentException("Headers doesnot match");
	}

	public Object importOneObjectFromCSV(String classtoload, DBComponent dbObj,
			String csvFileName, char seperator, boolean isOneRow)
			throws ImportComponentException {
		Object obj = null;
		totalRecords = 1;
		this.dbObj = null;
		recordsList = null;
		mappingManager = null;
		methodList = null;
		jarFile = null;
		loader = null;
		save = 0;
		cnvtDataType = new ConvertDataType();
		this.dbObj = dbObj;
//		csvFileName = (new StringBuilder(ImportComponent.CSVFilePathLocation))
//				.append(csvFileName).toString();
		ImportUtil importUtil = new ImportUtil(csvFileName, seperator);
		recordsList = importUtil.readAll();
		String headerNames[] = (String[]) (String[]) recordsList.get(0);
		recordsList.remove(0);
		mappingManager = new MappingManager(classtoload, headerNames, dbObj);
		methodList = mappingManager.getSortedSettersList();
		if (methodList.size() == headerNames.length)
			obj = importOneRowNoSave();
		else
			throw new ImportComponentException("Headers doesnot match");
		return obj;
	}

	public long getImportedData() {
		return importedData;
	}

	public void setImportedData(long importedData) {
		this.importedData = importedData;
	}

}
