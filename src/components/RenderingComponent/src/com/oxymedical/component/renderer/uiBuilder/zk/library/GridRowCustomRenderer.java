package com.oxymedical.component.renderer.uiBuilder.zk.library;
import org.zkoss.zk.ui.*;
import org.zkoss.zk.ui.event.*;
import org.zkoss.zk.ui.util.*;
import org.zkoss.zk.ui.ext.*;
import org.zkoss.zk.au.*;
import org.zkoss.zk.au.out.*;
import org.zkoss.zul.*;


public class GridRowCustomRenderer implements RowRenderer 
{

	@Override
	public void render(Row arg0, Object arg1, int arg2) throws Exception {
		// TODO Auto-generated method stub
		
	}
//	public static Window window = null;
//	
//	public static Label timeLabel;
//	public static Combobox methodCombobox;
//	public static Combobox feedTypeCombobox;
//	public static Textbox volumeTextBox;
//	public static Combobox  ivFluidsCombobox;
//	public static Textbox ivPerHourTextbox;
//	public static Textbox aaPerHourTextbox;
//	public static Textbox lipidPerHourTextbox;
//	public static Textbox ivTotalTextbox;
//	public static Textbox aspireVomitTextbox;
//	public static Textbox urineTextbox;
//	public static Combobox  boCombobox;
//	public static Textbox saO2Textbox;
//	public static Textbox hrTextBox;
//	public static Textbox rrTextBox;
//	public static Textbox bpTextBox;
//	public static Combobox FIO2Combobox;
//	public static Textbox tempAxillaTextbox;
//	public static Combobox tempIncBedCombobox;
//	public static Textbox cpapTextbox;
//	
//	ControlEventListener controlListener= new ControlEventListener();
//    public void render(final Row row, final java.lang.Object data) 
//    {
//    	window = (Window)row.getParent().getRoot();
// 
//       	
//    	String[] ary = (String[]) data;
//        System.out.println("Inside GridRowCustomRenderer ary length="+ary.length+" ary[20]"+ ary[20]);
//        String uniqueId =  ary[20];
//       
//        
//        timeLabel = new Label(ary[0]);
//        timeLabel.setId("Time"+ uniqueId);
//        timeLabel.setParent(row);
//        
//        methodCombobox =  new Combobox(ary[1]);
//        methodCombobox.setId("Method"+ uniqueId);
//        controlListener.onEvent(methodCombobox);
//        methodCombobox.setText(ary[1]);
//        methodCombobox.setParent(row);
//        
//        feedTypeCombobox = new Combobox(ary[2]);
//        feedTypeCombobox.setId("FeedType"+ uniqueId);
//        controlListener.onEvent(feedTypeCombobox);
//        feedTypeCombobox.setText(ary[2]);
//        feedTypeCombobox.setParent(row);
//       
//        volumeTextBox = new Textbox(ary[3]);
//        volumeTextBox.setId("Volume"+uniqueId);
//        volumeTextBox.setParent(row);
//                
//        ivFluidsCombobox = new Combobox(ary[4]);
//        ivFluidsCombobox.setId("IVFluidsGrid"+ uniqueId);
//        controlListener.onEvent(ivFluidsCombobox);
//        ivFluidsCombobox.setText(ary[4]);;
//        ivFluidsCombobox.setParent(row);
//        
//        ivPerHourTextbox =  new Textbox(ary[5]);
//        ivPerHourTextbox.setId("IVPerHour"+uniqueId);
//        ivPerHourTextbox.setParent(row);
//        
//        
//        aaPerHourTextbox = new Textbox(ary[6]);
//        aaPerHourTextbox.setId("AAPerHr"+uniqueId);
//        aaPerHourTextbox.setParent(row);
//        
//        
//        lipidPerHourTextbox = new Textbox(ary[7]);
//        lipidPerHourTextbox.setId("LipidPerHr"+uniqueId);
//        lipidPerHourTextbox.setParent(row);
//        
//        ivTotalTextbox =  new Textbox(ary[8]);
//        ivTotalTextbox.setId("IVTotal"+uniqueId);
//        controlListener.onEvent(ivTotalTextbox);
//        ivTotalTextbox.setParent(row);
//        
//        aspireVomitTextbox = new Textbox(ary[9]);
//        aspireVomitTextbox.setId("AspireVomit"+ uniqueId);
//        aspireVomitTextbox.setParent(row);
//        
//        urineTextbox = new Textbox(ary[10]);
//        urineTextbox.setId("UrineGrid"+uniqueId);
//        urineTextbox.setParent(row);
//        
//        
//        boCombobox =  new Combobox(ary[11]);
//        boCombobox.setId("BO"+ uniqueId);
//        controlListener.onEvent(boCombobox);
//        boCombobox.setText(ary[11]);;
//        boCombobox.setParent(row);
//        
//        saO2Textbox = new Textbox(ary[12]);
//        saO2Textbox.setId("SAO2"+ uniqueId);
//        saO2Textbox.setParent(row);
//        
//        hrTextBox =  new Textbox(ary[13]);
//        hrTextBox.setId("HR"+uniqueId);
//        hrTextBox.setParent(row);
//        
//        rrTextBox =   new Textbox(ary[14]);
//        rrTextBox.setId("RR"+uniqueId);
//        rrTextBox.setParent(row);
//       
//        bpTextBox =  new Textbox(ary[15]);
//        bpTextBox.setId("BP"+uniqueId);
//        bpTextBox.setParent(row);
//        
//        FIO2Combobox =  new Combobox(ary[16]);
//        FIO2Combobox.setId("FIO2"+ uniqueId);
//        controlListener.onEvent(FIO2Combobox);
//        FIO2Combobox.setText(ary[16]);;
//        FIO2Combobox.setParent(row);
//        
//        tempAxillaTextbox = new Textbox(ary[17]);
//        tempAxillaTextbox.setId("TempAxilla"+ uniqueId);
//        tempAxillaTextbox.setParent(row);
//        
//        tempIncBedCombobox = new Combobox(ary[18]);
//        tempIncBedCombobox.setId("TempIncBed"+ uniqueId);
//        controlListener.onEvent(tempIncBedCombobox);
//        tempIncBedCombobox.setText(ary[18]);
//        tempIncBedCombobox.setParent(row);
//        
//        cpapTextbox = new Textbox(ary[19]);
//        cpapTextbox.setId("CPAP"+ uniqueId);
//        cpapTextbox.setParent(row);
//       
//        row.setAttribute("uniquevalue", uniqueId);
//        System.out.println("GridRowCustomRenderer ary uniquevalue=" + uniqueId);
//    }
}
