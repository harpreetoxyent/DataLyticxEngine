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
import org.zkoss.chart.plotOptions.PiePlotOptions;
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
import com.oxymedical.core.commonData.IHICData;

public class DLQPieChartComposer extends SelectorComposer<Window> {

/**
	 * 
	 */
	private static final long serialVersionUID = 6349754106283377725L;
@Wire
Charts chart;
@Wire
Charts chart1;
@Wire
Charts chart2;
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
	
	PiePlotOptions plotOptions = chart.getPlotOptions().getPie();
    plotOptions.setCursor("pointer");
}

	private void initSeries() throws DataLyticxQualityEngineException{
		System.out.println("----------------Inside initseries----------");
		
		Series series = chart.getSeries();
		//List<Series> drilldowns = new ArrayList<Series>();		
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
			point.setColor(getColor(entry.getValue()));
			series.addPoint(point);
		}
		//chart.getDrilldown().setSeries(drilldowns);	
	}
	
	private String getColor(Double value) {
		String color = "";
		if(value == 100){
			color = "#008000";//Green
		}else if(value >=80 && value<100){
			color = "#FFFF00";//Yellow
		}else{
			color = "#FF0000";//Red			
		}
		return color;
	}

	@Listen("onPlotClick = #chart")
	public void display2ndChart(ChartsEvent event) throws DataLyticxQualityEngineException {
		try{
			String data = (String)event.getCategory();
			DataLyticxQueries dataLyticxData = new DataLyticxQueries();
			if(dataLyticxData.compareBusinessUnit(data)){
				businessUnit = data;
				Point point = event.getPoint();
				String businessUnitLabel = point.getName();
				double entryPoint = (Double) point.getY();
				if (entryPoint > 1) {
					List<DataQuality> dataQuality = DataQualityPieChartModel.getBusinessQuality(businessUnitLabel);
					if (!dataQuality.isEmpty()) {
						chart1.getPlotData().getSeries().remove();
						Series series = chart1.getSeries();
						ColumnPlotOptions plotOptions = new ColumnPlotOptions();						
						series.setName("Quality");
						plotOptions.setColorByPoint(true);
						series.setPlotOptions(plotOptions);
						series.setId(businessUnitLabel);
						for (DataQuality dataQ: dataQuality) {
							Point p = new Point(dataQ.getQuality(), dataQ.getPercentage());
							String label = dataQ.getQuality();
							p.setColor(getColor(dataQ.getPercentage()));
							series.addPoint(p);
						}
						chart1.setVisible(true);
					}	
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Listen("onPlotClick = #chart1")
	public void display3rdChart(ChartsEvent event) throws DataLyticxQualityEngineException {
		try{
			String data = (String)event.getCategory();
			DataLyticxQueries dataLyticxData = new DataLyticxQueries();
			if(dataLyticxData.compareQuality(data)){
				quality = data;
				Point point = event.getPoint();
				String superLabel = point.getName();
				double entryPoint = (Double) point.getY();
				if (entryPoint > 1) {
					List<DataQuality> entityData = DataQualityPieChartModel.getQualityPerEntity(businessUnit,(String)superLabel);
					if (!entityData.isEmpty()) {
						chart2.getPlotData().getSeries().remove();
						Series series = chart2.getSeries();	
						ColumnPlotOptions plotOptions = new ColumnPlotOptions();						
						series.setName("Entity");
						plotOptions.setColorByPoint(true);
						series.setPlotOptions(plotOptions);
						series.setId(businessUnit+"_"+superLabel);	
						for(DataQuality dataQ: entityData){
							Point p = new Point(dataQ.getEntity(),dataQ.getPercentage());
							p.setColor(getColor(dataQ.getPercentage()));
							series.addPoint(p);
						}
						chart2.setVisible(true);
					}	
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Listen("onClick = #chart")
	public void hideListData(ChartsClickEvent event) {
	    listDL.setVisible(false);
	}
	@Listen("onPlotClick = #chart2")
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
