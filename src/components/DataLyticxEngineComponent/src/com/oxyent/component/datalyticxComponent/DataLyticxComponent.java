package com.oxyent.component.datalyticxComponent;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;

import com.oxyent.component.datalyticxComponent.constants.DLQCommonMethods;
import com.oxyent.datalyticx.Accuracy;
import com.oxyent.datalyticx.Completeness;
import com.oxyent.datalyticx.DataLyticxEntity;
import com.oxyent.datalyticx.Field;
import com.oxyent.datalyticx.engine.DataLyticxQualityEngine;
import com.oxyent.datalyticx.engine.DataLyticxQualityEngineException;
import com.oxymedical.component.baseComponent.IComponent;
import com.oxymedical.component.baseComponent.annotations.EventSubscriber;
import com.oxymedical.component.baseComponent.exception.ComponentException;
import com.oxymedical.component.db.DBComponent;
import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.importcomponent.ImportComponent;
import com.oxymedical.component.render.resource_datalyticx.IncorrectData;
import com.oxymedical.component.render.resource_datalyticx.Quality;
import com.oxymedical.component.rulesComponent.IRuleClass;
import com.oxymedical.component.rulesComponent.RuleComponent;
import com.oxymedical.core.commonData.HICData;
import com.oxymedical.core.commonData.IData;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.maintenanceData.IMaintenanceData;
import com.oxymedical.framework.objectbroker.annotations.InjectNew;

public class DataLyticxComponent implements IDataLyticxEngineComponent , IComponent
{
	private DataLyticxQualityEngine dataLyticxEngine =null;
	@InjectNew
	static public DBComponent dbComponent;

	@InjectNew
	static public RuleComponent ruleComponent;
	
	@InjectNew
	static public ImportComponent importComponent;
	
	private IHICData hicData;
	
	@Override
	public void start(Hashtable<String, Document> configData) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void run() throws ComponentException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void stop() throws ComponentException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void destroy() throws ComponentException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public IHICData getHicData() {
		return hicData;
	}
	@Override
	public void setHicData(IHICData hicData) {
		this.hicData = hicData;
	}
	@Override
	public void maintenance(IMaintenanceData maintenanceData) {
		// TODO Auto-generated method stub
		
	}

	@EventSubscriber(topic = "executeRuleAgainstInputCSV")
	public IHICData executeRuleAgainstInputCSV(IHICData hicData) throws ComponentException
	{
		try
		{
			IData data = hicData.getData();
			String csvFileNameToImport = (String) data.getFormPattern().getFormValues().get("csvFileNameToImport");
			String tableName = (String) data.getFormPattern().getFormValues().get("tableName");
			Object[] entityDataArray = importComponent.importCSVAllRowAndReturnObject(csvFileNameToImport, ",", tableName);
			
			initializeFieldsForValidation();
			ruleComponent.buildReteHIC((HICData)hicData);
			
			for(int loop=0; loop <entityDataArray.length; loop++){
				intializeRule(entityDataArray[loop], hicData);
				System.out.println("Rules have been executed "+loop+" times.");
			}
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
			throw new ComponentException(exp.getMessage());			
		}
		return hicData;
	}
	
	public void intializeRule(Object entityData, IHICData hicData) throws ComponentException
	{
		System.out.println("----Inside intializeRules----");
		try
		{	
			//Populate entity object with data
			DataLyticxEntity entity = new DataLyticxEntity(entityData,dataLyticxEngine.getDefinitionData());
			Object[] facts = {entity};
			List<IRuleClass> ruleClassList = new ArrayList<IRuleClass>();
			ruleClassList = ruleComponent.executeRules(facts);
			
			storeQualityData(entity);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new ComponentException("Exception while inilializing Rules.");
		}
	}
	
	private void storeQualityData(DataLyticxEntity entity) throws DataLyticxQualityEngineException {
		Map<String, Field> fieldMap = entity.getFieldMap();
		Iterator<String> itFieldMap = fieldMap.keySet().iterator();
		int accuracyCount = 0;
		int completeCount = 0;
		int completeFieldCount = 0;
		double qualityPercent = 0.0;
		int accuracyFieldCount = 0;
		while(itFieldMap.hasNext()){
			String key = itFieldMap.next();
			Field field = fieldMap.get(key);
			com.oxyent.datalyticx.Quality quality = field.getQuality();
			Accuracy accuracy = quality.getAccuracy();
			if(accuracy != null){
				if(accuracy.isAccurate()){
					accuracyCount++;
				}else{
					storeIncorrectData(entity, field, "Accuracy");					
				}
				accuracyFieldCount++;
			}
			Completeness completeness = quality.getCompleteness();
			if(completeness != null){
				if(completeness.isCompleteness()){
					completeCount++;
				}else{
					storeIncorrectData(entity, field, "Completeness");					
				}
				completeFieldCount++;
			}
		}
		if(accuracyCount !=0){
			System.out.println("accuracyCount >>>> "+accuracyCount);
			System.out.println("accuracyFieldCount >>>> "+accuracyFieldCount);
			System.out.println("qualityPercent >>>> "+qualityPercent);
			qualityPercent = ((double)accuracyCount / (double)accuracyFieldCount) * 100 ;
		}
		saveQualityData(entity, "Accuracy", qualityPercent);
		if(completeCount !=0){
			System.out.println("completeCount >>>> "+completeCount);
			System.out.println("completeFieldCount >>>> "+completeFieldCount);
			System.out.println("qualityPercent >>>> "+qualityPercent);
			qualityPercent = ((double)completeCount / (double)completeFieldCount) * 100 ;
		}
		saveQualityData(entity, "Completeness", qualityPercent);
	}
	
	private static void saveQualityData(DataLyticxEntity entity, String qualityName, double qualityPercent) throws DataLyticxQualityEngineException {
		String BU = entity.getBusinessUnit();
		String entityName = entity.getType();
		String queryStr = "get BU_Entity.BU_EntityId from datalyticx.BU_Entity conditions BU_Entity.BU:=["+ BU+ "] and BU_Entity.Entity:=["+entityName+"]";
		String[][] allValues = DLQCommonMethods.executeQueryReturnArray(queryStr,"indexDl.zul");
		Long buEntityID = Long.parseLong(allValues[0][0]);
		// Insert quality for Given BU and Entity in database
		String qualityQuery = "get Quality.Id , Quality.RecordCount from datalyticx.Quality conditions " +
				"Quality.BU_EntityId:=["+buEntityID+"] and Quality.Quality:=["+qualityName+"]";
		String[][] qualityData = DLQCommonMethods.executeQueryReturnArray(qualityQuery,"indexDl.zul"); 
		Quality quality = new Quality();
		String qualityId = "";
		Integer recordCount = 1;
		if(qualityData != null && qualityData[0] != null){
			qualityId = qualityData[0][0];
			recordCount = Integer.parseInt(qualityData[0][1]);
		}
		if(qualityId != null && !"".equals(qualityId.trim())){				
			quality.setId(Long.parseLong(qualityId));
		}
		quality.setBuEntityId(buEntityID);
		quality.setQuality(qualityName);
		quality.setPercentage(((Double)qualityPercent).toString());//Change this field to Double in db and then change in code accordingly
		try {
			dbComponent.saveObject(quality);
		} catch (DBComponentException e) {
			throw new DataLyticxQualityEngineException("DBComponentException in DataLyticxQualityEngine.saveQualityData :"+e.getMessage());
		}	
	}
	
	private static void storeIncorrectData(DataLyticxEntity entity, Field fieldObj, String qualityName)  throws DataLyticxQualityEngineException {
		String BU = entity.getBusinessUnit();
		String entityType = entity.getType();
		String[][] allValues = DLQCommonMethods.executeQueryReturnArray("get BU_Entity.BU_EntityId from datalyticx.BU_Entity conditions BU_Entity.BU:=["+ BU+ "] and BU_Entity.Entity:=["+entityType+"]","index.zul");
		Long buEntityID = Long.parseLong(allValues[0][0]);
		
		IncorrectData incorrectData = new IncorrectData();
		incorrectData.setBuEntityId(buEntityID);
		incorrectData.setFieldName(fieldObj.getName());
		incorrectData.setIdealValue(fieldObj.getLegitimateValue());
		incorrectData.setQualityName(qualityName);
		incorrectData.setActualValue(fieldObj.getValue());
		try {
			dbComponent.saveObject(incorrectData);
		} catch (DBComponentException e) {
			throw new DataLyticxQualityEngineException("DBComponentException in DataLyticxQualityEngine.storeIncorrectData :"+e.getMessage());
		}
	}
	
	public void initializeFieldsForValidation() throws ComponentException{
		System.out.println("----Inside intializeImport----");
        try 
        {
        	if(dataLyticxEngine == null)
        	{
        		dataLyticxEngine = new DataLyticxQualityEngine(dbComponent);
        	}
			DataLyticxQualityEngine.DefinitionData = dataLyticxEngine.getDefinitionData();
		} 
        catch (Exception e) 
        {
			throw new ComponentException(e.getMessage());
		}
	}
	
	@EventSubscriber(topic = "importInputDataDefinitionDatabase")
	public  void importInputDataDefinitionDatabase(IHICData hicData) throws ComponentException
	{
		try
		{
			initializeFieldsForValidation();
		}
		catch(Exception exp)
		{
			throw new ComponentException(exp.getMessage());			
		}
	}
	
	public static void main(String a[]) throws ComponentException 
	{
		//Create DataLyticx Component 
		DataLyticxComponent dl = new DataLyticxComponent();	
		//First Import the CSV file containing the defininition of mandatory and legitimate values
//		dl.importComponent = new ImportComponent();
//		dl.initializeImport("Entity_Definition.csv","EntityDefinition");
		//Now the definition is built, load one sample record to check data quality
        //dl.ruleComponent = new RuleComponent();
        //dl.intializeRule("Material_Master.csv",null);
	}
}
