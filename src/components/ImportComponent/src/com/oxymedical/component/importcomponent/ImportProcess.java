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
	List<String[]> containerList;
	IMappingManager mappingManager;
	List methodList;
	List containerMethodList;
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
			Method containerSetMethod = null;
			String containerFieldType = null;
			String containerRecord[] = (String[]) (String[])containerList.get(0); 
			Object containerObj[] = { containerRecord[0].trim() };
			if(containerMethodList !=null){
				for(int i=0; i < containerMethodList.size();i++){					
					containerSetMethod = (Method) (Method) containerMethodList.get(i);
					Class parameterTypes[] = containerSetMethod.getParameterTypes();
					Class _tmp1 = parameterTypes[0];
					containerFieldType = parameterTypes[0].getName();
					
				}
			}
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
					
					if(containerMethodList !=null && containerFieldType != null){
						Object containerFieldValue = cnvtDataType.setObjectValue(containerObj[0], containerFieldType);
						containerSetMethod.invoke(obj, new Object[] { containerFieldValue });
					}
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

	public Object importOneRow() throws ImportComponentException {
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
	
	public Object[] importAllRowsSaveAndReturn() throws ImportComponentException {
		Object[] obj = null;
		try {			
			int row = 0;
			totalRecords = recordsList.size();
			obj = new Object[totalRecords];
			for (row = 0; row < totalRecords; row++) {
				String recordObj[] = (String[]) (String[]) recordsList.get(row);
				obj[row] = dbObj.createObject(mappingManager.getClassName());
				for (int col = 0; col < recordObj.length; col++) {
					Object fieldObj[] = { recordObj[col].trim() };
					Method setMethod = (Method) (Method) methodList.get(col);
					Class parameterTypes[] = setMethod.getParameterTypes();
					Object fieldValue = cnvtDataType.setObjectValue(
							fieldObj[0], parameterTypes[0].getName());
					setMethod.invoke(obj[row], new Object[] { fieldValue });
				}
				dbObj.saveObject(obj[row]);
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
		return obj;
	}

	public ImportProcess(String classtoload, DBComponent dbObj,
			String csvFileName, char seperator) throws ImportComponentException {
		this(classtoload, dbObj, csvFileName, seperator, null);
	}
	public ImportProcess(String classtoload, DBComponent dbObj,
			String csvFileName, char seperator, List<String[]> containerList) throws ImportComponentException {
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
		this.containerList = containerList;
//		csvFileName = (new StringBuilder(ImportComponent.CSVFilePathLocation))
//				.append(csvFileName).toString();
		ImportUtil importUtil = new ImportUtil(csvFileName, seperator);
		recordsList = importUtil.readAll();
		String headerNames[] = (String[]) (String[]) recordsList.get(0);
		recordsList.remove(0);
		mappingManager = new MappingManager(classtoload, headerNames, dbObj);
		methodList = mappingManager.getSortedSettersList();
		
		if(containerList != null && containerList.size() > 0){
			String containerheaderNames[] = (String[]) containerList.get(0);
			containerList.remove(0);
			MappingManager containerMappingMgr = new MappingManager(classtoload, containerheaderNames, dbObj);
			containerMethodList = containerMappingMgr.getSortedSettersList();
		}
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
			obj = importOneRow();
		else
			throw new ImportComponentException("Headers doesnot match");
		return obj;
	}

	public Object[] importAllObjectFromCSV(String classtoload, DBComponent dbObj,
			String csvFileName, char seperator, boolean isOneRow)
			throws ImportComponentException {
		Object[] obj = null;
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
			obj = importAllRowsSaveAndReturn();
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
