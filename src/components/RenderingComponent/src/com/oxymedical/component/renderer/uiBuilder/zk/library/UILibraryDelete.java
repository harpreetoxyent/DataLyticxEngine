package com.oxymedical.component.renderer.uiBuilder.zk.library;

import java.io.File;
import java.util.Random;
import java.io.FileOutputStream;
import java.awt.image.BufferedImage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Date;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.zkoss.zk.ui.*;
import org.zkoss.zk.ui.event.*;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.*;
import org.zkoss.zul.event.PagingEvent;
import org.zkoss.image.AImage;
import org.zkoss.zul.ext.Paginal;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.impl.XulElement;
import org.zkoss.zul.impl.InputElement;
import org.zkoss.zk.device.Devices;
import com.sun.org.apache.xerces.internal.impl.dv.util.*;
import org.zkoss.zul.SimpleXYModel;
import org.zkoss.zul.XYModel;
import org.dom4j.Document;


import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.zkoss.zul.Div;
import org.zkoss.zul.Iframe;

import com.oxymedical.core.userdata.IRights;
import com.oxymedical.component.renderer.application.router.*;
import com.oxymedical.core.xmlutil.XmlReader;
import com.oxymedical.core.commonData.IFormPattern;
import com.oxymedical.component.renderer.uiBuilder.zk.library.GridRowCustomRenderer;
import com.oxymedical.core.stringutil.StringUtil;
import com.oxymedical.component.renderer.uiBuilder.zk.library.RowRendererArray;
import com.oxymedical.component.renderer.data.GridDef;
import com.oxymedical.component.renderer.data.ColumnDef;
import com.oxymedical.component.renderer.data.CustomListModel;
import com.oxymedical.component.renderer.uiBuilder.zk.library.CustomComparator;
import com.oxymedical.component.renderer.uiBuilder.zk.library.ItemRendererArray;
import com.oxymedical.component.renderer.uiBuilder.zk.library.UiLibraryUtil;
import com.oxymedical.component.renderer.uiBuilder.zk.library.SimpleListModelExt;
import com.oxymedical.component.renderer.uiBuilder.zk.library.PagingInfo;
import com.oxymedical.component.renderer.uiBuilder.zk.library.LogOutCommand;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.commonData.IData;
import com.oxymedical.core.commonData.IFormPattern;
import com.oxymedical.core.stringutil.StringUtil;
import com.oxymedical.servlet.HICServlet.HICRouter;
import com.oxymedical.core.renderdata.IDataUnit;
import com.oxymedical.core.renderdata.DataUnit;
import com.oxymedical.core.querydata.*;
import com.oxymedical.component.renderer.RendererComponent;
import com.oxymedical.core.router.IRouter;
import com.oxymedical.core.router.IDataUnitRouter;
import com.oxymedical.core.commonData.ISource;
import com.oxymedical.core.commonData.Source;
import com.oxymedical.component.renderer.constants.ZKConstants;
import com.oxymedical.core.renderdata.ExternalData;
import com.oxymedical.core.userdata.IUserPattern;
import com.oxymedical.core.userdata.UserPattern;
import com.oxymedical.component.renderer.util.RenderingUtility;
import com.oxymedical.component.renderer.data.HibernateQueryResultDataSource;
import com.oxymedical.component.renderer.command.UiLibraryCompositeCommand;
import com.oxymedical.component.renderer.constants.UILibraryConstant;
import com.oxymedical.component.renderer.controller.HICDataController;
import com.oxymedical.core.propertyUtil.PropertyUtil;
import com.oxymedical.core.dateutil.DateUtil;
import java.text.*;
import java.sql.Time;
import java.util.LinkedHashMap;
import com.oxymedical.core.propertyUtil.PropertyUtil;
import java.io.*;

public class UILibraryDelete {

//
////tree variables
//private Hashtable formValues = new Hashtable();
//private LinkedHashMap columnOrder = new LinkedHashMap();
//private Hashtable levelPrimaryId = new Hashtable();
//private Hashtable comboTable = new Hashtable();
//private String comboSelectedValue;
//private int treeNodeCount = 0;
//private int lastTreeLevel = -1;
//private String displayLabelPaging=null;
//
//private String dataPatternId ="";
//private String formPatternId ="";
//private boolean loginStatus = false;
//List textId = new ArrayList();
//UiLibraryUtil uiLibraryUtil=new UiLibraryUtil();
//boolean validRequest = true;
//String webMessage = null;
//// String deleteRecordId=null;
//Object comboObj=null;
//LinkedHashMap updateHash= null;
//boolean validationValue = true;
//String[] queryFields = null; 
//IHICData returnHicData = null;
//private String pagingId;
//private String listId;
//boolean validListRequest = false;
//LogOutCommand logout = null;
//private int totalSize;
//
//private int cancel = 2;
//private int yes = 16;
//
//private Hashtable photoMap = new Hashtable();
//
//String updatedMsg = "Patient Information has not been updated.";
//
//String THEME_COOKIE_KEY = "zktheme";
//
// public CategoryModel populateDataFromExcel(int indexColumn, String graphTitle) 
//{
//	CategoryModel catmodel = new SimpleCategoryModel();
//	String csvFile = "/fenton_boy_50.csv.csv";
//	BufferedReader br = null;
//	String line = "";
//	String cvsSplitBy = ",";
//	try 
//	{
//		br = new BufferedReader(new FileReader(csvFile));
//		int i = 0;
//		while ((line = br.readLine()) != null) 
//		{
//			//skip header
//			if(i==0)
//			{
//				i++;
//				continue;
//			}
//				// use comma as separator
//			String[] country = line.split(cvsSplitBy);
//			catmodel.setValue(graphTitle, ""+i, new Integer((new Double(country[indexColumn])).intValue()));
//			i++;
//		}
//	
//	} catch (FileNotFoundException e) {
//		e.printStackTrace();
//	} catch (IOException e) {
//		e.printStackTrace();
//	} finally {
//		if (br != null) {
//			try {
//				br.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//	return catmodel;	
//}
//
//
// public CategoryModel populateDataFromFentonExcel(String csvFile , int categoryColumn, int weekColumn, double weightColumn, String graphTitle) 
//{
//	CategoryModel catmodel = new SimpleCategoryModel();
//	BufferedReader br = null;
//	String line = "";
//	String cvsSplitBy = ",";
//	try 
//	{
//		br = new BufferedReader(new FileReader(csvFile));
//		int i = 0;
//		while ((line = br.readLine()) != null) 
//		{
//			// use comma as separator
//			String[] fentonData = line.split(cvsSplitBy);
//			catmodel.setValue(""+ (new Integer(fentonData[categoryColumn]).intValue()), ""+ (new Integer(fentonData[weekColumn]).intValue()), new Double(fentonData[weightColumn]));
//			i++;
//		}
//	
//	} catch (FileNotFoundException e) {
//		e.printStackTrace();
//	} catch (IOException e) {
//		e.printStackTrace();
//	} finally {
//		if (br != null) {
//			try {
//				br.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//	return catmodel;	
//}
//public Object dynamicObjectCreation(Object controlObject,int columnNoToAdd,String controlIDValue, String displayValue, int selectedRowID, String controlType){
//	List obj_child = ((Listbox)controlObject).getChildren();
//	String id = "";
//	for(int j = 1; j< obj_child.toArray().length ; j++){
//		if(selectedRowID == j-1){
//			Object obj = obj_child.toArray()[j];
//			List lst=((Listitem)obj).getChildren();
//			for(int i=1;i<lst.size();i++){
//				if(i==columnNoToAdd){
//					((Listitem)obj).removeChild((Listcell)lst.get(i));
//				}
//			}
//			for(int i=1;i<lst.size();i++){ 
//				if(i==columnNoToAdd-1){
//					Listcell listcell=new Listcell("");
//					var randomnumber=Math.floor(Math.random()*999999);					
//					id = controlIDValue+randomnumber;	
//					listcell.value=	id;		
//					if(controlType == "button"){
//						Button bt = new Button();
//						bt.setId(id);
//						bt.setLabel(displayValue);
//						bt.addEventListener("onClick", new EventListener()
//						{
//							public void onEvent(Event event)
//							{
//								 try
//								{
//								}
//								catch(Exception e)	
//								{
//									System.out.println("Add Event Listener warning on dynamicObjectCreation in render function");
//								}
//							}
//						});	
//						listcell.appendChild(bt);	
//						listcell.setParent((Listitem)obj);
//						((Listitem)obj).setParent(controlObject);
//						return (Object) bt;
//					}
//					if(controlType== "textbox"){
//						Textbox txtbox = new Textbox();
//						txtbox.setWidth("100px");
//						txtbox.setHeight("20px");						
//						txtbox.setId(id);
//						listcell.appendChild(txtbox);	
//						listcell.setParent((Listitem)obj);
//						((Listitem)obj).setParent(controlObject);
//						return (Object) txtbox;
//					}
//				}
//			}
//		}
//	}
//	return (Object) bt;
//}
//
//public List getGridDynamicControlValue(Object controlObject,String objectName){
//	String values = "";
//	ArrayList dynamicContValuesList=new ArrayList();
//	List obj_child = ((Listbox)controlObject).getChildren();
//	String id = "";
//	for(int j = 1; j< obj_child.toArray().length ; j++){
//		Object obj = obj_child.toArray()[j];
//		List lst=((Listitem)obj).getChildren();
//		for(int i=0;i<lst.size();i++){ 
//			Listcell lc=(Listcell)lst.get(i);
//			if(objectName =="txtBox"){
//				if(lc.value.contains(objectName)){
//					List txt=lc.getChildren();
//					Object txtBox  = (Object)txt.get(0);					
//					if(values == ""){
//						values=txtBox.text;
//					}else{
//							values=values+ ","+txtBox.text;							
//					}
//				}else{
//					if(lc.value!= ""){
//						if(values == ""){
//							values=lc.value;
//						}else{
//								values=values+ ","+lc.value;
//						}						
//					}					
//				}
//			}else{
//				if(lc.value!= ""){
//					if(values == ""){
//						values=lc.value;
//					}else{
//							values=values+ ","+lc.value;
//					}						
//				}	
//			}
//		}
//		dynamicContValuesList.add(values);
//		values = "";
//	}
//	return dynamicContValuesList;
//}
//
//public String getDateDiff(String toDateStr, String fromDateStr){
//	DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy"); 
//	Date fromDate = (Date)dateFormat.parse(fromDateStr);
//	Date toDate = (Date)dateFormat.parse(toDateStr);
//	Calendar fromCal = Calendar.getInstance();
//	fromCal.setTime(fromDate);
//	Calendar toCal = Calendar.getInstance();
//	toCal.setTime(toDate);
//	long fromMs = fromCal.getTimeInMillis();
//	long toMs = toCal.getTimeInMillis();
//	long diffMs = toMs - fromMs;
//	long daysDiff = (diffMs / (24 * 60 * 60 * 1000)) + 1;
//	String dateDiff = Long.toString(daysDiff);
//	return dateDiff;
//}
//
//public String convertQueryDataToString(String component, String methodName, String componentName, String pageName){	
//	String queryData= invokeComponent(component,methodName,componentName,pageName);	
//	boolean queryDataCheck = equalsTest(queryData,"true");	
//	String queryDataValues = "";
//	if(queryDataCheck){
//		IData dataUnit = returnHicData.getData();
//		List listValue = null;
//		if(dataUnit.getQueryData().getListData()!=null)
//		{
//			listValue = dataUnit.getQueryData().getListData();
//		}		
//		if(listValue.size() > 0) {		
//			String[][] allValues = dataUnit.getQueryData().iterateListData(listValue);		
//			for(int i = 0; i < allValues.length; i++){
//				for(int j = 0; j < allValues[i].length; j++){
//					if(queryDataValues!= ""){
//						queryDataValues = queryDataValues + ","+ allValues[i][j];
//					}else{
//						queryDataValues = allValues[i][j];
//					}
//				}	
//			}
//		}	
//	}
//	return queryDataValues;
//}
//
//public String getDateString(Object controlId, String dateFormatStr){
//	String dateString = null;
//	if(controlId != null){
//		Date currentDate = ((Datebox)controlId).getValue();
//		if(currentDate != null){
//			dateString = (new SimpleDateFormat(dateFormatStr).format(currentDate));
//		}
//	}
//	return dateString;
//}
//
//public String getDateString(Object controlId)
//{
//	if(controlId!=null)
//	{
//		Date currentDate=((Datebox)controlId).getValue();
//		String dateString = null;
//		if(currentDate !=null)
//		dateString=(new SimpleDateFormat("dd-MM-yyyy").format(currentDate));
//		return dateString;
//	}
//	return null;
//}
//
//public String getQueryDataToString(String queryValues,int id){	
//	String queryValue = "";
//	if(queryValues!= ""){
//		var queryValuesArr = queryValues.split(",");
//		queryValue =(String) queryValuesArr[id];	
//	}	
//	return queryValue;
//}
//
//public  Date convertStringToDate(String dateStr){
//	Date dt=null;
//	if(dateStr!=null){	
//		String date=dateStr +" "+"00:00";
//		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm");
//		dt=dateFormat.parse(date);
//		return dt;
//	}
//	return dt;
//}
//
//
///*public String fileLocationPath()
//{
//	String none="";
//	org.zkoss.util.media.Media media = org.zkoss.zhtml.Fileupload.get();
//	if (media != null) 
//	{
//		File f=new File(media.getName());
//		InputStream inputStream= media.getStreamData();
//		OutputStream out=new FileOutputStream(f);
//		byte[] buf=new byte[1024];
//		int len;
//		while((len=inputStream.read(buf))>0)
//		out.write(buf,0,len);
//		out.close();
//		out.flush();
//		inputStream.close();
//		String filepath = f.getAbsolutePath();
//		f.deleteOnExit();
//		return filepath;
//	}
//	else
//	{
//		System.out.println("media null");
//	}
//	return none;
//}*/
//
//private void showUniqueComboData(IHICData hicData,Object comboObj)
//{
//	
//    
//	List values=null;
//	
//	if(hicData==null)
//	{
//		if(returnHicData ==null)
//		{
//				return;
//		}
//		else
//		{
//				hicData = returnHicData;
//		}
//	}
//	comboObj.setText("");
//	comboObj.getItems().clear();
//	IData dataUnit = hicData.getData();
//	List listValue = null;
//	if(dataUnit.getQueryData().getListData()!=null)
//	{
//		listValue = dataUnit.getQueryData().getListData();
//		Set uniqueValues=new HashSet(listValue);
//		alert(" the set is "+uniqueValues);
//		Iterator it=uniqueValues.iterator();
//		while(it.hasNext())
//		{
//			String unqValues= it.next();
//			values=new ArrayList();
//			values.add(unqValues);
//			alert("the unique values are"+unqValues);
//		}
//	}
//	else
//		return;
//	String[][] allValues = dataUnit.getQueryData().iterateListData(values);	
//	if(allValues==null)
//			return;
//	comboTable.put(comboObj.getId(),listValue);
//	uiLibraryUtil.showComboData(allValues,comboObj);
//}
//
//private void showUniqueComboData(String comboid)
//{
//    Object rootFormValue = self.getRoot();
//	Object elementObj = rootFormValue.getVariable(comboid,true);
//	if(elementObj !=null)
//	{
//		showUniqueComboData(null,elementObj);	
//	}
//	
//}
//
//void message(String value, String titleStr){
//	Messagebox.setTemplate("/templates/OMMessagebox.zul"); 
// 	Messagebox.show(value, titleStr, Messagebox.OK, Messagebox.INFORMATION);
//}
//
//void message(String value){
//	Messagebox.setTemplate("/templates/OMMessagebox.zul"); 
// 	Messagebox.show(value, "SunPharma", Messagebox.OK, Messagebox.INFORMATION);
//}
//void setListId(String list)
//{
//listId = list;
//}
//
//void setDisplayLabelId(String label)
//{
//displayLabelPaging = label;
//}
//
//public String getDisplayLabelId()
//{
//
//	return displayLabelPaging;
//}
//public String getListId()
//{
//
//	return listId;
//}
//
//
//void setPagingId(String paging)
//{
//pagingId = paging;
//}
//
//public String getPagingId()
//{
//	return pagingId;
//}
//
//
////Changes done for implementing paging done by pra on 27May2009
//void setTotalSize()
//{
//	if(returnHicData ==null)
//	{
//		return;
//	}
//	else
//	{			
//		IData dataUnit = returnHicData.getData();
//		if(dataUnit.getQueryData().getListData()!=null)
//		{
//		totalSize=dataUnit.getQueryData().getListData().size();
//		}
//	}
//}
//
//public int getTotalSize()
//{
//	return totalSize;
//}
//
//
//public void updateSession(String id, String value)
//{
//	session.setAttribute(id,value);
//}
//
//public String getSessionData(String rowId)
//{
//	String idValue = session.getAttribute(rowId);
//	// session.setAttribute(rowId,null);
//	return idValue;
//}
//
////file upload 14 Oct 2010
//public String fileLocationPath()
//{
//	String none="";
//	org.zkoss.util.media.Media media = org.zkoss.zhtml.Fileupload.get(true);
//	if (media != null) 
//	{
//		File f=new File(media.getName());
//		InputStream inputStream= media.getStreamData();
//		OutputStream out=new FileOutputStream(f);
//		byte[] buf=new byte[1024];
//		int len;
//		while((len=inputStream.read(buf))>0)
//		out.write(buf,0,len);
//		out.close();
//		inputStream.close();
//		String filepath = f.getAbsolutePath();
//		return filepath;
//	}
//	else
//	{
//		System.out.println("media null");
//	}
//	return none;
//}
//
//public void showData(String[] txtField)
//{
//	Object rootFormValue = self.getRoot();
//	String[][] allValues =null;
//	int j=0;
//	LinkedHashMap displayValues;
//	if(null != returnHicData)
//	{
//		IData dataUnit = returnHicData.getData();
//		List listValue = null;
//		if(dataUnit.getQueryData().getListData()!=null)
//		{
//			listValue = dataUnit.getQueryData().getListData();
//		}
//		else
//			return;
//		allValues = dataUnit.getQueryData().iterateListData(listValue);
//		if(allValues==null)
//			return;
//		uiLibraryUtil.showData(allValues,formValues,rootFormValue,txtField,comboTable);
//		updateSession("updatedMsg",updatedMsg);
//	}
//
//
//}
//
//public void showDataForKeyValue(Object idArray)
//{
//    IHICData localHicData = HICDataController.getHICData(""+session.hashCode());
//	Hashtable formValueTable = localHicData.getData().getFormPattern().getFormValues();
//	uiLibraryUtil.showKeyValueData((String[])idArray, formValueTable, self.getRoot(), comboTable);
//}
//
//public void setFormValuesDataWhileOpening(String fieldName, String fieldValue, String fieldType)
//{
//	if (fieldValue != null)
//	{
//		formValues.put(fieldName, fieldValue);
//		if (fieldType.equals("textbox")) textId.add(fieldName);
//	}
//}
//void setDatapatternId(String dataId)
//{
//	dataPatternId = dataId;
//}
//void setFormPatternId(String formId)
//{
//	formPatternId = formId;
//	validationValue=true;
//}
//
//void move(String formID)
//{
//	formID = formID+".zul";
//	Executions.sendRedirect(formID);
//}
//void printWindow()
//{
//	//System.out.println("inside the print method ");
//	Clients.print();
//}
//public String getComboBoxValue(String comboBox){
//	String comboSelectedVal = null;
//	Component rootFormValue = self.getRoot();
//	Combobox comboObj = (Combobox)rootFormValue.getVariable(comboBox, true);
//	if(comboObj == null){
//		comboObj = getComponentIfInPlaceHolder(comboBox);
//	}
//	if(comboObj != null){
//		Comboitem selectedComboRow = comboObj.getSelectedItem();
//		if(selectedComboRow != null){
//			comboSelectedVal = selectedComboRow.getLabel();
//		}
//	}
//	return comboSelectedVal;
//}
///*public String getComboBoxValue(String comboBox)
//{
//	String comboSelectedVal = null;
//	Object rootFormValue = self.getRoot();
//	Object comboObj = rootFormValue.getVariable(comboBox,true);
//	if(comboObj == null)return null;
//	Comboitem selectedComboRow = comboObj.getSelectedItem();
//	if(selectedComboRow == null)return null;
//	String rowValue = selectedComboRow.getLabel();
//	return rowValue;
//}*/
////Added null check.done by pra on 15 june2009	
//void setDateBoxId()
//{
//    String date="";
//    if(self.value!=null)
//	{
//	 date= (new SimpleDateFormat("yyyy-MM-dd").format(self.value));
//	checkFormValue(self.id,date);
//	}
//	formValues.put(self.id,date);
//}
//public String getDate()
//{
//	Date fullDate = self.value;
//	String date = null;
//	if(fullDate !=null)
//		date =(new SimpleDateFormat("yyyy-MM-dd").format(fullDate));
//	return date;
//}
//
//
//void setRadioSelected(Object radioID)
//	{
//
//		self.setSelectedItem(radioID);
//		checkFormValue(self.id,self.selectedItem.label);
//		formValues.put(self.id,self.selectedItem.value);
// 	}
//void setRadioGroupId()
//	{
//		checkFormValue(self.id,self.selectedItem.label);
//		if(self.selectedItem.value != null && !self.selectedItem.value.equals("")){
//			formValues.put(self.id,self.selectedItem.value);
//		}
//		else{
//			formValues.put(self.id,self.selectedItem.label);
//		}
// 	}
//void setCheckboxId()
//	{
//		checkFormValue(self.id,self.label);
//		String value = String.valueOf(self.isChecked());
//		formValues.put(self.id,value);
//	}
//
//
//void setCheckboxValue()
//{
//		String value = String.valueOf(self.isChecked());
//		formValues.put(self.id,value);
//}
//void setTextboxId()
//{
//		checkFormValue(self.id,self.value);
//		formValues.put(self.id,self.value);
//		textId.add(self.id);
//}
//
//void setListItemId(String listBox)
//{
//	String listSelectedVal = null;
//	Object rootFormValue = self.getRoot();
//	Object listObj = rootFormValue.getVariable(listBox,true);
//	Listitem selectedListRow = listObj.getSelectedItem();
//	if (selectedListRow.getId().indexOf("_") != -1)
//	{
//		int idSeperatorPos = selectedListRow.getId().indexOf("_");
//		listSelectedVal = (String)selectedListRow.getId().substring(0,idSeperatorPos);
//	}
//	else
//	{
//		listSelectedVal = (String)selectedListRow.getId();
//	}
//	listSelectedValue = listSelectedVal;
//	formValues.put(self.id,listSelectedValue);
//}
//
//void refresh(String formID)
//{
//	Executions.getCurrent().sendRedirect(formID+".zul");
//}
//
//void setFocus(String controlID){
//	Component rootFormValue = self.getRoot();
//	Component controlObj = rootFormValue.getFellowIfAny(controlID, true);
//	if(controlObj == null){
//		controlObj = getComponentIfInPlaceHolder(controlID);
//	}
//	if(controlObj != null){
//		controlObj.setFocus(true);
//	}
//}
///*void setFocus(String controlID)
//{
//	Object rootFormValue = self.getRoot();
//	Object controlObj = rootFormValue.getVariable(controlID,true);
//	controlObj.focus();
//	
//}*/
// void update(var formId) {
//    Clients.submitForm(formId);
//  }
//
//private String getComboItemId(String comboBox)
//{
//	String comboSelectedVal = null;
//  	Object rootFormValue = self.getRoot();
//	Object comboObj = rootFormValue.getVariable(comboBox,true);
//   	if(null == comboSelectedValue)
//   	{
//	  	Comboitem selectedComboRow = comboObj.getSelectedItem();
//	  	int idSeperatorPos = selectedComboRow.getId().indexOf("_");
//	 	comboSelectedVal = (String)selectedComboRow.getId().substring(0,idSeperatorPos);
//	   	comboSelectedValue =comboSelectedVal;
//	    formValues.put(self.id,comboSelectedValue);
//	   	return comboSelectedVal;
//	}
//}
//public String getRowData(int index)
//{
//	getRowData(self,index);
//}
////added this method to get selected row based on the index and grid
//public String getRowData(Object gridObject,int index)
//{
//    List cell=null;
//	String output="";
//	Set selectedItems = gridObject.getSelectedItems();
//	if(selectedItems!=null && selectedItems.size()>0)
//	{
//	for(Iterator itr = selectedItems.iterator();itr.hasNext();)
//	{
//		Listitem listNode = (Listitem) itr.next();
//		cell =listNode.getChildren();
//	}
//	for(Iterator itr=cell.iterator(); itr.hasNext();)
//	{
//		Listcell lc=(Listcell)itr.next();
//		if(lc.getColumnIndex()==index)
//		{
//			output=lc.value;
//			//output = getRowItemData(lc);
//		}
//	}
//	return output;
//	}
//	return null;
//}
//
//
//public void setCurrentDate(Object controlId)
//	{
//		Date currentDate=new Date();
//		String dateString = null;
//		if(currentDate !=null)
//		dateString=(new SimpleDateFormat("dd-MM-yyyy").format(currentDate));
//		String strdate=dateString +" "+"00:00";
//		SimpleDateFormat simpleDate=new SimpleDateFormat("yyyy-MM-dd hh:mm");             
//		Date date=null;
//		try 
//		{
//			if(controlId instanceof Datebox)
//			{
//				//date = simpleDate.parse(strdate);
//				((Datebox)controlId).setValue(currentDate);
//			}
//			else if(controlId instanceof Textbox)
//			{
//				controlId.setText(dateString);
//			}
//			else if(controlId instanceof Combobox)
//			{
//				controlId.setText(strdate);
//			}
//		} 
//		catch (ParseException e) 
//		{
//			e.printStackTrace();
//		}
//		
//	}
//
//private String getComboItemId1(String comboBox)
//{
//	String comboSelectedVal = null;
//	Object rootFormValue = self.getRoot();
//	Object comboObj = rootFormValue.getVariable(comboBox,true);
//	if(comboObj ==null)return null;
//	Comboitem selectedComboRow = comboObj.getSelectedItem(); 
//	//Added null check by pra on 25 may 2009
//	if(selectedComboRow!=null)
//	{
//		if (selectedComboRow.getId().indexOf("_") != -1)
//		{
//			int idSeperatorPos = selectedComboRow.getId().indexOf("_");
//			comboSelectedVal = (String)selectedComboRow.getId().substring(0,idSeperatorPos);
//		}
//		else
//		{
//			comboSelectedVal = (String)selectedComboRow.getId();
//		}
//		comboSelectedValue = comboSelectedVal;
//		checkFormValue(self.id,comboSelectedValue);
//		comboSelectedValue=null;
//	}
//	return comboSelectedVal;
//}
//
//String invokeComponent(String componentId ,String method ,String classname, String paramList)
//{
//	String isValidStatus = "true";
//	IDataUnitRouter router = RendererComponent.dataUnitRouter;
//	Object rootFormValue = self.getRoot();
//	/*
//	*Before completing any request it will check that session is valid or not.
//	*
//	*/
//	checkSessionTime(method);
//	
//	/*
//	*Following code is used when session would be time out
//	*/
//	String logOutValue = getSessionData("LogOut");		
//	if(logOutValue!=null)
//	{
//	
//		if(logOutValue=="true")
//		{
//			return "";
//		}
//		return "";
//	}
//	
//    if(method.equalsIgnoreCase("save")||method.equalsIgnoreCase("addUserFromApplication"))
//	{		
//		if(clientSideValidation(formPatternId))
//		{
//		  
//		 	validationValue=true;
//		}
//		else
//		{
//			validationValue=false;
//			isValidStatus = "false";
//		}
//	}
//	if(validationValue)
//	{
//    UiLibraryCompositeCommand command=new UiLibraryCompositeCommand();
//	command.setMethodName(method);
//	command.setRouter(router);
//    command.setClassname(classname);
//	command.setComponentId(componentId);
//    command.setDataPatternId(dataPatternId);
//    command.setFormPatternId(formPatternId);
//    command.setFormValues(formValues);
//	command.setRootFormValue(rootFormValue);
//	command.setParamList(paramList);
//	command.setSession(session);
//	command.setComboSelectedValue(comboSelectedValue);
//	command.setValidListRequest(validListRequest);
//	command.setPagingId(pagingId);
//	command.execute();
//	returnHicData=command.getHICData();
//	if(returnHicData==null)
//	{
//		isValidStatus = "false";
//		return isValidStatus;
//	}
//	if (returnHicData.getData()!=null) 
//    {
//		if ((returnHicData.getData().getStatus()!=null) && ((returnHicData.getData().getStatus().equalsIgnoreCase("error")) || (returnHicData.getData().getStatus().equalsIgnoreCase("false"))) )
//		{
//			isValidStatus = "false";
//			return isValidStatus;
//		}
//	}
//  }
//  else
//  {
// 	  return "false";
//  }
//
//	if(method.equalsIgnoreCase("authenticateUserInLDAP")){
//		if(returnHicData.getLdapData() != null) {
//			return (String)returnHicData.getLdapData().getAttributes().get("UserAuthenticatedInLDAP");
//		}
//	}else if(method.equalsIgnoreCase("searchInLDAP")){
//		if(returnHicData.getLdapData() != null) {
//			org.dom4j.Document resultDoc = returnHicData.getLdapData().getLdapDoc();
//			if(resultDoc != null){
//				return resultDoc.asXML();
//			}else{
//				return null;
//			}
//		}
//	}
//	if(method.equalsIgnoreCase("authenticateUserEx"))
//	{
//		if(returnHicData.getData()!=null && returnHicData.getData().getStatus()!=null) 
//		{
//			if(returnHicData.getData().getStatus().equalsIgnoreCase("invalid"))
//			{
//				//formValues =new Hashtable();
//				return "false";
//			}
//			else
//			{
//				loginStatus = true;
//				
//								
//			}
//		}
//	}
//	if(method.equalsIgnoreCase("executeList"))
//	{
//		if(returnHicData.getData()!=null && returnHicData.getData().getStatus()!=null) 
//		{
//			IData data=returnHicData.getData();
//			IFormPattern formpattern=data.getFormPattern();
//			String formPatternId=formpattern.getFormId();
//			Hashtable formvalues=new Hashtable();
//			formvalues=data.getFormPattern().getFormValues();
//		}
//	}
//    if(method.equalsIgnoreCase("sendandrecieve"))
//    {
//	      if(returnHicData.getData()!=null && returnHicData.getData().getStatus()!=null ) 
//	      {
//	            String message= returnHicData.getData().getStatus();
//	            String msg = "The return message is:"+message;
//	            message(msg);
//	            return message;
//	       }
//  	}
//	if(method.equalsIgnoreCase("invokeClientCall"))
//	{
//		webMessage = (String) returnHicData.getMetaData().getCommonObject();
//		return webMessage;
//	}
//	sessionUpdate(returnHicData);
//	return isValidStatus;
//}
////added argument as formdesginer not allowing method without argument.
//void displayErrorMessageFromComponent(String id)
//{
//	if ((returnHicData.getData() != null) && (returnHicData.getData().getReturnMessage() != null))
//	{
//		message( returnHicData.getData().getReturnMessage() );
//	}
//}
//
//void sessionUpdate(IHICData returnData)
//	{
//		String userkeyId = returnData.getData().getUserId();
//		String[] keyArray;
//		if(userkeyId!=null)
//		{
//			keyArray = userkeyId.trim().split("-");
//		}
//		if(session.getAttribute("userId")== null || (session.getAttribute("userId")!=returnData.getData().getUserId()))
//		{
//			//session.setMaxInactiveInterval(600);
//			//Devices.setTimeoutURI("ajax", "/zul/Logout.zul");
//			
//			if(keyArray!=null)
//			{
//				session.setAttribute("userId",keyArray[0]);
//				if(keyArray.length>1)
//				{
//					session.setAttribute("loginKey",keyArray[1]);
//				}
//			}
//			Set usp = returnData.getData().getUserPatterns();
//				if(usp!=null)
//				{ 
//				
//				        List rights=null;
//					String userpatterids = "";
//					Iterator usrpt = usp.iterator();
//					while(usrpt.hasNext())
//					{
//						IUserPattern pt = (IUserPattern)usrpt.next();
//						if(userpatterids.equals(""))
//						{
//							userpatterids = pt.getUserPatternId();
//						    if(pt.getRoles()!=null && pt.getRoles().getRights()!=null)
//							{
//							 rights=pt.getRoles().getRights();
//							 session.setAttribute("rights",rights);
//							 }
//							
//						}
//						else
//						{
//							userpatterids = userpatterids+"##"+pt.getUserPatternId();
//							if(pt.getRoles()!=null && pt.getRoles().getRights()!=null)
//							{
//							 rights=pt.getRoles().getRights();
//							 session.setAttribute("rights",rights);
//							 }
//						}
//					}
//					session.setAttribute("userPatterns",userpatterids);
//					
//				}
//	
//		}
//		if(session.getAttribute("EIBUNID")== null || (session.getAttribute("EIBUNID")!=returnData.getUniqueID()))
//		{
//			session.setAttribute("EIBUNID",returnData.getUniqueID());
//		}
//	}
//void authenticateUserExernally(String userFieldId, String pwdFieldId)
//{
// 	HICRouter router = RendererComponent.router;
//	IDataUnit dataUnit = new DataUnit();
//	dataUnit.setMethodName("authenticateUserEx");
//	Hashtable reqData = new Hashtable();
//	reqData.put("userName",formValues.get(userFieldId));
//	reqData.put("password",formValues.get(pwdFieldId));
//	dataUnit.setFormValues(reqData);
//	IHICData data1 = router.routeToModeler(dataUnit);
//}
//
//
//
///*
//*showComboData method changed, now it receive parameter double array and combobox object obly
//changes by wasim, 20-May-2009
//*
//*/
//private void showComboData(IHICData hicData,Object comboObj)
//{
//	if(hicData==null)
//	{
//		if(returnHicData ==null)
//		{
//				return;
//		}
//		else
//		{
//				hicData = returnHicData;
//		}
//	}
//	comboObj.setText("");
//	comboObj.getItems().clear();
//	IData dataUnit = hicData.getData();
//	List listValue = null;
//	Hashtable formValues=new Hashtable();
//	formValues=dataUnit.getFormPattern().getFormValues();
//	String emailCondition=(String)formValues.get("emailCondition");
//	if(dataUnit.getQueryData().getListData()!=null)
//	{
//		listValue = dataUnit.getQueryData().getListData();
//		
//	}
//	else
//		return;
//	boolean emailConditionResult=equalsTest(emailCondition,"emailCondition");
//	String[][] allValues = dataUnit.getQueryData().iterateListData(listValue);
//	if(allValues==null)
//		return;	
//	if(emailConditionResult)	
//	{
//		String[][] userIds=new String[allValues.length][1];
//		String[][] mailIds=new String[allValues.length][1];
//		for(int i=0;i<allValues.length;i++)
//		{
//			userIds[i][0] =allValues[i][0];
//			mailIds[i][0] =allValues[i][1];
//			formValues.put(userIds[i][0],mailIds[i][0]);
//		}
//		comboTable.put(comboObj.getId(),mailIds);
//		uiLibraryUtil.showComboData(userIds,comboObj);
//		formValues.put("emailCondition","");
//	}
//	else
//	{
//		comboTable.put(comboObj.getId(),listValue);
//		uiLibraryUtil.showComboData(allValues,comboObj);
//	}
//}
//
//
//
//
//
//public boolean showListData(IHICData hicData, Object elementObj)
//{
//		
//		// Here element Object is grid
//		Object listValues = session.getAttribute("dbListValue");
//		int noOfheader = 0;
//		if(elementObj !=null)
//			noOfheader = getNoOfHeader(elementObj);
//		if(listValues==null)
//		{
//			if(hicData==null)
//			{
//				if(returnHicData ==null)
//				{
//					return false;
//				}
//				else
//				{
//					hicData = returnHicData;
//				}
//			}
//			IData dataUnit = hicData.getData();
//			List listValue = null;
//			if(dataUnit.getQueryData().getListData()!=null)
//			{
//				listValue = dataUnit.getQueryData().getListData();
//			}
//			else
//				return false;
//			String[][] allValues = dataUnit.getQueryData().iterateListData(listValue);
//			ListModel myModel = null;
//			if(allValues==null)
//			{
//				//System.out.println("Inside showdatalist 1"+elementObj);
//				myModel = new ListModelList();
//				elementObj.setModel(myModel);
//				if(elementObj instanceof Listbox)
//				{
//					elementObj.setItemRenderer(new ItemRendererArray());
//				}
//				else 
//				{
//					elementObj.setRowRenderer(new GridRowCustomRenderer());
//				}	
//				return false;
//			}
//			else
//			{
//				//System.out.println("Inside showdatalist 2"+elementObj+"allValues="+allValues.length);
//				myModel = new ListModelList(allValues);
//				elementObj.setModel(myModel);
//				if(elementObj instanceof Listbox)
//				{
//					elementObj.setItemRenderer(new ItemRendererArray());
//				}
//				else 
//				{
//					elementObj.setRowRenderer(new GridRowCustomRenderer());
//				}		
//			}
//		}
//		else
//		{
//			String[][] allValues = QueryData.iterateListData(listValues);
//			System.out.println("Inside showdatalist else"+"allValues="+allValues.length);
//			if(allValues!=null)
//			{
//				allValues = getGridLengthValue(allValues,noOfheader);
//				ListModel myModel = new ListModelList(allValues);
//				elementObj.setModel(myModel);
//				System.out.println("Inside showdatalist elementObj="+elementObj);
//				if(elementObj instanceof Listbox)
//				{
//					elementObj.setItemRenderer(new ItemRendererArray());
//				}
//				else 
//				{
//					elementObj.setRowRenderer(new GridRowCustomRenderer());
//				}	
//			}
//			else
//				return false;
//		}
//		return true;
//		
//}
//
//void setDataStatus(String status, String formPattern, String dataPattern)
//{
//	invokeComponent(null, "changeDOStatus", null, status + "_SEP_" + formPattern + "_SEP_" + dataPattern);
//}
//
//void processNextWorkflowTool(String formPattern, String dataPattern)
//{
//	String patientId = null; // getSessionData("rowId");
//	String patientMRN = null; // getSessionData("patientmrn");
//	String status = getSessionData("currentworkflownodestatus");
//	
//	invokeDynamicWorkflow(
//		"com.oxyentmedical.component.workflowcomponent", 
//		"com.oxymedical.component.workflowComponent.WorkflowComponent", 
//		"ProcessNextWorkflowTool", status, formPattern, dataPattern, patientId, patientMRN, null, null);
//}
//
//void invokeDynamicWorkflow(String status, String formPattern, String dataPattern, String patientId, String patientMRN, String scheduleId, String scheduleWorkArea)
//{
//	System.out.println("****************************************\n***************" 
//		+ status 
//		+ "***************\n****************************************");
//	invokeDynamicWorkflow(null, null, "changedostatusdynamic", status, formPattern, dataPattern, patientId, patientMRN, scheduleId, scheduleWorkArea);
//}
//
//void invokeDynamicWorkflow(String compId, String compClass, String methodName, String status, String formPattern, String dataPattern, String patientId, String patientMRN, String scheduleId, String scheduleWorkArea)
//{
//	if (dataPattern == null) dataPattern = "_NULL_";
//	if (patientMRN == null) patientMRN = "_NULL_";
//	if (status == null) status = "_NULL_";
//	if (formPattern == null) formPattern = "_NULL_";
//	if (patientId == null) patientId = "_NULL_";
//	if (scheduleId == null) scheduleId = "_NULL_";
//	if (scheduleWorkArea == null) scheduleWorkArea = "_NULL_";
//	
//	invokeComponent(compId, methodName, compClass, status + "_SEP_" + formPattern + "_SEP_" + dataPattern + "_SEP_" + patientId + "_SEP_" + patientMRN+ "_SEP_" + scheduleId+ "_SEP_" + scheduleWorkArea);
//}
//
//private String getCurrentUserPattern()
//{
//	return session.getAttribute("userPatterns");
//}
//void clearFormData(String formId)
//{
//	Object rootFormValue = self.getRoot();
//	Object formObj = rootFormValue.getVariable(formId,true);
//    uiLibraryUtil.clearFormData(formId,rootFormValue);
//	formValues.clear();
//}
//
//Object createArray(String arrayType, String arrayValues)
//{
//	if (arrayType.equals("String"))
//	{
//		return arrayValues.split(",");
//	}
//	return null;
//}
//
//public boolean clientSideValidation(String formId)
//{
//	boolean isValid = true;
//	Object rootFormValue = self.getRoot();
//	Window formObj = rootFormValue.getVariable(formId,true);
//	if(formObj == null){
//		if(rootFormValue instanceof Window){
//			formObj = (Window)rootFormValue; //just incase the window is a pop-up
//		}
//	}
//	//alert("formId: " + formId);
//	//alert("rootFormValue: " + rootFormValue);
//	//alert("formObj: " + formObj);
//	String msg=uiLibraryUtil.clientSideValidation(formObj,formValues );
//	
//	if(msg != null)
//		{
//			msg = msg.trim();
//		}
//		try {
//			if(msg !="" && msg.length() !=0)
//			{
//				//Messagebox.show(msg,title,Messagebox.OK, Messagebox.ERROR);
//				message(msg);
//				isValid = false;
//			}
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		
// 	return isValid;
//}
//
//
//public boolean validateDate(String datepicker1,String datepicker2)
//{
//    boolean isDateValid = false;
//	Object rootFormValue = self.getRoot();
//	 isDateValid=uiLibraryUtil.validateDate(datepicker1,datepicker2,
//			rootFormValue,formValues);
//	return isDateValid;
//}
//public boolean equalsTest(String firstStr,String secondStr)
//{
//	
//	if(firstStr!=null && secondStr!= null)
//	{
//	      firstStr=firstStr.trim();
//	      secondStr=secondStr.trim();
//		if (firstStr.equalsIgnoreCase(secondStr))
//			return true;
//	}
//	return false;
//}
//public boolean selectCurrentOrFutureDate(String datepicker1)
//{
//	 boolean isDateValid = true;
//	 Object rootFormValue = self.getRoot();
//	 isDateValid= uiLibraryUtil.selectCurrentOrFutureDate(datepicker1,rootFormValue,formValues);
//	 return isDateValid;
//}
//public void showWebServiceMessage(String displayControl)
//{
//	 Object rootFormValue = self.getRoot();
//	 Object displayObject = rootFormValue.getVariable(displayControl,true);
//	 if(displayObject instanceof Textbox || displayObject instanceof Label )
//	 {
//	 displayObject.setValue(webMessage);
//	 }
//	 else  if(displayObject instanceof Listbox )
//	 {
//	  // showListData(hicData,displayObject)
//	 }
//}
//public boolean checkValueWithOperator(Object val1, Object val2, String checkType)
//{
//	if (checkType.equals("EQUAL"))
//	{
//		if(val1!=null && val2!=null)
//		{
//			if (val1.equals(val2)) return true;
//		}
//	}
//	return false;
//}
//
//void showReport(String controlObject, String jasperFile)
//{ 	  
//	Object listData = session.getAttribute("dbListValue");
//	if(listData==null)
//	{
//	
//		if(returnHicData ==null)
//		{
//			
//			message("HIC Return data is null.");
//			return;
//		}
//		
//		String configPath=PropertyUtil.setUpProperties("GLASSFISH_DOMAIN_HOME")+"config/";
//		jasperFile =configPath+jasperFile;
//		IData dataUnit = returnHicData.getData();
//		Object rootFormValue = self.getRoot();
//		uiLibraryUtil.showJasperReport(controlObject,jasperFile,rootFormValue,dataUnit);
//	}
//	else
//	{
//		String configPath=PropertyUtil.setUpProperties("GLASSFISH_DOMAIN_HOME")+"config/";
//		jasperFile =configPath+jasperFile;
//		Object rootFormValue = self.getRoot();
//		uiLibraryUtil.showExcelReport(controlObject,jasperFile,rootFormValue,listData);
//		session.setAttribute("dbListValue",null);
//	}
//}
//private void showComboData(String comboid)
//{
//    Object rootFormValue = self.getRoot();
//	Object elementObj = rootFormValue.getVariable(comboid,true);
//	if(elementObj !=null)
//	{
//		showComboData(null,elementObj);	
//	}
//	
//}
//// Change the color of screen based on boy/girl
//void changeScreenColorAsPerInput(String colorScreen)
//{
//	Object rootFormValue = self.getRoot();
//	//for window its setContentStyle
//	rootFormValue.setContentStyle("background-color: #"+colorScreen+";");
//	changeChildrenColor(rootFormValue,colorScreen );
//}
//
//void changeChildrenColor(Object rootFormValue , String colorCode)
//{
//	List children = rootFormValue.getChildren();
//	if(children!=null && children.size() > 0)
//	{
//		Object[] charr = children.toArray();
//		for(int i = 0; i < charr.length; i++)
//		{
//				//change color
//				if(charr[i] != null)
//				{
//					//System.out.println("all charr[i]=="+charr[i]);
//					//for all child controls 
//					if(charr[i].getChildren() !=null && charr[i].getChildren().size() > 0)
//					{
//						//System.out.println("parent charr[i]=="+charr[i]+"   charr[i].getChildren()"+charr[i].getChildren());
//						if(charr[i] instanceof  Row )
//						{
//							charr[i].setStyle("background-color: #"+colorCode+";");
//						}
//						else if(charr[i] instanceof  Rows )
//						{
//
//						}						
//						else if(charr[i] instanceof  North )
//						{
//							charr[i].setStyle("background-color: #"+colorCode+";border: 2px solid #"+colorCode+"; box-shadow: 0 0 10px #fff inset;");
//
//						}	
//						else if(charr[i] instanceof  South )
//						{
//							charr[i].setStyle("background-color: #"+colorCode+";border: 2px solid #"+colorCode+"; box-shadow: 0 0 10px #fff inset;");
//
//						}
//						else if(charr[i] instanceof  Center )
//						{
//							charr[i].setStyle("background-color: #"+colorCode+";border: 2px solid #"+colorCode+"; box-shadow: 0 0 10px #fff inset;");
//
//						}
//						else if(charr[i] instanceof  West )
//						{
//							charr[i].setStyle("background-color: #"+colorCode+";border: 2px solid #"+colorCode+"; box-shadow: 0 0 10px #fff inset;");
//
//						}		
//						else if(charr[i] instanceof  East )
//						{
//							charr[i].setStyle("background-color: #"+colorCode+";border: 2px solid #"+colorCode+"; box-shadow: 0 0 10px #fff inset;");
//
//						}													
//						else if(charr[i] instanceof  Cell )
//						{
//							charr[i].setStyle("background-color: #"+colorCode+";border: 2px solid #"+colorCode+"; box-shadow: 0 0 10px #fff inset;");
//						}						
//						else if(charr[i] instanceof  Div && charr[i].getParent() instanceof Panelchildren )
//						{
//							charr[i].setStyle("background-color: #"+colorCode+";");
//						}
//						else if(charr[i] instanceof  Panelchildren && charr[i].getParent() instanceof Panel )
//						{
//							charr[i].setStyle("background-color: #"+colorCode+";");
//						}	
//						else if(charr[i] instanceof  Panel )
//						{
//							charr[i].setStyle("background-color: #"+colorCode+";");
//						}	
//						else if(charr[i] instanceof  Borderlayout )
//						{
//
//						}	
//						else if(charr[i] instanceof  Hlayout )
//						{
//							charr[i].setStyle("background-color: #"+colorCode+";");
//						}	
//						else if(charr[i] instanceof  Toolbar )
//						{
//							charr[i].setStyle("background-color: #"+colorCode+";");
//						}																
//						else if(charr[i] instanceof  Grid )
//						{
//							
//						}
//						else if(charr[i] instanceof  Div && charr[i].getParent() instanceof Row )
//						{
//							charr[i].setStyle("background-color: #"+colorCode+";");
//						}	
//						changeChildrenColor(charr[i],colorCode);
//					}
//					else
//					{
//						
//						if(charr[i] instanceof  Image )
//						{
//						}
//						else if(charr[i] instanceof  Style )
//						{
//						}
//						else if(charr[i] instanceof  Column )	
//						{	
//							charr[i].setStyle("background-color: #"+colorCode+";");
//						}
//						else
//						{
//							//System.out.println("apply style on charr[i]=="+charr[i]);
//							charr[i].setStyle("background-color: #"+colorScreen+";");
//
//						}
//
//					}
//				}
//		}
//	}
//}
//boolean displayImageArray(String parentId, int hiddenobjects)
//{
//	boolean isDisplay = true;
//	IHICData hicData = null;
//	Object listValues = session.getAttribute("dbListValue");
//	if(listValues==null)
//	{
//		if(hicData==null)
//		{
//			if(returnHicData ==null)
//			{
//				return false;
//			}
//			else
//			{
//				hicData = returnHicData;
//			}
//		}
//		IData dataUnit = hicData.getData();
//		List listValue = null;
//		if(dataUnit.getQueryData().getListData()!=null)
//		{
//			listValue = dataUnit.getQueryData().getListData();
//		}
//		else
//			return false;
//		String[][] allValues = dataUnit.getQueryData().iterateListData(listValue);
//		Object rootFormValue = self.getRoot();
//		Object displayObject = rootFormValue.getVariable(parentId,true);
//		List children = displayObject.getChildren();
//		if(children!=null && children.size() > hiddenobjects)
//		{
//			Object[] charr = children.toArray();
//			for(int i = hiddenobjects; i < charr.length; i++)
//			{
//					displayObject.removeChild(charr[i]);
//			}
//		}
//		if(allValues==null)
//			return false;
//		for(int i = 0; i < allValues.length; i++)
//		{
//			byte[] imagedata = Base64.decode(allValues[i][0]);
//			AImage imgcontent = new AImage("",imagedata);
//			Image img = new Image();
//			img.setHeight("150px");
//			img.setWidth("200px");
//			photoMap.put(img, allValues[i][1]);
//			img.addEventListener("onClick",new EventListener()
//			{
//				public void onEvent(Event event)
//				{
//					List ch = displayObject.getChildren();
//					if(ch!=null && ch.size() > hiddenobjects)
//					{
//						Object[] arr = ch.toArray();
//						for(int i = hiddenobjects; i < arr.length ; i++)
//						{
//							if(arr[i] instanceof Image )
//							{
//								arr[i].setHeight("150px");
//								arr[i].setWidth("200px");
//								arr[i].setStyle("border:2px solid;margin-bottom:3px;margin-right:3px;margin-left:5px;margin-top:3px;");
//							}
//						}
//					}
//					Image target = event.getTarget();
//					updateSession("imageid", photoMap.get(target));
//					target.setHeight("142px");
//					target.setWidth("192px");
//					target.setStyle("border:6px solid;margin-bottom:3px;margin-right:3px;margin-left:5px;margin-top:3px;");
//				}
//			});
//			img.setContent(imgcontent);
//			img.setStyle("border:2px solid;margin-bottom:3px;margin-right:3px;margin-left:5px;margin-top:3px;");
//			displayObject.appendChild(img);
//		}
//		children = displayObject.getChildren();
//	}
//	return isDisplay;
//}
//
//public void displayImageContent(Object displayObject, int x, int y)
//{
//	
//	String value = getDataFromAllValuesVar(x, y);
//	try
//	{
//	if(value==null || value.equals("null"))
//	{
//		displayObject.setSrc("/zul/img/silhouette.JPG");
//	}
//	else
//	{
//		byte[] imagedata = Base64.decode(value);
//		AImage imgcontent = new AImage("",imagedata);
//		displayObject.setContent(imgcontent);
//	}
//	}
//	catch(Exception e)
//	{
//	}
//}
//
//boolean displayDataInControl(String controlObj)
//{
//	boolean isDisplay = true;
//	Object rootFormValue = self.getRoot();
//	System.out.println("Execute List is working");
//	Object elementObj = rootFormValue.getVariable(controlObj,true);
//	//System.out.println("Inside displayDataInControl"+elementObj);
//	if(elementObj instanceof Grid || elementObj instanceof Listbox )
//	{
//		isDisplay = showListData(null ,elementObj);	
//	}
//	else if(elementObj instanceof Label)
//	{
//		
//	}
//	return isDisplay;
//}
//
//
//public String uploadFileToolXML() 
//{
//	String path=null;
//	Object media = Fileupload.get();
//	if(media!=null)
//	{
//		String inputStream = media.getStringData();
//		String fileName = media.getName();
//		File xmlFile = File.createTempFile(fileName, ".xml");
//		FileOutputStream out = new FileOutputStream(xmlFile);
//		try {
//				out.write(inputStream.getBytes());
//			} finally  {
//					     out.close();
//		}
//		path=xmlFile.getPath();
//		return path;
//	}
//	return path;
//}
//
//
//public String uploadImage(String parentId) 
//{
//	String retvalue = null;
//	Object rootFormValue = self.getRoot();
//	Object displayObject = rootFormValue.getVariable(parentId,true);
//	Object media = Fileupload.get();
//	if (media instanceof org.zkoss.image.Image)
//	{
//		byte[] fi = media.getByteData();
//		String value=Base64.encode(fi);
//		formValues.put(parentId,value);
//		retvalue = value;
//		displayObject.setContent(media);
//	}
//	else if (media != null)
//	{
//	Messagebox.show("Not an image: "+media, "Error",
//	Messagebox.OK, Messagebox.ERROR);
//	retvalue = null;
//	}
//	else if(media==null || media=="")
//	{
//	retvalue = null;
//		
//	}
//	return retvalue;
//}
//
//
//public boolean uploadSignature(String parentId) 
//{
//	boolean isUploaded = true;
//	Object rootFormValue = self.getRoot();
//	Object media = Fileupload.get();
//	if (media instanceof org.zkoss.image.Image)
//	{
//		String imagetype = null;
//		byte[] fi = media.getByteData();
//		String value = HexBin.encode(fi);
//		formValues.put("userId",parentId);
//		formValues.put("imagedata",value);
//		if(media.getFormat()!=null)
//		imagetype = media.getFormat();	
//		formValues.put("imagetype",imagetype);		
//	}
//	else if (media != null)
//	{
//	Messagebox.show("Not an image: "+media, "Error",
//	Messagebox.OK, Messagebox.ERROR);
//	isUploaded =false;
//	}
//	else if(media==null || media=="")
//	{
//	isUploaded =false;
//		
//	}
//	return isUploaded;
//}
////Changes done for implementing paging done by pra on 27May2009
//public void createPagingEvent(String id)
//{  	
//	validListRequest=true;
//	String[] listInfo=PagingInfo.getListInfo();
//	final String componentId= listInfo[0];
//	final String method=listInfo[1];
//	final String classname= listInfo[2];
//	final String condition= listInfo[3];
//	final String listName=  listInfo[4];
//	Object rootFormValue = self.getRoot();
//	Paging pag = rootFormValue.getVariable(pagingId,true);
//	String labelId=getDisplayLabelId();
//	Object label1=null;
//	if(labelId!=null)
//	{
//	 label1=rootFormValue.getVariable(labelId,true);
//	}
//	final Object label=label1;
//	final Object list = rootFormValue.getVariable(listName,true);
//	invokeComponent(componentId,method,classname,condition); 
//	setTotalSize();
//	pag.setTotalSize(getTotalSize());
//	validListRequest=false;
//	final int PAGE_SIZE = pag.getPageSize(); 
//    if(list.getChildren().size()>PAGE_SIZE)
//	{
//	String value="[ "+1+" - "+PAGE_SIZE.toString()+" / "+getTotalSize()+" ]";
//	if(label!=null)
//	label.setValue(value);
//	}
//	else
//	{
//	int total=list.getChildren().size()-1;
//	if(total>0)
//	{
//	String value="[ "+1+" - "+total.toString()+" / "+getTotalSize()+" ]";
//	if(label!=null)
//	label.setValue(value);
//	}
//    else
//    {
//    if(label!=null)
//    label.setValue("");
//    }
//	}
//	pag.addEventListener("onPaging", new EventListener()
//	{
//		public void onEvent(Event event)
//		{
//			PagingEvent pe = (PagingEvent) event;
//			int pgno = pe.getActivePage();
//			int ofs = pgno * PAGE_SIZE;
//			String conditonS=condition+" limit "+ofs+","+PAGE_SIZE;
//			invokeComponent(componentId,method,classname,conditonS);
//			displayDataInControl(listName);
//			int size=list.getChildren().size();
//			int total=ofs+size-1;
//			if(label!=null)
//			{
//			String value="[ "+(ofs+1).toString()+" - "+total.toString()+" / "+getTotalSize()+" ]";
//			label.setValue(value);
//			}
//		}
//	}); 
//}
//public String uploadFile(String serverPath) 
//{
//    String path=null;
//	Object media = Fileupload.get(true);
//	if(media!=null)
//	{
//		String contentType=media.getContentType();
//		InputStream inputStream=media.getStreamData();
//		String fileName=media.getName();
//		String folderPath=serverPath;
//		path=writeToFile(folderPath,fileName,inputStream);
//		return path;
//	}
//	return path;
//}
//public String uploadFile(String serverpath, String file) 
//{
//    String path=null;
//	Object media = Fileupload.get(true);
//	if(media!=null)
//	{
//		String contentType=media.getContentType();
//		InputStream inputStream=media.getStreamData();
//		String fileName=media.getName();
//		String newfilename=file+fileName.substring(fileName.lastIndexOf("."));
//		String folderPath=serverpath;
//		path=writeToFile(folderPath,newfilename,inputStream);
//		return path;
//	}
//	return path;
//}
//
//public String uploadTemplate(String temptype)
//{
//	if(temptype == null || temptype.isEmpty())
//	{
//		return null;
//	}
//	String path=null;
//	Object media = Fileupload.get();
//	if(media!=null)
//	{
//	InputStream inputStream=media.getStreamData();
//    String fileName=media.getName();
//	String folderPath=PropertyUtil.setUpProperties("PSG_REPORT_TEMPLATE_DIR");
//	path=writeToFile(folderPath+"/"+temptype+"/",fileName,inputStream);
//	return path;
//	}
//	else
//		return null;
//}		
//
////this method is added to write file on the server. added by pra  on july 06 2009
//public String writeToFile(String folder, String fileName,InputStream inputStream)
//{
//	File file = new File(folder);
//	if(!file.exists())
//	{
//		file.mkdirs();
//	}
//    File f=new File(folder+fileName);   
//    OutputStream out=new FileOutputStream(f);
//    byte[] buf=new byte[1024];
//    int len;
//    while((len=inputStream.read(buf))>0)
//    out.write(buf,0,len);
//    out.close();
//    inputStream.close();   
//    return folder+fileName; 
//}
//
//public void downloadFile() 
//{    
//	Object obj =null;
//	if(null != returnHicData)
//	{
//		IData dataUnit = returnHicData.getData();
//		List listValue = null;
//		if(dataUnit.getQueryData().getListData()!=null)
//		{
//			listValue = dataUnit.getQueryData().getListData();
//		}
//		else
//			return;
//		obj=listValue.get(0);
//	}
//	if(obj!=null)
//	{
//		String txtValue = (String)obj;
//		Filedownload.save(txtValue,"text/plain","test.txt");
//	}
//}
//
//void createParameterComponents(Object obj)
//{
//	if(obj !=null)
//	{
//		obj.setStyle("overflow:auto;");
//	}
//	Object gridObj = obj.getChildren().get(0);
//	Object rowsObj = gridObj.getChildren().get(0);
//	IHICData localHicData = HICDataController.getHICData(""+session.hashCode());
//	if(localHicData.getData().getReturnedData() != null){
//		Hashtable paramTable = (Hashtable)localHicData.getData().getReturnedData();
//		Enumeration htEnum = paramTable.keys();
//		while(htEnum.hasMoreElements()){
//			String key = htEnum.nextElement();
//			String value = paramTable.get(key);
//			
//			// create row, key label and value label objects
//			Row row = new Row();
//			rowsObj.appendChild(row);
//			Label keyLabel = new Label();
//			keyLabel.setHeight("18px");
//			keyLabel.setValue(key.replaceAll("_", " "));
//			row.appendChild(keyLabel);
//			
//			Label valueLabel = new Label();
//			valueLabel.setHeight("18px");
//			valueLabel.setValue(value);
//			row.appendChild(valueLabel);
//		}
//	}
//}
//
//int counter=1;
//
//public void deleteComponent(String str)
//{
//	Object rootFormValue = self.getRoot();
//	Object obj =rootFormValue.getVariable(str, true);	
//	Object div =rootFormValue.getVariable(obj.getParent().getParent().getParent().getParent().getId(), true);
//	obj =rootFormValue.getVariable(div.getParent().getId(), true);
//	obj.removeChild(div);
//	String numStr = str.substring(str.length()-1);
//	formValues.remove("secondrange"+numStr);
//	formValues.remove("firstrange"+numStr);
//	formValues.remove("variable"+numStr);
//	formValues.remove("field"+numStr);
//	formValues.remove("logical"+numStr);
//	formValues.remove("category"+numStr);
//	
//		
//}
//
//public void readOnly(String formId)
//{
//	Object rootFormValue = self.getRoot();
//   	Window obj = rootFormValue.getVariable(formId,true);
//	uiLibraryUtil.readOnlyData(obj);
//}
//public void editOnly(String formId)
//{
//    Object rootFormValue = self.getRoot();
//    Window obj = rootFormValue.getVariable(formId,true);
//	uiLibraryUtil.editOnlyData(obj);
//}
//
//public class itemRendererArrayDragAndDrop implements ListitemRenderer
//{
//	public void render(Listitem li, java.lang.Object data) throws Exception
//	{
//		String[] _data = (String[])data;
//		if(_data!=null)
//		{
//			for(int i=0;i<_data.length;i++)
//			{
//				//new Listcell(_data[i]).setParent(li);
//				Listcell listcell=new Listcell(_data[i]);
//				listcell.value=_data[i];
//				li.setDraggable("true");
//				li.setDroppable("true");
//				li.addEventListener("onDrop", new EventListener()
//				{
//					public void onEvent(Event event)
//					{
//						try
//						{
//							move(event.dragged);
//						}
//						catch(Exception e)	
//						{
//							System.out.println("Add Event Listener warning on OnDrop in render function");
//						}
//					}
//				});
//				listcell.setParent(li);
//			}
//		}
//	}
//
//	@Override
//	public void render(Listitem arg0, Object arg1, int arg2) throws Exception {
//		// TODO Auto-generated method stub
//		
//	}
//}
//
//public createPopUp(String formId)
//{
//    Window popUp = (Window) Executions.createComponents(formId+".zul", null, null);
//    popUp.doModal();
//}
//
//public createPopUp(String formId, org.zkoss.zk.ui.event.EventListener onCloseEventListener){
//	Window popUp = (Window)Executions.createComponents(formId + ".zul", null, null);
//	popUp.setClosable(true);
//	popUp.addEventListener(Events.ON_CLOSE, onCloseEventListener);
//	popUp.doModal();
//}
//
////changed implementation for multiselect , done by pra on 09-june-2009
//public String getSelectedListData()
//{
//	 String output="";
//	 Object[] items=self.getSelectedItems().toArray();
//	 for(int i=0;i<items.length;i++)
//	 {
//	Listitem list = items[i];
//	output=output+self.id;
//	output =output+","+self.getSelectedIndex();
//	output = output+","+list.getLabel();
//	if(list.getValue()!="")
//	{
//		output=output+","+list.getValue()+"##";
//	}
//	}	
//	return output;
//}
//
//// Changes implemented for Selected Questions by dsi
//public void setListBoxValues(String controlId)
//{
//	Object rootFormValue = self.getRoot();
//	Object obj = rootFormValue.getVariable(controlId,true);
//	List ListItems = obj.getItems();
//	Iterator iter = ListItems.iterator();
//	List items = new ArrayList();
//	while(iter.hasNext()){
//		Listitem value = (Listitem)iter.next();
//		System.out.println("label = "+value.getLabel());
//		items.add(value.getLabel());
//	}
//	formValues.put("selectedQuestions", items);
//}
//
//// Changes implemented for Selected Questions by dsi
//public String getSelectedListBoxValue(String controlId)
//{
//	Object rootFormValue = self.getRoot();
//	Object obj = rootFormValue.getVariable(controlId,true);
//	String value = obj.getSelectedItem().getLabel();
//	return value;
//}
//
////changed implementation for multiselect , done by pra on 09-june-2009
////method implementation changed  for removing child and adding child by pra	
//public void moveValue(String itemValue,String parentControl, String controlId)
//{
//	Object rootFormValue = self.getRoot();
//	Object obj = rootFormValue.getVariable(controlId,true);
//	Object parentObj = rootFormValue.getVariable(parentControl,true);
//	if(itemValue!=null)
//	{
//	String []itemValues=itemValue.split("##");
//	for(int i=0;i<itemValues.length;i++)
//	{
//	String value=itemValues[i];
//	String [] values= value.split(",");
//	boolean present=true;
//	String val="";
//	String label="";
//	String id="";
//	String pid="";
//	if(values.length==4)
//	{ 
//	    pid=values[0];
//	    id=values[1];
//		label=values[2];
//		val=values[3];
//		
//	}
//	else if(values.length==3)
//	{	pid=values[0];
//	 	id=values[1];
//		label=values[2];
//		
//	}
//	if(pid.equalsIgnoreCase(parentControl) && !(pid.equalsIgnoreCase(controlId)))
//	{	
//		
//	List list =obj.getChildren();
//	Listitem child = new Listitem();
//	child.setLabel(label);
//	if(val!="")
//	{
//		child.setValue(val);
//	}
//	child.setDraggable("true");
//	child.setDroppable("true");
//	child.addEventListener("onDrop", new EventListener()
//	{
//		public void onEvent(Event event)
//	   {
//			try
//			{
//				move(event.dragged);
//			}
//			catch(Exception e)	
//			{
//				System.out.println("Add Child Event Listener warning OnDrop in moveValue function");
//			}
//			
//		}
//	});		
//    int rId=Integer.parseInt(id);
//	parentObj.removeItemAt(rId);	
//	obj.appendChild(child);
//	}
//	}
//	}
//	else
//	{
//	
//	 List list=parentObj.getChildren();
//	 if(list!=null && list.size()>0)
//	 {
//	 Listitem child = list.get(0);
//	 obj.appendChild(child);
//	 parentObj.removeChild(child);
//	 }
//	 
//	}
//			
//}
////Added method to move all items to other list added by pra
// public void moveAllItems(String parentControl,String controlId)
//{ 
//
//    Object rootFormValue = self.getRoot();   	
//   	Object controlObj = rootFormValue.getVariable(controlId,true);
//	Object parentObj = rootFormValue.getVariable(parentControl,true);
//   	List list =parentObj.getChildren();
//   	List newList=new ArrayList(list);	   
//   	int size=newList.size();
//   	for(int i=0;i<size;i++)
//   	{
//   	Listitem child = (Listitem)newList.get(i);	
//   	controlObj.appendChild(child);
//   	}
//   	 
//     
// }
//  
//public updateRefferences(String id)
//{
//	Object rootFormValue = self.getRoot();
//	Object obj = rootFormValue.getVariable(id,true);
//	if(obj.text=="")
//	{
//	formValues.put(id,"");
//	}
//}
//
//
//public void showLoginInfo(String firstLbl)
//{
//	Object rootFormValue = self.getRoot();
//	Object firstObj = rootFormValue.getVariable(firstLbl,true);
//	if(firstObj==null)
//	{
//	return;
//	}
//	if(returnHicData ==null)
//		{
//			return;
//		}
//	IData dataUnit = returnHicData.getData();
//	List listValue = null;
//	if(dataUnit.getQueryData().getListData()!=null)
//	{
//		listValue = dataUnit.getQueryData().getListData();
//	}
//	else
//		return;
//	String[][] allValues = dataUnit.getQueryData().iterateListData(listValue);
//	if(allValues==null)
//			return;
//	//String key = getSessionData("loginKey");
//	String user = getSessionData("userId");
//	String logDate = null;
//	String logTime = null;
//	String loginValue = null;
//	
//	for(int row=0;row<allValues.length;row++)
//		{
//			String user = allValues[row][1];
//						
//			if(user.equalsIgnoreCase(user))
//			{
//				if(row ==(allValues.length-2))
//				{
//					logDate = allValues[row][2];
//					logTime = allValues[row][3];
//				}
//			}
//		}
//	
//	String date = logDate+" "+logTime;
//	
//	if(logDate !=null)
//	{
//		Date date1 = DateUtil.stringToDate(date, "yyyy-MM-dd HH:mm:ss");
//		Date toDayDate =new Date();
//		String toDate = DateUtil.formatDate(toDayDate, "yyyy-MM-dd HH:mm:ss");
//		Date currentDate = DateUtil.stringToDate(toDate, "yyyy-MM-dd HH:mm:ss");
//		Long loggedTime = date1.getTime();
//		Long currentTime = currentDate.getTime();
//		Long diff = currentTime-loggedTime;	
//		long diffSeconds = diff / 1000;
//	   	long diffMinutes = diff / (60 * 1000);
//	   	long diffHours = diff / (60 * 60 * 1000);
//		long diffDays = diff / (24 * 60 * 60 * 1000);
//		
//		if(diffDays!=0)
//		{
//			logTime =diffDays + " days ago";
//		}
//		else if(diffHours!=0)
//		{
//			logTime =diffHours + " hours ago";
//		}
//		else if(diffMinutes!=0)
//		{
//			logTime =diffMinutes + " minutes ago";
//		}
//		else 
//		{
//			logTime =diffSeconds + " Seconds ago";
//		}
//		
//		loginValue = "last account login:"+logTime;
//	}
//	String ipStr=" at IP :";
//	String host=null;
//	for(int row=0;row<allValues.length;row++)
//	{
//		if(row ==(allValues.length-2))
//		{
//			host = allValues[row][4];
//			String logoutValue = allValues[row][5];
//			if(logoutValue.equalsIgnoreCase("0"))
//			{
//				String curr =session.getRemoteHost();
//				if(!(host.equalsIgnoreCase(curr)) )
//				{
//				
//					host="this account is active on IP location: "+host+", "+curr;
//					loginValue="";
//					ipStr="";
//				}
//				else
//				{
//					host="this account is active on IP location: "+host;
//					loginValue="";
//					ipStr="";
//				}
//			}
//		}
//	}
//	
//	if(host!=null)
//	{
//		ipStr = ipStr + host+"";
//	}
//	else
//	{
//		ipStr = ipStr +session.getRemoteHost();
//	}
//	
//	if(loginValue !=null)
//	{
//	loginValue = loginValue +ipStr;
//	}
//	else
//	{
//		loginValue="";
//	}
//	firstObj.setValue(loginValue);
//}
//
//
////These method are for the UserAdmin Component for Adding New UserPattern and
////for hiding and displaying the fields in the AddUser Form.
////added by pra on 22-May-2009
//public void createPrivilegesList(String id)
//{
//	Object rootFormValue = self.getRoot();
//	Object obj = rootFormValue.getVariable(id,true);
//	List list =obj.getChildren();
//	LinkedHashMap privileges = new LinkedHashMap();
//	if(list!=null && list.size()>0)
//	{
//		Iterator itr = list.iterator();
//		while(itr.hasNext())
//		{
//			Listitem child = (Listitem)itr.next();
//			privileges.put(child.getLabel(),child.getValue());
//		}
//	}
//	updateSessionList("privilege",privileges);
//}
//
//public void createFieldsList(String id)
//{
//	Object rootFormValue = self.getRoot();
//	Object obj = rootFormValue.getVariable(id,true);
//	List list =obj.getChildren();
//	LinkedHashMap fields = new LinkedHashMap();
//	if(list!=null && list.size()>0)
//	{
//		Iterator itr = list.iterator();
//		while(itr.hasNext())
//		{
//			Listitem child = (Listitem)itr.next();
//			fields.put(child.getLabel(),child.getValue());
//		}
//	}
//	updateSessionList("fields",fields);
//}
//
//public void setDefaultValue(LinkedHashMap fieldValues ,String id)
//{
//  Object rootFormValue = self.getRoot();
//  Object obj = rootFormValue.getVariable(id,true);
//  if(fieldValues!=null && fieldValues.size()>0)
//  {
//
//	  Object[] keys=fieldValues.keySet().toArray();
//	  for(int i=0;i<keys.length;i++)
//	  {
//		   String value=(String)fieldValues.get(keys[i]);
//		   Listitem li = new Listitem();
//		   li.setLabel((String)keys[i]);
//		   li.setValue(value);
//		   obj.appendChild(li);	   
//	   }
//  }
//}
//public  createFormValuesForUserPattern(String defaultFormPattern)
// {
//	
//	 List fields=createListValues(session.getAttribute("fields"));
//	 List privilege=createListValues(session.getAttribute("privilege"));
//	 formValues.put("userPatternId",getSessionData("userPatternId"));
//	 formValues.put("defaultFormPattern",defaultFormPattern);
//	 formValues.put("privileges",privilege);
//	 formValues.put("fields",fields); 
//	 formValues.put("companyId",getSessionData("companyId")); 
//	 updateSessionList("fields",null);
//	 updateSessionList("privilege",null);
// }
//public LinkedList createListValues(LinkedHashMap fieldValues)
//{
//	LinkedList result = new LinkedList();
//	if(fieldValues!=null && fieldValues.size()>0 )
//	{
//		Object[] keys=fieldValues.keySet().toArray();
//		for(int i=0;i<keys.length;i++)
//		{
//			String value=(String)fieldValues.get(keys[i]);
//			if(value!=null)
//			{
//				result.add(value);
//			}
//		}
//	}
//	return result;
//}
// 
//public LinkedList createListKeys(LinkedHashMap fieldValues)
//{
//	LinkedList result = new LinkedList();
//	if(fieldValues!=null && fieldValues.size()>0 )
//	{
//		Object[] keys=fieldValues.keySet().toArray();
//		for(int i=0;i<keys.length;i++)
//		{
//			result.add(keys[i]);
//		}
//	}
//	return result;
//}
//
//public setLabelValue(String id)
//{
//	Object rootFormValue = self.getRoot();
//	Object obj = rootFormValue.getVariable(id,true);
//	String label=getSessionData("userPatternId");
//	if(label!=null)
//	{
//		obj.setValue(label);
//		formValues.put(id,label);
//	}
//	
//}
// public updateSessionList(String id,Object value)
// {
//	session.setAttribute(id,value);
// }
//public void displayFieldDataInControl(String id)
//{
//
//	if(returnHicData ==null)
//	{
//		return;
//	}
//	else
//	{
//		IData dataUnit = returnHicData.getData();
//		List listValue = null;
//		
//		if (dataUnit.getQueryData() == null) return;
//		if(dataUnit.getQueryData().getListData()!=null)
//		{
//			listValue = dataUnit.getQueryData().getListData();
//		}
//		else
//			return;
//		String[][] allValues = dataUnit.getQueryData().iterateListData(listValue);
//		Object rootFormValue = self.getRoot();
//		Object obj = rootFormValue.getVariable(id,true);
//		List child = obj.getChildren();
//		List childData = new ArrayList(child);
//		Iterator itr = childData.iterator();
//		while(itr.hasNext())
//		{
//			Listitem listitem =  (Listitem)itr.next();
//			obj.removeChild(listitem);
//		}
//				
//		if(allValues !=null)
//		{
//			for(int i=0;i<allValues.length;i++)
//			{  
//				String name=allValues[i][1];
//				String value=allValues[i][0];
//				Listitem li = new Listitem();
//				li.setLabel(name);
//				li.setValue(value);
//				obj.appendChild(li);
//			}
//		}
//	}
//}
//
//public hideControls(String[] arg)
//{
//   Object rootFormValue = self.getRoot();
//   for(int i=0;i<arg.length;i++)
//   {
//   Object obj = rootFormValue.getVariable(arg[i],true);
//   obj.setVisible(false);
//   }
//}
//
//public showButtonControls(String[] arg)
//{
//   Object rootFormValue = self.getRoot();
//   for(int i=0;i<arg.length;i++)
//   {
//   Object obj = rootFormValue.getVariable(arg[i],true);
//   obj.setVisible(true);
//   }
//}
//
//public void showControls(String id,String[] arg)
//{
//	if(returnHicData ==null)
//	{
//		return;
//	}
//	else
//	{
//		IData dataUnit = returnHicData.getData();
//		List listValue = null;
//		if(dataUnit.getQueryData().getListData()!=null)
//		{
//			listValue = dataUnit.getQueryData().getListData();
//		}
//		else
//			return;
//		String[][] allValues = dataUnit.getQueryData().iterateListData(listValue);
//		Object rootFormValue = self.getRoot();		
//		int topMargin=183;
//		if(allValues==null)
//		{
//			for(int i=0;i<arg.length;i++)
//			{
//				String top=topMargin+"px";
//				Object obj = rootFormValue.getVariable(arg[i],true);
//				obj.setLeft("80px");
//				obj.setTop(top);
//				obj.setVisible(true);
//				topMargin=topMargin+30;
//			}
//		}
//		else
//		{		
//			for(int i=0;i<allValues.length;i++)
//			{
//				String id=allValues[i][0];
//				String top=topMargin+"px";
//				Object obj = rootFormValue.getVariable(id,true);
//				obj.setLeft("80px");
//				obj.setTop(top);
//				obj.setVisible(true);
//				topMargin=topMargin+30;
//			}
//		}
//	}
//}
//// Above methods are for UseraAdmin Component.................
//
//
//
//public void addFormValue(String id, Object value)
//{
//
//	if(id!=null && value!=null)
//	{
//		formValues.put(id,value);	
//	}
//
//}
//
///*
//public String createSearchQuery(String lastNameId,String firstNameId,String dobId, String mrnId)
//{
//	String firstNameValue = formValues.get(firstNameId);
//	String lastNameValue = formValues.get(lastNameId);
//	String dobIdValue = formValues.get(dobId);
//	String mrnValue = formValues.get(mrnId);
//	return createQueryForSearch(lastNameValue, firstNameValue, dobIdValue,mrnValue);
//}
//
//public String createQueryForSearch(String lastNameValue, String firstNameValue, String dobIdValue, String mrnValue)
//{
//	String conditionStr ="";
//	formValues = new Hashtable();
//	if(firstNameValue!=null)
//	{
//		conditionStr = conditionStr +"Patient.firstname:=["+firstNameValue+"] and ";
//	}
//	if(lastNameValue!=null)
//	{
//		conditionStr = conditionStr +"Patient.lastname:=[" +lastNameValue+"] and ";
//	}
//	if(dobIdValue!=null)
//	{
//		conditionStr = conditionStr + "patient.Dateofbirth:=["+dobIdValue+"] and ";
//	}
//	if(mrnValue!=null)
//	{
//		conditionStr = conditionStr + "patient.MedicalRecordNumber:=["+mrnValue+"] and ";
//	}
//	conditionStr = conditionStr + "patient.deleted:=[0]";
//	conditionStr = conditionStr.trim();
//	return conditionStr;
//}
//*/
//
////HardCoding table Name and field name removed. Wasim Khan, 4-Aug-2009.
//public String createQueryForSearch(String fieldValue,String fieldName,String TableName,String conditionStr)
//{
//	if(fieldValue!=null)
//	{
//		conditionStr = conditionStr +TableName+"."+fieldName+":=["+fieldValue+"] and ";
//	}
//	return conditionStr;
//}
//
////Method implemented makeVisible for showing controls added by pra on 30-May-2009	
//List viewElements;
//public void makeVisible(String id)
//{ 
//	viewElements= new ArrayList();
//	List rights=session.getAttribute("rights");
//	Object rootFormValue = self.getRoot(); 
//	if(rights!=null)
//	{
//		uiLibraryUtil.displayControlInForm(rootFormValue,rights,viewElements);
//	}
//}
//	  
//	  
//  
//  public String logOut(String pageId)
//{
//	String userId = getSessionData("userId");
//	String retValue = invokeComponent("com.oxymedical.component.useradmin","logoutUser","com.oxymedical.component.useradmin.UserAdminComponent",userId);
//	updateSession("OLDTime",null);
//	updateSession("login", null);
//	setDataStatus("DEFAULT", pageId, null);
//	updateSession("userId",null);
//	session.invalidate();
//	return retValue;
//
//}
//
//public boolean checkSessionTime(String method)
//{
//	boolean isSession = true;
//	if(method.equalsIgnoreCase("logoutUser"))
//	{
//		return isSession;
//	}
//	String oldTime = getSessionData("OLDTime");
//	String userId = getSessionData("userId");
//	
//	if(userId==null)
//	{
//		return isSession;
//	}
//	if(oldTime!=null)
//	{
//		Date oldDate = DateUtil.stringToDate(oldTime, "yyyy-MM-dd HH:mm:ss");
//		Date newDate = new Date();
//		Long oldTimeValue = oldDate.getTime();
//		Long currentTime = newDate.getTime();
//		Long timeDiff = currentTime-oldTimeValue;
//		long totalMinutes = timeDiff / (60 * 1000);
//		String time=PropertyUtil.setUpProperties("SESSION_TIME");
//		long sessionTime=session.getMaxInactiveInterval();
//		if(time!=null)
//		{
//		
//			sessionTime = Integer.parseInt(time);
//		}
//		if(totalMinutes>sessionTime)
//		{
//			isSession=false;
//			message("Sorry your session has timed out. Please sign in again.");		
//			logOut("login");
//			updateSession("LogOut","true");
//			session.invalidate();
//			return false;
//		}
//	}
//	Date newDate = new Date();	
//	String toDate = DateUtil.formatDate(newDate, "yyyy-MM-dd HH:mm:ss");
//	updateSession("OLDTime",toDate);
//	return isSession;
//
//}
//
//public void checkFormValue(String id,String value)
//{
//	String oldValue = formValues.get(id);
//	if(oldValue !=null)
//	{
//		if(oldValue.equalsIgnoreCase(value))
//		{
//			updatedMsg = "Patient Information has not been updated";
//		}
//		else
//		{
//			updatedMsg = null;
//		}
//	}
//	else
//	{
//		updatedMsg = null;	
//	}
//	updateSessionList("formValues",formValues);
//	updateSession("updatedMsg",updatedMsg);
//}
//
//public String getUpdatedMessage()
//{
//
//	return updatedMsg;
//}
//
// 
//public void paginalSetter(String gridName)
//{
//	Object rootFormValue = self.getRoot();
//	Object gridObj = rootFormValue.getVariable(gridName, true);
//	try
//	{
//		gridObj.setPaginal(self);
//	}
//	catch (Exception e)
//	{
//		System.out.println("!!ERROR!! Either '" + gridName + "' is not a Listbox / Grid or '" + self.id + "' is not a Paging component");
//	}
//}
////Added method to set height by pra on 04-june-2009
//public void setHeight(String controlId,String height)
//{
//	Object rootFormValue = self.getRoot();
//	Object controlObj = rootFormValue.getVariable(controlId, true);
//	controlObj.setHeight(height+"px");
//}
////Used to break the String according to pattern and return the rquired string .added by pra
//public String splitString(String value,String pattern)
//{  
//	if (value == null) return null;
//	String[] values= StringUtil.splitString(value,pattern);
//	if(values.length>1)
//	{
//		value=values[0];
//	}	
//	return value;
//}
//
//public boolean messageWithQuestion(String message, String titleStr){
//	Messagebox.setTemplate("/templates/OMMessagebox.zul");
//	if (!(Messagebox.show(message, 
//					titleStr, Messagebox.YES | Messagebox.NO,
//					"Messagebox.QUESTION") == Messagebox.YES)) {
//		return false;				
//	}else{ 
//		return true;
//	}
//}
//
////added method to display question done by pra on 5-june-2009
//public boolean messageWithQuestion(String message){
//	Messagebox.setTemplate("/templates/OMMessagebox.zul");
//	if (!(Messagebox.show(message, 
//					"SunPharma", Messagebox.YES | Messagebox.NO,
//					"Messagebox.QUESTION") == Messagebox.YES)) {
//		return false;				
//	}else{ 
//		return true;
//	}
//}
//
///*
//*This method check loging value is blank or not.
//if blank show worning msg without sending request to dbcomponent
//*/
//
//public boolean checkLoginBlankValue(String txtOne,String txtTwo)
//{
//	String valueOne = formValues.get(txtOne);
//	String valueTwo = formValues.get(txtTwo);
//	if(valueOne == null || valueTwo ==null)
//	{
//		formValues.remove(txtOne);
//		formValues.remove(txtTwo);
//		return false;
//	}
//	return true;
//
//}
//
///*
//Following methods are used for disable and enable control
//
//*/
//public void setDisable(String[] control)
//{
//	Object rootFormValue = self.getRoot();
//	
//	for(int counter=0; counter<control.length;counter++)
//	{
//		String controlId = control[counter];
//		Object controlObj = rootFormValue.getVariable(controlId, true);
//		controlObj.setDisabled(true);
//		
//	}
//
//
//}
//
//
//public void setEnable(String[] control)
//{
//	Object rootFormValue = self.getRoot();
//	
//	for(int counter=0; counter<control.length;counter++)
//	{
//		String controlId = control[counter];
//		Object controlObj = rootFormValue.getVariable(controlId, true);
//		controlObj.setDisabled(false);
//		
//	}
//
//
//}
////Method added by pra to check if userId is avaible on 09-june-2009
//public boolean isRecordExist()
//{
//boolean retVal=false;
//
//	if(returnHicData ==null)
//	{
//		return false;
//	}
//	else
//	{
//		IData dataUnit = returnHicData.getData();
//		List listValue = null;
//		if(dataUnit.getQueryData().getListData()!=null)
//		{
//			listValue = dataUnit.getQueryData().getListData();
//		}
//		else
//			return false;
//		String[][] allValues = dataUnit.getQueryData().iterateListData(listValue);
//		if(allValues!=null && allValues.length>0)
//		{
//		 retVal=true;
//		 return retVal;
//		}
//	}
//	return retVal;
//}
////Method added by pra to set backround color in case of error avaible on 09-june-2009
//public void setErrorColor(String[] control)
//{
//	Object rootFormValue = self.getRoot();
//	String errorBGColor ="background:#fad8d8";
//	for(int counter=0; counter<control.length;counter++)
//	{
//		String controlId = control[counter];
//		Object controlObj = rootFormValue.getVariable(controlId, true);
//		controlObj.setStyle(errorBGColor);		
//	}
//
//}
//
//
////this method is used to remove childs from the list if they are present in the list from which the data is moved from one list to second list
////added by pra 11 june 2009
//public removeChilds(String parentControl,String controlId)
//{  
//
//   Object rootFormValue = self.getRoot();   	
//   Object controlObj = rootFormValue.getVariable(controlId,true);
// 	Object parentObj = rootFormValue.getVariable(parentControl,true);
//   	List list =parentObj.getChildren();
//   	List controlList =controlObj.getChildren();
//   	for(int i=0;i<list.size();i++)
//   	{
//   	Listitem parentChild = (Listitem)list.get(i);
//   	String pLabel=parentChild.getLabel();
//   	  for(int j=0;j<controlList.size();j++)
//   	  {
//   	     Listitem controlChild = (Listitem)controlList.get(j);
//   	     String cLabel=controlChild.getLabel();
//   	     if(pLabel.equalsIgnoreCase(cLabel))
//	     {
//	     controlObj.removeChild(controlChild);
//	     }				
//	 
//   	  }
//   	}
// }
// 
// 
// //This method is used to setUpdatedvalue in case if user is on the same form and formvalues is not changed.
////Added by pra on 15june2009
// public void setUpdatedValue(String formId)
// {
//   updatedMsg =formId;
// }
//
// //this added for the search of user. 
// //Added by pra on 15june2009
//public String createUserQueryForSearch(String lastNameValue, String firstNameValue, String userIdValue)
//{
//	String conditionStr ="";
//	formValues = new Hashtable();
//	if(userIdValue!=null)
//	{
//		conditionStr = conditionStr + "user_.userId:=["+userIdValue+"] and ";
//	}
//	
//	if(lastNameValue!=null)
//	{
//		conditionStr = conditionStr +"contact_.lastName:=["+lastNameValue+"] and ";
//	}
//	if(firstNameValue!=null)
//	{
//		conditionStr = conditionStr +"contact_.firstname:=[" +firstNameValue+"] and ";
//	}
//	
//	conditionStr = conditionStr + "user_.userId:=contact_.userId and user_.deleted:=[0]";
//	conditionStr = conditionStr.trim();
//	return conditionStr;
//}
////this method is used to get ParentForm in case of placeholder .added by pra on 16-june-2009
//public String getParentFormPattern(String formId)
//{
//	 Object rootFormValue = self.getRoot();
//	 Object controlObj = rootFormValue.getVariable(formId,true);
//	 String parentId=controlObj.getAttribute("formpattern");
//	 return parentId;
//}
////this method is used to  add formValues  .added by pra on 16-june-2009
//public void addFormValues(Hashtable values)
//{ 
//	formValues=values;
//}
////this is method used to display logo question added by pra on 16-june-2009
//public String messageLogoQuestion(String message)
//{
//	int value=(Messagebox.show(message,
//						"NOLIS", Messagebox.YES |Messagebox.NO|Messagebox.CANCEL,
//						"Messagebox.QUESTION")) ;
//	if(value==cancel)
//	{								
//		return "cancel";
//	}
//	else if(value==yes)
//	{
//	return "yes";
//	}
//	else 
//	return "no";	
//
//}		
//
//
//public void setListDataInSession(String id)
//{
//		if(returnHicData ==null)
//		{
//			return;
//		}
//		IData dataUnit = returnHicData.getData();
//		List listValue = null;
//		if(dataUnit.getQueryData().getListData()!=null)
//		{
//			listValue = dataUnit.getQueryData().getListData();
//		}
//		else
//			return;
//			
//		if(listValue!=null)
//		{
//			//updateSessionList(id,listValue);
//		}
//}
//
//
//public void clearQueryValue(String variable)
// {
//	Object rootFormValue = self.getRoot();    
//	String countStr = formValues.get("counter");
//	if(countStr!=null)
//	{
//		int value = Integer.parseInt(countStr);
//		String id = variable.substring(0, variable.length()-1);;
//		
//		for(int counter=1;counter<=value;counter++)
//		{
//			Object controlObj = rootFormValue.getVariable(id+counter,true);
//			if(controlObj instanceof Checkbox)
//			{
//				controlObj.setChecked(false);
//				formValues.remove(controlObj.getId());
//			}
//			else if(controlObj !=null)
//			{
//				controlObj.setText("");
//				formValues.remove(controlObj.getId());
//			}
//		}
//	}
//	else
//	{
//		Object controlObj = rootFormValue.getVariable(variable,true);
//		if(controlObj instanceof Checkbox)
//			{
//				controlObj.setChecked(false);
//				formValues.remove(controlObj.getId());
//			}
//			else if(controlObj!=null)
//			{
//				controlObj.setText("");
//				formValues.remove(controlObj.getId());
//			}
//	}
// }
// 
// public void clearRangeValue(String range)
// {
//	Object rootFormValue = self.getRoot();    
//	String countStr = formValues.get("counter");
//	if(countStr!=null)
//	{
//		int value = Integer.parseInt(countStr);
//		String range1 = "firstrange";
//		String range2 = "secondrange";
//		for(int counter=1;counter<=value;counter++)
//		{
//			Object controlObj = rootFormValue.getVariable(range1+counter,true);
//			if(controlObj !=null)
//			{
//				controlObj.setText("");
//				formValues.remove(controlObj.getId());
//			}
//			controlObj = rootFormValue.getVariable(range2+counter,true);
//			if(controlObj !=null)
//			{
//				controlObj.setText("");
//				formValues.remove(controlObj.getId());
//			}
//		}
//	}
//	else
//	{
//		Object controlObj = rootFormValue.getVariable("firstrange1",true);
//		controlObj.setText("");
//		controlObj = rootFormValue.getVariable("secondrange1",true);
//		controlObj.setText("");
//	}
// }
// 
// // This method used to removed window popup
// // id would be popupwindow name.
// public void detachWindow(String id)
//{
//	Object rootFormValue = self.getRoot();    
//	Object controlObj = rootFormValue.getVariable(id,true);
//	if(controlObj !=null)
//	{
//	controlObj.detach();
//	}
//
//}
// 
// public void setAllTo(String variable)
// {
//	Object rootFormValue = self.getRoot();    
//	String catName = getSessionData("CategoryName");
//	String fieldName = getSessionData("FieldName");
//	String variableValue = getSessionData("VariableValue");
//	String logValue = getSessionData("LogicalName");
//	String realValue = null;
//	if(catName!=null)
//	{
//		realValue = catName;
//	}
//	else if(fieldName!=null)
//	{
//		realValue = fieldName;
//	}
//	else if(variableValue!=null)
//	{
//		realValue = variableValue;
//	}
//	else if(logValue!=null)
//	{
//		realValue = logValue;
//	}
//	
//	Hashtable newformValues = session.getAttribute("CatFormValue");
//	String countStr = null;
//	if(newformValues!=null)
//	{
//		countStr = newformValues.get("counter");
//			formValues = newformValues;
//	}
//	else
//	{
//		countStr = formValues.get("counter");
//			
//	}
//	if(countStr!=null)
//	{
//		int value = Integer.parseInt(countStr);
//		String id = variable.substring(0, variable.length()-1);;
//		
//		for(int counter=1;counter<=value;counter++)
//		{
//			Object controlObj = rootFormValue.getVariable(id+counter,true);
//				if(controlObj instanceof Checkbox)
//			{
//				controlObj.setChecked(true);
//			}
//			else if(realValue!=null && controlObj!=null)
//			{
//				controlObj.setText(realValue);
//				formValues.put(id+counter,realValue);
//			}
//		}
//	}
//	else
//	{
//		Object controlObj = rootFormValue.getVariable(variable,true);
//		
//		if(controlObj instanceof Checkbox)
//			{
//				controlObj.setChecked(true);
//			}
//			else if(realValue!=null && controlObj!=null)
//			{
//				controlObj.setText(realValue);
//				formValues.put(id+counter,realValue);
//			}
//	}
//	updateSession("FieldName",null);
//	updateSession("CategoryName",null);
//	updateSession("VariableValue",null);
//	updateSession("LogicalName",null);
//	session.setAttribute("CatFormValue",formValues);
// }
// 
// 
// public Object getListValue(String str)
// {
// return session.getAttribute(str);
// }
// 
// // this method return list selected data id.
// 
//  public String getSelectedListId()
//  {
//	 Listitem list = self.getSelectedItem();
//	 String id = list.getValue();
//	 return id;
//  }
//  
//   public String getSelectedListLabel()
//  {
//	 Listitem list = self.getSelectedItem();
//	 String label = list.getLabel();
//	 return label;
//  }
//  
//  
//  public void setRangeTo(String range)
// {
//	Object rootFormValue = self.getRoot();    
//	String range1 = getSessionData("range1");
//	String range2 = getSessionData("range2");
//	String countStr = null;
//	countStr = formValues.get("counter");
//	if(countStr!=null)
//	{
//		int value = Integer.parseInt(countStr);
//		String id1 = "firstrange";
//		String id2 = "secondrange";
//		for(int counter=1;counter<=value;counter++)
//		{
//			Object controlObj = rootFormValue.getVariable(id1+counter,true);
//			if(controlObj !=null  && range1!=null && range1!="")
//			{
//				controlObj.setText(range1);
//				formValues.put(id1+counter,range1);
//			}
//			controlObj = rootFormValue.getVariable(id2+counter,true);
//			if(controlObj !=null  && range2!=null && range2!="")
//			{
//				controlObj.setText(range2);
//				formValues.put(id2+counter,range2);
//			}
//		}
//	}
//	else
//	{
//		Object controlObj = rootFormValue.getVariable("firstrange1",true);
//		if(controlObj !=null && range1!=null && range1!="")
//		{
//			controlObj.setText(range1);
//			formValues.put("firstrange1",range1);
//			
//		}
//		controlObj = rootFormValue.getVariable("secondrange1",true);
//		if(controlObj !=null && range2!=null && range2!="")
//		{
//			controlObj.setText(range2);
//			formValues.put("secondrange1",range2);
//		}
//	}
//	updateSession("range1",null);
//	updateSession("range2",null);
//	session.setAttribute("CatFormValue",formValues);
// }
// 
// 
////this method is used to set Data on the schedular added by pra on june 24-2009
// 
//  public void setDataInCalendars(Object obj)
// {
// 
//
// if(returnHicData ==null)
//		{
//			return;
//		}
//		else
//	{
//
//		IData dataUnit = returnHicData.getData();
//		List listValue = null;
//		if(dataUnit.getQueryData().getListData()!=null)
//		{
//			listValue = dataUnit.getQueryData().getListData();
//		}
//		else
//			return;
//		String[][] allValues = dataUnit.getQueryData().iterateListData(listValue);		
//		SimpleCalendarModel cm = new SimpleCalendarModel();
//        String[]  colors={"#D96666","#0D7813","#B373B3"};
//		int count=0;
//		
//		if(allValues !=null)
//		{
//			for(int i=0;i<allValues.length;i++)
//			{   
//				if(count>2)
//				{
//				count=0;
//				}
//			    SimpleCalendarEvent sce = new SimpleCalendarEvent();
//				String startDate=allValues[i][0];
//				String endDate=allValues[i][1];
//				String startTime=allValues[i][2];
//				String endTime=allValues[i][3];
//				String studyType=null;
//				if(startDate!=null && endDate!=null && startTime!=null && endTime!=null )
//				{
//				startDate=startDate.replaceAll("-","/");
//				endDate=endDate.replaceAll("-","/");	
//				String[]  stimes=startTime.split(":");
//				String[]  etimes=endTime.split(":");
//				startDate=startDate+" "+stimes[0]+":"+stimes[1];
//				endDate=endDate+" "+etimes[0]+":"+etimes[1];
//				SimpleDateFormat dataSDF = new SimpleDateFormat("yyyy/MM/dd HH:mm");	
//				sce.setBeginDate(dataSDF.parse(startDate));
//				sce.setEndDate(dataSDF.parse(endDate));	
//				studyType=allValues[i][7];	
//				if(studyType==null)
//				{
//				studyType="";
//				}			
//				sce.setContent("Name: "+allValues[i][6]+" "+allValues[i][5]+"\n"+"Studytype: "+studyType);
//				sce.setTitle("Appointment Id: "+allValues[i][4]);
//				String color=colors[count];
//				sce.setHeaderColor(color);
//	            sce.setContentColor(color);
//				cm.add(sce);
//				count++;
//				}			
//			}
//		}
//		
//		obj.setModel(cm);
//	}
//}
// //This method is for schedular to retrive information of id.Added by pra on june-24-2009
//public String onEditEventCalendar()
//{
//CalendarsEvent evt = (CalendarsEvent) event;
//CalendarEvent ce = evt.getCalendarEvent();
//String title=ce.getTitle();
//String[] ids=title.split(":");
//String id=ids[1];
//return id;
//}
//
// 
//  
//  
////This Method used to show saved query details
//  
//public void showQueryUI(String frameId)
//{
//		if(returnHicData ==null)
//		{
//			return false;
//		}
//		Object rootFormValue = self.getRoot(); 
//		//first remove added component
//		deleteAllComponent("remove");
//		
//		IData dataUnit = returnHicData.getData();
//		List listValue = null;
//		if(dataUnit.getQueryData().getListData()!=null)
//		{
//			listValue = dataUnit.getQueryData().getListData();
//		}
//		else
//			return;
//		String[][] allValues = dataUnit.getQueryData().iterateListData(listValue);
//		if(allValues==null)
//		{
//			return;
//		}
//		for(int counter=0;counter<allValues.length;counter++)
//		{
//		if(counter==0)
//		{
//		showQueryComponent(rootFormValue,allValues[counter][0],allValues[counter][1],allValues[counter][2],allValues[counter][3],allValues[counter][4],allValues[counter][6]);
//		}
//		else
//		{
//				addQueryComponent(frameId,allValues[counter][0],allValues[counter][1],allValues[counter][2],allValues[counter][3],allValues[counter][4],allValues[counter][5],allValues[counter][6]);
//		}
//		}
//			
//}
//  
// // This method used to display saved query details, which added at run time
//
//
////This method delete all condition component which are added after selecting the custom query
//
//public void deleteAllComponent(String str)
//{
//	Object rootFormValue = self.getRoot();
//	String count = formValues.get("counter");
//	if(count!=null)
//	{
//	
//		int countValue = Integer.parseInt(count);
//		for(int loopCounter=1; loopCounter<=countValue;loopCounter++)
//		{
//			Object obj =rootFormValue.getVariable(str+loopCounter, true);
//			if(obj==null)continue;
//			Object div =rootFormValue.getVariable(obj.getParent().getParent().getParent().getParent().getId(), true);
//			obj =rootFormValue.getVariable(div.getParent().getId(), true);
//			obj.removeChild(div);			
//			formValues.remove("secondrange"+loopCounter);
//			formValues.remove("firstrange"+loopCounter);
//			formValues.remove("variable"+loopCounter);
//			formValues.remove("field"+loopCounter);
//			formValues.remove("logical"+loopCounter);
//			formValues.remove("category"+loopCounter);
//			formValues.remove("check"+loopCounter);
//			
//		}
//	}
//	Object obj =rootFormValue.getVariable("category1", true);
//	if(obj!=null)obj.setText("");
//	Object obj =rootFormValue.getVariable("field1", true);
//	if(obj!=null)obj.setText("");
//	Object obj =rootFormValue.getVariable("variable1", true);
//	if(obj!=null)obj.setText("");
//	Object obj =rootFormValue.getVariable("firstrange1", true);
//	if(obj!=null)obj.setText("");
//	Object obj =rootFormValue.getVariable("secondrange1", true);
//	if(obj!=null)obj.setText("");
//	Object obj =rootFormValue.getVariable("check1", true);
//	if(obj!=null)obj.setChecked(false);
//	formValues.remove("secondrange1");
//	formValues.remove("firstrange1");
//	formValues.remove("variable1");
//	formValues.remove("field1");
//	formValues.remove("logical1");
//	formValues.remove("category1");
//	formValues.remove("check1");
//		
//}  
//
////this method is used to compare the times.added by pra on june 26 2009.
//public boolean validateTime(String startId,String endId,String startDateId,String endDateId)
//{
//	Object rootFormValue = self.getRoot();
//	Object startTime  = rootFormValue.getVariable(startId,true);
//	Object endTime  = rootFormValue.getVariable(endId,true);
//	Object startDate  = rootFormValue.getVariable(startDateId,true);
//	Object endDate = rootFormValue.getVariable(endDateId,true);
//	Date sDate=startDate.getValue();
//	Date eDate=endDate.getValue();
//	String sTimeValue=formValues.get(startId).toString();
//	String eTimeValue=formValues.get(endId).toString();
//	int result=DateUtil.compareDates(sDate,eDate);	
//	if((sTimeValue)!=null && (eTimeValue)!=null)
//	{
//	String[] startTimeValue=(sTimeValue).split(":");
//	String[] endTimeValue=(eTimeValue).split(":");	
//	String sValue=startTimeValue[0]+"."+startTimeValue[1];
//	String eValue=endTimeValue[0]+"."+endTimeValue[1];
//	double sTime= Double.valueOf(sValue).doubleValue();
//	double eTime=Double.valueOf(eValue).doubleValue();	
//	if(eTime>sTime && result==0)
//	{
//		if((eTime-sTime)>.29)
//		{
//			return true;
//		}
//		else
//		{
//			message("End time should be 30 minutes greater than start time.");
//			return false;
//		}
//	}
//	else if(result<0 )
//	{
//		return true;
//	}		
//	else
//	{
//		message("End time should be greater than start time.");
//		return false;
//	}
//	}
//	else
//	{
//		message("Either start time or end time is blank.");
//		return false;
//	}
//	
//	return false;
//}
//  
//
//public showCSVFile(String id)
//{
//	List list = session.getAttribute("dbListValue");
//	if(list == null || list.size()<=0)
//	{
//		message("No record found.");
//		return;
//	}
//	String filePath = PropertyUtil.setUpProperties("CSV_DIR")+"/Report.csv";
//	File file = new File(filePath);
//	Filedownload.save(file,"text/plain");
//}
//
//
//
//public void createConditionList(String str)
//{
//	List categoryList=new ArrayList();
//	List fieldList=new ArrayList();
//	List variableList=new ArrayList();
//	List firstrangeList=new ArrayList();
//	List secondrangeList=new ArrayList();
//	List logicalList=new ArrayList();
//	
//	Object rootFormValue = self.getRoot();    
//	String countStr = null;
//	countStr = formValues.get("counter");
//	if(countStr!=null)
//	{
//		int value = Integer.parseInt(countStr);
//		for(int counter=1;counter<=value;counter++)
//		{
//			String categoryValue = formValues.get("category"+counter);	
//			if(categoryValue!=null)
//			{
//				categoryList.add(categoryValue);
//			}
//		}
//		formValues.put("combobox6",categoryList);
//		
//		for(int counter=1;counter<=value;counter++)
//		{
//			String fieldValue = formValues.get("field"+counter);	
//			if(fieldValue!=null)
//			{
//				fieldList.add(fieldValue);
//			}
//		}
//		formValues.put("combobox7",fieldList);
//		for(int counter=1;counter<=value;counter++)
//		{
//			String variableValue = formValues.get("variable"+counter);
//			if(variableValue !=null)
//			{
//				variableList.add(variableValue);
//			}
//		}
//		formValues.put("textbox38",variableList);
//		
//		for(int counter=1;counter<=value;counter++)
//		{
//			String range1 = formValues.get("firstrange"+counter);
//			if(range1 !=null)
//			{
//				firstrangeList.add(range1);
//			}
//		}
//		formValues.put("textbox39",firstrangeList);
//		for(int counter=1;counter<=value;counter++)
//		{	
//			String range2 = formValues.get("secondrange"+counter);
//			if(range2 !=null)
//			{
//				secondrangeList.add(range2);
//			}
//		}
//		formValues.put("textbox40",secondrangeList);
//		for(int counter=1;counter<=value;counter++)
//		{
//			String logicalValue = formValues.get("logical"+counter);
//			if(logicalValue !=null)
//			{
//				logicalList.add(logicalValue);
//			}
//		}
//		formValues.put("log1",logicalList);
//		
//	}
//	else
//	{
//			String categoryValue = formValues.get("category1");
//			if(categoryValue !=null)
//			{
//				categoryList.add(categoryValue);
//				formValues.put("combobox6",categoryList);
//			}
//			String fieldValue = formValues.get("field1");
//			if(fieldValue !=null)
//			{
//				fieldList.add(fieldValue);
//				formValues.put("combobox7",fieldList);
//			}
//			String variableValue = formValues.get("variable1");
//			if(variableValue !=null)
//			{
//				variableList.add(variableValue);
//				formValues.put("textbox38",variableList);
//			}
//			String range1 = formValues.get("firstrange1");
//			if(range1 !=null)
//			{
//				firstrangeList.add(range1);
//				formValues.put("textbox39",firstrangeList);
//			}
//			String range2 = formValues.get("secondrange1");
//			if(range2 !=null)
//			{
//				secondrangeList.add(range2);
//				formValues.put("textbox40",secondrangeList);
//			}
//			String logicalValue = formValues.get("logical1");
//			if(logicalValue !=null)
//			{
//				logicalList.add(logicalValue);
//				formValues.put("log1",logicalList);
//			}
//			
//	}
//	
//}
//
//
//public boolean searchInputCheck(String firstName,String lastname,String mrn,String dob)
//{
//	if(firstName !=null && firstName !="")return true;
//	if(lastname!=null && lastname !="")return true;
//	if(mrn!=null && mrn !="")return true;	
//	if(dob!=null && dob !="")return true;	
//	return false;
//}
//
////this method is used to return unqiue number .added by pra on 29-june-2009
//public int generateUniqueNumber()
//{
//Random generator = new Random();
//int r = Math.abs(generator.nextInt()) % 1000000;
//return r;
//}
//
//
//// This method used to save custom query in databases.
//
//public void clearFieldData(String[] data)
//{
//	Object rootFormValue = self.getRoot();
//	
//	for(int counter=0;counter<data.length;counter++)
//	{
//		Object obj =rootFormValue.getVariable(data[counter], true);
//		if(obj!=null)
//		{
//			obj.setText("");
//		}
//		formValues.remove(data[counter]);		
//	}
//}	
////method added to retrive the file path done by pra
//public String getReportFilePath()
//{
//	String reportFilePath=null;
//	if(returnHicData ==null)
//			{
//				return;
//			}
//			else
//	{
//
//		IData dataUnit = returnHicData.getData();
//		List listValue = null;
//		if(dataUnit.getQueryData().getListData()!=null)
//		{
//			listValue = dataUnit.getQueryData().getListData();
//		}
//		else
//			return null;
//		String[][] allValues = dataUnit.getQueryData().iterateListData(listValue);
//		if(allValues!=null)
//		{
//		reportFilePath=allValues[0][0];
//		}
//	}
//	return reportFilePath;
//}
//
//// This method returns the current date as String.
//public String getCurrentDate(String dateFormat)
//{
//	Date toDayDate =new Date();
//	String toDate = DateUtil.formatDate(toDayDate, dateFormat);
//	return toDate;
//}
//
//public Date convertIntoDate(String dob,String dateId)
//{
//	String dateStr=dob +" "+"00:00";
//	Date date = DateUtil.stringToDate(dateStr,"yyyy-MM-dd hh:mm");
//	Object rootFormValue = self.getRoot();
//	Object obj =rootFormValue.getVariable(dateId, true);
//	if(obj!=null)
//	{
//		obj.setValue(date);
//	}
//	return date;
//}
//
////this method remove the all list children
//public removeChildFromList(String controlId)
//{  
//   Object rootFormValue = self.getRoot();   	
//   Object controlObj = rootFormValue.getVariable(controlId,true);
//   List controlList =controlObj.getChildren();
//   List dupList = new ArrayList(controlList);
//   for(int j=0;j<dupList.size();j++)
//   {
//		if(dupList.get(j) instanceof Listitem)
//		{
//		     Listitem controlChild = (Listitem)dupList.get(j);
//		     controlObj.removeChild(controlChild);
//		}
//  	}
//}
//
////this method is used to get the object of calendar or other required object added by pra on july-14-2009
//public Object getSelectedObject(HtmlBasedComponent formObj,Class requiredObject)
//{ 
//	Object val=null;
//	String requiredClass=requiredObject.toString();
//	List childElement = formObj.getChildren();
//	Iterator iter = childElement.iterator();
//	while (iter.hasNext())
//	{
//		Object value = iter.next();
//		String valueClass=value.getClass().toString();
//		if(valueClass.equalsIgnoreCase(requiredClass))
//		{
//			val=value;
//			return value;
//		}
//		else 
//		{
//			Object values= getSelectedObject(((HtmlBasedComponent) value),requiredObject);
//			if(values!=null)
//			{
//				val=values;
//				return val;
//			}
//		}
//	}
//return val;
//}
//
//
//
//
////This is temporary method.
//public void showFormValues()
//{
//	alert(formvalues);
//}
//
////Following method used to store and restore formvalues in session. When you need old FormValues.
//
//public storFormValue(String id)
//{
//	session.setAttribute(id,formValues);
//
//}
//
//public restorFormValue(String id)
//{
//	if(session.getAttribute(id) !=null)
//	formValues = session.getAttribute(id);
//	updatedMsg = null;
//
//}
//
//
//
//
//
////this method is added to create the list added by pra on 22-june-2009
//public List createChildListItems(Object obj)
//{
//	List list=new ArrayList();
//	List childs=obj.getChildren();
//	for(int i=1;i<childs.size();i++)
//	{
//		Listitem li=childs.get(i);
//		List nextchilds=li.getChildren();	
//		for(int j=0;j<nextchilds.size();j++)
//		{
//			Listcell cell=nextchilds.get(j);
//			if(cell.getLabel()!="empty")
//			{
//				list.add(li);
//			}
//		}	
//	}
//return list;
//}
////this method is added to remove child list items added by pra on 22-june-2009
//public void removeChildListItems(Object obj)
//{
//	List childs=obj.getChildren();
//	List newList=new ArrayList(childs);	
//	for(int i=1;i<newList.size();i++)
//	{
//		obj.removeChild(newList.get(i));
//	}
//}
//
////used to get username based on userid added by pra on 22/07/2009
//public String getUserNameBasedOnUserId(String userId)
//{
//	invokeComponent("com.oxymedical.component.useradmin","GetListUserAdmin","com.oxymedical.component.useradmin.UserAdminComponent","get contact_.userName from useradmin.contact_ conditions contact_.userId:=["+userId+"]");
//	String userName=getUserName();
//	return userName;
//}
////used to get username based on userid added by pra on 22/07/2009	
//public String getUserName()
//{
//	String userName=null;
//	if(returnHicData ==null)
//	{
//		return null;
//	}
//	else
//	{
//		IData dataUnit = returnHicData.getData();
//		List listValue = null;
//		if(dataUnit.getQueryData().getListData()!=null)
//		{
//			listValue = dataUnit.getQueryData().getListData();
//		}
//		else
//			return null;
//		String[][] allValues = dataUnit.getQueryData().iterateListData(listValue);
//		if(allValues==null)
//		{
//		return null;
//		}
//		else
//		{
//			for(int i=0;i<allValues.length;i++)
//			{
//			userName=allValues[i][0];
//			}
//		}
//	}
//return 	userName;	
//}
//
//
//
//
////This method is used to store combodata only in uilibry at run time.
//public void storeComboData(String comboId)
//{
//	IHICData hicData = null;
//	if(returnHicData ==null)
//	{
//			return;
//	}
//	else
//	{
//			hicData = returnHicData;
//	}
//	Object rootFormValue = self.getRoot();
//	Object comboObj = rootFormValue.getVariable(comboId, true);
//	IData dataUnit = hicData.getData();
//	List listValue = null;
//	if(dataUnit.getQueryData().getListData()!=null)
//	{
//		listValue = dataUnit.getQueryData().getListData();
//	}
//	else
//		return;
//	if(listValue !=null)
//	{
//	comboTable.put(comboObj.getId(),listValue);
//	}
//}
//
//public String getDataFromAllValuesVar(int firstIndex, int secondIndex)
//{
//	String retVal = null;
//	String[][] allValues = null;
//	if(null != returnHicData)
//	{	
//		IData dataUnit = returnHicData.getData();
//		List listValue = null;
//		if(dataUnit.getQueryData().getListData()!=null)
//		{
//			listValue = dataUnit.getQueryData().getListData();
//		}
//		else
//			return null;
//		allValues = dataUnit.getQueryData().iterateListData(listValue);
//	if (null != allValues && allValues.length>0)
//		{ 
//		  if(allValues[firstIndex]!=null && allValues[firstIndex].length>0)
//			retVal = allValues[firstIndex][secondIndex];
//		}
//	}
//	return retVal;
//}
//
//
//public String getDataFromFormValues(String id)
//{
//	String value = null;
//	if(id !=null)
//	{
//		value = formValues.get(id);
//	}
//	return value;
//	
//}
//
//
////This method check that date is valid or not.
//public boolean dateValidation(String dobValue)
//{
//	String dateFormat = "yyyy-mm-dd";
//	boolean yes =DateUtil.validateDate(dobValue,dateFormat);
//	return yes;
//
//}
//
//public void showDataInGrid(String id)
//{
//	Object rootFormValue = self.getRoot();
//	Object gridObj = rootFormValue.getVariable(id, true);
//	if(gridObj == null)
//	return;
//	String name = getSessionData("name");
//	String dose = getSessionData("dose");
//	String timeValue =getSessionData("time");
//	Listitem li = new Listitem();
//	Listcell lc = new Listcell();
//	boolean isChild = false;
//	if(timeValue !=null && timeValue !="")
//	{
//		lc.setLabel(timeValue);
//		li.appendChild(lc);
//		isChild = true;
//	}
//	lc = new Listcell();
//	if(name !=null && name !="")
//	{
//		lc.setLabel(name);
//		li.appendChild(lc);
//		isChild = true;
//	}
//	if(dose !=null && dose !="")
//	{
//		lc = new Listcell();
//		lc.setLabel( dose);
//		li.appendChild(lc);
//		isChild = true;
//	}
//	if(isChild)
//	gridObj.appendChild(li);
//	
//}
//
//
//// This method return check field name in query builder.
//
//public String getCheckedField()
//{
//	Object rootFormValue = self.getRoot();    
//	String fieldList = "";
//	String countStr = null;
//	countStr = formValues.get("counter");
//	if(countStr!=null)
//	{
//		int value = Integer.parseInt(countStr);
//		for(int counter=1;counter<=value;counter++)
//		{
//			Object controlObj = rootFormValue.getVariable("check"+counter,true);
//			if(controlObj!=null)
//			{
//				boolean checkValue = controlObj.isChecked();
//				if(checkValue)
//				{
//					String fieldName = formValues.get("field"+counter);
//					if(fieldName !=null)
//					{
//						fieldList =fieldList+fieldName;
//						if(counter<value)
//						{
//						fieldList =fieldList+",";
//						}
//					}
//				}
//			}
//		}
//	}
//	else
//	{
//		Object controlObj = rootFormValue.getVariable("check1",true);
//		if(controlObj!=null)
//		{
//			boolean value = controlObj.isChecked();
//				if(value)
//				{
//					String fieldName = formValues.get("field1");
//					if(fieldName !=null)
//					{
//						fieldList =fieldList+fieldName;
//					}
//				}
//		}
//	}
//	return fieldList;
//}
//
//// concatenate string 
//public String addString(String first,String second,String third)
//{
//	String result = first+ second+third;
//	return result;
//}
//
//// This method return no of header of list or grid.
//public int getNoOfHeader(Object gridObj)
//{
//	List list =gridObj.getChildren();
//	int headerNo=0;
//		if(list!=null && list.size()>0)
//		{
//			Iterator itr = list.iterator();
//			while(itr.hasNext())
//			{
//				Object obj = itr.next();
//				if(obj instanceof Listhead)
//				{
//					List listHead =obj.getChildren();
//					headerNo = listHead.size();
//					break;
//				}
//			}
//		}
//	return headerNo;
//}
//
//public String[][] getGridLengthValue(String[][] allValues, int len)
//	{
//		String[][] dupValue = new String[allValues.length][];;
//		for(int valueCounter =0;valueCounter<allValues.length;valueCounter++)
//		{
//			dupValue[valueCounter] = new String[len];
//			for(int col =0;col<len;col++)
//			{
//				dupValue[valueCounter][col] = allValues[valueCounter][col];
//			}
//		
//		}
//		return dupValue;
//
//	}
//
//	
////method is used to get select startDate,startTime,endDate,endTime from schedular.added by pra on 1/8/2009
//public boolean getDefaultValuesForSchedular(String id)
//{
//	boolean isValid=false;
//	CalendarsEvent evt = (CalendarsEvent) event;
//	List list = new ArrayList();
//	Date beginDate=evt.getBeginDate();
//
//	String date=DateUtil.formatDate(beginDate,"yyyy-MM-dd");
//	Date dateValue=DateUtil.stringToDate(date,"yyyy-MM-dd");
//	String currentDate = getCurrentDate("yyyy-MM-dd");
//	Date currentDateValue=DateUtil.stringToDate(currentDate,"yyyy-MM-dd");
//	int resultCompare=DateUtil.compareDates(dateValue,currentDateValue);
//	if(resultCompare>=0)
//	{
//		isValid=true;
//		Date endDate=evt.getEndDate();
//		String startDate=DateUtil.formatDate(beginDate,"yyyy-MM-dd,HH:mm:ss");
//		String endDateTime=DateUtil.formatDate(endDate,"yyyy-MM-dd,HH:mm:ss");
//		String[] startTime=startDate.split(",");
//		String[] endTime=endDateTime.split(",");
//		list.add(startTime[0]);
//		list.add(endTime[0]);
//		list.add(startTime[1]);
//		list.add(endTime[1]);
//		updateSessionList("defaultValues",list);
//	}
//	else
//	{
//		message("Please select current or future date.");
//	}
//	return isValid;
//}
////method to set default values in appointment.added by pra on 1/08/2009
//public void setDefaultForSchedule(String startId,String endId,String sTimeId,String eTimeId)
//{
//	Object rootFormValue = self.getRoot();   
//	List defaultValues=session.getAttribute("defaultValues");
//	if(defaultValues!=null && defaultValues.size()>0)
//	{
//	Object startDateObject=rootFormValue.getVariable(startId,true);
//	Object endDateObject=rootFormValue.getVariable(endId,true);
//	Object startTimeObject=rootFormValue.getVariable(sTimeId,true);
//	Object endTimeObject=rootFormValue.getVariable(eTimeId,true);
//	SimpleDateFormat ds1=new SimpleDateFormat("yyyy-MM-dd hh:mm");  
//	String sDate=defaultValues.get(0)+" "+"00:00";
//	String eDate=defaultValues.get(1)+" "+"00:00";
//	Date startDate = ds1.parse(sDate);
//	Date endDate = ds1.parse(eDate);
//	startDateObject.setValue(startDate);
//	endDateObject.setValue(endDate);
//	Time startTime = Time.valueOf(defaultValues.get(2));
//	Time endTime = Time.valueOf(defaultValues.get(3));
//	startTimeObject.setValue(startTime);
//	endTimeObject.setValue(endTime);
//	formValues.put(startId,defaultValues.get(0));
//	formValues.put(endId,defaultValues.get(1));
//	formValues.put(sTimeId,startTime);
//	formValues.put(eTimeId,endTime);
//	updateSessionList("defaultValues",null);
//	}	
//}	
//
//
//
////to check the list. added by pra.
//public boolean checkListSizeValid(List objects,int checkValue)
//{
//	if(objects!=null && objects.size()>checkValue)
//	{
//	return true;
//	}
//	else
//	return false;
//}
//
//
////This method validate that selected dob is valid or not. It don't allow future date.
//public boolean checkDOB(String datepicker1) {
//		
//	boolean isDateValid = true;
//	Object rootFormValue = self.getRoot();
//	isDateValid =uiLibraryUtil.checkDOB(datepicker1,rootFormValue,formValues); 
//	return isDateValid;
//}
//
//
//// SORTING EVENT START
//public void enableSorting(String listHeaderId, String fieldName)
//{
//	Object rootFormValue = self.getRoot();
//	Listheader listheader = rootFormValue.getVariable(listHeaderId, true);
//	listheader.setSortAscending(new RowLabelComparator(fieldName, true));
//	listheader.setSortDescending(new RowLabelComparator(fieldName, false));
//}
//
//public class RowLabelComparator extends FieldComparator 
//{
//	public RowLabelComparator(String orderBy, boolean asc) { super(orderBy, asc); }
//	public int compare(Object o1, Object o2) { return 0; }
//}
//
//public void createSortingEvent(String listId, String pagingId)
//{
//	
//	Object rootFormValue = self.getRoot();
//	Listbox _listBox = rootFormValue.getVariable(listId, true);
//	setListSortListeners(_listBox);
//}
//
///*
// * Sets the listeners. <br>
// * <br>
// * 1. "onSort" for all listheaders that have a sortDirection declared. <br>
// */
//private void setListSortListeners(Listbox listBox) 
//{
//	/*
//	 * Add 'onSort' listeners to the used listheader components. All not
//	 * used Listheaders must me declared as:
//	 * listheader.setSortAscending(""); <br>
//	 * listheader.setSortDescending(""); <br>
//	 */
//	Listhead listhead = listBox.getListhead();
//	List list = listhead.getChildren();
//	for (Object object : list)
//	{
//		if (object instanceof Listheader)
//		{
//			Listheader lheader = (Listheader) object;
//			if (lheader.getSortAscending() != null || lheader.getSortDescending() != null)
//			{
//				lheader.addEventListener("onSort", new OnSortEventListener());
//			}
//		}
//	}
//}
//
//public class OnSortEventListener implements EventListener
//{
//	public void onEvent(Event event) throws Exception
//	{
//		String[] listInfo = PagingInfo.getListInfo();
//		final String componentId = listInfo[0];
//		final String method = listInfo[1];
//		final String classname = listInfo[2];
//		final String condition = listInfo[3];
//		final String listName = listInfo[4];
//		final String pagingId=listInfo[5];
//		Object rootFormValue = self.getRoot();
//	    Object paging = rootFormValue.getVariable(pagingId, true);		
//		paging.setActivePage(0);
//		String orderBy = "", conditions = condition;
//		final Listheader lh = (Listheader) event.getTarget();
//		final String sortDirection = lh.getSortDirection();
//		if (sortDirection.equalsIgnoreCase("ascending"))
//		{ 
//		    
//			final Comparator cmpr = lh.getSortDescending();
//			if (cmpr instanceof FieldComparator)
//			{
//				orderBy = ((FieldComparator) cmpr).getOrderBy();
//				orderBy = orderBy.replace("DESC", "desc").trim();
//				orderBy = orderBy.replace("ASC", "asc").trim();
//			}
//		}
//		else if (sortDirection.equalsIgnoreCase("descending") || sortDirection.equalsIgnoreCase("natural") || sortDirection.equalsIgnoreCase(""))
//		{
//			final Comparator cmpr = lh.getSortAscending();
//			if (cmpr instanceof FieldComparator)
//			{
//				orderBy = ((FieldComparator) cmpr).getOrderBy();
//				orderBy = orderBy.replace("DESC", "desc").trim();
//				orderBy = orderBy.replace("ASC", "asc").trim();
//			}
//		}
//		
//		if (condition.indexOf(" orderby ") > 0)
//		{
//			conditions = conditions.substring(0, condition.indexOf(" orderby "));
//		}
//	
//		if(orderBy!=null && !orderBy.equalsIgnoreCase(""))
//		{
//		conditions = conditions + " orderby " + orderBy;
//		}	
//		invokeComponent(componentId, method, classname, conditions);
//		displayDataInControl(listName);		
//		createPagingEvent(listName);
//	}
//}
//
////for removing given id from formvalues hashtable
//public void removeFromFormValue(String id)
//{
//	formValues.remove(id);
//}
//// SORTING EVENT END	 
//
////this method is added to validate the formValues in case user has clicked the logo
////added by pra on 10 aug 2009.
//public boolean clientSideValidationForLogo(String formId)
//{
//	boolean isValid = true;
//	Object rootFormValue = Path.getComponent("//"+formId+"//"+formId);
//	Window formObj = rootFormValue.getVariable(formId,true);
//	formValues=createFormValues(formObj,formValues);
//	String msg=uiLibraryUtil.clientSideValidation(formObj,formValues );
//	
//	if(msg != null)
//		{
//			msg = msg.trim();
//		}
//		try {
//			if(msg !="" && msg.length() !=0)
//			{
//				//Messagebox.show(msg,title,Messagebox.OK, Messagebox.ERROR);
//				//message(msg);
//				isValid = false;
//			}
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
// 	return isValid;
//}
//
//
//public List getAllRowsListData(String gridId)
//{
//	List returnDataList=new ArrayList();
//	Object rootFormValue = self.getRoot();
//	Object listObject = rootFormValue.getVariable(gridId,true);
//	List childrenObjects=listObject.getChildren();
//	if(childrenObjects!=null && childrenObjects.size()>1)
//	{
//		for(int i=1;i<childrenObjects.size();i++)
//		{
//			Object obj=childrenObjects.get(i);
//			List rowData=new ArrayList();
//			List nextChilds=obj.getChildren();
//			for(int j=0;j<nextChilds.size();j++)
//			{ 
//			rowData.add(nextChilds.get(j).getValue());
//			}
//			returnDataList.add(rowData);
//		}
//	}
//	return returnDataList;
//}
//
//
////to check if a record exists with id = value. call this method after "execlist"
//public boolean isRecordExistCompareValues(String value)
//{
//	boolean result=false;
//	if(returnHicData ==null)
//	{
//				
//			return;
//	}
//	else
//	{  
//		IData dataUnit = returnHicData.getData();
//		List listValue = null;
//		if(dataUnit.getQueryData().getListData()!=null)
//		{
//			listValue = dataUnit.getQueryData().getListData();
//		}
//		else
//			return false;
//		String[][] allValues = dataUnit.getQueryData().iterateListData(listValue);
//		if(allValues==null)
//		{
//		return false;
//		}
//		else if(allValues!=null && allValues.length>0)
//		{  
//			String dbValue=allValues[0][0];
//			 if(value.equalsIgnoreCase(dbValue))
//			 {
//			 result=true;
//			 }
//		}
//	}
//	return result;
//}
//
//// This Method Clear all condition value in query builder.
//public void clearAllQueryField(String str)
//{
//	clearQueryValue("logical2");
//	clearQueryValue("category1");
//	clearQueryValue("field1");
//	clearQueryValue("check1");
//	clearQueryValue("variable1");
//	clearRangeValue("range");
//}
//
//public showFileDownload(String filePath)
//{
//	if(filePath==null || filePath.isEmpty())
//		return false;
//	File file = new File(filePath);
//	Filedownload.save(file,"text/plain");
//	return true;
//}
//
//
//public void validateDataInQueryObject(HtmlBasedComponent formObj) {
//		List childElement = formObj.getChildren();
//		Iterator iter = childElement.iterator();
//		while (iter.hasNext()) {
//			Object value = iter.next();
//			if (value instanceof InputElement) {
//				validateDataInQueryObject(((InputElement) value));
//			
//				if ((((InputElement) value).getId().indexOf("category") >= 0)||
//				(((InputElement) value).getId().indexOf("field") >= 0) ||
//				(((InputElement) value).getId().indexOf("variable") >= 0)||
//				(((InputElement) value).getId().indexOf("firstrange") >= 0)||
//				(((InputElement) value).getId().indexOf("secondrange") >= 0)||
//				(((InputElement) value).getId().indexOf("logical") >= 0)
//				)	 
//				{
//					String valueObject=((InputElement) value).getText();
//					if(valueObject==null || valueObject.isEmpty())
//					{
//					String errorBGColor ="background:#fad8d8";
//					((InputElement) value).setStyle(errorBGColor);
//					}
//					else
//					{
//						String normalBGColor ="background:#FFFFFF";
//					   ((InputElement) value).setStyle(normalBGColor);
//					}
//				} 
//			}
//			
//			else {
//				validateDataInQueryObject(((HtmlBasedComponent) value));
//			}
//		}
//	}
//public Object getComponentFromPage(String formId,String componentId)
//{
//	Object rootFormValue = Path.getComponent("//"+formId+"//"+formId);
//	Object formObj = rootFormValue.getVariable(componentId,true);
//	return formObj;
//}
//Hashtable rowmap = new Hashtable();
//public void addGridToFrame(Object obj, String headers, String types, String defaultvalues)
//{
//	boolean error = false;
//
//
//	if(obj !=null)
//	{
//		obj.setStyle("overflow:auto;");
//	}
//	else
//	{
//		alert("Error in PatientAssignment:Parent is null.");
//		return;
//	}
//	
//	List children = obj.getChildren();
//	if(children!=null && children.size() > 0)
//	{
//		Object[] charr = children.toArray();
//		for(int i = 0; i < charr.length; i++)
//		{
//				obj.removeChild(charr[i]);
//		}
//	}
//	
//	Grid grid= new Grid();
//	grid.setId("PatientAssignmentGrid");
//	Columns clmns = new Columns();
//	clmns.setSizable(true);
//	Rows rws = new Rows();
//	grid.setHeight("600px");
//	grid.appendChild(clmns);
//	grid.appendChild(rws);
//	String gridStyle = "background-color:#FFFFFF;border:1px solid";
//	grid.setStyle(gridStyle);
//	rws.setStyle(gridStyle);
//	grid.setFixedLayout(true);
//	
//	String[] heads = headers.split(",");
//	String[] componentNames = types.split(",");
//	String[] defaults = defaultvalues.split(":");
//
//	if(heads != null && heads.length > 0)
//	{
//		for(int i = 0; i < heads.length; i++)
//		{
//			Column clmn = new Column();
//			String Style = "sort:auto;height:30px;width:100px";
//			clmn.setStyle(Style);
//			clmn.setLabel(heads[i]);
//			clmns.appendChild(clmn);
//		}
//	}
//	if(heads.length == componentNames.length)
//	{
//
//		String[][] allValues = null;
//		if(null != returnHicData)
//		{	
//			IData dataUnit = returnHicData.getData();
//			List listValue = null;
//			if(dataUnit.getQueryData().getListData()!=null)
//			{
//				listValue = dataUnit.getQueryData().getListData();
//				allValues = dataUnit.getQueryData().iterateListData(listValue);
//			}
//			else
//			{
//				error = true;
//			}
//		}
//
//
//		if(!error && allValues != null && allValues.length > 0 && allValues[0].length >= componentNames.length)
//		{
//			int totalfields = allValues[0].length;
//			String selected = "null";
//			for(int j = 0; j < allValues.length; j++)
//			{
//				Row row = new Row();
//				row.setId("row_pa_"+j);
//				rowmap.put(row.getId(), allValues[j]);
//				for(int i = 0; i < componentNames.length; i++)
//				{
//					if(componentNames[i].equalsIgnoreCase("label"))
//					{
//						Label label = null;
//						if(defaults[i] != null && defaults[i].equalsIgnoreCase("user"))
//						{
//							String name = getUserNameBasedOnUserId(allValues[j][i]);
//							label = new Label(name);
//						}
//						else
//						{
//							label = new Label(allValues[j][i]);
//						}
//						row.appendChild(label);
//					}
//					else if(componentNames[i].equalsIgnoreCase("textbox"))
//					{
//						Textbox txtbox = new Textbox(allValues[j][i]);
//						txtbox.setRows(4);
//						txtbox.setWidth("98%");
//						txtbox.setId("auto_textbox"+j+""+i);
//						row.appendChild(txtbox);
//					}
//					else if(componentNames[i].equalsIgnoreCase("Checkbox"))
//					{
//						Checkbox chkbox = new Checkbox();
//						if(allValues[j][i]!=null && allValues[j][i].equalsIgnoreCase("1"))
//							chkbox.setChecked(true);
//						row.appendChild(chkbox);
//						chkbox.setId("auto_checkbox"+j+""+i);
//					}
//					else if(componentNames[i].equalsIgnoreCase("Combobox"))
//					{
//						Combobox cmbbox = new Combobox();
//						cmbbox.setWidth("70px");
//						cmbbox.setReadonly(true);
//						cmbbox.setId("auto_combobox"+j+""+i);
//						row.appendChild(cmbbox);
//						if(defaults[i] != null && !defaults[i].isEmpty())
//						{
//							String retval = invokeComponent("dbComponent","executeList","com.oxymedical.component.db.DBComponent",defaults[i]);
//							showComboData(null,cmbbox);
//							cmbbox.setValue(allValues[j][i]);
//						}
//					}
//				}
//
//				row.addEventListener("onClick",new EventListener()
//				{
//					public void onEvent(Event event)
//					{
//					
//						List ch = rws.getChildren();
//						if(ch!=null && ch.size() > 0 && !selected.equals(row.getId()))
//						{
//							Object[] arr = ch.toArray();
//							for(int i = 0; i < arr.length ; i++)
//							{
//								if(arr[i] instanceof Row )
//								{
//									arr[i].setStyle("background-color:#FFFFFF;border:1px solid");
//								}
//							}
//							row.setStyle("background-color:#DDD9C3;border:1px solid");
//							selected = row.getId();
//						}
//						String[] data = rowmap.get(row.getId());
//						String patientid = data[totalfields-2];
//						String scheduleid = data[totalfields-1];
//						updateSession("patientid", patientid);
//						updateSession("scheduleid", scheduleid);
//					}
//				});
//				row.setStyle("background-color:#FFFFFF;border:1px solid");
//				rws.appendChild(row);
//			}
//		}
//	}
//	else
//	{
//		alert("Error in PatientAssignment:Headers count is not equal to types count.");
//	}
//	obj.appendChild(grid);
//}
//
//public String checkIntegerValueWithOperator(Object val1, Object val2, String checkType)
//{  
//	try
//	{
//	    int value1=Integer.parseInt(val1);
//	    int value2=Integer.parseInt(val2);
//	    if(checkType.equals("EQUAL"))
//	    {
//	      if(value1==value2)
//	      return "true";
//		  else
//		  message("Please select value equal to "+value2+".");
//	    }
//	    else if(checkType.equals("GREATERTHAN"))
//	    {
//	     if(value1>=value2)
//	      return "true";
//		  else
//		  message("Please select value greater than or equal to "+value2+".");
//		 
//	    }
//	    else if(checkType.equals("SMALLERTHAN"))
//	    {
//	     if(value1<=value2)
//	      return "true";
//		  else
//		  message("Please select value smaller than or equal to "+value2+".");
//	    }
//	}
//	catch(Exception)
//	{
//	message("Please give numeric value only.");
//	}	
//	    return null;
//}
//
//
//public void setWorkFlowData(String workFlowName,String workFlowId,String isVisual,String isWorkflowNameChanged )
// {
//	
//	 LinkedList nodeSelected=createListValues(session.getAttribute("fields"));
//	 formValues.put("nodeSelected",nodeSelected);
//	 formValues.put("workFlowName",workFlowName);
//	 formValues.put("workFlowId",workFlowId);
//	 formValues.put("isVisual",isVisual);
//	 formValues.put("isWorkflowNameChanged",isWorkflowNameChanged);
//	 invokeComponent("com.oxyentmedical.component.workflowcomponent","SaveWorkflow","com.oxymedical.component.workflowComponent.WorkflowComponent","");
//	 updateSessionList("fields",null);
// }
//
//public void displayWorkflowStatus(String controlObj,String patientId,String patientMRN,String nodeStatus)
//{
//    Object rootFormValue = self.getRoot();
//	Object elementObj = rootFormValue.getVariable(controlObj,true);
//	if(patientId!=null)
//	formValues.put( "PATIENTID", patientId);	
//	if(patientMRN!=null)
//	formValues.put( "PATIENTMRN", patientMRN);
//	if(nodeStatus!=null && nodeStatus.equalsIgnoreCase("Completed") )
//	formValues.put( "NODESTATUS","C");
//	if(nodeStatus!=null && nodeStatus.equalsIgnoreCase("Exception") )
//	formValues.put( "NODESTATUS","E");
//	if(nodeStatus!=null && nodeStatus.equalsIgnoreCase("Waiting") )
//	formValues.put( "NODESTATUS","1");
//	if(nodeStatus!=null && nodeStatus.equalsIgnoreCase("Processing") )
//	formValues.put( "NODESTATUS","2");
//	if((nodeStatus!=null && nodeStatus.equalsIgnoreCase("All"))|| nodeStatus==null)
//	formValues.remove("NODESTATUS");
//
//invokeComponent("com.oxyentmedical.component.workflowcomponent","getworkflowtoolstatus","com.oxymedical.component.workflowComponent.WorkflowComponent","workflowinfo.name,nodeinfo.nodename,patient.PATIENTID,patient.PATIENTMRN,nodedetails.nodeDescription,dataobject.nodeexecutionstatus,schedule.SCHEDULEID,dataobjectmetadata.datavalue,dataobject.toolstartdate,dataobject.toolstarttime");
//	IData dataUnitQuery = returnHicData.getData();
//	List listValueQuery = null;
//	if(dataUnitQuery.getQueryData().getListData()!=null)
//	{
//		listValueQuery = dataUnitQuery.getQueryData().getListData();
//	}
//	else
//	{
//		ListModel myModel = new ListModelList();
//		elementObj.setModel(myModel);
//		elementObj.setItemRenderer(new ItemRendererArray());
//		return;
//	}
//	String[][] allValues= dataUnitQuery.getQueryData().iterateListData(listValueQuery);
//	if(allValues==null)
//	{
//	ListModel myModel = new ListModelList();
//	elementObj.setModel(myModel);
//	elementObj.setItemRenderer(new ItemRendererArray());
//	return;
//	}
//	String[][] displayResult = new String[allValues.length][11];
//	for(int j = 0; j < allValues.length; j++)
//			{
//			   String[] values=allValues[j];
//					for(int i=0;i<values.length;i++)
//					{
//						 if(values[i]!=null)
//						 {
//							 if(values[i].equalsIgnoreCase("1") && i==5)
//							 {
//							  displayResult[j][i]="Waiting";
//							 }
//							 else if((values[i].equalsIgnoreCase("2") || values[i].equalsIgnoreCase("0")) && i==5 )
//							 {
//							 displayResult[j][i]="Processing";
//							 }
//							 else if(values[i].equalsIgnoreCase("c") && i==5 )
//							 {
//							 displayResult[j][values.length-1]="100%";
//							 displayResult[j][i]="Completed";
//							 }
//							 else if(values[i].equalsIgnoreCase("E") && i==5 )
//							 {
//							 displayResult[j][i]="Exception";
//							 }
//							 else if(i==(values.length-1))
//							 {
//							 if(values[5].equalsIgnoreCase("c"))
//							 {
//							  displayResult[j][i]="100%";
//							 }
//							 else
//							 {
//							 displayResult[j][i]=values[i]+"%";
//							 }
//							 }
//							 else
//							 displayResult[j][i]=values[i];
//						 }
//					}
//			}
//	ListModel myModel = new ListModelList(displayResult);
//	elementObj.setModel(myModel);
//	elementObj.setItemRenderer(new ItemRendererArray());		
//}
//
//public void displayWorkflowStatusForVisulaizer(String controlObj,String patientId,String patientMRN, String scheduleID)
//{
//	Object rootFormValue = self.getRoot();
//	Object elementObj = rootFormValue.getVariable(controlObj,true);
//	String userId = getSessionData("userId");
//	invokeComponent("com.oxyentmedical.component.workflowcomponent","executeListWorkflow","com.oxymedical.component.workflowComponent.WorkflowComponent","get workflowinfo.name,nodeinfo.nodename,nodedetails.nodeDescription,dataobject.nodeexecutionstatus" +
//				" from appadmin.dataobjectmetadata,appadmin.workflownodeinfo,appadmin.nodeinfo joins " +
//				"leftjoin dataobjectmetadata.dataobject:=dataobject.id," +
//				"leftjoin workflownodeinfo.workflowid:=workflowinfo.id," +
//				"leftjoin nodeinfo.nodeDetail:=nodedetails.id " +
//				"conditions dataobject.nodeexecutionstatus:=[C] and dataobject.userid:=["+userId+"]" +
//						" and dataobjectmetadata.datakey:=[PATIENTID] and workflownodeinfo.nodeid:=nodeinfo.id and dataobject.workflownodeid:=workflownodeinfo.id and dataobjectmetadata.datavalue:="+patientId);
//	IData dataUnitQuery1 = returnHicData.getData();
//	List listValueQuery1 = null;
//	if(dataUnitQuery1.getQueryData().getListData()!=null)
//	{
//		listValueQuery1 = dataUnitQuery1.getQueryData().getListData();
//	}
//	else
//	{
//			return ;
//	}
//	String[][] allValuesQuery1 = dataUnitQuery1.getQueryData().iterateListData(listValueQuery1);
//	if(allValuesQuery1==null)return;
//	
//	invokeComponent("com.oxyentmedical.component.workflowcomponent","executeListWorkflow","com.oxymedical.component.workflowComponent.WorkflowComponent","get workflowinfo.name,nodeinfo.nodename,nodedetails.nodeDescription,dataobject.nodeexecutionstatus" +
//				" from appadmin.dataobjectmetadata,appadmin.workflownodeinfo,appadmin.nodeinfo joins " +
//				"leftjoin dataobjectmetadata.dataobject:=dataobject.id," +
//				"leftjoin workflownodeinfo.workflowid:=workflowinfo.id," +
//				"leftjoin nodeinfo.nodeDetail:=nodedetails.id " +
//				"conditions dataobject.nodeexecutionstatus:=[C] and dataobject.userid:=["+userId+"]" +
//						" and dataobjectmetadata.datakey:=[PATIENTMRN] and workflownodeinfo.nodeid:=nodeinfo.id and dataobject.workflownodeid:=workflownodeinfo.id and dataobjectmetadata.datavalue:="+patientMRN);			
//	IData dataUnitQuery2 = returnHicData.getData();
//	List listValueQuery2 = null;
//	if(dataUnitQuery2.getQueryData().getListData()!=null)
//	{
//		listValueQuery2 = dataUnitQuery2.getQueryData().getListData();
//	}
//	else
//	{
//		return ;
//	}
//	String[][] allValuesQuery2 = dataUnitQuery2.getQueryData().iterateListData(listValueQuery2);
//	if(allValuesQuery2==null) return;	
//	
//	
//	invokeComponent("com.oxyentmedical.component.workflowcomponent","executeListWorkflow","com.oxymedical.component.workflowComponent.WorkflowComponent","get workflowinfo.name,nodeinfo.nodename,nodedetails.nodeDescription,dataobject.nodeexecutionstatus" +
//				" from appadmin.dataobjectmetadata,appadmin.workflownodeinfo,appadmin.nodeinfo joins " +
//				"leftjoin dataobjectmetadata.dataobject:=dataobject.id," +
//				"leftjoin workflownodeinfo.workflowid:=workflowinfo.id," +
//				"leftjoin nodeinfo.nodeDetail:=nodedetails.id " +
//				"conditions dataobject.nodeexecutionstatus:=[C] and dataobject.userid:=["+userId+"]" +
//						" and dataobjectmetadata.datakey:=[SCHEDULEID] and workflownodeinfo.nodeid:=nodeinfo.id and dataobject.workflownodeid:=workflownodeinfo.id and dataobjectmetadata.datavalue:="+scheduleID);			
//	IData dataUnitQuery3 = returnHicData.getData();
//	List listValueQuery3 = null;
//	if(dataUnitQuery3.getQueryData().getListData()!=null)
//	{
//		listValueQuery3 = dataUnitQuery3.getQueryData().getListData();
//	}
//	else
//	{
//		return ;
//	}
//	String[][] allValuesQuery3 = dataUnitQuery3.getQueryData().iterateListData(listValueQuery3);
//	if(allValuesQuery3==null) return;	
//	
//	int stringLength = allValuesQuery3.length;
//	String[][] displayResult = new String[stringLength][4];
//	int counter=0;
//	for(int i=0;i<allValuesQuery3.length;i++)
//	{
//	
//		if(allValuesQuery1[i][1].indexOf("inputscreen")>=0)
//		{
//		continue;
//		}
//		else
//		{
//		displayResult[counter][0] = allValuesQuery1[i][0];
//		displayResult[counter][1] = allValuesQuery1[i][1];
//		displayResult[counter][2] = allValuesQuery1[i][2];
//		displayResult[counter][3] = "Completed";
//		counter++;
//		}
//	}
//	ListModel myModel = new ListModelList(displayResult);
//	elementObj.setModel(myModel);
//	elementObj.setItemRenderer(new ItemRendererArray());
//}
//
//
//
//void setPACSInfo(){
//	addFormValue("pacsIpAddress", PropertyUtil.setUpProperties("PACS_IPADDRESS"));
//	addFormValue("pacsPortNumber", PropertyUtil.setUpProperties("PACS_PORTNUMBER"));
//	addFormValue("pacsServerPath", PropertyUtil.setUpProperties("PACS_SERVERPATH"));
//}
//
//void getPatientInfo()
//{
//	Object rootFormValue = self.getRoot();
//	
//	Object pMRN = rootFormValue.getVariable("PATIENTMRN",true);
//	pMRN.setValue(getSessionData("patientmrn"));
//	
//	Object pID = rootFormValue.getVariable("PATIENTID",true);
//	pID.setValue(getSessionData("rowId"));
//
//	Object pSCHEDULEID = rootFormValue.getVariable("SCHEDULEID",true);
//	pSCHEDULEID.setValue(getSessionData("scheduleId"));
//
//	Object pWORKINGAREA = rootFormValue.getVariable("WORKINGAREA",true);
//	pWORKINGAREA.setValue(getSessionData("workingarea"));
//	
//	addFormValue("PATIENTMRN", getSessionData("patientmrn"));
//	addFormValue("PATIENTID", getSessionData("rowId"));
//	addFormValue("SCHEDULEID", getSessionData("scheduleId"));
//	addFormValue("SCHEDULEWORKAREA", getSessionData("workingarea"));
//}
//public void updateSessionDataForPatient()
//{
//	updateSession("rowId", null);
//	updateSession("patientmrn", null);
//	updateSession("scheduleId", null);
//	updateSession("workingarea", null);
//	updateSession("workflowname", null);
//	updateSession("nodename", null);
//}
//	
//
//public void displayWorkflowErrorStatus(String controlObj)
//{
//	Object rootFormValue = self.getRoot();
//	Object elementObj = rootFormValue.getVariable(controlObj,true);
//	
//	String userId = getSessionData("userId");
//	invokeComponent("com.oxyentmedical.component.workflowcomponent","executeListWorkflow","com.oxymedical.component.workflowComponent.WorkflowComponent","get workflowinfo.name,nodedetails.nodeDescription,dataobjectmetadata.datavalue,errorinfo.errormessage," +
//				"dataobject.nodeexecutionstatus,nodeinfo.nodename from appadmin.dataobjectmetadata,appadmin.workflownodeinfo,appadmin.nodeinfo," +
//				"appadmin.errorinfo joins leftjoin dataobjectmetadata.dataobject:=dataobject.id,leftjoin workflownodeinfo.workflowid:=workflowinfo.id," +
//				"leftjoin nodeinfo.nodeDetail:=nodedetails.id conditions dataobject.nodeexecutionstatus:=[E] and dataobject.userid:=["+userId+"]" +
//				" and dataobjectmetadata.datakey:=[PATIENTID] and workflownodeinfo.nodeid:=nodeinfo.id and " 
//						+"dataobject.workflownodeid:=workflownodeinfo.id and errorinfo.dataobjectid:=dataobject.id");
//	IData dataUnitQuery1 = returnHicData.getData();
//	List listValueQuery1 = null;
//	if(dataUnitQuery1.getQueryData().getListData()!=null)
//	{
//		listValueQuery1 = dataUnitQuery1.getQueryData().getListData();
//	}
//	else
//	{
//			return ;
//	}
//	String[][] allValuesQuery1 = dataUnitQuery1.getQueryData().iterateListData(listValueQuery1);
//	if(allValuesQuery1==null)
//	if(allValuesQuery1==null)
//	{
//	ListModel myModel = new ListModelList();
//	elementObj.setModel(myModel);
//	elementObj.setItemRenderer(new ItemRendererArray());
//	return;
//	}
//	invokeComponent("com.oxyentmedical.component.workflowcomponent","executeListWorkflow","com.oxymedical.component.workflowComponent.WorkflowComponent","get workflowinfo.name,nodedetails.nodeDescription,dataobjectmetadata.datavalue,errorinfo.errormessage," +
//				"dataobject.nodeexecutionstatus,nodeinfo.nodename from appadmin.dataobjectmetadata,appadmin.workflownodeinfo,appadmin.nodeinfo," +
//				"appadmin.errorinfo joins leftjoin dataobjectmetadata.dataobject:=dataobject.id,leftjoin workflownodeinfo.workflowid:=workflowinfo.id," +
//				"leftjoin nodeinfo.nodeDetail:=nodedetails.id conditions dataobject.nodeexecutionstatus:=[E] and dataobject.userid:=["+userId+"]" +
//				" and dataobjectmetadata.datakey:=[PATIENTMRN] and workflownodeinfo.nodeid:=nodeinfo.id and " 
//						+"dataobject.workflownodeid:=workflownodeinfo.id and errorinfo.dataobjectid:=dataobject.id");
//	IData dataUnitQuery2 = returnHicData.getData();
//	List listValueQuery2 = null;
//	if(dataUnitQuery2.getQueryData().getListData()!=null)
//	{
//		listValueQuery2 = dataUnitQuery2.getQueryData().getListData();
//	}
//	else
//	{
//		return ;
//	}
//	String[][] allValuesQuery2 = dataUnitQuery2.getQueryData().iterateListData(listValueQuery2);
//	if(allValuesQuery2==null)
//	{
//	ListModel myModel = new ListModelList();
//	elementObj.setModel(myModel);
//	elementObj.setItemRenderer(new ItemRendererArray());
//	return;
//	}
//		invokeComponent("com.oxyentmedical.component.workflowcomponent","executeListWorkflow","com.oxymedical.component.workflowComponent.WorkflowComponent","get workflowinfo.name,nodedetails.nodeDescription,dataobjectmetadata.datavalue,errorinfo.errormessage," +
//				"dataobject.nodeexecutionstatus,nodeinfo.nodename from appadmin.dataobjectmetadata,appadmin.workflownodeinfo,appadmin.nodeinfo," +
//				"appadmin.errorinfo joins leftjoin dataobjectmetadata.dataobject:=dataobject.id,leftjoin workflownodeinfo.workflowid:=workflowinfo.id," +
//				"leftjoin nodeinfo.nodeDetail:=nodedetails.id conditions dataobject.nodeexecutionstatus:=[E] and dataobject.userid:=["+userId+"]" +
//				" and dataobjectmetadata.datakey:=[SCHEDULEID] and workflownodeinfo.nodeid:=nodeinfo.id and " 
//						+"dataobject.workflownodeid:=workflownodeinfo.id and errorinfo.dataobjectid:=dataobject.id");
//	IData dataUnitQuery3 = returnHicData.getData();
//	List listValueQuery3 = null;
//	if(dataUnitQuery3.getQueryData().getListData()!=null)
//	{
//		listValueQuery3 = dataUnitQuery3.getQueryData().getListData();
//	}
//	else
//	{
//		return ;
//	}
//	String[][] allValuesQuery3 = dataUnitQuery3.getQueryData().iterateListData(listValueQuery3);
//	if(allValuesQuery3==null) return;		
//	int stringLength = allValuesQuery3.length;
//	String[][] displayResult = new String[stringLength][8];
//	for(int i=0;i<allValuesQuery3.length;i++)
//	{
//		displayResult[i][0] = allValuesQuery1[i][0];
//		displayResult[i][1] = allValuesQuery1[i][1];
//		displayResult[i][2] = allValuesQuery1[i][2];
//		displayResult[i][3] = allValuesQuery2[i][2];
//		displayResult[i][4] = allValuesQuery2[i][3];
//		displayResult[i][5] = "Exception";
//		displayResult[i][6] = allValuesQuery2[i][5];
//		displayResult[i][7] = allValuesQuery3[i][2];
//		
//	}
//	ListModel myModel = new ListModelList(displayResult);
//	elementObj.setModel(myModel);
//	elementObj.setItemRenderer(new ItemRendererArray());
//		
//}
//
//public void attachEventListener(String objectID,String eventName)
//{
//	Object rootFormValue = self.getRoot();
//	Object obj =rootFormValue.getVariable(objectID, true);
//	if(obj == null)
//	{
//		alert("object is null");
//		return;
//	}
//	
//
//	if(objectID == "radiobutton2")
//	{
//		obj.addEventListener(eventName, new OnCustomizedParamEventListener());
//    }
//	else if(objectID == "radiobutton1")
//	{
//		obj.addEventListener(eventName, new EventListener()
//								{
//									public void onEvent(Event event)
//									{
//								//String mes="Customized settings would be lost.Are you sure you want to continue" ;
//										//boolean result=messageWithQuestion(mes);
//										//if(result)
//										//{
//											Object rootFormValue = self.getRoot();
//											Object obj =rootFormValue.getVariable("frame40", true);
//											List children = obj.getChildren();
//											if(children!=null && children.size() > 0)
//											{
//												Object[] charr = children.toArray();
//												for(int i = 0; i < charr.length; i++)
//												{
//														obj.removeChild(charr[i]);
//												}
//											}
//											obj.setVisible(false);
//										//}
//									}
//								}
//							);
//	}
//      
//}
//
//	public void createDataInChart(String barchart,String textboxId, String textboxId1,String textboxId2)
//	{
//
//		Object rootFormValue = self.getRoot();
//		Object obj =rootFormValue.getVariable(barchart, true);
//		Object objText =rootFormValue.getVariable(textboxId, true);
//		Object objText2 =rootFormValue.getVariable(textboxId1, true);
//		Object objText3 =rootFormValue.getVariable(textboxId2, true);
//
//
//		if(returnHicData ==null)
//		{
//
//			return;
//		}
//		else
//		{
//
//			List returnList=returnHicData.getData().getList();
//			if(returnList!=null && returnList.size()>0)
//			{
//				Long value=(Long)returnList.get(0);
//				Long value2=(Long)returnList.get(2);
//				objText.setValue(value.toString());
//				objText2.setValue(value2.toString());
//				double[] errorVector=returnList.get(1);
//				double[] errorVector2=returnList.get(3);
//				String value3=(String)returnList.get(4);
//				objText3.setValue(value3);
//				XYModel xymodel = new SimpleXYModel();
//				for(int i=0;i<errorVector.length;i+=20)
//				{
//					xymodel.addValue("Start error", new Integer(i), new Double(errorVector[i]));
//					xymodel.addValue("End error", new Integer(i), new Double(errorVector2[i]));
//				}
//
//				obj.setModel(xymodel);
//
//			}
//
//		}
//	}
//public void createHypnogram(String hypnogram) { 
//
//Object rootFormValue = self.getRoot();
//Object obj =rootFormValue.getVariable(hypnogram, true);
//
//if(returnHicData ==null)
//
//{
// return;
//}
//else
//
//{
// List returnList=returnHicData.getData().getList();
// if(returnList!=null && returnList.size()>0)
// {
//  int value=(Integer)returnList.get(0);
//  Object[] stages = returnList.get(1);
// 
//    
//  XYModel xymodel = new SimpleXYModel();
//  for(int i=0;i<stages.length;i+=1)
//  {
//  if(stages[i].equals(255)) stages[i]=(Integer)20;
//   xymodel.addValue("Stages",new Integer(i), ((Integer) stages[i]));
//
//}
//  obj.setModel(xymodel);
//  obj.setXAxis("Time. Sampling of stages every " + value + " seconds");
//  obj.setYAxis("Stage Value");
// }
//}
//}
//
//
//	public String uploadPSGFile(String id)
//	{
//		String path=null;
//		org.zkoss.util.media.Media media = Fileupload.get();
//		if (media != null) 
//		{ 
//			String contentType=media.getContentType();
//
//
//			if (!media.inMemory() && !media.isBinary()){
//				InputStreamReader inputStream=media.getReaderData();
//
//				String fileName=media.getName();
//				String folderPath=PropertyUtil.setUpProperties("PSG_FILE_LOC");
//				File file = new File(folderPath);
//				if(!file.exists())
//				{
//					file.mkdirs();
//				}
//
//
//
//				File f=new File(folderPath+fileName);   
//				OutputStream out=new FileOutputStream(f);
//				OutputStreamWriter o= new OutputStreamWriter(out);
//				char[] buf=new char[1024];
//				int len;
//				while((len=inputStream.read(buf,0,1024))>0)
//					o.write(buf,0,len);
//				o.close();
//				inputStream.close();   
//				return folderPath+fileName; 
//			}
//			else if(media.inMemory() && !media.isBinary()) {
//				String str=media.getStringData();
//				String fileName=media.getName();
//				String folderPath=PropertyUtil.setUpProperties("PSG_FILE_LOC");
//
//				File file = new File(folderPath);
//				if(!file.exists())
//				{
//					file.mkdirs();
//				}
//
//				File f=new File(folderPath+fileName);   
//
//				BufferedWriter out = new BufferedWriter(new FileWriter(f));
//				out.write(str);
//				out.close();
//
//				return folderPath + fileName;
//			}
//			else {
//				InputStream inputStream=media.getStreamData();
//
//				String fileName=media.getName();
//				String folderPath=PropertyUtil.setUpProperties("PSG_FILE_LOC");
//				path=writeToFile(folderPath,fileName,inputStream);
//				return path;
//			}
//		}
//		return null;
//	}
//
//
//public class OnCustomizedParamEventListener implements EventListener
//{
//
//	public void onEvent(Event event) throws Exception
//	{
//		
//		showCustomizedParamTabbox("");
//		
//		
//	}
//}
//public void showCustomizedParamTabbox(String noUse)
//{
//
//		String selectedModule = getSessionData("selectedModule");
//		if(selectedModule == null)
//		{
//			message("select a module!");
//			return;
//		}
//
//		invokeComponent("com.oxymedical.component.BrainKComponent","BrainKParamInfo","com.oxymedical.component.braink.BrainKComponent",selectedModule);
//		if(returnHicData == null || returnHicData.getData().getList() == null)
//		{
//			System.out.println("returnhicdata null");
//			return;
//		}
//
//
//		List infoList = returnHicData.getData().getList();
//
//		Object rootFormValue = self.getRoot();
//		Object obj =rootFormValue.getVariable("frame40", true);
//		List children = obj.getChildren();
//		if(children!=null && children.size() > 0)
//		{
//			Object[] charr = children.toArray();
//			for(int i = 0; i < charr.length; i++)
//			{
//
//					obj.removeChild(charr[i]);
//			}
//		}
//		
//		obj.setVisible(true);
//		obj.setStyle("background:white;");
//		Tabbox tabbox = new Tabbox();
//		tabbox.setMold("accordion");
//		Tabs tabs =  new Tabs();
//		Tabpanels tpanels = new Tabpanels();
//		
//		for(int i=0;i<infoList.size();i++)
//		{
//			BrainKModuleParam moduleNameParaInfo = infoList.get(i)[0];
//			String moduleName = moduleNameParaInfo.getModuleName();
//			
//
//			Tab tab = new Tab();
//			tab.setLabel(moduleName);
//			
//			tabs.appendChild(tab);
//			
//			List paraInfoList = moduleNameParaInfo.getParamInfoList();
//			
//			Tabpanel tpanel = new Tabpanel();
//			tpanel.setStyle("overflow:auto;");
//			for(int j=0;j<paraInfoList.size();j++)
//			{
//
//				BrainKParam paraInfo = paraInfoList.get(j);
//				
//				String parameterName = paraInfo.getParameterName();
//				String dataType = paraInfo.getDataType();
//				String defaultValue = paraInfo.getDefaultValue();
//				String rangeStartValue = paraInfo.getRangeStartValue();
//				String rangeEndValue = paraInfo.getRangeEndValue();
//				
//				Groupbox gbox = new Groupbox();
//				Caption cp = new Caption();
//				cp.setLabel(parameterName);
//				gbox.appendChild(cp);
//				Textbox tbox = new Textbox();
//				tbox.setName(parameterName);
//				gbox.appendChild(tbox);
//				
//				
//				if(dataType.equalsIgnoreCase("Integer"))
//				{
//					Slider slider = new Slider(Integer.parseInt(defaultValue));
//					slider.setMaxpos(Integer.parseInt(rangeEndValue));
//					slider.setMold("scale");
//					gbox.appendChild(slider);
//					
//					
//					Integer sliderValue = (Integer)slider.getCurpos();
//					String value = String.valueOf(sliderValue);
//					tbox.setValue(value);
//					slider.addEventListener("onScroll",new EventListener()
//												{
//													public void onEvent(Event event)
//													{
//														Integer startValue = Integer.parseInt(rangeStartValue); 
//														Integer sliderValue = (Integer)slider.getCurpos();
//														Integer actualValue;
//														if(sliderValue<startValue)
//														{
//															actualValue = startValue;
//															alert("minimun possible value is:"+startValue);
//														}
//														else
//														{
//															actualValue = sliderValue; 
//														}
//														String value = String.valueOf(actualValue);
//														tbox.setValue(value);
//													}
//												}
//											);
//				}
//				else if(dataType.equalsIgnoreCase("Boolean"))
//				{
//					Slider slider = new Slider(Integer.parseInt(defaultValue));
//					slider.setMaxpos(Integer.parseInt(rangeEndValue));
//					gbox.appendChild(slider);
//					Integer sliderValue = (Integer)slider.getCurpos();
//					String value = String.valueOf(sliderValue);
//					tbox.setValue(value);
//					slider.addEventListener("onScroll",new EventListener()
//												{
//													public void onEvent(Event event)
//													{
//														
//														Integer sliderValue = (Integer)slider.getCurpos();
//														String value = String.valueOf(sliderValue);
//														tbox.setValue(value);
//													}
//
//
//												}
//
//
//											);
//																
//				}
//				else if(dataType.equalsIgnoreCase("Float"))
//				{
//					float fnum = 0;  
//					Integer inum = (int)(fnum*10); 
//					Slider slider = new Slider(inum);
//					fnum = Float.valueOf(rangeEndValue).floatValue()*10; 
//					inum = (int)fnum;
//					slider.setMaxpos(inum);
//					slider.setMold("scale");
//					gbox.appendChild(slider);
//					float sliderValue = slider.getCurpos();
//					float deciValue = sliderValue/10;
//					String value = String.valueOf(deciValue);
//					tbox.setValue(value);
//					slider.addEventListener("onScroll",new EventListener()
//												{
//													public void onEvent(Event event)
//													{
//												float startValue = Float.valueOf(rangeStartValue).floatValue()*10;
//														float sliderValue = (Float)slider.getCurpos();
//														
//														Float actualValue;
//														
//														if(sliderValue<startValue)
//														{
//															actualValue = startValue;
//															alert("minimun possible value is:"+(startValue/10));
//														}
//
//
//														else
//														{
//															actualValue = sliderValue; 
//														}
//														
//														String value = String.valueOf(actualValue);
//														tbox.setValue(value);
//														float deciValue = actualValue/10;
//														String value = String.valueOf(deciValue);
//														tbox.setValue(value);
//													}
//
//												}
//											);
//				}
//				tpanel.appendChild(gbox);
//				tpanel.setHeight("300px");
//				tpanel.setStyle("overflow:auto;");
//				
//				
//			}
//			
//			tpanels.appendChild(tpanel);
//			
//		}
//		tabbox.appendChild(tabs);
//		tabbox.appendChild(tpanels);
//		Div div = new Div();
//		div.appendChild(tabbox);
//		div.setHeight("350px");
//		div.setStyle("overflow:auto;");
//		obj.appendChild(div);
//}
//
////this method is used to display tool tip on any component and given the id of component and display text in the tool tip.
//public void createToolTip(String id, String displayText){
//	Component rootFormValue = self.getRoot();
//	Component ctrl = rootFormValue.getFellowIfAny(id, true);
//	if(ctrl == null){
//		ctrl = getComponentIfInPlaceHolder(id);
//	}
//	if(ctrl != null){
//		/*Popup popup_tt = new Popup();
//	    Label lbl = new Label();
//	    lbl.setValue(displayText);
//		popup_tt.appendChild(lbl);
//		rootFormValue.appendChild(popup_tt);
//		ctrl.setTooltip(popup_tt);*/
//		ctrl.setTooltiptext(displayText);
//	}
//}
//
//List categoryList=new ArrayList();
//public List getCategoryList(Object formObj) 
//{
//  	List childElement;
//	if (formObj != null) 
//	{
//		childElement = formObj.getChildren();
//		Iterator iter = childElement.iterator();
//		while (iter.hasNext())
//		{
//			Object value = iter.next();
//			if (value instanceof Combobox) 
//			{
//			if (((Combobox) value).getId().indexOf("category") >= 0)
//				categoryList.add(value.id);
//			}
//
//
//
//			else
//			{
//				categoryList = getCategoryList(((XulElement) value));
//			}
//
//
//		}
//	}
//return categoryList;
//}
//
//
//
//public void showQueryComboData(String id)
//{
//   Object rootFormValue=self.getRoot();
//   Object obj =rootFormValue.getVariable(id, true);
//   List ids=getCategoryList(obj);
//   if(ids!=null && ids.size()>0)
//   {
//      for(int i=0;i<ids.size();i++)
//	  {
//	  String catId=ids.get(i);
//	  showComboData(catId);
//	  }
//   }
// categoryList.clear();  
//}
//
//
//
//public void showDataInPage(String[] txtField,String formId)
//{
//	Object rootFormValue = Path.getComponent("//"+formId+"//"+formId);
//	String[][] allValues =null;
//	int j=0;
//	LinkedHashMap displayValues;
//	if(null != returnHicData)
//	{
//		IData dataUnit = returnHicData.getData();
//		List listValue = null;
//		if(dataUnit.getQueryData().getListData()!=null)
//		{
//			listValue = dataUnit.getQueryData().getListData();
//		}
//		else
//			return;
//		allValues = dataUnit.getQueryData().iterateListData(listValue);
//		if(allValues==null)
//			return;
//		uiLibraryUtil.showData(allValues,formValues,rootFormValue,txtField,comboTable);
//		updateSession("updatedMsg",updatedMsg);
//	}
//}
//
//
//
//public Hashtable iterateObject(Object obj,Hashtable hashtable)
//{
//	List children = obj.getChildren();
//	if(children!=null && children.size() > 0)
//	{
//		int j = 0;
//		while(j<children.size())
//		{
//			Object childObject = children.get(j);
//			if(childObject instanceof Textbox)
//			{
//				 hashtable.put(childObject.getName(),childObject.getValue());
//			}
//			else
//			{
//			 hashtable = iterateObject(childObject,hashtable);
//			}
//			j++;
//		}
//		return hashtable;
//	}
//	return hashtable;
//}
//
//
//public Hashtable createFormValues(Object formObj,Hashtable formValues) 
//{
//	
//  	List childElement;
//	if (formObj != null) 
//	{
//		childElement = formObj.getChildren();
//		Iterator iter = childElement.iterator();
//		while (iter.hasNext())
//		{
//			Object value = iter.next();
//			System.out.println("Value in Iter="+value.getClass());
//			if (value instanceof InputElement)
//			{	
//				if (value instanceof Combobox) 
//				{
//				
//					if(((Combobox) value).getValue()!=null && !(((Combobox) value).getValue().trim().isEmpty()))
//					{
//				  		String id=((Combobox) value).getId();			  
//				 		String valueCombobox = getComboItemId1(id);
//				 		System.out.println("Inside Combobox in uilibrary id ="+ id+ "value="+ valueCombobox);
//						if(valueCombobox !=null)
//						{
//							formValues.put(id,valueCombobox);
//						}
//					} 
//				}	
//
//							
//				else if(value instanceof Textbox)
//				{
//					if(((Textbox) value).getValue()!=null && !(((Textbox) value).getValue().trim().isEmpty()))
//					{
//						checkFormValue(((Textbox) value).getId(),((Textbox) value).getValue());
//						System.out.println("Inside Textbox in uilibrary id ="+ ((Textbox) value).getId()+ "value="+ ((Textbox) value).getValue());
//						formValues.put(((Textbox) value).getId(),((Textbox) value).getValue());
//						textId.add(((Textbox) value).getId());
//					}
//				} 
//				else if(value instanceof Doublebox)
//				{
//					Object doubleboxValue = ((Doublebox) value);
//					System.out.println("Inside Doublebox in uilibrary id ="+doubleboxValue.getId());
//					if(doubleboxValue.getValue()!=null )
//					{
//						formValues.put(((Doublebox) value).getId(),((Doublebox) value).getValue());
//						System.out.println("Inside Doublebox in uilibrary id ="+ ((Doublebox) value).getId() + "value="+ ((Doublebox) value).getValue());
//
//					}
//				} 
//				
//				else if(value instanceof Datebox)
//				{
//					String date="";
//					Object dateboxValue = ((Datebox) value);
//					if(dateboxValue!=null)
//					{
//						if(dateboxValue.getValue() != null)
//						{
//							date= (new SimpleDateFormat("yyyy-MM-dd").format(((Datebox) value).getValue()));
//							formValues.put(((Datebox) value).getId(),date);
//							System.out.println("Inside Datebox in uilibrary id ="+ ((Datebox) value).getId() + "value="+ date);
//							checkFormValue(((Datebox) value).getId(),date);
//						}
//					}
//					
//				}
//				else if(value instanceof Timebox)
//				{
//					String time="";
//					Object timeboxValue = ((Timebox) value);
//					if(timeboxValue!=null)
//					{
//						if(timeboxValue.getValue() != null)
//						{
//							time= (new SimpleDateFormat("HH:MM:ss").format(((Timebox) value).getValue()));
//							formValues.put(timeboxValue.getId(),time);				
//							System.out.println("Inside Timebox in uilibrary id ="+ ((Timebox) value).getId() + "value="+ time);
//						}
//					}
//					
//				}
//			}
//			else if (value instanceof Radiogroup) 
//			{
//				System.out.println("Inside Radiogroup check");
//				String id=((Radiogroup) value).getId();	
//				//System.out.println("Inside Radiogroup in uilibrary id ="+id);
//				Object selectedRadioItem = ((Radiogroup) value).selectedItem;	
//				//System.out.println("Inside Radiogroup in uilibrary selectedRadioItem ="+selectedRadioItem);
//				if(selectedRadioItem != null) 
//				{ 
//					String valueRadiogroup = selectedRadioItem.value;
//					//System.out.println("id ="+id + " valueRadiogroup="+valueRadiogroup);
//					formValues.put(id,valueRadiogroup);
//				}
//			}				
//			else
//			{
//			 	formValues = createFormValues(value,formValues);
//			}
//		}
//	}
//	else
//	{
//		System.out.println("form obj is null");
//	}
//	return formValues;
//}
//
//public void getFilesfromFolder(String directoryName,Object comboObj)
//{
//    File dir = new File(directoryName);
//    String[] children = dir.list();
//    if (children == null) {
//        // Either dir does not exist or is not a directory
//    } else {
//	   ArrayList fileList=new ArrayList();
//	   int counter=0;
//        for (int i=0; i<children.length; i++) {
//            // Get filename of file or directory
//            String filename = children[i];
//            File tempFile = new File(dir,filename);
//			if(!tempFile.isHidden())
//			{
//				fileList.add(filename);
//			}
//        }
//		Collections.sort(fileList,String.CASE_INSENSITIVE_ORDER);
//		String[][] displayFiles=new String[fileList.size()][2];
//        for(int j=0;j<fileList.size();j++)
//        {
//		   displayFiles[j][0]=fileList.get(j);
//		   displayFiles[j][1]=fileList.get(j);
//        }
//		if(displayFiles!=null && displayFiles.length>0)
//		uiLibraryUtil.showComboData(displayFiles,comboObj);
//    }    
//}
//
//public void XMLtoTools(String path,String formpatternId,String packageId,String actionId,String descriptionId,String categoryId, String visualizerId, String xmlId)
//{
//		XmlReader xmlReader= new XmlReader();
//		String returnMessage="";
//	    org.dom4j.Document domDoc =xmlReader.getDocumentFromPath(path);
//		if(domDoc!=null)
//		{
//			org.dom4j.Element rootElement  = domDoc.getRootElement();
//			List elements0 = rootElement.elements();
//			if(elements0!=null && elements0.size()>0)
//			{
//				for(int i=0 ; i<elements0.size() ; i++)
//				{
//					org.dom4j.Element element= (org.dom4j.Element)elements0.get(i);
//					List elements1 = element.elements();
//
//					for(int j=0 ; j<elements1.size() ; j++)
//					{
//						org.dom4j.Element element1= (org.dom4j.Element)elements1.get(j);
//						List elements2 = element1.elements();
//
//						for(int k=0 ; k<elements2.size() ; k++)
//						{
//							org.dom4j.Element element2= (org.dom4j.Element)elements2.get(k);
//							List elements3 = element2.elements();
//							if(element2.getName().equalsIgnoreCase("Tool"))
//							{
//								formValues = new Hashtable();
//								String formPattern=null;
//								String pacakageName=null;
//								boolean classFound=false;
//								boolean isCategoryFound=false;
//								for(int l=0 ; l<elements3.size() ; l++)
//								{
//									org.dom4j.Element element3= (org.dom4j.Element)elements3.get(l);
//									if(element3.getName().equalsIgnoreCase("ZUL"))
//									{
//									    formPattern=element3.getText().trim();
//										formValues.put( formpatternId,formPattern);
//									}
//									else if(element3.getName().equalsIgnoreCase("Package"))
//									{
//									    pacakageName=element3.getText().trim();
//										formValues.put( packageId,pacakageName);
//										try
//										{
//											Class clazz=Class.forName(pacakageName);
//											classFound=true;
//										}
//										catch(Exception e)
//										{
//										 classFound=false;
//										}
//										
//									}
//									else if(element3.getName().equalsIgnoreCase("Action"))
//									{
//										formValues.put( actionId, element3.getText().trim() );
//									}
//									else if(element3.getName().equalsIgnoreCase("Description"))
//									{
//										formValues.put( descriptionId, element3.getText().trim());	
//									}
//									else if(element3.getName().equalsIgnoreCase("Tool-Cateogry"))
//									{
//									    String category=element3.getText().trim();
//								invokeComponent("com.oxyentmedical.component.workflowcomponent","executeListWorkflow","com.oxymedical.component.workflowComponent.WorkflowComponent","get toolcateogry .id,toolcateogry.category from appadmin.toolcateogry conditions toolcateogry.category:=["+category+"]");	
//								      String id=getDataFromAllValuesVar(0, 0);
//									  if(id!=null)
//									  {
//									    isCategoryFound=true;
//										formValues.put(categoryId,id);	
//									  }
//									}
//									else if(element3.getName().equalsIgnoreCase("IS-VISUALIZER"))
//									{
//										formValues.put( visualizerId, element3.getText().trim());	
//									}
//                                    else if(element3.getName().equalsIgnoreCase("INPUT-XML"))
//									{
//									    String inputXml=element3.getText()!=null ?element3.getText().trim():null;
//									    if(inputXml!=null && !inputXml.equalsIgnoreCase(""))
//										formValues.put( xmlId, element3.getText().trim());	
//									}										
//								 }
//								if(classFound && formValues!=null && isCategoryFound && formValues.size()>0)  
//								{
//									setFormPatternId("AddTool");  
//									invokeComponent("com.oxyentmedical.component.workflowcomponent","executeListWorkflow","com.oxymedical.component.workflowComponent.WorkflowComponent","get nodedetails.id from appadmin.nodedetails conditions nodedetails.formpattern:=["+formPattern+"]");									 if(isRecordExist())
//									{
//									  returnMessage = returnMessage+" Record for :"+formPattern+" already exists \n";
//									 }
//									 else
//								    {   
//								 invokeComponent("com.oxyentmedical.component.workflowcomponent","AddNewNodeFromUI","com.oxymedical.component.workflowComponent.WorkflowComponent","");
//									}
//								 }
//								 else if(!classFound)
//								 {
//								 returnMessage=returnMessage+ " Class not found for :"+formPattern+" \n";
//								 }
//								 else if(!isCategoryFound)	
//								 {
//								  returnMessage=returnMessage+ " Category not found for :"+formPattern+" \n";
//								 }																	
//							}
//						}
//					}
//				}
//			}
//			
//			if(!returnMessage.trim().equals(""))
//			{
//			message(returnMessage);
//			}
//			
//		}
//		else
//		{
//		message("File not found.");
//		}
//}
//public String splitStringForRequiredIndex(String inputValue,String pattern,int index)
//{
//    String returnValue=null;
//	if(inputValue!=null)
//	{
//		 String[] values=StringUtil.splitString(inputValue.trim(),pattern);
//		 if(values.length>index)
//		 {
//		    returnValue=values[index];
//		 }
//	}
//  return returnValue;
//}
//public void getCSVFilesFromFolder(String directoryName,Object comboObj)
//{
//    File dir = new File(directoryName);
//    String[] children = dir.list();
//    if (children == null) 
//    {
//        // Either dir does not exist or is not a directory
//    } 
//    else 
//    {  
//       ArrayList fileList=new ArrayList();
//	   int counter=0;
//        for (int i=0; i<children.length; i++) 
//        {
//          String filename = children[i];
//			  if(filename.lastIndexOf(".csv") != -1)
//			{
//				File tempFile = new File(dir,filename);
//				if(!tempFile.isHidden())
//				{
//					   //Get filename of file or directory
//				fileList.add(filename);
//					counter++;
//				}
//			}
//            
//        }
//        Collections.sort(fileList,String.CASE_INSENSITIVE_ORDER);
//        String[][] displayFiles=new String[fileList.size()][2];
//        for(int j=0;j<fileList.size();j++)
//        {
//		   displayFiles[j][0]=fileList.get(j);
//		   displayFiles[j][1]=fileList.get(j);
//        }
//	if(displayFiles!=null && displayFiles.length>0)
//	uiLibraryUtil.showComboData(displayFiles,comboObj);
//    }    
//}
//
//public boolean checkWorkflowStopped(String id)
//{ 
//	boolean isStopped=false;
//	if(returnHicData!=null)
//	{
//	  if(returnHicData.getData().getReturnedData()!=null)
//	  {
//	  isStopped=returnHicData.getData().getReturnedData();
//	  }
//	}
//	return isStopped;
//}
//
//public String replaceStringValue(String replaceString,String replaceWith,String value)
//{
// if(value!=null)
// {
// value=value.replaceAll(replaceString,replaceWith);
// }
// return value;
//}
//
//public void displayFieldDataInControlForDragAndDrop(String id)
//{
//
//	if(returnHicData ==null)
//	{
//		return;
//	}
//	else
//	{
//		IData dataUnit = returnHicData.getData();
//		List listValue = null;
//		
//		if (dataUnit.getQueryData() == null) return;
//		if(dataUnit.getQueryData().getListData()!=null)
//		{
//			listValue = dataUnit.getQueryData().getListData();
//		}
//		else
//			return;
//		String[][] allValues = dataUnit.getQueryData().iterateListData(listValue);
//		Object rootFormValue = self.getRoot();
//		Object obj = rootFormValue.getVariable(id,true);
//		List child = obj.getChildren();
//		List childData = new ArrayList(child);
//		Iterator itr = childData.iterator();
//		while(itr.hasNext())
//		{
//			Listitem listitem =  (Listitem)itr.next();
//			obj.removeChild(listitem);
//		}
//				
//		if(allValues !=null)
//		{
//			for(int i=0;i<allValues.length;i++)
//			{  
//				String name=allValues[i][1];
//				String value=allValues[i][0];
//				Listitem li = new Listitem();
//				li.setLabel(name);
//				li.setValue(value);
//				li.setDraggable("true");
//				li.setDroppable("true");
//				li.addEventListener("onDrop", new EventListener()
//				{
//					public void onEvent(Event event)
//				 	{
//						try
//						{
//							move(event.dragged);
//						}
//						catch(Exception e)	
//						{
//							System.out.println("Add Event Listener warning on OnDrop in displayFieldDataInControlForDragAndDrop function");
//						}
//					}
//				});
//				obj.appendChild(li);
//			}
//		}
//	}
//}
//
//static String winname="";
//static String windowUrl="";
//public void openNewWindow(String windowName,Object button)
//{
//	 Object rootFormValue=self.getRoot();
//	 winname = "browser"+ generateUniqueNumber();
//	 windowUrl=windowName;
//	 rootFormValue.getDesktop().enableServerPush(true);
//	 button.setAction("onClick: newWindow=window.open( #{windowUrl},   #{winname},'scrollbars=yes,resizable=yes,height=500px,width=500px');newWindow.focus();");
//	 button.setParent(rootFormValue);
//}
//
//public void openVisulaizer()
//{
//	String patientId=getSessionData("rowId");
//	String nodeName=getSessionData("nodename");
//	String workflowName=getSessionData("workflowname");
//	String patientMRN=getSessionData("patientmrn");
//	String userId=getSessionData("userId");
//	String scheduleId=getSessionData("scheduleId");
//	addFormValue("PATIENTMRN", patientMRN);
//	addFormValue("PATIENTID", patientId);
//	addFormValue("SCHEDULEID", scheduleId);
//	addFormValue("WORKFLOWNAME", workflowName);
//	addFormValue("NODENAME", nodeName);
//	invokeComponent("com.oxyentmedical.component.workflowcomponent","OpenVisualiser","com.oxymedical.component.workflowComponent.WorkflowComponent",workflowName+"."+nodeName);
//}
//public void getFilesBasedOnFileType(String directoryName,Object comboObj, String fileType)
//{
//    File dir = new File(directoryName);
//    String[] children = dir.list();
//	
//	if(comboObj.getChildren()!=null && comboObj.getChildren().size()>0)
//	{
//		List list = new ArrayList(comboObj.getChildren());
//		for(int i=0;i<list.size();i++)
//		{
//			comboObj.removeChild(list.get(i));
//		}
//	}
//	
//    if (children == null) 
//    {
//        // Either dir does not exist or is not a directory
//    } 
//    else 
//    {  
//       ArrayList fileList=new ArrayList();
//	   int counter=0;
//        for (int i=0; i<children.length; i++) 
//        {
//          String filename = children[i];
//          String fileCon="."+fileType; 
//			  if(filename.lastIndexOf(fileCon) != -1)
//			{
//				File tempFile = new File(dir,filename);
//				if(!tempFile.isHidden())
//				{
//					   //Get filename of file or directory
//				fileList.add(filename);
//					counter++;
//				}
//			}
//            
//        }
//        Collections.sort(fileList,String.CASE_INSENSITIVE_ORDER);
//        String[][] displayFiles=new String[fileList.size()][2];
//        for(int j=0;j<fileList.size();j++)
//        {
//		   displayFiles[j][0]=fileList.get(j);
//		   displayFiles[j][1]=fileList.get(j);
//        }
//	if(displayFiles!=null && displayFiles.length>0)
//	uiLibraryUtil.showComboData(displayFiles,comboObj);
//    }    
//}
//
//private String getNextFormId()
//{
//String formCounter = session.getAttribute("formCounter");
//String visitId = session.getAttribute("visitId");
//invokeComponent("dbComponent","executeList","com.oxymedical.component.db.DBComponent","get VisitForm.FormName from agedcdb.VisitForm conditions VisitForm.VisitId:="+visitId);
//String formName = null;
//try{
//	formName = getDataFromAllValuesVar(Integer.parseInt(formCounter)+1, 0);
//	session.setAttribute("formCounter", ""+(Integer.parseInt(formCounter)+1));
//}
//catch(Exception e){
//}
//return formName;
//}
//
//private String getNextVisitId()
//{
//String visitCounter = session.getAttribute("visitCounter");
//String studyId = session.getAttribute("studyId");
//invokeComponent("dbComponent","executeList","com.oxymedical.component.db.DBComponent","get Visit.VisitId from agedcdb.Visit conditions Visit.StudyName:=["+studyId+"]");
//String visitId = null;
//
//try{
//	visitId = getDataFromAllValuesVar(Integer.parseInt(visitCounter)+1, 0);
//	
//	invokeComponent("dbComponent","executeList","com.oxymedical.component.db.DBComponent","get VisitForm.FormName from agedcdb.VisitForm conditions VisitForm.VisitId:=["+visitId+"]");
//	
//	IData dataUnit = returnHicData.getData();
//	listValue = dataUnit.getQueryData().getListData();
//	String[][] allValues = dataUnit.getQueryData().iterateListData(listValue);
//	if(allValues == null || allValues.length <= 0)
//		return "false";
//		
//	session.setAttribute("visitId",visitId);
//	session.setAttribute("visitCounter",""+(Integer.parseInt(visitCounter)+1));
//	session.setAttribute("formCounter","-1");
//}
//catch(Exception e){
//}
//return visitId;
//}
//
//private String getPreviousFormId()
//{
//String formCounter = session.getAttribute("formCounter");
//String visitId = session.getAttribute("visitId");
//invokeComponent("dbComponent","executeList","com.oxymedical.component.db.DBComponent","get VisitForm.FormName from agedcdb.VisitForm conditions VisitForm.VisitId:="+visitId);
//String formName = null;
//try{
//	formName = getDataFromAllValuesVar(Integer.parseInt(formCounter)-1, 0);
//	session.setAttribute("formCounter", ""+(Integer.parseInt(formCounter)-1));
//}
//catch(Exception e){
//}
//return formName;
//}
//
//private String getPreviousVisitId()
//{
//String visitCounter = session.getAttribute("visitCounter");
//String studyId = session.getAttribute("studyId");
//invokeComponent("dbComponent","executeList","com.oxymedical.component.db.DBComponent","get Visit.VisitId from agedcdb.Visit conditions Visit.StudyName:=["+studyId+"]");
//String visitId = null;
//
//try{
//	visitId = getDataFromAllValuesVar(Integer.parseInt(visitCounter)-1, 0);
//	
//	invokeComponent("dbComponent","executeList","com.oxymedical.component.db.DBComponent","get VisitForm.FormName from agedcdb.VisitForm conditions VisitForm.VisitId:=["+visitId+"]");
//	
//	IData dataUnit = returnHicData.getData();
//	listValue = dataUnit.getQueryData().getListData();
//	String[][] allValues = dataUnit.getQueryData().iterateListData(listValue);
//	if(allValues == null || allValues.length <= 0)
//		return "false";
//
//	session.setAttribute("visitId",visitId);
//	session.setAttribute("visitCounter",""+(Integer.parseInt(visitCounter)-1));
//	session.setAttribute("formCounter",""+allValues.length);
//}
//catch(Exception e){
//}
//return visitId;
//}
//String invokeComponentDownloadFile(String componentId ,String method ,String classname, String paramList)
//{
//	String stringValues="";
//	alert("inside invokecomponent download");
//	String isValidStatus = "true";
//	IDataUnitRouter router = RendererComponent.dataUnitRouter;
//	Object rootFormValue = self.getRoot();
//	/*
//	*Before completing any request it will check that session is valid or not.
//	*
//	*/
//	checkSessionTime(method);
//	/*
//	*Following code is used when session would be time out
//	*/
//	String logOutValue = getSessionData("LogOut");		
//	if(logOutValue!=null)
//	{
//	
//		if(logOutValue=="true")
//		{
//			return "";
//		}
//		return "";
//	}
//
//	
//    UiLibraryCompositeCommand command=new UiLibraryCompositeCommand();
//	command.setMethodName(method);
//	command.setRouter(router);
//    command.setClassname(classname);
//	command.setComponentId(componentId);
//    command.setDataPatternId(dataPatternId);
//    command.setFormPatternId(formPatternId);
//    command.setFormValues(formValues);
//	command.setRootFormValue(rootFormValue);
//	command.setParamList(paramList);
//	command.setSession(session);
//	command.setComboSelectedValue(comboSelectedValue);
//	command.setValidListRequest(validListRequest);
//	command.setPagingId(pagingId);
//	command.execute();
//	returnHicData=command.getHICData();
//	if(method.equalsIgnoreCase("searchDb"))
//	{
//		alert("inside SearchDb====");
//		if(returnHicData==null)
//		{
//			alert("if hicdata is not there");
//		}
//		else
//		{
//			IData data=	returnHicData.getData();
//			IFormPattern formPattern=data.getFormPattern();
//			Hashtable formValues=formPattern.getFormValues();
//			List lst=formValues.get("FileValues");
//			stringValues =formValues.get("Headers")+"\n";
//			alert("the headers are"+stringValues);
//			for(int i=0;i<lst.size();i++)
//			{
//				stringValues=stringValues+lst.get(i)+"\n";
//	
//			}
//			Filedownload.save(stringValues,"csv/plain","report.csv");
//		}
//	}
//	else if(method.equalsIgnoreCase("generatePDF"))
//	{
//		alert("inside generatePDF====");
//		if(returnHicData==null)
//		{
//			alert("if hicdata is not there");
//		}
//		else
//		{
//			IData data=	returnHicData.getData();
//			IFormPattern formPattern=data.getFormPattern();
//			Hashtable formValues=formPattern.getFormValues();
//			byte[] bytes=formValues.get("StreamData");
//			Filedownload.save(bytes,"application/pdf","reports.pdf");
//		}
//	}
//		
//	sessionUpdate(returnHicData);
//	return isValidStatus;
//}
//
//
//
//
//
//
//
//
//
//
//// Added by dsi for setting selected item of combobox
//private void setSelectedComboboxItemId(String comboBox, String id)
//{
//	String comboSelectedVal = null;
//	Object rootFormValue = self.getRoot();
//	Object comboObj = rootFormValue.getVariable(comboBox,true);
//	List items = comboObj.getItems();
//	List items = comboObj.getItems();
//	for(Iterator iter = items.iterator(); iter.hasNext();)
//	{
//		Comboitem comboItem = (Comboitem)iter.next();
//		int idSeperatorPos = comboItem.getId().indexOf("_");
//		String comboSelectedVal = (String)comboItem.getId().substring(0,idSeperatorPos);
//		if(id.equals(comboSelectedVal))
//		{
//			comboObj.setSelectedItem(comboItem);
//			return;
//		}
//	}
//}
//
//Object getFormValues()
//{
//	return formValues;
//}
//
//setFormValues(Object lastFormValues)
//{
//	formValues = lastFormValues;
//}
//
//public String fileLocationPath(String fileType)
//{
//	String none="";
//	org.zkoss.util.media.Media[] media = org.zkoss.zul.Fileupload.get(1, true);
//	if (media != null) 
//	{
//		File f = new File(media[0].getName());
//		int indexOfDot = f.getName().lastIndexOf(".");
//		if(fileType != null && (indexOfDot >= 0 && !media[0].getName().substring(indexOfDot+1).equalsIgnoreCase(fileType))){
//			alert("Please select the "+fileType+" file");
//			return none;
//		}
//
//	 if (media[0].isBinary()) {
//		 File file = new File(media[0].getName());
//		 org.zkoss.io.Files.copy(file, media[0].getStreamData());
//		}
//	  else {
//		 BufferedWriter writer = new BufferedWriter(new FileWriter(media[0].getName()));
//		 org.zkoss.io.Files.copy(writer, media[0].getReaderData());
//		 writer.flush();
//	   }
//		
//		/*File f = new File(media.getName());
//		Reader reader;
//		try{
//			InputStream inputStream = media.getStreamData();
//			reader = new InputStreamReader(inputStream);
//		}catch(Exception e){
//			reader = media.getReaderData();
//		}
//		BufferedReader br = new BufferedReader(reader);
//		FileOutputStream outFile = new FileOutputStream(f);
//		PrintWriter textFileWriter = new PrintWriter(outFile);
//		String line;
//		while ((line = br.readLine()) != null )
//		{
//			textFileWriter.println(line);
//		}
//		textFileWriter.close();
//		br.close();*/
//		String filepath = f.getAbsolutePath();
//		return filepath;
//	}
//	else
//	{
//		System.out.println("media null");
//	}
//	return none;
//}
//
//public void showMultiUpload(org.zkoss.zk.ui.event.EventListener onMultiUploadCloseListener, String allowedExtensions){
//	ArrayList al_uploadedFileNames = new ArrayList();
//	session.setAttribute("uploadedFileNames", al_uploadedFileNames);
//	Window win = (Window)Executions.createComponents("../JUpload/jUpload.zul", null, null);
//	win.setAttribute("allowedExtensions", allowedExtensions);
//	win.setMaximizable(false);
//	win.setMinimizable(false);
//	win.setClosable(true);
//	win.addEventListener(Events.ON_CLOSE, onMultiUploadCloseListener);
//	win.doModal();
//}
//
//public void setComboModel(Combobox cmb, List dataList){
//	ListModel cityModel = new SimpleListModelExt(dataList);
//	cmb.setModel(cityModel);
//}
//
//public void setCookie(String name, String value){
//	Cookie ck = new Cookie(name, value);
//	//sec in min * min in hour * hour in day * day in year * no of years
//	ck.setMaxAge(60 * 60 * 24 * 365 * 4);
//	((HttpServletResponse)Executions.getCurrent().getNativeResponse()).addCookie(ck);
//}
//
//public static String getCookie(String name){
//	Cookie[] cookies = ((HttpServletRequest)Executions.getCurrent().getNativeRequest()).getCookies();
//	String cookieValue = null;
//	if(cookies != null){
//		for(Cookie cookie : cookies){
//			if(cookie.getName().equals(name)){
//				cookieValue = cookie.getValue();
//				break;
//			}
//		}
//	}
//	return cookieValue;
//}
//
//private String getRowType(Component dRow){
//	String rowType = "";
//	if(dRow.getAttribute("RowEdited") != null && dRow.getAttribute("RowEdited").toString().equalsIgnoreCase("true")){
//		rowType = "Edited";
//	}else if(dRow.getAttribute("RowSelected") != null && dRow.getAttribute("RowSelected").toString().equalsIgnoreCase("true")){
//		rowType = "Selected";
//	}
//	return rowType;
//}
//
//private String getRowItemData(Component rowItem){
//	String itemData = "";
//	if(rowItem instanceof Label){
//		itemData = ((Label)rowItem).getValue();
//	}else if(rowItem instanceof Textbox){
//		itemData = ((Textbox)rowItem).getValue();
//	}else if(rowItem instanceof Datebox){
//		itemData = ((Datebox)rowItem).getText();
//	}else if(rowItem instanceof Cell || rowItem instanceof Listcell){
//		List cellItems = rowItem.getChildren();
//		for(Object cellItemObj : cellItems){
//			Component cellItem = (Component)cellItemObj;
//			if(cellItem.getAttribute("OnlyForSelect") != null && cellItem.getAttribute("OnlyForSelect").toString().equalsIgnoreCase("true")){
//				
//			}else{
//				itemData = itemData + getRowItemData(cellItem) + "|";
//			}
//		}
//		if(itemData.length() > 0){
//			itemData = itemData.substring(0, itemData.length()-1);
//		}
//	}
//	return itemData;
//}
//
//public List getDataFromListBox(String lBoxID, String dataType){
//	List editedRows = null;
//	Component gridComp = getComponentInPage(lBoxID);
//	if(gridComp != null && gridComp instanceof Listbox){
//		editedRows = new ArrayList();
//		Listbox dGrid = (Listbox)gridComp;
//		for(int i = 0; i < dGrid.getItems().size(); i++){
//			Listitem listItem = (Listitem)dGrid.getItems().get(i);
//			if(dataType.equalsIgnoreCase("All")
//					|| (dataType.equalsIgnoreCase("SelectedOrEdited") && (listItem.isSelected() || getRowType(listItem).equalsIgnoreCase("Edited")))
//					|| (dataType.equalsIgnoreCase("Selected&Edited") && (listItem.isSelected() && getRowType(listItem).equalsIgnoreCase("Edited")))
//					|| (dataType.equalsIgnoreCase("Selected") && listItem.isSelected())
//					|| (dataType.equalsIgnoreCase("Edited") && getRowType(listItem).equalsIgnoreCase("Edited"))){
//				List rowData = new ArrayList();
//				for(int j = 0; j < listItem.getChildren().size(); j++){
//					Component rowItem = (Component)listItem.getChildren().get(j);
//					rowData.add(getRowItemData(rowItem));
//				}
//				editedRows.add(rowData);
//			}
//		}
//	}
//	return editedRows;
//}
//
//public List getDataFromGrid(String dGridID, String dataType){
//	List editedRows = null;
//	Component gridComp = getComponentInPage(dGridID);
//	if(gridComp != null && gridComp instanceof Grid){
//		editedRows = new ArrayList();
//		Grid dGrid = (Grid)gridComp;
//		for(int i = 0; i < dGrid.getRows().getChildren().size(); i++){
//			Row dRow = (Row)dGrid.getRows().getChildren().get(i);
//			if(dataType.equalsIgnoreCase("All")
//					|| (dataType.equalsIgnoreCase("Selected") && getRowType(dRow).equalsIgnoreCase("Selected"))
//					|| (dataType.equalsIgnoreCase("Edited") && getRowType(dRow).equalsIgnoreCase("Edited"))){
//				List rowData = new ArrayList();
//				for(int j = 0; j < dRow.getChildren().size(); j++){
//					Component rowItem = (Component)dRow.getChildren().get(j);
//					rowData.add(getRowItemData(rowItem));
//				}
//				editedRows.add(rowData);
//			}
//		}
//	}
//	return editedRows;
//}
//
//public void displayDataInGrid(String dGridID, GridDef gridDef, String[][] _gridData){
//	Component gridComp = getComponentInPage(dGridID);
//	if(gridComp != null && (gridComp instanceof Grid || gridComp instanceof Listbox)){
//		String[][] gridData = _gridData;
//		if(gridData == null){
//			gridData = getDataForGrid();
//		}
//		CustomListModel clm = new CustomListModel(gridData, gridComp);
//		ListModel lm = new ListModelList(gridData);
//		if(gridComp instanceof Grid){
//			Grid dGrid = (Grid)gridComp;
//			dGrid.setModel(clm);
//			if(gridDef.getIsSortable() == true && gridDef != null){
//				Columns cols = dGrid.getColumns();
//				List colList = cols.getChildren();
//				for(int i=0;i<colList.size();i++){
//					String colDatatype = "String";
//					if(gridDef.getColumns()[i].getType().contains("textbox")){
//						Column col = (Column) cols.getChildren().get(i);
//						if(gridDef.getColumns()[i].getDataType() != null){
//							colDatatype = gridDef.getColumns()[i].getDataType();
//						}
//						CustomComparator asce = new CustomComparator(true, i, null, colDatatype);
//						CustomComparator dsce = new CustomComparator(false, i, null, colDatatype);
//						col.setSortAscending(asce);
//						col.setSortDescending(dsce);
//					}else if(gridDef.getColumns()[i].getType().contains("label")){
//						Column col = (Column) cols.getChildren().get(i);
//						if(gridDef.getColumns()[i].getDataType() != null){
//							colDatatype = gridDef.getColumns()[i].getDataType();
//						}
//						CustomComparator asce = new CustomComparator(true, i, null, colDatatype);
//						CustomComparator dsce = new CustomComparator(false, i, null, colDatatype);
//						col.setSortAscending(asce);
//						col.setSortDescending(dsce);
//					}else if(gridDef.getColumns()[i].getType().equalsIgnoreCase("datebox")){
//						Column col = (Column) cols.getChildren().get(i);
//						colDatatype = "date";
//						String dateFormat = gridDef.getColumns()[i].getFormat();
//						CustomComparator asce = new CustomComparator(true, i, dateFormat, colDatatype);
//						CustomComparator dsce = new CustomComparator(false, i, dateFormat, colDatatype);
//						col.setSortAscending(asce);
//						col.setSortDescending(dsce);
//					}
//				}
//			}
//			RowRendererArray rowRendArr = new RowRendererArray();
//			if(gridDef != null){
//				rowRendArr.setGridDef(gridDef);
//			}
//			dGrid.setRowRenderer(rowRendArr);
//		}else{
//			dGrid = (Listbox)gridComp;
//			dGrid.setModel(lm);
//			ItemRendererArray ira = new ItemRendererArray();
//			if(gridDef != null){
//				ira.setGridDef(gridDef);
//			}
//			dGrid.setItemRenderer(ira);
//		}
//	}
//}
//
//
//private String[][] getDataForGrid(){
//	Object listValues = session.getAttribute("dbListValue");
//	String[][] gridData = null;
//	if(listValues == null){
//		if(returnHicData != null){
//			hicData = returnHicData;
//			
//			IData dataUnit = hicData.getData();
//			List listValue = null;
//			if(dataUnit.getQueryData().getListData() != null){
//				listValue = dataUnit.getQueryData().getListData();
//				
//				gridData = dataUnit.getQueryData().iterateListData(listValue);
//			}
//		}
//	}else{
//		gridData = QueryData.iterateListData(listValues);
//	}
//	return gridData;
//}
//
//public void setEnableListBox(String lBoxID, boolean status){
//	Component lBoxComp = getComponentInPage(lBoxID);
//	if(lBoxComp != null && lBoxComp instanceof Listbox){
//		Listbox lBox = (Listbox)lBoxComp;
//		List listItems = lBox.getItems();
//		for(Listitem lItem : listItems){
//			if(status == true){
//				lItem.setStyle(null);
//			}
//			lItem.setDisabled(!status);
//			if(status == false && lItem.isSelected() == true){
//				lItem.setStyle("background: #3e697c");
//			}
//		}
//	}
//}
//
//public Component getComponentInPage(String compId){
//	Component comp = null;
//	Component rootFormValue = self.getRoot();
//	comp = rootFormValue.getFellowIfAny(compId, true);
//	if(comp == null){
//		comp = getComponentIfInPlaceHolder(compId);
//	}
//	return comp;
//}
//
//public Component getComponentIfInPlaceHolder(String compId){
//	Component comp = null;
//	if(self.getParent() != null){
//		comp = self.getParent().getFellowIfAny(compId, true);
//	}
//	return comp;
//}
//
//public void setTheme (Execution exe, String theme) {                
//	Cookie cookie = new Cookie(THEME_COOKIE_KEY, theme);                
//	cookie.setMaxAge(60*60*24*30); //store 30 days                
//	String cp = exe.getContextPath();                
//	// if path is empty, cookie path will be request path, which causes problems                
//	if(cp.length() == 0)                        
//		cp = "/";                
//	cookie.setPath(cp);                
//	((HttpServletResponse)exe.getNativeResponse()).addCookie(cookie);        
//}
//
//public String getTheme (Execution exe) {                
//	Cookie[] cookies = ((HttpServletRequest)exe.getNativeRequest()).getCookies();                
//	if(cookies == null)                         
//		return "";                
//	for(int i=0; i < cookies.length; i++){                        
//		Cookie c = cookies[i];                        
//		if(THEME_COOKIE_KEY.equals(c.getName())) {                                
//			String theme = c.getValue();                                
//			if(theme != null)                                         
//				return theme;                        
//			}                
//		}                
//	return "";        
//}
//
//public void clearListboxHighlight(String lBoxID){
//	List editedRows = null;
//	Component gridComp = getComponentInPage(lBoxID);
//	if(gridComp != null && gridComp instanceof Listbox){
//		Component listRow = null;
//		for(Iterator itr_listItems = gridComp.getChildren().iterator(); itr_listItems.hasNext();){
//			listRow = itr_listItems.next();
//			if(listRow instanceof Listitem){
//				((Listitem)listRow).setStyle(null);
//			}
//		}
//	}
//}
//
//public org.dom4j.Element createXmlElement(org.dom4j.Element parentElement, String nodeName, Map attributes, org.dom4j.Element addBefore_childElement){
//	org.dom4j.Element newElement = org.dom4j.DocumentHelper.createElement(nodeName);
//	for(Iterator itr_attrs = attributes.keySet().iterator(); itr_attrs.hasNext();){
//		String attrName = (String)itr_attrs.next();
//		String attrVal = (String)attributes.get(attrName);
//		newElement.addAttribute(attrName, attrVal);
//	}
//	if(parentElement != null){
//		if(addBefore_childElement == null){
//			parentElement.add(newElement);
//		}else{
//			List childElements = parentElement.elements();
//			for(org.dom4j.Element childElement : childElements){
//				if(childElement.equals(addBefore_childElement)){
//					int addBeforeIndex = childElements.indexOf(childElement);
//					childElements.add(addBeforeIndex, newElement);
//					break;
//				}
//			}
//		}
//	}
//
//	return newElement;
//}
//
//public String matchAndGetFileNameWithVersion(String fileNameWithPath, String alfrescoFileNameWithPath, boolean isMinor)
//{
//	int index = fileNameWithPath.lastIndexOf("/");
//	if(index == -1)
//		index = fileNameWithPath.lastIndexOf("\\");
//	String fileName = fileNameWithPath.substring(index+1);
//	System.out.println(fileName);
//	String filePath = fileNameWithPath.substring(0, index+1);
//	String alfrescoFileName = alfrescoFileNameWithPath.substring(alfrescoFileNameWithPath.lastIndexOf(":")+1);
//
//	String prefixWithVer = alfrescoFileName.substring(0, alfrescoFileName.lastIndexOf("_ver")+4);
//	String prefix = alfrescoFileName.substring(0, alfrescoFileName.lastIndexOf("_ver"));
//	String suffix = alfrescoFileName.substring(alfrescoFileName.lastIndexOf("."));
//	String versionNumber = alfrescoFileName.replace(prefixWithVer, "").replace(suffix, "");
//	Float versionNumberInDouble = Float.parseFloat(versionNumber);
//	if(isMinor){
//		versionNumberInDouble = versionNumberInDouble+0.1f;
//	}else{
//		versionNumberInDouble = Math.round(versionNumberInDouble)+1.0f;
//	}
//
//	if(fileName.indexOf("_ver") != -1){
//		if(!fileName.equalsIgnoreCase(alfrescoFileName)){
//			message("Please select the same file with or without version");
//			return null;
//		}
//	}else{
//		System.out.println(prefix+suffix);
//		if(!fileName.equalsIgnoreCase(prefix+suffix)){
//			message("Please select the same file with or without version");
//			return null;
//		}
//	}
//	System.out.println("1 = "+filePath+prefixWithVer+versionNumberInDouble+suffix);
//
//	System.out.println("2 = "+filePath+fileName);
//	File file = new File(filePath+fileName);
//	File verFile = new File(filePath+prefixWithVer+versionNumberInDouble+suffix);
//	if(verFile.exists())
//		verFile.delete();
//	file.renameTo(verFile);
//	return verFile.getAbsolutePath();
//}
//// The following API getDate and getTime are used to retrieve system current date and time to be displayed on initial examination screen
//public String getCurrentDate()
//{
//	  DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//	   //get current date time with Date()
//	   Date date = new Date();
//	   return (dateFormat.format(date));
//}
//public void getCurrentTime()
//{
//}
//
//public void makeGridForm(String dGridID, List formDataValues){
//	Component gridComp = getComponentInPage(dGridID);
//	Listbox dGrid = (Listbox)gridComp;
//	Iterator itr_formDatavalues = formDataValues.iterator();
//	while(itr_formDatavalues.hasNext()){
//		Object[] formFieldValues = itr_formDatavalues.next();
//		String labelName = (String)formFieldValues[0];
//		Object control = (Object)formFieldValues[1];
//		Object controlDataValue = (Object)formFieldValues[2];
//		String controlValue=null;
//		List comboItemList=null;
//		if(controlDataValue  instanceof List){
//			comboItemList =(List)formFieldValues[2];
//		}else{
//			controlValue =(String)formFieldValues[2];
//		}
//		Listitem listItem =new Listitem(); 
//		Listcell formNameField = new Listcell();
//		formNameField.setLabel(labelName);
//		formNameField.setParent(listItem);
//		Listcell formListCell = new Listcell();
//		if(control instanceof Combobox){
//			Combobox  ctrl = new Combobox();
//			for(int i=0; i < comboItemList.size(); i++){
//				String comboItemValue =comboItemList.get(i);
//				String[] keyValuePair = comboItemValue.split("\\|");
//				String comboItemKey =keyValuePair[0];
//				String comboItemLabel =keyValuePair[1];
//				formListCell.setStyle("text-align:center");
//				ctrl.setWidth("80%");
//				Comboitem comItem = new Comboitem();
//				comItem.setLabel(comboItemLabel);
//				comItem.setStyle("text-align:center");
//				comItem.setValue(comboItemKey);
//				ctrl.setText("-Select-");
//				ctrl.appendChild(comItem);
//				ctrl.setReadonly(true);
//				ctrl.setStyle("text-align:center");
//				ctrl.setParent(formListCell);
//				formListCell.setParent(listItem);	
//			}
//		}else if(control instanceof Datebox){
//			formListCell.setStyle("text-align:center");
//			Datebox  ctrl = new Datebox();
//			ctrl.setWidth("80%");
//			ctrl.setFormat("yyyy-MM-dd");
//			ctrl.setText(controlValue);
//			ctrl.setInplace(true);
//			ctrl.setStyle("text-align:center");
//			ctrl.setParent(formListCell);
//			formListCell.setParent(listItem);	
//		}else if(control instanceof Textbox){
//			formListCell.setStyle("text-align:center");
//			Textbox  ctrl = new Textbox();
//			ctrl.setWidth("80%");
//			ctrl.setText(controlValue);
//			ctrl.setInplace(true);
//			ctrl.setStyle("text-align:center");
//			ctrl.setParent(formListCell);
//			formListCell.setParent(listItem);
//		}else if(control instanceof Label){
//			formListCell.setStyle("text-align:center");
//			Label  ctrl = new Label();
//			ctrl.setWidth("80%");
//			ctrl.setValue(controlValue);
//			ctrl.setStyle("text-align:center");
//			ctrl.setParent(formListCell);
//			formListCell.setParent(listItem);
//		}
//		listItem.setParent(dGrid);
//	}
//}
}