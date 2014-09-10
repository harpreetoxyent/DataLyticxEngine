package com.oxyent.datalyticx;

import java.lang.reflect.InvocationTargetException;

import com.oxyent.datalyticx.engine.DataLyticxQualityEngine;
import com.oxymedical.core.commonData.IHICData;

public class DataLyticxEntity {
	
	private Object entityData;
	private Object[] entityArrayData;
	public IHICData hicData = null;
	public String type;
	public String businessUnit;
	public String entity;
	
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
	public void checkQuality(){
		//double completeness = dataLyticxComp.checkForCompleteness(this);
		System.out.println("Inside Check Quality of entity");
		DataLyticxQualityEngine.CheckQuality(this);
	}
}
