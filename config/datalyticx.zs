import java.io.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.zkoss.io.Files;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zk.ui.Session;
import org.zkoss.util.media.Media;

import com.oxymedical.component.renderer.RendererComponent;
import com.oxymedical.component.renderer.command.UiLibraryCompositeCommand;
import com.oxymedical.component.renderer.uiBuilder.zk.library.UiLibraryUtil;
import com.oxymedical.component.renderer.uiBuilder.zk.library.LogOutCommand;
import com.oxymedical.component.renderer.uiBuilder.zk.library.ItemRendererArray;
import com.oxymedical.core.commonData.IData;
import com.oxymedical.core.commonData.IFormPattern;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.dateutil.DateUtil;
import com.oxymedical.core.propertyUtil.PropertyUtil;
import com.oxymedical.core.querydata.*;
import com.oxymedical.core.router.IDataUnitRouter;
import com.oxymedical.core.userdata.IUserPattern;


//tree variables
private Hashtable formValues = new Hashtable();
private LinkedHashMap columnOrder = new LinkedHashMap();
private Hashtable levelPrimaryId = new Hashtable();
private Hashtable comboTable = new Hashtable();
private String comboSelectedValue;
private int treeNodeCount = 0;
private int lastTreeLevel = -1;
private String displayLabelPaging=null;
private String dataPatternId ="";
private String formPatternId ="";
private boolean loginStatus = false;
List textId = new ArrayList();
UiLibraryUtil uiLibraryUtil=new UiLibraryUtil();
boolean validRequest = true;
String webMessage = null;
// String deleteRecordId=null;
Object comboObj=null;
LinkedHashMap updateHash= null;
boolean validationValue = true;
String[] queryFields = null; 
IHICData returnHicData = null;
private String pagingId;
private String listId;
boolean validListRequest = false;
LogOutCommand logout = null;
private int totalSize;
private int cancel = 2;
private int yes = 16;

public void updateSession(String id, String value)
{
	session.setAttribute(id,value);
}

void setDataStatus(String status, String formPattern, String dataPattern)
{
	invokeComponent(null, "changeDOStatus", null, status + "_SEP_" + formPattern + "_SEP_" + dataPattern);
}

public String getSessionData(String rowId)
{
	String idValue = session.getAttribute(rowId);
	// session.setAttribute(rowId,null);
	return idValue;
}

public String logOut(String pageId)
{
	String userId = getSessionData("userId");
	String retValue = invokeComponent("com.oxymedical.component.useradmin","logoutUser","com.oxymedical.component.useradmin.UserAdminComponent",userId);
	updateSession("OLDTime",null);
	updateSession("login", null);
	setDataStatus("DEFAULT", pageId, null);
	updateSession("userId",null);
	session.invalidate();
	return retValue;

}

void message(String value)
{
	Messagebox.show(value, "DataLyticx", Messagebox.OK, Messagebox.INFORMATION);
}

public boolean checkSessionTime(String method)
{
	boolean isSession = true;
	if(method.equalsIgnoreCase("logoutUser"))
	{
		return isSession;
	}
	String oldTime = getSessionData("OLDTime");
	String userId = getSessionData("userId");
	
	if(userId==null)
	{
		return isSession;
	}
	if(oldTime!=null)
	{
		Date oldDate = DateUtil.stringToDate(oldTime, "yyyy-MM-dd HH:mm:ss");
		Date newDate = new Date();
		Long oldTimeValue = oldDate.getTime();
		Long currentTime = newDate.getTime();
		Long timeDiff = currentTime-oldTimeValue;
		long totalMinutes = timeDiff / (60 * 1000);
		String time=PropertyUtil.setUpProperties("SESSION_TIME");
		long sessionTime=session.getMaxInactiveInterval();
		if(time!=null)
		{
		
			sessionTime = Integer.parseInt(time);
		}
		if(totalMinutes>sessionTime)
		{
			isSession=false;
			message("Sorry your session has timed out. Please sign in again.");		
			logOut("login");
			updateSession("LogOut","true");
			session.invalidate();
			return false;
		}
	}
	Date newDate = new Date();	
	String toDate = DateUtil.formatDate(newDate, "yyyy-MM-dd HH:mm:ss");
	updateSession("OLDTime",toDate);
	return isSession;

}

void setDatapatternId(String dataId)
{
	dataPatternId = dataId;
}

void setFormPatternId(String formId)
{
	formPatternId = formId;
	validationValue=true;
}

public void addFormValue(String id, Object value)
{

	if(id!=null && value!=null)
	{
		formValues.put(id,value);	
	}

}

public void showComboData(IHICData hicData,Object comboObj)
{
	if(hicData==null)
	{
		if(returnHicData ==null)
		{
				return;
		}
		else
		{
				hicData = returnHicData;
		}
	}
	comboObj.setText("");
	comboObj.getItems().clear();
	IData dataUnit = hicData.getData();
	List listValue = null;
	Hashtable formValues=new Hashtable();
	formValues=dataUnit.getFormPattern().getFormValues();
	if(dataUnit.getQueryData().getListData()!=null)
	{
		listValue = dataUnit.getQueryData().getListData();
	}
	else
		return;
	String[][] allValues = dataUnit.getQueryData().iterateListData(listValue);
	if(allValues==null)
		return;	
	comboTable.put(comboObj.getId(),listValue);
	uiLibraryUtil.showComboData(allValues,(Combobox)comboObj);
}


public boolean equalsTest(String firstStr,String secondStr)
{
	
	if(firstStr!=null && secondStr!= null)
	{
	      firstStr=firstStr.trim();
	      secondStr=secondStr.trim();
		if (firstStr.equalsIgnoreCase(secondStr))
			return true;
	}
	return false;
}

private void showComboData(String comboid)
{
    Window rootFormValue = self.getRoot();
	Object elementObj = rootFormValue.getAttribute(comboid,true);
	if(elementObj !=null)
	{
		showComboData(null,elementObj);	
	}
}

String invokeComponent(String componentId ,String method ,String classname, String paramList)
{
	String isValidStatus = "true";
	IDataUnitRouter router = (IDataUnitRouter)RendererComponent.dataUnitRouter;
	Object rootFormValue = self.getRoot();
	/*
	*Before completing any request it will check that session is valid or not.
	*
	*/
	checkSessionTime(method);
	
	/*
	*Following code is used when session would be time out
	*/
	String logOutValue = getSessionData("LogOut");		
	if(logOutValue!=null)
	{
	
		if(logOutValue=="true")
		{
			return "";
		}
		return "";
	}
	
    if(method.equalsIgnoreCase("save")||method.equalsIgnoreCase("addUserFromApplication"))
	{		
		if(clientSideValidation(formPatternId))
		{
		  
		 	validationValue=true;
		}
		else
		{
			validationValue=false;
			isValidStatus = "false";
		}
	}
	if(validationValue)
	{
    UiLibraryCompositeCommand command=new UiLibraryCompositeCommand();
	command.setMethodName(method);
	command.setRouter(router);
    command.setClassname(classname);
	command.setComponentId(componentId);
    command.setDataPatternId(dataPatternId);
    command.setFormPatternId(formPatternId);
    command.setFormValues(formValues);
	command.setRootFormValue((Window)rootFormValue);
	command.setParamList(paramList);
	command.setSession((Session)session);
	command.setComboSelectedValue(comboSelectedValue);
	command.setValidListRequest(validListRequest);
	command.setPagingId(pagingId);
	command.execute();
	returnHicData=command.getHICData();
	if(returnHicData==null)
	{
		isValidStatus = "false";
		return isValidStatus;
	}
	if (returnHicData.getData()!=null) 
    {
		if ((returnHicData.getData().getStatus()!=null) && ((returnHicData.getData().getStatus().equalsIgnoreCase("error")) || (returnHicData.getData().getStatus().equalsIgnoreCase("false"))) )
		{
			isValidStatus = "false";
			return isValidStatus;
		}
	}
  }
  else
  {
 	  return "false";
  }

	if(method.equalsIgnoreCase("authenticateUserInLDAP")){
		if(returnHicData.getLdapData() != null) {
			return (String)returnHicData.getLdapData().getAttributes().get("UserAuthenticatedInLDAP");
		}
	}else if(method.equalsIgnoreCase("searchInLDAP")){
		if(returnHicData.getLdapData() != null) {
			org.dom4j.Document resultDoc = returnHicData.getLdapData().getLdapDoc();
			if(resultDoc != null){
				return resultDoc.asXML();
			}else{
				return null;
			}
		}
	}
	if(method.equalsIgnoreCase("authenticateUserEx"))
	{
		if(returnHicData.getData()!=null && returnHicData.getData().getStatus()!=null) 
		{
			if(returnHicData.getData().getStatus().equalsIgnoreCase("invalid"))
			{
				//formValues =new Hashtable();
				return "false";
			}
			else
			{
				loginStatus = true;
				
								
			}
		}
	}
	if(method.equalsIgnoreCase("executeList"))
	{
		if(returnHicData.getData()!=null && returnHicData.getData().getStatus()!=null) 
		{
			IData data=returnHicData.getData();
			IFormPattern formpattern=data.getFormPattern();
			String formPatternId=formpattern.getFormId();
			Hashtable formvalues=new Hashtable();
			formvalues=data.getFormPattern().getFormValues();
		}
	}
    if(method.equalsIgnoreCase("sendandrecieve"))
    {
	      if(returnHicData.getData()!=null && returnHicData.getData().getStatus()!=null ) 
	      {
	            String message= returnHicData.getData().getStatus();
	            String msg = "The return message is:"+message;
	            message(msg);
	            return message;
	       }
  	}
	if(method.equalsIgnoreCase("invokeClientCall"))
	{
		webMessage = (String) returnHicData.getMetaData().getCommonObject();
		return webMessage;
	}
	sessionUpdate(returnHicData);
	return isValidStatus;
}

void sessionUpdate(IHICData returnData)
{
	String userkeyId = returnData.getData().getUserId();
	String[] keyArray;
	if(userkeyId!=null)
	{
		keyArray = userkeyId.trim().split("-");
	}
	if(session.getAttribute("userId")== null || (session.getAttribute("userId")!=returnData.getData().getUserId()))
	{
		//session.setMaxInactiveInterval(600);
		//Devices.setTimeoutURI("ajax", "/zul/Logout.zul");
		
		if(keyArray!=null)
		{
			session.setAttribute("userId",keyArray[0]);
			if(keyArray.length>1)
			{
				session.setAttribute("loginKey",keyArray[1]);
			}
		}
		Set usp = returnData.getData().getUserPatterns();
			if(usp!=null)
			{ 
			
			        List rights=null;
				String userpatterids = "";
				Iterator usrpt = usp.iterator();
				while(usrpt.hasNext())
				{
					IUserPattern pt = (IUserPattern)usrpt.next();
					if(userpatterids.equals(""))
					{
						userpatterids = pt.getUserPatternId();
					    if(pt.getRoles()!=null && pt.getRoles().getRights()!=null)
						{
						 rights=pt.getRoles().getRights();
						 session.setAttribute("rights",rights);
						 }
						
					}
					else
					{
						userpatterids = userpatterids+"##"+pt.getUserPatternId();
						if(pt.getRoles()!=null && pt.getRoles().getRights()!=null)
						{
						 rights=pt.getRoles().getRights();
						 session.setAttribute("rights",rights);
						 }
					}
				}
				session.setAttribute("userPatterns",userpatterids);
				
			}

	}
	if(session.getAttribute("EIBUNID")== null || (session.getAttribute("EIBUNID")!=returnData.getUniqueID()))
	{
		session.setAttribute("EIBUNID",returnData.getUniqueID());
	}
}

public boolean clientSideValidation(String formId)
{
	boolean isValid = true;
	Window rootFormValue = self.getRoot();
	Object formObj = rootFormValue.getAttribute(formId,true);
	if(formObj == null){
		if(rootFormValue instanceof Window){
			formObj = (Window)rootFormValue; //just incase the window is a pop-up
		}
	}
	//alert("formId: " + formId);
	//alert("rootFormValue: " + rootFormValue);
	//alert("formObj: " + formObj);
	String msg=uiLibraryUtil.clientSideValidation((Component)formObj,formValues );
	
	if(msg != null)
		{
			msg = msg.trim();
		}
		try {
			if(msg !="" && msg.length() !=0)
			{
				//Messagebox.show(msg,title,Messagebox.OK, Messagebox.ERROR);
				message(msg);
				isValid = false;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
 	return isValid;
}

public boolean showListData(IHICData hicData, Object elementObj)
{

				System.out.println("	elementObj >>>"+elementObj);		
		// Here element Object is grid
		Object listValues = session.getAttribute("dbListValue");
		int noOfheader = 0;
		if(elementObj !=null)
			noOfheader = getNoOfHeader(elementObj);
		if(listValues==null)
		{
			if(hicData==null)
			{
				if(returnHicData ==null)
				{
					return false;
				}
				else
				{
					hicData = returnHicData;
				}
			}
			IData dataUnit = hicData.getData();
			List listValue = null;
			if(dataUnit.getQueryData().getListData()!=null)
			{
				listValue = dataUnit.getQueryData().getListData();
			}
			else
				return false;
			String[][] allValues = dataUnit.getQueryData().iterateListData(listValue);
			ListModel myModel = null;
			if(allValues==null)
			{
				//System.out.println("Inside showdatalist 1"+elementObj);
				myModel = new ListModelList();
				elementObj.setModel(myModel);
				System.out.println("	elementObj >>>"+elementObj);
				if(elementObj instanceof Listbox)
				{
					elementObj.setItemRenderer(new ItemRendererArray());
				}
				else 
				{
					elementObj.setRowRenderer(new GridRowCustomRenderer());
				}	
				return false;
			}
			else
			{
				//System.out.println("Inside showdatalist 2"+elementObj+"allValues="+allValues.length);
				myModel = new ListModelList(allValues);
				elementObj.setModel(myModel);
				if(elementObj instanceof Listbox)
				{
					elementObj.setItemRenderer(new ItemRendererArray());
				}
				else 
				{
					elementObj.setRowRenderer(new GridRowCustomRenderer());
				}		
			}
		}
		else
		{
			String[][] allValues = QueryData.iterateListData(listValues);
			System.out.println("Inside showdatalist else"+"allValues="+allValues.length);
			if(allValues!=null)
			{
				allValues = getGridLengthValue(allValues,noOfheader);
				ListModel myModel = new ListModelList(allValues);
				elementObj.setModel(myModel);
				System.out.println("Inside showdatalist elementObj="+elementObj);
				if(elementObj instanceof Listbox)
				{
					elementObj.setItemRenderer(new ItemRendererArray());
				}
				else 
				{
					elementObj.setRowRenderer(new GridRowCustomRenderer());
				}	
			}
			else
				return false;
		}
		return true;
		
}
// This method return no of header of list or grid.
public int getNoOfHeader(Object gridObj)
{
	List list =gridObj.getChildren();
	int headerNo=0;
		if(list!=null && list.size()>0)
		{
			Iterator itr = list.iterator();
			while(itr.hasNext())
			{
				Object obj = itr.next();
				if(obj instanceof Listhead)
				{
					List listHead =obj.getChildren();
					headerNo = listHead.size();
					break;
				}
			}
		}
	return headerNo;
}

public String[][] getGridLengthValue(String[][] allValues, int len)
{
	String[][] dupValue = new String[allValues.length][];;
	for(int valueCounter =0;valueCounter<allValues.length;valueCounter++)
	{
		dupValue[valueCounter] = new String[len];
		for(int col =0;col<len;col++)
		{
			dupValue[valueCounter][col] = allValues[valueCounter][col];
		}
	
	}
	return dupValue;

}
public String getComboBoxValue(String comboBox){
	String comboSelectedVal = null;
	Component rootFormValue = self.getRoot();
	Combobox comboObj = (Combobox)rootFormValue.getVariable(comboBox, true);
	if(comboObj == null){
		comboObj = (Combobox) getComponentIfInPlaceHolder(comboBox);
	}
	if(comboObj != null){
		Comboitem selectedComboRow = comboObj.getSelectedItem();
		if(selectedComboRow != null){
			comboSelectedVal = selectedComboRow.getLabel();
		}
	}
	return comboSelectedVal;
}

public Component getComponentIfInPlaceHolder(String compId){
	Component comp = null;
	if(self.getParent() != null){
		comp = self.getParent().getFellowIfAny(compId, true);
	}
	return comp;
}

public String getComboBoxValue(String comboBox){
	String comboSelectedVal = null;
	Component rootFormValue = self.getRoot();
	Combobox comboObj = (Combobox)rootFormValue.getAttribute(comboBox, true);
	if(comboObj == null){
		comboObj = (Combobox) getComponentIfInPlaceHolder(comboBox);
	}
	if(comboObj != null){
		Comboitem selectedComboRow = comboObj.getSelectedItem();
		if(selectedComboRow != null){
			comboSelectedVal = selectedComboRow.getLabel();
		}
	}
	return comboSelectedVal;
}

public Component getComponentIfInPlaceHolder(String compId){
	Component comp = null;
	if(self.getParent() != null){
		comp = self.getParent().getFellowIfAny(compId, true);
	}
	return comp;
}
