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
import com.oxymedical.component.render.resource_datalyticx.ActualData;
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
			String csvFileNameToImport = (String) data.getFormPattern().
					getFormValues().get("csvFileNameToImport");

			
			initializeFieldsForValidation();

			ruleComponent.buildReteHIC((HICData)hicData);
			
			intializeRule(csvFileNameToImport, hicData);
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
			throw new ComponentException(exp.getMessage());			
		}
		return hicData;
	}

	public void intializeRuleBackUp(String csvFileNameToImport, IHICData hicData) throws ComponentException
	{
		System.out.println("----Inside intializeRules----");
		try
		{
			//Check an incoming fact against our definitions of data quality
			//Create an entity. This can represent MM, Plant, WC  or BOM
			DataLyticxEntity entity = new DataLyticxEntity();
			//For now add MM as dummy data to validate
			Object entityData = importComponent.importCSVOneRowAndReturnObject(csvFileNameToImport, ",", "ActualData");
			System.out.println("----Set Data inside enity from DB----entityData="+entityData);			
			entity.setEntityData(entityData);
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
	
	public void intializeRule(String csvFileNameToImport, IHICData hicData) throws ComponentException
	{
		System.out.println("----Inside intializeRules----");
		try
		{
			//Check an incoming fact against our definitions of data quality
			//Create an entity. This can represent MM, Plant, WC  or BOM
			DataLyticxEntity entity = new DataLyticxEntity();
			//For now add MM as dummy data to validate
			Object[] entityArrayData = importComponent.importCSVAllRowAndReturnObject(csvFileNameToImport, ",", "ActualData");
			System.out.println("----Set Data inside enity from DB----entityArrayData="+entityArrayData);			
			entity.setEntityArrayData(entityArrayData);
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
			e.printStackTrace();
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
//		dl.initializeImport("Entity_Definition.csv","EntityDefinition");
		//Now the definition is built, load one sample record to check data quality
        //dl.ruleComponent = new RuleComponent();
        //dl.intializeRule("Material_Master.csv",null);
	}
}
