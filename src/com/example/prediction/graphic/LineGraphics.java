package com.example.prediction.graphic;

import java.util.Vector;

import org.afree.chart.ChartFactory;
import org.afree.chart.AFreeChart;
import org.afree.chart.axis.CategoryAxis;
import org.afree.chart.axis.ValueAxis;
import org.afree.chart.plot.CategoryPlot;
import org.afree.chart.plot.PlotOrientation;
import org.afree.chart.renderer.category.CategoryItemRenderer;
import org.afree.data.category.DefaultCategoryDataset;
import org.afree.graphics.SolidColor;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;

import com.example.prediction.logica.*;


public class LineGraphics extends AbsGraphics {
	private int bestPrediction = 0;
	private BitmapDrawable img;
	private Context context;
	
	
	public LineGraphics(Context context,BitmapDrawable image) throws Exception {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.img = image;
	}
	
	@Override
	protected AFreeChart createChart(String chartTitle, String axisX, String axisY) throws Exception {
		// TODO Auto-generated method stub	
		AFreeChart lineChart = ChartFactory.createLineChart(
				chartTitle,			
				axisX,		
				axisY,
				dataset,
				PlotOrientation.VERTICAL,
				true,true,false );
		return lineChart;
	}

	protected  void customizeChart(AFreeChart chart) throws Exception{
		
		double factorRange = 5.0;
		height = Config.Graphic.GRAPHIC_LINE_HEIGHT;
		width = Config.Graphic.GRAPHIC_LINE_WIDTH;
		
		chart.setBackgroundPaintType(new SolidColor(Color.WHITE));		
		
		CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundImage(img);
		plot.setBackgroundAlpha(1);
	    plot.setBackgroundPaintType(new SolidColor(Color.WHITE));
	    plot.setOutlineVisible(false);
	    plot.setDomainGridlinePaintType(new SolidColor(Color.WHITE));
	    plot.setRangeGridlinePaintType(new SolidColor(Color.WHITE));
	    
	    CategoryAxis categoryAxis = (CategoryAxis) plot.getDomainAxis();
	    categoryAxis.setVisible(false);
	  
	    CategoryItemRenderer renderer = plot.getRenderer();
		// FOR REAL VALUES:
		renderer.setSeriesPaintType(0, Config.Graphic.GRAPHIC_LINE_COLOR_REALVALUES);
		renderer.setSeriesStroke(0, Config.Graphic.GRAPHIC_LINE_STROKE_REALVALUES);
				
		//OTHERS:
		for(int s=1; s< dataset.getRowCount(); s++)
			renderer.setSeriesStroke(s, Config.Graphic.GRAPHIC_LINE_STROKE_PREDVALUES);
		
		//FOR BEST PREDICTED VALUES
		renderer.setSeriesStroke(bestPrediction, Config.Graphic.GRAPHIC_LINE_STROKE_REALVALUES);
		
		//RANGE VALUES:
		ValueAxis yAxis = plot.getRangeAxis();
		yAxis.setRange(min - factorRange, max + factorRange); 	
	}
	
	private void configureErrorPrediction(AbsDataset trainingSet, AbsEvaluation evaluator) throws Exception {
		// TODO Auto-generated method stub
		min = Double.MAX_VALUE;
		max = Double.MIN_VALUE;
		dataset = new DefaultCategoryDataset( );
		
		int limit = Config.Graphic.GRAPHIC_LINE_LIMIT_INSTANCES;
		
		Vector<Double> actual = trainingSet.getActualValues();
		int instances = actual.size();
		if(instances > limit)
			instances = limit;
		Vector<Double> predicted = null;
		
		for(AbsClassifier scheme: series){
			for(int v=0; v < instances; v++){
		    	  Integer index = v+1;
		    	  //REAL VALUE:
		    	  double actualValue = actual.elementAt(v);
		    	  dataset.addValue( actualValue , context.getString(Config.Graphic.GRAPHIC_LINE_LABEL_REALVALUES) , index);
		    	  max = Math.max(max,actualValue);
	  	    	  min = Math.min(min, actualValue);
			}
			
			predicted = evaluator.getPredictedValues(trainingSet,scheme);
	      	for(Integer v=0; v < instances; v++){
	      		Integer index = v+1;
	      			
	      		//PREDICTED VALUE:
	      		double predictedValue = predicted.elementAt(v);
	      		dataset.addValue( predictedValue , "Prediction - " + scheme.getName() , index );
	  	    	  
	      		max = Math.max(max,predictedValue);
	      		min = Math.min(min, predictedValue);
	      	}
		}	
	}
	
	private void configureLearningCurve(AbsDataset trainingSet, AbsEvaluation evaluator) throws Exception {
		dataset = new DefaultCategoryDataset();
		int groupInstances = Config.Graphic.GRAPHIC_LINE_INSTANCES_LEARNING_CURVE;
		int instances = (trainingSet.numInstances() / groupInstances);
		int last = trainingSet.numInstances();
		AbsDataset newTrainingSet = null;
		
		for(AbsClassifier scheme: series){
			for(int i=1; i<=instances; i++){
				Integer cantInstances = groupInstances * i; 
				int first = cantInstances+1;
				if(i == instances)
					newTrainingSet =  trainingSet;
				else
					newTrainingSet =  trainingSet.getNewDatasetByRemove(first, last );
				
				Object et = evaluator.evaluateUseTrainingSet(newTrainingSet, scheme);
				Double value = evaluator.getErrorEvaluation(et);
				dataset.addValue(value, context.getString(Config.Graphic.GRAPHIC_LINE_LABEL_TRAINING), cantInstances);
				
				max = Math.max(max,value);
		    	min = Math.min(min, value);
				
				Object ecv = evaluator.evaluateUseCrossValidation(newTrainingSet, scheme);
				value = evaluator.getErrorEvaluation(ecv);
				dataset.addValue(value, context.getString(Config.Graphic.GRAPHIC_LINE_LABEL_CROSSVALIDATION), cantInstances );
				
				max = Math.max(max,value);
		    	min = Math.min(min, value);
			}	
		}
		
		
	}
	
	public AFreeChart graphedLearningCurve(AbsDataset trainingSet, AbsEvaluation evaluator, Vector<AbsClassifier> schemes) throws Exception {
		setSeries(schemes);
		configureLearningCurve(trainingSet,evaluator);
		return getChart(series, context.getString(Config.Graphic.GRAPHIC_LINE_TITLE_CHART_LC) + " - " + schemes.elementAt(0).getName(),
				context.getString(Config.Graphic.GRAPHIC_LINE_TITLE_AXISX),
				context.getString(Config.Graphic.GRAPHIC_LINE_TITLE_AXISY) );
	}
	
	public AFreeChart graphedErrorPrediction(AbsDataset trainingSet, AbsEvaluation evaluator, Vector<AbsClassifier> schemes) throws Exception{
		setSeries(schemes);
		configureErrorPrediction(trainingSet,evaluator);
		return getChart(series, context.getString(Config.Graphic.GRAPHIC_LINE_TITLE_CHART_EP) + " - " +
								schemes.elementAt(0).getName(),
				context.getString(Config.Graphic.GRAPHIC_LINE_TITLE_AXISX),
				context.getString(Config.Graphic.GRAPHIC_LINE_TITLE_AXISY) );
	}
}
