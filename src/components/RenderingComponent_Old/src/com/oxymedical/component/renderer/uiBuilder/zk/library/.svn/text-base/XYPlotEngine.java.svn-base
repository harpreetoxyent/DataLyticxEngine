package com.oxymedical.component.renderer.uiBuilder.zk.library;

import java.awt.Shape;
import java.io.Serializable;

import org.zkoss.zkex.zul.impl.JFreeChartEngine;

import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.util.ShapeUtilities;
import org.zkoss.zul.Chart;

public class XYPlotEngine extends JFreeChartEngine implements Serializable {

	public boolean showShape = true;
	@Override
	public byte[] drawChart(Object data) {
		return super.drawChart(data);
	}

	public boolean prepareJFreeChart(org.jfree.chart.JFreeChart jfchart, Chart chart) {
		Shape cross = ShapeUtilities.createDiagonalCross(3, 1);
		CategoryPlot plot = (CategoryPlot) jfchart.getPlot();
		LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
		renderer.setSeriesPaint(0, ChartColors.COLOR_1);
		renderer.setSeriesPaint(1, ChartColors.COLOR_2);
		renderer.setSeriesPaint(2, ChartColors.COLOR_3);
		renderer.setSeriesPaint(3, ChartColors.COLOR_4);
		renderer.setSeriesPaint(4, ChartColors.COLOR_5);
		renderer.setSeriesPaint(5, ChartColors.COLOR_9);
		//renderer.setSeriesLinesVisible(0, false);
		//renderer.setSeriesShapesVisible(0, showShape);
		renderer.setSeriesLinesVisible(5, false);
		renderer.setSeriesShapesVisible(5, showShape);		
		//renderer.setSeriesShape(0, cross);
		renderer.setSeriesShape(5, cross);
		return false;
	}
}