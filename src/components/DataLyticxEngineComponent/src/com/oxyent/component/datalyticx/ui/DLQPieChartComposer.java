package com.oxyent.component.datalyticx.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.zkoss.chart.Charts;
import org.zkoss.chart.ChartsClickEvent;
import org.zkoss.chart.ChartsEvent;
import org.zkoss.chart.Point;
import org.zkoss.chart.Series;
import org.zkoss.chart.plotOptions.ColumnPlotOptions;
import org.zkoss.chart.plotOptions.DataLabels;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Window;

import com.oxyent.component.datalyticxComponent.DataLyticxComponent;
import com.oxyent.datalyticx.engine.DataLyticxQualityEngine;
import com.oxyent.datalyticx.engine.DataLyticxQualityEngineException;
import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.renderer.uiBuilder.zk.library.ItemRendererArray;
import com.oxymedical.core.commonData.IData;
import com.oxymedical.core.commonData.IHICData;

public class DLQPieChartComposer extends SelectorComposer<Window> {

/**
	 * 
	 */
	private static final long serialVersionUID = 6349754106283377725L;
@Wire
Charts chart;
@Wire
Listbox listDL;
DataQualityPieChartModel dataQualityChartModel;
HashMap<String, String> pieData;
String businessUnit;
String entity;
String quality;

public void doAfterCompose(Window comp) throws DataLyticxQualityEngineException {
	try {
		super.doAfterCompose(comp);
	} catch (Exception e) {
		throw new DataLyticxQualityEngineException("Exception in Super call of DLQPieChartComposer.doAfterCompose :"+e.getMessage());
	}
	
	DataLabels dataLabels = chart.getPlotOptions().getSeries().getDataLabels();
	dataLabels.setEnabled(true);
	dataLabels.setFormat("{point.name}: {point.y:.1f}%");
	
	chart.getTooltip().setHeaderFormat("<span style=\"font-size:11px\">{series.name}</span><br>");
	chart.getTooltip().setPointFormat("<span style=\"color:{point.color}\">{point.name}" +
		"</span>: <b>{point.y:.2f}%</b> of total<br/>");
	
	initSeries();
}

	private void initSeries() throws DataLyticxQualityEngineException{
		System.out.println("----------------Inside initseries----------");
		
		Series series = chart.getSeries();
		List<Series> drilldowns = new ArrayList<Series>();		
		ColumnPlotOptions plotOptions = new ColumnPlotOptions();
		series.setName("Business Units");
		plotOptions.setColorByPoint(true);
		series.setPlotOptions(plotOptions);
		dataQualityChartModel = new DataQualityPieChartModel();
		Map<String,Double> buPercentageMap = dataQualityChartModel.getBuPercentageMap();
		Iterator<Entry<String, Double>> iterator = buPercentageMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Double> entry = iterator.next();
			String label = entry.getKey();
			Point point = new Point(label, entry.getValue());
			if (entry.getValue() > 1) {
				point.setDrilldown(label);
				List<DataQuality> dataQuality = DataQualityPieChartModel.getBusinessQuality(entry.getKey());
				if (!dataQuality.isEmpty()) {
					Series s = new Series();
					s.setId(label);
					s.setName("Quality");
					for (DataQuality dataQ: dataQuality) {
						Point p = new Point(dataQ.getQuality(), dataQ.getPercentage());
						//String label1 = dataQ.getQuality();
						//Starts
						if(dataQ.getPercentage() > 1){
							p.setDrilldown(label+"_"+dataQ.getQuality());
								List<DataQuality> entityData = DataQualityPieChartModel.getQualityPerEntity((String)entry.getKey(),dataQ.getQuality());
								setNextSeries(entityData, drilldowns, p, dataQ.getQuality(), "Entity",label);							
							}
						//End
						s.addPoint(p);
						}
					drilldowns.add(s);
				}	
			}
			series.addPoint(point);
		}
		chart.getDrilldown().setSeries(drilldowns);	
	}
	
	public void setNextSeries(List<DataQuality> dataList, List<Series> drilldowns, Point p, String label, String name, String topLabel){
		if (!dataList.isEmpty()) {
			Series s = new Series();
			s.setId(topLabel+"_"+label);
			s.setName(name);
			for(DataQuality dataQ: dataList){
				Point p1 = new Point(dataQ.getEntity(),dataQ.getPercentage());
				s.addPoint(p1);
			}
			drilldowns.add(s);
		}							
	}
	
	@Listen("onClick = #chart")
	public void hideListData(ChartsClickEvent event) {
	    listDL.setVisible(false);
	}
	@Listen("onPlotClick = #chart")
	public void displayListData(ChartsEvent event) throws DataLyticxQualityEngineException {
		try{
			String data = (String)event.getCategory();
			DataLyticxQueries dataLyticxData = new DataLyticxQueries();
			if(dataLyticxData.compareBusinessUnit(data)){
				businessUnit = data;
			}else if(dataLyticxData.compareQuality(data)){
				quality = data;
			}else if(dataLyticxData.compareEntity(data)){
				entity = data;
				String[][] allValues = executeQueryReturnArray("get BU_Entity.BU , BU_Entity.Entity , Incorrect_Data.QualityName , Incorrect_Data.FieldName," +
						" Incorrect_Data.IdealValue, Incorrect_Data.ActualValue from datalyticx.Incorrect_Data," +
						" datalyticx.BU_Entity conditions Incorrect_Data.BU_EntityId=BU_Entity.BU_EntityId" +
						" and BU_Entity.BU:=["+businessUnit+"] and BU_Entity.Entity:=["+entity+"]" +
						" and Incorrect_Data.QualityName:=["+quality+"]" );
				ListModel myModel = null;
				if(allValues!=null)
				{
					myModel = new ListModelList(allValues);
					listDL.setModel(myModel);
					listDL.setItemRenderer(new ItemRendererArray());
					listDL.setVisible(true);
					Clients.scrollIntoView(listDL);
				}
			} 
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	 
	 public String[][] executeQueryReturnArray(String queryStr) throws DataLyticxQualityEngineException{
		IHICData requestData = null;
		IHICData outputHICData = null;
		List listValue = null;
		String[][] allValues;
		try {
			requestData = DataLyticxQualityEngine.buildDataLyticxHICData("pie-drilldown.zul", queryStr);
			outputHICData = DataLyticxComponent.dbComponent.getListData(requestData);
			if (outputHICData != null && outputHICData.getData() != null)
			{
				listValue = outputHICData.getData().getQueryData().getListData();
			}
			allValues = outputHICData.getData().getQueryData().iterateListData(listValue);
		} catch (DBComponentException e) {
			throw new DataLyticxQualityEngineException("DBComponentException in DataQualityPieChartModel : "+e.getMessage());
		}
		return allValues;
	}
}
