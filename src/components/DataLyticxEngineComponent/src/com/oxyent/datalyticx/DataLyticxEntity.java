package com.oxyent.datalyticx;

import java.lang.reflect.InvocationTargetException;

import com.oxyent.datalyticx.engine.DataLyticxQualityEngine;
import com.oxymedical.core.commonData.IHICData;

public class DataLyticxEntity {
	
	private Object entityData;
	public IHICData hicData = null;
	public String type;
	//public DataLyticxEngineComponent dataLyticxComp;
	public Object getEntityData() {
		return entityData;
	}
	public void setEntityData(Object entityData) {
		this.entityData = entityData;
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
	public void checkQuality(){
		//double completeness = dataLyticxComp.checkForCompleteness(this);
		System.out.println("Inside Check Quality of entity");
		DataLyticxQualityEngine.CheckQualityMaterialMaster(this);
	}
}
