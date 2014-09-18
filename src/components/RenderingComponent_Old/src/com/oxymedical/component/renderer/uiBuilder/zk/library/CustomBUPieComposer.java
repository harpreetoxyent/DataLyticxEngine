package com.oxymedical.component.renderer.uiBuilder.zk.library;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Chart;
import org.zkoss.zul.PieModel;
import org.zkoss.zul.SimplePieModel;
import org.zkoss.zul.Flashchart;


public class CustomBUPieComposer extends GenericForwardComposer
{
	
	private static PieModel createPieModel() {
		 PieModel piemodel = new SimplePieModel();
	        piemodel.setValue("Logistics", new Double(100));
	        piemodel.setValue("Sales", new Double(100));
	        piemodel.setValue("Purchasing", new Double(100));
	        piemodel.setValue("Storage", new Double(100));
	        piemodel.setValue("Scheduling", new Double(100));	        
	        piemodel.setValue("Manufacturing", new Double(100));
		return piemodel;
	}
	
	@Override
	public void doAfterCompose( Component comp ) throws Exception
	{
	        ((Chart) comp).setModel(createPieModel());
	}
}