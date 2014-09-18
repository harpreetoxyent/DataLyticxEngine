package com.oxyent.component.datalyticx.ui;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.zkoss.chart.model.DefaultPieModel;
import org.zkoss.chart.model.PieModel;

import com.oxyent.component.datalyticxComponent.DataLyticxComponent;
import com.oxyent.component.datalyticxComponent.constants.DLQCommonMethods;
import com.oxyent.datalyticx.engine.DataLyticxQualityEngine;
import com.oxyent.datalyticx.engine.DataLyticxQualityEngineException;
import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.core.commonData.IHICData;

public class DataQualityPieChartModel {

	private PieModel model;
	private Map<String, Double> buPercentageMap;
	private static List<DataQuality> dataQualityList;
	private static List<DataQuality> dataQualityPerEntityList;

	public DataQualityPieChartModel() throws DataLyticxQualityEngineException
	{
		model = new DefaultPieModel();
		buPercentageMap = new LinkedHashMap<String, Double>();
		dataQualityList = new LinkedList<DataQuality>();
		dataQualityPerEntityList = new LinkedList<DataQuality>();
		try {
			List listValue = DLQCommonMethods.executeQueryReturnList("get Business_Unit.Department from datalyticx.Business_Unit","indexDl.zul");

			for(int i=0;i<listValue.size();i++){ 

				String buDept = (String) listValue.get(i);
				model.setValue((Comparable<?>) buDept, new Double(100/listValue.size())); 

				String qualityQuery = "get BU_Entity.Entity,BU_Entity.BU_EntityId,Quality.Quality,Quality.Percentage " +
				"from datalyticx.Business_Unit, datalyticx.Entities, datalyticx.BU_Entity, datalyticx.Quality " +
				"conditions Business_Unit.Department:=["+buDept+"] and Business_Unit.Department:=BU_Entity.BU" +
				"  and BU_Entity.Entity:=Entities.Entity and BU_Entity.BU_EntityId:=Quality.BU_EntityId";
				String[][] allValues = DLQCommonMethods.executeQueryReturnArray(qualityQuery,"indexDl.zul");

				if(allValues!= null && allValues.length > 0)
				{
					double quality = 0;
					Map<String,Double> qualityPercentMap = new HashMap<String, Double>();
					Map<String,Integer> count = new HashMap<String, Integer>();
					for(int j=0;j<allValues.length;j++)
					{
						// Based On the Sql Query
						String entityName = allValues[j][0];
						String buEntityId = allValues[j][1];
						String qualityName = allValues[j][2];
						String percentage = allValues[j][3];

						double qualityVal = Double.parseDouble(percentage);
						double qualityPercent =0;
						if(qualityPercentMap.get(qualityName) != null){
							qualityPercent = (Double)qualityPercentMap.get(qualityName) + qualityVal;
							count.put(qualityName, ((Integer)count.get(qualityName))+1);
						}else{
							qualityPercent = qualityVal;
							count.put(qualityName, 1); 
						}
						qualityPercentMap.put(qualityName, qualityPercent);
						//Will give for all bu quality
						quality = quality + qualityVal;
						dataQualityPerEntityList.add(new DataQuality((String)buDept, qualityName, qualityVal, entityName, buEntityId));
					}						
					Iterator<String> it = qualityPercentMap.keySet().iterator();
					while(it.hasNext()){
						String key_QualityName = (String) it.next();
						double val_qualitySum = (Double) qualityPercentMap.get(key_QualityName);
						val_qualitySum = val_qualitySum / (Integer)count.get(key_QualityName);
						dataQualityList.add(new DataQuality((String)buDept,key_QualityName, val_qualitySum));
					}
					buPercentageMap.put(buDept, quality/allValues.length);//Override
				}else{
					buPercentageMap.put(buDept, 100.0);//Override
				}
			}
		} catch (DataLyticxQualityEngineException e) {
			throw new DataLyticxQualityEngineException(e.getMessage());
		}		
	}

	public  PieModel getPieModel() {
		return model;
	}

	public  Map<String,Double> getBuPercentageMap() {
		return buPercentageMap;
	}

	public static List<DataQuality> getBusinessQuality(String bu) {
		List<DataQuality> result = new LinkedList<DataQuality>();
		for (DataQuality dataQuality : dataQualityList) {
			if (bu.equals(dataQuality.getBusinessUnit())) {
				result.add(dataQuality);
			}
		}
		return result;
	}

	public static List<DataQuality> getQualityPerEntity(String bu,String qualityName) {
		List<DataQuality> result = new LinkedList<DataQuality>();
		for (DataQuality dataQuality : dataQualityPerEntityList) {
			if (bu.equals(dataQuality.getBusinessUnit()) && qualityName.equals(dataQuality.getQuality())) {
				result.add(dataQuality);
			}
		}
		return result;
	}
}
