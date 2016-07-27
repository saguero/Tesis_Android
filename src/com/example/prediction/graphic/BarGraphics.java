package com.example.prediction.graphic;


import com.example.prediction.logica.*;
import com.example.prediction.logica.AbsMetricsEvaluation.*;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Vector;

import org.afree.chart.ChartFactory;
import org.afree.chart.AFreeChart;
import org.afree.chart.axis.ValueAxis;
import org.afree.chart.block.BlockBorder;
import org.afree.chart.labels.CategoryItemLabelGenerator;
import org.afree.chart.labels.StandardCategoryItemLabelGenerator;
import org.afree.chart.plot.CategoryPlot;
import org.afree.chart.plot.PlotOrientation;
import org.afree.chart.renderer.category.BarRenderer;
import org.afree.chart.renderer.category.CategoryItemRenderer;
import org.afree.data.category.DefaultCategoryDataset;
import org.afree.graphics.PaintType;
import org.afree.graphics.SolidColor;
import org.afree.ui.RectangleInsets;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;

public class BarGraphics extends AbsGraphics {
	private Vector<Integer> bestResults;
	private Metric[] categorys;
	private BitmapDrawable image;
	private Context context;
	
	class CustomRenderer extends BarRenderer{
		private static final long serialVersionUID = 1L;

		public CustomRenderer() {}
		
		public PaintType getItemPaintType(final int row, final int column) {
			int index1 = column * 2;									//ROW: SERIE (BARRA)
			  int index2 = (column * 2) + 1;							//COLUMN: CATEGORY (METRIC)
			  if(column < 4){
				  if(row == bestResults.elementAt(index1)){
					  return Config.Graphic.GRAPHIC_BAR_COLOR_BESTRESULT1;
				   }
				   if(row == bestResults.elementAt(index2)){
					   return Config.Graphic.GRAPHIC_BAR_COLOR_BESTRESULT2;
					   
				   }  
			  }
				return Config.Graphic.GRAPHIC_BAR_COLOR;																					  
		   }
		
		//SHOW VALUES ON BAR WHICH RESULTS ARE BETTER!!
		public CategoryItemLabelGenerator getItemLabelGenerator(int i, int j){ 				//J = COLUMN = METRIC
			int index1 = j * 2;																//I = ROW = SCHEME
			int index2 = (j * 2) + 1;
			if(bestResults.elementAt(index1) == i || bestResults.elementAt(index2) == i)	
				return new StandardCategoryItemLabelGenerator("{2}", new DecimalFormat("##.###"));
			return null;
		} 
	}
	
	public BarGraphics(Context context,BitmapDrawable image) {
		this.context = context;
		this.image = image;
	}

	//DEFINE EL ANCHO DE LAS BARRAS 
	private double itemMargin(int categorys, int series){
		if (categorys == 1 || (categorys == 2 && series == 2) )
			return -1.2;
		else
			if(categorys == 2 && (series == 2 || series == 3))
				return -0.5;
			else
				if( (categorys == 2 && series == 5) || 
						(categorys == 3 && (series == 2 || series == 3)) ||
							(categorys == 4 && series == 2) )
					return 0;
		return 0.2;
	}

	private void settings(){
		bestResults = new Vector<Integer>();
		min = Double.MAX_VALUE;
		max = Double.MIN_VALUE;
		double value=0;
		Vector<Double> values = new Vector<Double>();
		Vector<Double> auxValues = new Vector<Double>();
																							//row = serie
		for(int column=0; column < dataset.getColumnCount(); column++){						//column = category
			Required req = categorys[column].getRequired();
			for(int row=0; row < dataset.getRowCount(); row++) {
				value = dataset.getValue(row, column).doubleValue();
				values.add(value);
				auxValues.add(value);
				if(value < min)
					min = value;
				if(value > max)
					max = value;	
			}
			Collections.sort(auxValues);
			if(req.equals(Required.MAX)){
				bestResults.add(values.indexOf(auxValues.elementAt(auxValues.size()-1)));	//PLANTEAR EXCEPCION SI SOLO TENGO UN SCHEME
				if(auxValues.size() > 1)
					bestResults.add(values.indexOf(auxValues.elementAt(auxValues.size()-2)));
			}
			else {
				bestResults.add(values.indexOf(auxValues.elementAt(0)));
				if(auxValues.size() > 1)
					bestResults.add(values.indexOf(auxValues.elementAt(1)));
			}
			auxValues.removeAllElements();
			values.removeAllElements();
		}
		max += max/10;				
		min = 0;
	}
	
	private AFreeChart graphed(Vector<Metric> metrics, AbsDataset trainingSet, AbsEvaluation evaluator) throws Exception{
		int index=0;
		categorys = new Metric[metrics.size()];
		for(Metric m:metrics){	
			categorys[index]=m;
			index++;
		}

		configureDataset(trainingSet, evaluator);
		return getChart(series, context.getString(Config.Graphic.GRAPHIC_BAR_TITLE_CHART),
				context.getString(Config.Graphic.GRAPHIC_BAR_TITLE_AXISX),  context.getString(Config.Graphic.GRAPHIC_BAR_TITLE_AXISY) );	
	}
	
	protected AFreeChart createChart(String chartTitle, String axisX, String axisY) throws Exception{
		AFreeChart chart = ChartFactory.createBarChart(
				chartTitle,       
				axisX,               
				axisY,                  
	            dataset,                 
	            PlotOrientation.VERTICAL,
	            true, true, false );
	    return chart;
	}
	 
	protected  void configureDataset(AbsDataset trainingSet, AbsEvaluation evaluator) throws Exception{
		dataset = new DefaultCategoryDataset();
		Vector<AbsClassifier> axisY = series;										
		  double value=0;
		  for(int y=0; y< axisY.size(); y++){	
			  Object e = evaluator.evaluateUseTrainingSet(trainingSet, axisY.elementAt(y));
			  for(int x=0; x< categorys.length; x++){
				  value = categorys[x].calculateNormalized(e);								
				  dataset.addValue(value, axisY.elementAt(y).getName(), categorys[x].getID()); 
			  }
		  }
	}
	
	protected void customizeChart(AFreeChart chart) throws Exception{
		height = Config.Graphic.GRAPHIC_BAR_HEIGHT;
		width = Config.Graphic.GRAPHIC_BAR_WIDTH;
		settings();
		
		chart.setBackgroundPaintType(new SolidColor(Color.WHITE));	
		
		CategoryPlot plot = chart.getCategoryPlot();
	  
		
		plot.setBackgroundImage(image);
		plot.setBackgroundAlpha(1);				//VER
	    plot.setOutlineVisible(false);
	    plot.setDomainGridlinePaintType(new SolidColor(Color.WHITE));
	    plot.setRangeGridlinePaintType(new SolidColor(Color.WHITE));
	    
	   CategoryItemRenderer renderer = new CustomRenderer();
	   
	    ((BarRenderer) renderer).setMaximumBarWidth( Config.Graphic.GRAPHIC_BAR_MAXBARWIDTH);
		((BarRenderer) renderer).setItemMargin(itemMargin(dataset.getColumnCount(),dataset.getRowCount()));
	    
	    if(dataset.getRowCount() < Config.Graphic.GRAPHIC_BAR_MAXLABELSHORIZONTAL) {
	    	chart.getLegend().setFrame(BlockBorder.NONE);	
			//TOP-LEFT-BOTTOM-RIGHT
			chart.getLegend().setItemLabelPadding(new RectangleInsets(5.0,2.0,3.0,width));
			
		}
	    
	   for(int i=0;i<dataset.getRowCount();i++){
	    	renderer.setSeriesItemLabelsVisible(i, true);
	    	renderer.setSeriesPaintType(i, Config.Graphic.GRAPHIC_BAR_COLOR );
	   }
	    
	    renderer.setBaseItemLabelsVisible(true);
	    plot.setRenderer(renderer);
	    //SET RANGE AXIS
	    ValueAxis yAxis = plot.getRangeAxis();
	    yAxis.setRange(min, max);  	    
	}

	
	
	public AFreeChart graphedErrorPredictionNormalized(AbsDataset trainingSet, AbsEvaluation evaluator, AbsMetricsEvaluation metricsEvaluation) throws Exception{
		Vector<Metric> metrics = metricsEvaluation.ErrorPredictionNormalizedMetrics();
		return graphed(metrics, trainingSet, evaluator);	
	}
	
	public AFreeChart graphedErrorPredictionScale(AbsDataset trainingSet, AbsEvaluation evaluator, AbsMetricsEvaluation metricsEvaluation) throws Exception{
		Vector<Metric> metrics = metricsEvaluation.ErrorPredictionScaleMetrics();
		return graphed(metrics, trainingSet, evaluator);	
	}
	
	public AFreeChart graphedRelationData(AbsDataset trainingSet, AbsEvaluation evaluator, AbsMetricsEvaluation metricsEvaluation) throws Exception{
		Vector<Metric> metrics = metricsEvaluation.RelationDataMetrics();
		return graphed(metrics, trainingSet, evaluator);
	}
	
	
	
	
}
