package com.oxymedical.component.renderer.uiBuilder.zk.library;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Chart;
import org.zkoss.zul.PieModel;
import org.zkoss.zul.SimplePieModel;
import org.zkoss.zul.Flashchart;


public class CustomPieComposerBackUp extends GenericForwardComposer
{
	
	private static PieModel createPieModel() {
		 PieModel piemodel = new SimplePieModel();
	        piemodel.setValue("Accuracy", new Double(100));
	        piemodel.setValue("Integrity", new Double(100));
	        piemodel.setValue("Validity", new Double(100));
	        piemodel.setValue("Relevance", new Double(100));
	        piemodel.setValue("Completeness", new Double(100));	        
		return piemodel;
	}
	
	@Override
	public void doAfterCompose( Component comp ) throws Exception
	{
	        ((Chart) comp).setModel(createPieModel());
	}
}