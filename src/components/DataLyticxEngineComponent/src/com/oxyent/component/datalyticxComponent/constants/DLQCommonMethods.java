package com.oxyent.component.datalyticxComponent.constants;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.lang.WordUtils;
import org.hibernate.dialect.FirebirdDialect;

import com.oxyent.component.datalyticxComponent.DataLyticxComponent;
import com.oxyent.datalyticx.DataLyticxEntity;
import com.oxyent.datalyticx.Field;
import com.oxyent.datalyticx.engine.DataLyticxQualityEngine;
import com.oxyent.datalyticx.engine.DataLyticxQualityEngineException;
import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.render.resource_datalyticx.IncorrectData;
import com.oxymedical.core.commonData.IHICData;

public class DLQCommonMethods {

	public static String getActualValue(Object entityObject, String fieldName) throws DataLyticxQualityEngineException {
		System.out.println("Inside getActualValue ="+entityObject+"fieldName="+fieldName);
		String returnObject = null;
			
			char[] regx = {'_'};
			String fieldToGetter = fieldName.toLowerCase();
			fieldToGetter = WordUtils.capitalize(fieldToGetter, regx);
			fieldToGetter = fieldToGetter.replaceFirst("_", "");
			fieldToGetter = "get" + fieldToGetter;//Complete this
			Method method;
			try {
				method = entityObject.getClass().getMethod(fieldToGetter);
				returnObject = (String)method.invoke(entityObject);
			} catch (SecurityException e) {
				//System.out.print("SecurityException in DataLyticxQualityEngine.getActualValue :");
				throw new DataLyticxQualityEngineException("SecurityException in DataLyticxQualityEngine.getActualValue :"+e.getMessage());
			} catch (NoSuchMethodException e) {
				//System.out.print("NoSuchMethodException in DataLyticxQualityEngine.getActualValue :");
				throw new DataLyticxQualityEngineException("NoSuchMethodException in DataLyticxQualityEngine.getActualValue :"+e.getMessage());
			} catch (IllegalArgumentException e) {
				//System.out.print("IllegalArgumentException in DataLyticxQualityEngine.getActualValue :");
				throw new DataLyticxQualityEngineException("IllegalArgumentException in DataLyticxQualityEngine.getActualValue :"+e.getMessage());
			} catch (IllegalAccessException e) {
				//System.out.print("IllegalAccessException in DataLyticxQualityEngine.getActualValue :");
				throw new DataLyticxQualityEngineException("IllegalAccessException in DataLyticxQualityEngine.getActualValue :"+e.getMessage());
			} catch (InvocationTargetException e) {
				//System.out.print("InvocationTargetException in DataLyticxQualityEngine.getActualValue :");
				throw new DataLyticxQualityEngineException("InvocationTargetException in DataLyticxQualityEngine.getActualValue :"+e.getMessage());
			}
		return returnObject;
	}

	public boolean checkMultipleOf(DataLyticxEntity dataLyticxEntity,Field fieldObj, List<String> functionParameters) {
		//To Do : Apply simple logic of LCM and do this all together
		String fieldValue = fieldObj.getValue();
		boolean isMultiple = false;
		for(int i = 0; i <functionParameters.size(); i++){
			if(fieldValue != null && "0".equals(functionParameters.get(i))){
				int remainder = Integer.parseInt(fieldValue) % Integer.parseInt(functionParameters.get(i)); 
				if(remainder == 0){
					isMultiple = true;
				}
			}
		}
		return isMultiple;
	}
	
	public void storeIncorrectData(DataLyticxEntity entity, Field fieldObj, String qualityName)  throws DataLyticxQualityEngineException {
		String BU = entity.getBusinessUnit();
		String entityType = entity.getType();
		String query = "get BU_Entity.BU_EntityId from datalyticx.BU_Entity conditions BU_Entity.BU:=["+ BU+ "] and BU_Entity.Entity:=["+entityType+"]";
		String[][] allValues = executeQueryReturnArray(query,"index.zul");
		Long buEntityID = Long.parseLong(allValues[0][0]);
		
		IncorrectData incorrectData = new IncorrectData();
		incorrectData.setBuEntityId(buEntityID);
		incorrectData.setFieldName(fieldObj.getName());
		incorrectData.setIdealValue(fieldObj.getLegitimateValue());
		incorrectData.setQualityName(qualityName);
		incorrectData.setActualValue(fieldObj.getValue());
		try {
			DataLyticxComponent.dbComponent.saveObject(incorrectData);
		} catch (DBComponentException e) {
			throw new DataLyticxQualityEngineException("DBComponentException in DataLyticxQualityEngine.storeIncorrectData :"+e.getMessage());
		}
	}
	
	public static String[][] executeQueryReturnArray(String queryStr, String formName) throws DataLyticxQualityEngineException{
		IHICData requestData = null;
		IHICData outputHICData = null;
		requestData = DataLyticxQualityEngine.buildDataLyticxHICData(formName, queryStr);
		//To Do : Change above function call or place of this method
		try {
			outputHICData = DataLyticxComponent.dbComponent.getListData(requestData);
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
	public static List executeQueryReturnList(String queryStr,String formName) throws DataLyticxQualityEngineException{
		IHICData requestData = null;
		IHICData outputHICData = null;
		List listValue = null;
		try {
			requestData = DataLyticxQualityEngine.buildDataLyticxHICData(formName, queryStr);
			outputHICData = DataLyticxComponent.dbComponent.getListData(requestData);
			if (outputHICData != null && outputHICData.getData() != null)
			{
				listValue = outputHICData.getData().getQueryData().getListData();
			}
		} catch (DBComponentException e) {
			throw new DataLyticxQualityEngineException("DBComponentException in DataQualityPieChartModel : "+e.getMessage());
		}
		return listValue;
	}
}
