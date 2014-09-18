package com.oxyent.component.datalyticx.ui;

import java.util.ArrayList;
import java.util.List;

import com.oxyent.component.datalyticxComponent.DataLyticxComponent;
import com.oxyent.component.datalyticxComponent.constants.DLQCommonMethods;
import com.oxyent.datalyticx.engine.DataLyticxQualityEngine;
import com.oxyent.datalyticx.engine.DataLyticxQualityEngineException;
import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.core.commonData.IHICData;

public class DataLyticxQueries {

    public List getBusinessUnitList() throws DataLyticxQualityEngineException {
    	List buNames = DLQCommonMethods.executeQueryReturnList("get Business_Unit.Department from datalyticx.Business_Unit","indexDl.zul");
        return buNames;
    }
    public List getEntityList() throws DataLyticxQualityEngineException {
    	return DLQCommonMethods.executeQueryReturnList("get Entities.Entity from datalyticx.Entities","indexDl.zul");
    }
    
    public List getBuEntityId() throws DataLyticxQualityEngineException {
    	return DLQCommonMethods.executeQueryReturnList("get BU_Entity.BU_EntityId from datalyticx.BU_Entity","indexDl.zul");
    }
    
    public List getQualityList() throws DataLyticxQualityEngineException {
    	List<String> list = new ArrayList<String>();
    	list.add("Completeness");
    	list.add("Accuracy");
    	list.add("Integrity");
    	list.add("Relevance");
    	//list.add(4, "");
    	return list;
	}
    
    public boolean compareBusinessUnit(String data) throws DataLyticxQualityEngineException{
    	List list = getBusinessUnitList();
    	return compare(data,list);
    }
    public boolean compareEntity(String data) throws DataLyticxQualityEngineException{
    	List list = getEntityList();
    	return compare(data,list);
    }
    public boolean compareQuality(String data) throws DataLyticxQualityEngineException{
    	List list = getQualityList();
    	return compare(data,list);
    }
    
    public boolean compare(String data,List listData){
    	boolean hasData = false;
    	for(int i=0;i<listData.size();i++){ 
    		if(data.equals((String) listData.get(i))){
    			hasData = true;
    		}
    	}
    	return hasData;
    }
}
