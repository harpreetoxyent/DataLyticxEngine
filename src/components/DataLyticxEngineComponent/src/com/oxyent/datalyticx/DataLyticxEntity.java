package com.oxyent.datalyticx;

import java.util.LinkedHashMap;
import java.util.Map;

import com.oxyent.component.datalyticxComponent.constants.DLQCommonMethods;
import com.oxyent.datalyticx.engine.DataLyticxQualityEngine;
import com.oxyent.datalyticx.engine.DataLyticxQualityEngineException;

public class DataLyticxEntity {
	
	private String type;
	private String businessUnit;
	private Map<String, Field> fieldMap;

	public DataLyticxEntity(){
		
	}
	
	public DataLyticxEntity(Object entityData, String[][] defData) throws DataLyticxEntityException{
		try {
			
			businessUnit = DLQCommonMethods.getActualValue(entityData,"BU");
			type = DLQCommonMethods.getActualValue(entityData,"Entity");
			fieldMap = new LinkedHashMap<String, Field>();
			for(int i=0;i<defData.length;i++){
				String businessUnit = defData[i][4];
				String type = defData[i][0];
				Field field;
				if(this.businessUnit.equals(businessUnit) && this.type.equals(type)){
					String fieldName = defData[i][1];
					String legitimateValue= defData[i][2];
					String mandatoryFlag= defData[i][3];
					boolean isMandatory = "y".equals(mandatoryFlag.toLowerCase())?true:false;
					String value = DLQCommonMethods.getActualValue(entityData,fieldName);
					field = new Field(fieldName, value, isMandatory, legitimateValue, new Quality(isMandatory , legitimateValue));
					fieldMap.put(fieldName, field);
				}
			}
		} catch (DataLyticxQualityEngineException e) {
			e.printStackTrace();
			throw new DataLyticxEntityException("DataLyticxQualityEngineException in DataLyticxEntity"+e.getMessage());
		}
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBusinessUnit() {
		return businessUnit;
	}

	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
	}

	public Map<String, Field> getFieldMap() {
		return fieldMap;
	}

	public void setFieldMap(Map<String, Field> fieldMap) {
		this.fieldMap = fieldMap;
	}
	
	public String fieldName(String fieldName){
		Field field = fieldMap.get(fieldName);
		if(field != null){
			return field.getValue();			
		}
		else{
			System.out.print("Field object is null at DataLyticxEntity.field().");
			return "";
		}
	}
	
	public Field field(String fieldName){
		Field field = fieldMap.get(fieldName);
		return field;
	}
	
	public void checkQuality(){
		System.out.println("Inside Check Quality of entity");
		DataLyticxQualityEngine.CheckQuality(this);
	}
}