package com.oxymedical.component.renderer.uiBuilder.zk.library;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Chart;
import org.zkoss.zul.PieModel;
import org.zkoss.zul.SimplePieModel;
import org.zkoss.zul.Flashchart;


public class CustomEntityPieComposer extends GenericForwardComposer
{
	
	private static PieModel createPieModel() {
		 PieModel piemodel = new SimplePieModel();
	        piemodel.setValue("Material Master", new Double(100));
	        piemodel.setValue("Working Center", new Double(100));
	        piemodel.setValue("Plant", new Double(100));
	        piemodel.setValue("FI", new Double(100));
	        piemodel.setValue("BOM", new Double(100));	        
		return piemodel;
	}
	
	@Override
	public void doAfterCompose( Component comp ) throws Exception
	{
	        ((Chart) comp).setModel(createPieModel());
	}
}