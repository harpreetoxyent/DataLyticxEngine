package com.oxyent.datalyticx.engine;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.oxyent.component.datalyticxComponent.constants.DLQCommonMethods;
import com.oxyent.datalyticx.DataLyticxEntity;
import com.oxyent.datalyticx.Field;
import com.oxymedical.component.db.DBComponent;
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
	
	public List getEntityDefinitionData() throws DataLyticxQualityEngineException{
		String queryStr = "get BU_Entity.Entity, BU_Entity_Def.FieldName , BU_Entity_Def.LegitimateValue, BU_Entity_Def.Mandatory ,BU_Entity.BU ,BU_Entity.BU_EntityId from datalyticx.BU_Entity_Def,datalyticx.BU_Entity conditions BU_Entity.BU_EntityId:=BU_Entity_Def.BU_EntityId";
		List entityDefData;
		try {
			entityDefData = DLQCommonMethods.executeQueryReturnList(queryStr, "indexDL.zul");
		} catch (DataLyticxQualityEngineException e) {
			throw new DataLyticxQualityEngineException(e.getMessage());
		}
		return entityDefData;
	}
	public String[][] getDefinitionData() throws DataLyticxQualityEngineException{
		String queryStr = "get BU_Entity.Entity, BU_Entity_Def.FieldName , BU_Entity_Def.LegitimateValue, BU_Entity_Def.Mandatory ,BU_Entity.BU ,BU_Entity.BU_EntityId from datalyticx.BU_Entity_Def,datalyticx.BU_Entity conditions BU_Entity.BU_EntityId:=BU_Entity_Def.BU_EntityId";
		String[][] allValues;
		try {
			allValues = DLQCommonMethods.executeQueryReturnArray(queryStr, "indexDL.zul");
		} catch (DataLyticxQualityEngineException e) {
			throw new DataLyticxQualityEngineException(e.getMessage());
		}
		return allValues;
	}
	
	public static IHICData buildDataLyticxHICData(String formPatternId, String queryValue) throws DataLyticxQualityEngineException  {
		// Get the hicData instance from Bus

		IHICData hicData = new HICData();
		Hashtable<String, Object> formValues = new Hashtable<String, Object>();
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
	
	public static void quality(DataLyticxEntity entity){
		System.out.println("Inside quality  ::::");
		
	}

	public static void CheckQuality(DataLyticxEntity entity){
		System.out.println("Inside Quality check of engine");
		try {
			checkCompleteness(entity);
			checkAccuracy(entity);
		} catch (DataLyticxQualityEngineException e) {
			e.printStackTrace();
			//throw new DataLyticxQualityEngineException("NoSuchMethodException in CheckQuality :"+e.getMessage());
		} 

	}
	private static void checkCompleteness(DataLyticxEntity entity) throws DataLyticxQualityEngineException {
			System.out.println("Inside Completeness check of engine");
			Map<String, Field> fieldMap = entity.getFieldMap();
			try{
				Iterator<String> itFieldMap = fieldMap.keySet().iterator();  					
				while(itFieldMap.hasNext()){
					String key = itFieldMap.next();
					Field fieldObj = fieldMap.get(key);
					boolean isMandatory = fieldObj.isMandatory();
					if(isMandatory){
						String actualValue = fieldObj.getValue();
						com.oxyent.datalyticx.Quality quality = fieldObj.getQuality();
						if(actualValue != null && !"".equals(actualValue.trim())){
							quality.completeness(true);
						}else{
							quality.completeness(false);
						}
					}
				}
		}catch (Exception e) {
			throw new DataLyticxQualityEngineException("Exception in DataLyticxQualityEngine.checkCompleteness"+e.getMessage());
		}
	}
	
	public static void checkAccuracy(DataLyticxEntity entity) throws DataLyticxQualityEngineException {
		System.out.println("Inside Accuracy check of engine");
		Map<String, Field> fieldMap = entity.getFieldMap();
		Iterator<String> itFieldMap = fieldMap.keySet().iterator();  					
		while(itFieldMap.hasNext()){
			String key = itFieldMap.next();
			Field fieldObj = fieldMap.get(key);
			boolean isMandatory = fieldObj.isMandatory();
			if(isMandatory){
				String actualValue = fieldObj.getValue();
				String legitimateVal = fieldObj.getLegitimateValue();
				com.oxyent.datalyticx.Quality quality = fieldObj.getQuality();
				if(actualValue != null && !"".equals(actualValue.trim()) && actualValue.equals(legitimateVal)){
					quality.accuracy(true);
				}else{
					quality.accuracy(false);
				}
			}
		}
	}
		
	public static void checkValidity(DataLyticxEntity entity) {
		
	}
}
