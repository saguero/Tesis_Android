package com.example.prediction.graphic;


import com.example.prediction.logica.*;

import org.afree.chart.AFreeChart;
import org.afree.data.category.DefaultCategoryDataset;

public abstract class AbsGraphics {
	
	protected double min;
	protected double max;
	protected DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	protected int height;
	protected int width;
	protected AbsClassifier[] series;
	protected AbsMetricsEvaluation metricsEvaluation;
	
	
	protected AFreeChart getChart(AbsClassifier[] series, String chartTitle, String axisX, String axisY) throws Exception{
		AFreeChart chart = createChart(chartTitle,axisX,axisY);
		customizeChart(chart);
		return chart;	
	}
		
	public void setSeries(AbsClassifier[] series){	
		this.series = series; 
	}

	/* ABSTRACT METHODS  */
	protected abstract AFreeChart createChart(String chartTitle, String axisX, String axisY) throws Exception;
	protected abstract void customizeChart(AFreeChart chart) throws Exception;
}
