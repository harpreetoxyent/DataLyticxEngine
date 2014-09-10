package com.oxyent.component.datalyticx.ui;

import java.util.ArrayList;
import java.util.List;

import com.oxyent.component.datalyticxComponent.DataLyticxComponent;
import com.oxyent.datalyticx.engine.DataLyticxQualityEngine;
import com.oxyent.datalyticx.engine.DataLyticxQualityEngineException;
import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.core.commonData.IHICData;

public class DataLyticxQueries {

    public List getBusinessUnitList() throws DataLyticxQualityEngineException {
    	List buNames = executeQueryReturnList("get Business_Unit.Department from datalyticx.Business_Unit");
        return buNames;
    }
    public List getEntityList() throws DataLyticxQualityEngineException {
    	return executeQueryReturnList("get Entities.Entity from datalyticx.Entities");
    }
    
    public List getBuEntityId() throws DataLyticxQualityEngineException {
    	return executeQueryReturnList("get BU_Entity.BU_EntityId from datalyticx.BU_Entity");
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
    
    public List executeQueryReturnList(String queryStr) throws DataLyticxQualityEngineException{
		IHICData requestData = null;
		IHICData outputHICData = null;
		List listValue = null;
		try {
			requestData = DataLyticxQualityEngine.buildDataLyticxHICData("indexDL.zul", queryStr);
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
