package com.example.prediction.graphic;

import java.util.Vector;

import org.afree.chart.AFreeChart;
import org.afree.data.category.DefaultCategoryDataset;

import com.example.prediction.logica.metrics.AbsMetricsEvaluation;
import com.example.prediction.logica.models.AbsModeler;

public abstract class AbsGraphics {
	
	protected double min;
	protected double max;
	protected DefaultCategoryDataset dataset;
	protected int height;
	protected int width;
	protected Vector<AbsModeler> series;
	protected AbsMetricsEvaluation metricsEvaluation;
	
	
	protected AFreeChart getChart(Vector<AbsModeler> series, String chartTitle, String axisX, String axisY) throws Exception{
		AFreeChart chart = createChart(chartTitle,axisX,axisY);
		customizeChart(chart);
		return chart;	
	}
		
	public void setSeries(Vector<AbsModeler> series){	
		this.series = series; 
	}

	/* ABSTRACT METHODS  */
	protected abstract AFreeChart createChart(String chartTitle, String axisX, String axisY) throws Exception;
	protected abstract void customizeChart(AFreeChart chart) throws Exception;
}
