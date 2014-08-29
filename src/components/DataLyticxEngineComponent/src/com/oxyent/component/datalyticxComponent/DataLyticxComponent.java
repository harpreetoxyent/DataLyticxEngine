package com.oxyent.component.datalyticxComponent;


import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import org.dom4j.Document;

import com.oxyent.component.datalyticxComponent.constants.DataLyticxConstants;
import com.oxyent.datalyticx.DataLyticxEntity;
import com.oxyent.datalyticx.engine.DataLyticxQualityEngine;
import com.oxymedical.component.baseComponent.IComponent;
import com.oxymedical.component.baseComponent.annotations.EventSubscriber;
import com.oxymedical.component.baseComponent.exception.ComponentException;
import com.oxymedical.component.db.DBComponent;
import com.oxymedical.component.importcomponent.ImportComponent;
//import com.oxymedical.component.render.resource_datalyticx.MaterialMaster;
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
	public void checkQuality(){
		//double completeness = checkForCompleteness(this);
	}
	@Override
	public double checkForCompleteness(DataLyticxEntity entity) {
		double completeness = 0;
				
		return completeness;
	}
	@Override
	public double checkForValidation(DataLyticxEntity entity) {
		
		return 0;
	}
	
	@Override
	public double checkForAccuracy(DataLyticxEntity entity) {
		
		return 0;
	}	
	
	/*public void addMaterialMasterInEntity(DataLyticxEntity entity)
	{
		
		//Create Material Master object with default values
		MaterialMaster materialMasterRecord = new MaterialMaster();
		materialMasterRecord.setMatnr("NIED");
		materialMasterRecord.setMaktx("EA");
		materialMasterRecord.setPrdha("ZA");
		materialMasterRecord.setMstae("01.01.2012");
		materialMasterRecord.setMstde("DIEN");
		materialMasterRecord.setNtgew("EN");
		materialMasterRecord.setSpras("");
		materialMasterRecord.setVtweg("ZA - Full Production");
		materialMasterRecord.setMstav("1");
		materialMasterRecord.setStawn("0");
		materialMasterRecord.setEkgrp("ZA");
		materialMasterRecord.setMmsta("01.01.2012");
		materialMasterRecord.setMmstd("ZA");
		materialMasterRecord.setDispo("1");
		materialMasterRecord.setBstma("1");
		materialMasterRecord.setDisls("E");
		materialMasterRecord.setBeskz("2000");
		materialMasterRecord.setLgpro("0");
		materialMasterRecord.setFhori("40");
		//Set MM object into entity
		entity.setEntityData(materialMasterRecord);	
	}*/

	@EventSubscriber(topic = "executeRuleAgainstInputCSV")
	public IHICData executeRuleAgainstInputCSV(IHICData hicData) throws ComponentException
	{
		try
		{
			IData data = hicData.getData();
			ruleComponent.buildReteHIC((HICData)hicData);
			String csvFileNameToImport = (String) data.getFormPattern().
											getFormValues().get("csvFileNameToImport");
			
			intializeRule(csvFileNameToImport, hicData);
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
			throw new ComponentException(exp.getMessage());			
		}
		return hicData;
	}

	public void intializeRule(String csvFileNameToImport, IHICData hicData) throws ComponentException
	{
		System.out.println("----Inside intializeRules----");
		try
		{
			//Check an incoming fact against our definitions of data quality
			//Create an entity. This can represent MM, Plant, WC  or BOM
			DataLyticxEntity entity = new DataLyticxEntity();
			//For now add MM as dummy data to validate
			entity.setEntityData(importComponent.importCSVOneRowAndReturnObject(csvFileNameToImport, ",", "MaterialMaster"));
			//addMaterialMasterInEntity(entity);
			Object[] facts = {entity};
			entity.setType(DataLyticxConstants.MATERIAL_MASTER);
			List<IRuleClass> ruleClassList = new ArrayList<IRuleClass>();
			//Check if any rule matches for MM
			ruleClassList = ruleComponent.executeRules(facts);
			hicData.getData().getFormPattern().getFormValues().put("completeness", ""+DataLyticxQualityEngine.completeness);
			hicData.getData().getFormPattern().getFormValues().put("accuracy", ""+DataLyticxQualityEngine.accuracy);
			System.out.println("Rules have been executed and consequence invoked");
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new ComponentException("Exception while inilializing Rules.");
		}
	}
	/*
	public void intializeRule(String csvFileNameToImport) throws ComponentException
	{
		System.out.println("----Inside intializeRules----");
		try
		{
			//Build dummy HIC Data to query incoming fact against definition in
			//database
			HICData hicData = new HICData();
			hicData.setData(new Data());
			hicData = ruleComponent.buildReteHIC(hicData);
			//Check an incoming fact against our definitions of data quality
			//Create an entity. This can represent MM, Plant, WC  or BOM
			DataLyticxEntity entity = new DataLyticxEntity();
			//For now add MM as dummy data to validate
			entity.setEntityData(importComponent.importCSVOneRowAndReturnObject("Material_Master.csv", ",", "MaterialMaster"));
			//addMaterialMasterInEntity(entity);
			Object[] facts = {entity};
			entity.setType(DataLyticxConstants.MATERIAL_MASTER);
			List<IRuleClass> ruleClassList = new ArrayList<IRuleClass>();
			//Check if any rule matches for MM
			ruleClassList = ruleComponent.executeRules(facts);
			System.out.println("Rules have been executed and consequence invoked");
			//ruleComponent.executeRuleHICData(hicData);
		}catch (Exception e) {
			e.printStackTrace();
			//throw new ImportComponentException("Exception while inilializing Rules.");
		}
	}*/
	
	
	public void initializeImport() throws ComponentException{
		System.out.println("----Inside intializeImport----");
        try 
        {
        	if(dataLyticxEngine == null)
        	{
        		dataLyticxEngine = new DataLyticxQualityEngine(dbComponent);
        	}
			DataLyticxQualityEngine.MaterialMasterMandatoryFields = dataLyticxEngine.getAllMandatoryFieldsForMM();
		} 
        catch (Exception e) 
        {
			e.printStackTrace();
			throw new ComponentException(e.getMessage());
		}
	}
	
	@EventSubscriber(topic = "importInputDataDefinitionDatabase")
	public synchronized void importInputDataDefinitionDatabase(IHICData hicData) throws ComponentException
	{
		try
		{
			initializeImport();
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
			throw new ComponentException(exp.getMessage());			
		}
	}
	
	@EventSubscriber(topic = "runRulesOnInputCSVData")
	public synchronized void runRulesOnInputCSVData(IHICData hicData) throws ComponentException
	{
		
	}
	
	public static void main(String a[]) throws ComponentException 
	{
		//Create DataLyticx Component 
		DataLyticxComponent dl = new DataLyticxComponent();	
		//First Import the CSV file containing the defininition of mandatory and legitimate values
//		dl.importComponent = new ImportComponent();
//		dl.initializeImport("Material_Master_Definition.csv","MaterialMasterDefinition");
		//Now the definition is built, load one sample record to check data quality
        dl.ruleComponent = new RuleComponent();
        dl.intializeRule("Material_Master.csv",null);
	}
}
