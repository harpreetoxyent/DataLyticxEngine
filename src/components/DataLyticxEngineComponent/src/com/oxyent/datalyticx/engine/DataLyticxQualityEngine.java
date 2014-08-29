package com.oxyent.datalyticx.engine;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import com.oxyent.datalyticx.DataLyticxEntity;
import com.oxymedical.component.db.DBComponent;
import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.core.commonData.Application;
import com.oxymedical.core.commonData.Data;
import com.oxymedical.core.commonData.DataPattern;
import com.oxymedical.core.commonData.FormPattern;
import com.oxymedical.core.commonData.HICData;
import com.oxymedical.core.commonData.IApplication;
import com.oxymedical.core.commonData.IData;
import com.oxymedical.core.commonData.IDataPattern;
import com.oxymedical.core.commonData.IFormPattern;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.commonData.IMetaData;
import com.oxymedical.core.commonData.MetaData;
import com.oxymedical.core.querydata.QueryData;

public class DataLyticxQualityEngine 
{
	private DBComponent dbComponentDE;
	final static String MaterialMasterClass = "com.oxymedical.component.importcomponent.resource_import.MaterialMaster";
	public static double completeness = 0;
	public static double accuracy = 0;

	public DataLyticxQualityEngine(DBComponent currentDBComponent) {
		dbComponentDE = currentDBComponent;
		if(DBComponentStatic ==null)
		{
			DBComponentStatic = dbComponentDE;
		}
	}
	
	public static String[][] MaterialMasterMandatoryFields = null;
	static DBComponent DBComponentStatic = null;
	public String[][]  getAllMandatoryFieldsForMM() throws DBComponentException {
		String listQuery = "get Material_Master_Definition.FieldName, Material_Master_Definition.LegitimateValue, Material_Master_Definition.Mandatory " +
				"from datalyticx.Material_Master_Definition";
		HICData requestData = new HICData();
		Hashtable<String, Object> formValues = new Hashtable<String, Object>();
		IData data = new Data();
		requestData.setData(data);
		IDataPattern dataPattern = new DataPattern();
		dataPattern.setDataPatternId("datalyticx");
		data.setDataPattern(dataPattern);

		// Added Form Pattern in DO
		IFormPattern formPattern = new FormPattern();
		formPattern.setFormId("MaterialMaster");
		formPattern.setFormValues(formValues);
		data.setFormPattern(formPattern);

		QueryData queryData = new QueryData();
		queryData.setCondition(listQuery);
		data.setQueryData(queryData);

		data.setStatus("DEFAULT");
		data.setUserId(null);
		data.setUserPatterns(null);
		// Added ComponentId to Check existence
		//data.setInvokeComponentId("dbComponent");
		// Added ComponentClass for external Component
		//data.setInvokeComponentClass("com.oxymedical.component.db.DBComponent");
		// Added Method Name to set topic for publishing and subscribing
		data.setMethodName("executeList");

		// Added Meta Data in DO
		IMetaData metadata = new MetaData();
		metadata.setCommonObject(null);
		requestData.setMetaData(metadata);
		
		IApplication app = new Application();
		app.setServerDirectory("/home/oxyent/tomcat/");
		app.setBaseDirectoryPath("/home/oxyent/tomcat/lib");
		app.setApplicationFolderPath("/home/oxyent/datalyticx/");
		app.setApplicationFileName("/datalyticx.esp");
		app.setApplicationName("datalyticx");
		requestData.setApplication(app);
		IHICData outputData = dbComponentDE.getListData(requestData);
		QueryData queryData1 = outputData.getData().getQueryData();
		List listValue = outputData.getData().getQueryData().getListData();
		String[][] allValues = data.getQueryData().iterateListData(listValue);
		return allValues;
	}
	public static void CheckQualityMaterialMaster(DataLyticxEntity entity){
		System.out.println("Inside Quality check of engine");
		//Check and Save all required fields
		HashMap<String, String> reqFieldsMap = new HashMap<String, String>();
		int length = MaterialMasterMandatoryFields.length;
		for(int i=0;i<length;i++){
			String fieldName = MaterialMasterMandatoryFields[i][0];
			String legitimateValue= MaterialMasterMandatoryFields[i][1];
			String mandatoryFlag= MaterialMasterMandatoryFields[i][2];
			boolean required = checkRequiredField(mandatoryFlag);
			if(required){
				reqFieldsMap.put(fieldName,legitimateValue);// FieldName[i] will come from MMMFields list for now its hard coded
			}
		}
		// Check the corresponding field in actual data for completeness, accuracy
		if(reqFieldsMap.size() != 0){
			try {
				completeness = checkCompleteness(entity, reqFieldsMap);
				accuracy = checkAccuracy(entity, reqFieldsMap);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
				//throw new DataLyticxQualityEngineException(e.getMessage());
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}

			System.out.println("completeness >>>"+ completeness);
			System.out.println("accuracy >>>"+ accuracy);
		}else{
			//No Fields are Mandatory
		}
	}
	private static double checkCompleteness(DataLyticxEntity entity, HashMap<String, String> requiredFieldsMap) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		System.out.println("Inside Completeness check of engine");
		int completeCount = 0;
		Iterator<String> itReqFieldMap = requiredFieldsMap.keySet().iterator();
		while(itReqFieldMap.hasNext()){
			String fieldName = itReqFieldMap.next();		
			String actualValue = getActualValue(entity,fieldName);
			if(actualValue != null && !"".equals(actualValue.trim())){
				completeCount++;
			}
		}
		if(completeCount !=0){
			completeness = ((double)completeCount / (double)requiredFieldsMap.size()) * 100 ;
		}
		return completeness;
	}
	
	private static String getActualValue(DataLyticxEntity entity,
			String fieldName) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String fieldToGetter = fieldName.replace("_", "");//Complete the code, apply other cases for capitalize next letter
		fieldToGetter = fieldToGetter.toLowerCase();
		fieldToGetter = "get" + fieldToGetter.substring(0, 1).toUpperCase() + fieldToGetter.substring(1);//Complete this 
		Object materialMasterObject = entity.getEntityData();
		Method method = materialMasterObject.getClass().getMethod(fieldToGetter);
		String returnObject = (String)method.invoke(materialMasterObject);
		return returnObject;
	}
	private static boolean checkRequiredField(String fieldValue) {
		if("Y".equals(fieldValue))
			return true;
		else
			return false;
	}

	public static double checkAccuracy(DataLyticxEntity entity,HashMap<String, String> requiredFieldsMap) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		System.out.println("Inside Accuracy check of engine");
		int accuracyCount = 0;
		Iterator<String> itReqFieldMap = requiredFieldsMap.keySet().iterator();
		while(itReqFieldMap.hasNext()){
			String fieldName = itReqFieldMap.next();
			String legitimateVal = requiredFieldsMap.get(fieldName);
			String actualValue = getActualValue(entity,fieldName);
			if(actualValue != null && !"".equals(actualValue.trim()) && actualValue.equals(legitimateVal)){
				accuracyCount++;
			}
		}
		if(accuracyCount !=0){
			accuracy = ((double)accuracyCount / (double)requiredFieldsMap.size()) * 100 ;
		}
		return accuracy;
	}

	public static void checkValidity(DataLyticxEntity entity) {
	}
}
