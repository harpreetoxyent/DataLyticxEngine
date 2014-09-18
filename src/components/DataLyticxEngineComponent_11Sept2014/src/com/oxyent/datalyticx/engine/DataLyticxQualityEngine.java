package com.oxyent.datalyticx.engine;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import com.oxyent.datalyticx.DataLyticxEntity;
import com.oxymedical.component.db.DBComponent;
import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.render.resource_datalyticx.Quality;
import com.oxymedical.component.render.resource_datalyticx.IncorrectData;
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
import com.oxymedical.core.commonData.ISource;
import com.oxymedical.core.commonData.MetaData;
import com.oxymedical.core.commonData.Source;
import com.oxymedical.core.querydata.QueryData;
import com.oxymedical.component.UniqueIDGeneratorComponent.UniqueIdGenerator;

public class DataLyticxQualityEngine 
{
	private DBComponent dbComponentDE;
	public static double completeness = 0;
	public static double accuracy = 0;
	public DataLyticxQualityEngine(DBComponent currentDBComponent) {
		dbComponentDE = currentDBComponent;
		if(DBComponentStatic ==null)
		{
			DBComponentStatic = dbComponentDE;
		}
	}

	public static String[][] DefinitionData = null;
	static DBComponent DBComponentStatic = null;

	public String[][]  getDefinitionData() throws DBComponentException {
		//String listQuery = "get Entity_Definition.Entity, Entity_Definition.FieldName, Entity_Definition.LegitimateValue, Entity_Definition.Mandatory " +
		//"from datalyticx.Entity_Definition";
		String listQuery = "get BU_Entity.Entity, BU_Entity_Def.FieldName , BU_Entity_Def.LegitimateValue, BU_Entity_Def.Mandatory ,BU_Entity.BU ,BU_Entity.BU_EntityId from datalyticx.BU_Entity_Def,datalyticx.BU_Entity conditions BU_Entity.BU_EntityId:=BU_Entity_Def.BU_EntityId";
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
		List listValue = outputData.getData().getQueryData().getListData();
		String[][] allValues = data.getQueryData().iterateListData(listValue);
		return allValues;
	}
	public static IHICData buildDataLyticxHICData(String formPatternId, String queryValue) throws DataLyticxQualityEngineException  {
		// Get the hicData instance from Bus

		IHICData hicData = new HICData();
		Hashtable<String, Object> formValues = new Hashtable<String, Object>();
		if (hicData == null) {
			throw new DataLyticxQualityEngineException(" hicData is null");
		}
		try {
			IData data = new Data();
			// Added Data Pattern in DO
			IDataPattern dataPattern = new DataPattern();
			dataPattern.setDataPatternId("datalyticx");
			data.setDataPattern(dataPattern);

			// Added Form Pattern in DO
			IFormPattern formPattern = new FormPattern();
			formPattern.setFormId(formPatternId);
			formPattern.setFormValues(formValues);
			data.setFormPattern(formPattern);

			QueryData query = new QueryData();
			query.setCondition(queryValue);
			data.setQueryData(query);

			data.setStatus("DEFAULT");
			data.setUserId(null);
			data.setUserPatterns(null);
			// Added ComponentId to Check existence
			data.setInvokeComponentId("dbComponent");
			// Added ComponentClass for external Component
			data.setInvokeComponentClass("com.oxymedical.component.db.DBComponent");
			// Added Method Name to set topic for publishing and subscribing
			data.setMethodName("executeList");

			// Added Meta Data in DO
			IMetaData metadata = new MetaData();
			metadata.setCommonObject(null);
			hicData.setMetaData(metadata);

			// Added source in DO
			LinkedList<ISource> srcHistory = new LinkedList<ISource>();
			ISource src = new Source();
			src.setMethodName("invokeComponent");
			srcHistory.add(src);
			hicData.setSrcHistoryList(srcHistory);

			hicData.setUniqueID(UniqueIdGenerator.generateUniqueID("IHICData"));
			// Set application
			IApplication app = null;
			hicData.setData(data);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return hicData;
	}

	public static void CheckQuality(DataLyticxEntity entity){
		System.out.println("Inside Quality check of engine");
		//Check and Save all required fields
		//NewChange Start
		HashMap<String, HashMap<String, String>> reqFieldsMapPerBU = new HashMap<String, HashMap<String, String>>();
		//NewChange Ends
		HashMap<String, String> reqFieldsMap = new HashMap<String, String>();
		int length = DefinitionData.length;
		for(int i=0;i<length;i++){
			String fieldName = DefinitionData[i][1];
			String legitimateValue= DefinitionData[i][2];
			String mandatoryFlag= DefinitionData[i][3];
			boolean required = checkRequiredField(mandatoryFlag);
			if(required){
				reqFieldsMap.put(fieldName,legitimateValue);// FieldName[i] will come from MMMFields list for now its hard coded
				//NewChange Start
				String bu= DefinitionData[i][4];
				String buEntityID= DefinitionData[i][5];
				reqFieldsMapPerBU.put(buEntityID, reqFieldsMap);//New Map
				//NewChange Ends
			}
		}
		// Check the corresponding field in actual data for completeness, accuracy
		if(reqFieldsMap.size() != 0)
		{
			try {
				checkCompleteness(entity, reqFieldsMapPerBU);
				checkAccuracy(entity, reqFieldsMapPerBU);
			} catch (DataLyticxQualityEngineException e) {
				e.printStackTrace();
				//throw new DataLyticxQualityEngineException("NoSuchMethodException in CheckQuality :"+e.getMessage());
			} 
		}else{
			//No Fields are Mandatory
		}
	}
	
	private static void checkCompleteness(DataLyticxEntity entity, HashMap<String, HashMap<String, String>> reqFieldsMapPerBU) throws DataLyticxQualityEngineException {
		System.out.println("Inside Completeness check of engine");
		int completeCount = 0;
		Object[] entityArray = entity.getEntityArrayData();
		//Object entityArray = entity.getEntityData();
		for(int loop=0; loop <entityArray.length; loop++)
		{
			Iterator<String> itReqFieldwithBUMap = reqFieldsMapPerBU.keySet().iterator();
			HashMap<String, String> requiredFieldsMap;
			String buEntityId;
			completeCount = 0;
			while(itReqFieldwithBUMap.hasNext()){
				buEntityId = itReqFieldwithBUMap.next();
				requiredFieldsMap = reqFieldsMapPerBU.get(buEntityId);
				Iterator<String> itReqFieldMap = requiredFieldsMap.keySet().iterator();
				
				String actualDataBU = getActualValue(entityArray[loop],"BU");
				String entityName =getActualValue(entityArray[loop],"ENTITY");
				String[][] allValues = executeQueryReturnArray("get BU_Entity.BU_EntityId from datalyticx.BU_Entity conditions BU_Entity.BU:=["+ actualDataBU+ "] and BU_Entity.Entity:=["+entityName+"]");
				String actualBuEntityID = allValues[0][0];
				
				if(buEntityId != null && buEntityId.equals(actualBuEntityID)){					
					while(itReqFieldMap.hasNext()){
						String fieldName = itReqFieldMap.next();
						String actualValue = getActualValue(entityArray[loop],fieldName);//entityArray[loop]
						if(actualValue != null && !"".equals(actualValue.trim())){
							completeCount++;
						}else{
							String legitimateVal = requiredFieldsMap.get(fieldName);
							storeIncorrectData(entityArray[loop], fieldName , legitimateVal , actualValue , "Completeness");
						}
					}
					if(completeCount !=0){
						completeness = ((double)completeCount / (double)requiredFieldsMap.size()) * 100 ;
					}
					saveQualityData(entityArray[loop],"Completeness",completeness);//entityArray[loop]
				}
			}
		}
		//return completeness;
	}
	
	private static void storeIncorrectData(Object entityData, String fieldName, String legitimateVal, String actualValue, String qualityName)  throws DataLyticxQualityEngineException {
		String BU = getActualValue(entityData,"BU");
		String entityName =getActualValue(entityData,"ENTITY");
		String[][] allValues = executeQueryReturnArray("get BU_Entity.BU_EntityId from datalyticx.BU_Entity conditions BU_Entity.BU:=["+ BU+ "] and BU_Entity.Entity:=["+entityName+"]");
		Long buEntityID = Long.parseLong(allValues[0][0]);
		
		IncorrectData incorrectData = new IncorrectData();
		incorrectData.setBuEntityId(buEntityID);
		incorrectData.setFieldName(fieldName);
		incorrectData.setIdealValue(legitimateVal);
		incorrectData.setQualityName(qualityName);
		incorrectData.setActualValue(actualValue);
		try {
			DBComponentStatic.saveObject(incorrectData);
		} catch (DBComponentException e) {
			throw new DataLyticxQualityEngineException("DBComponentException in DataLyticxQualityEngine.storeIncorrectData :"+e.getMessage());
		}
	}
	public static void checkAccuracy(DataLyticxEntity entity,HashMap<String, HashMap<String, String>> reqFieldsMapPerBU) throws DataLyticxQualityEngineException {
		System.out.println("Inside Accuracy check of engine");
		int accuracyCount = 0;
		Object[] entityArray = entity.getEntityArrayData();
		//Object entityArray = entity.getEntityData();
		for(int loop=0; loop <entityArray.length; loop++)
		{
			accuracyCount = 0;
			Iterator<String> itReqFieldwithBUMap = reqFieldsMapPerBU.keySet().iterator();
			HashMap<String, String> requiredFieldsMap;
			String buEntityId;
			while(itReqFieldwithBUMap.hasNext()){
				buEntityId = itReqFieldwithBUMap.next();
				requiredFieldsMap = reqFieldsMapPerBU.get(buEntityId);
				Iterator<String> itReqFieldMap = requiredFieldsMap.keySet().iterator();
				String actualDataBU = getActualValue(entityArray[loop],"BU");
				String entityName =getActualValue(entityArray[loop],"ENTITY");
				String[][] allValues = executeQueryReturnArray("get BU_Entity.BU_EntityId from datalyticx.BU_Entity conditions BU_Entity.BU:=["+ actualDataBU+ "] and BU_Entity.Entity:=["+entityName+"]");
				String actualBuEntityID = allValues[0][0];
				
				if(buEntityId != null && buEntityId.equals(actualBuEntityID)){
					while(itReqFieldMap.hasNext()){
						String fieldName = itReqFieldMap.next();
						String legitimateVal = requiredFieldsMap.get(fieldName);
						String actualValue = getActualValue(entityArray[loop],fieldName);//entityArray[loop]
						if(actualValue != null && !"".equals(actualValue.trim()) && actualValue.equals(legitimateVal)){
							accuracyCount++;
						}else{
							storeIncorrectData(entityArray[loop], fieldName , legitimateVal , actualValue , "Accuracy");
						}
					}
					if(accuracyCount !=0){
						accuracy = ((double)accuracyCount / (double)requiredFieldsMap.size()) * 100 ;
					}
					saveQualityData(entityArray[loop],"Accuracy",accuracy);//entityArray[loop]
				}
			}
		}
		//return accuracy;
	}
	
	private static void saveQualityData(Object entityData, String qualityName, double qualityPercent) throws DataLyticxQualityEngineException {
		
		
		String BU = getActualValue(entityData,"BU");
		String entityName =getActualValue(entityData,"ENTITY");
		String queryStr = "get BU_Entity.BU_EntityId from datalyticx.BU_Entity conditions BU_Entity.BU:=["+ BU+ "] and BU_Entity.Entity:=["+entityName+"]";

		IHICData requestData = null;
		IHICData outputHICData = null;
		requestData = buildDataLyticxHICData("indexDL.zul", queryStr);
		try {
			outputHICData = DBComponentStatic.getListData(requestData);
		} catch (DBComponentException e) {
			throw new DataLyticxQualityEngineException("DBComponentException in DataLyticxQualityEngine.saveQualityData :"+e.getMessage());
		}
		if (outputHICData != null && outputHICData.getData() != null) 
		{
			List listValue = outputHICData.getData().getQueryData().getListData();
			String[][] allValues = outputHICData.getData().getQueryData().iterateListData(listValue);
			// Insert quality for Given BU and Entity in database
			Long buEntityID = Long.parseLong(allValues[0][0]);
			String qualityQuery = "get Quality.Id from datalyticx.Quality conditions " +
					"Quality.BU_EntityId:=["+buEntityID+"] and Quality.Quality:=["+qualityName+"]";
			String[][] qualityData = executeQueryReturnArray(qualityQuery); 
			Quality quality = new Quality();
			String qualityId = "";
			if(qualityData != null && qualityData[0] != null)
				qualityId = qualityData[0][0];
			if(qualityId != null && !"".equals(qualityId.trim())){				
				quality.setId(Long.parseLong(qualityId));
			}
			quality.setBuEntityId(buEntityID);
			quality.setQuality(qualityName);
			quality.setPercentage(((Double)qualityPercent).toString());//Change this field to Double in db and then change in code accordingly
			try {
				DBComponentStatic.saveObject(quality);
			} catch (DBComponentException e) {
				throw new DataLyticxQualityEngineException("DBComponentException in DataLyticxQualityEngine.saveQualityData :"+e.getMessage());
			}
		}
	}

	private static String[][] executeQueryReturnArray(String queryStr) throws DataLyticxQualityEngineException{
		IHICData requestData = null;
		IHICData outputHICData = null;
		requestData = buildDataLyticxHICData("indexDL.zul", queryStr);
		try {
			outputHICData = DBComponentStatic.getListData(requestData);
		} catch (DBComponentException e) {
			throw new DataLyticxQualityEngineException("DBComponentException in DataLyticxQualityEngine.executeQueryReturnArray :"+e.getMessage());
		}
		String[][] allValues = null;
		if (outputHICData != null && outputHICData.getData() != null) 
		{
			List listValue = outputHICData.getData().getQueryData().getListData();
			allValues = outputHICData.getData().getQueryData().iterateListData(listValue);
		}
		return allValues;
	}
		
	private static String getActualValue(Object entityObject, String fieldName) throws DataLyticxQualityEngineException {
		System.out.println("Inside getActualValue ="+entityObject+"fieldName="+fieldName);
		String returnObject = null;
		
			String fieldToGetter = fieldName.replace("_", "");//Complete the code, apply other cases for capitalize next letter
			fieldToGetter = fieldToGetter.toLowerCase();
			fieldToGetter = "get" + fieldToGetter.substring(0, 1).toUpperCase() + fieldToGetter.substring(1);//Complete this
			//Object entityObject = entity.getEntityData();
			Method method;
			try {
				method = entityObject.getClass().getMethod(fieldToGetter);
				returnObject = (String)method.invoke(entityObject);
			} catch (SecurityException e) {
				throw new DataLyticxQualityEngineException("SecurityException in DataLyticxQualityEngine.getActualValue :"+e.getMessage());
			} catch (NoSuchMethodException e) {
				throw new DataLyticxQualityEngineException("NoSuchMethodException in DataLyticxQualityEngine.getActualValue :"+e.getMessage());
			} catch (IllegalArgumentException e) {
				throw new DataLyticxQualityEngineException("IllegalArgumentException in DataLyticxQualityEngine.getActualValue :"+e.getMessage());
			} catch (IllegalAccessException e) {
				throw new DataLyticxQualityEngineException("IllegalAccessException in DataLyticxQualityEngine.getActualValue :"+e.getMessage());
			} catch (InvocationTargetException e) {
				throw new DataLyticxQualityEngineException("InvocationTargetException in DataLyticxQualityEngine.getActualValue :"+e.getMessage());
			}
		return returnObject;
	}
	private static boolean checkRequiredField(String fieldValue) {
		if("Y".equals(fieldValue))
			return true;
		else
			return false;
	}

	public static void checkValidity(DataLyticxEntity entity) {
	}
}
