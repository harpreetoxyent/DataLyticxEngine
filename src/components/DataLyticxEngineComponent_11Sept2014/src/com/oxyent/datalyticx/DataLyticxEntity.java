package com.oxyent.datalyticx;

import com.oxyent.component.datalyticxComponent.constants.DLQCommonMethods;
import com.oxyent.datalyticx.engine.DataLyticxQualityEngine;
import com.oxymedical.core.commonData.IHICData;

public class DataLyticxEntity {
	
	private Object entityData;
	private Object[] entityArrayData;
	public IHICData hicData = null;
	public String type;
	public String businessUnit;
	public String entity;
	public String fieldName;
	public DLQCommonMethods dlqCommonMethods = new DLQCommonMethods();
	
	public DLQCommonMethods getDlqCommonMethods() {
		return dlqCommonMethods;
	}
	public void setDlqCommonMethods(DLQCommonMethods dlqCommonMethods) {
		this.dlqCommonMethods = dlqCommonMethods;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	//public DataLyticxEngineComponent dataLyticxComp;
	public Object getEntityData() {
		return entityData;
	}
	public void setEntityData(Object entityData) {
		this.entityData = entityData;
	}
	public Object[] getEntityArrayData() {
		return entityArrayData;
	}
	public void setEntityArrayData(Object[] entityArrayData) {
		this.entityArrayData = entityArrayData;
	}
	public IHICData getHicData() {
		return hicData;
	}
	public void setHicData(IHICData hicData) {
		this.hicData = hicData;
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
	public String getEntity() {
		return entity;
	}
	public void setEntity(String entity) {
		this.entity = entity;
	}
	
	// New Changes Starts
	public String isBusinessUnit(String bu) {
		businessUnit = "";
		for(int loop=0; loop <entityArrayData.length; loop++){
			String actualDataBU = dlqCommonMethods.getActualValue(entityArrayData[loop],"BU");
			if(bu != null && bu.equals(actualDataBU)){
				businessUnit = bu;
				break;
			}
		}
		return businessUnit;
	}
	public String isEntity(String entityType) {
		entity = "";
		for(int loop=0; loop <entityArrayData.length; loop++){
			String actualDataBU = dlqCommonMethods.getActualValue(entityArrayData[loop],"BU");
			String actualDataEntity = dlqCommonMethods.getActualValue(entityArrayData[loop],"Entity");
			if(businessUnit != null && businessUnit.equals(actualDataBU)){
				if(entityType != null && entityType.equals(actualDataEntity))
					entity = entityType;
				break;
			}
		}
		return entity;
	}
	public String isField(String fName) {
		fieldName = "";
		for(int loop=0; loop <entityArrayData.length; loop++){
			String actualDataBU = dlqCommonMethods.getActualValue(entityArrayData[loop],"BU");
			String actualDataEntity = dlqCommonMethods.getActualValue(entityArrayData[loop],"Entity");
			
			if(businessUnit != null && businessUnit.equals(actualDataBU)){
				if(entity != null && entity.equals(actualDataEntity))
				{
					if(fName != null && fName.equals(actualDataEntity))
						entity = fName;					
				}
				break;
			}
		}
		return fieldName;
	}
	//New Changes End
	public void checkQuality(){
		//double completeness = dataLyticxComp.checkForCompleteness(this);
		System.out.println("Inside Check Quality of entity");
		DataLyticxQualityEngine.CheckQuality(this);
	}
}
