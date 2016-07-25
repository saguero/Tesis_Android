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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;

import com.example.prediction.logica.Config;
import com.example.prediction.logica.database.AbsDatabase;
import com.example.prediction.logica.metrics.simple_metrics.SimpleERMetric;
import com.example.prediction.logica.models.AbsModeler;

public class LineGraphics extends AbsGraphics {
	private int bestPrediction = 0;
	private int image;
	Context context;
	
	
	public LineGraphics(Context context) throws Exception {
		// TODO Auto-generated constructor stub
		this.context = context;
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
		
	
		Bitmap img = BitmapFactory.decodeResource(context.getResources(), image);
		
		CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundImage(new BitmapDrawable(context.getResources(),img));
	   // plot.setBackgroundImageAlpha(0.3F);
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
	
	private void configureErrorPrediction(AbsDatabase trainingSet, int indexClass) throws Exception {
		// TODO Auto-generated method stub
		min = Double.MAX_VALUE;
		max = Double.MIN_VALUE;
		dataset = new DefaultCategoryDataset( );
		
		int limit = Config.Graphic.GRAPHIC_LINE_LIMIT_INSTANCES;
		
		Vector<Double> actual = trainingSet.getActualValues(indexClass);
		int instances = actual.size();
		if(instances > limit)
			instances = limit;
		Vector<Double> predicted = null;
		
		for(AbsModeler scheme: series){
			for(int v=0; v < instances; v++){
		    	  Integer index = v+1;
		    	  //REAL VALUE:
		    	  double actualValue = actual.elementAt(v);
		    	  dataset.addValue( actualValue , Config.Graphic.GRAPHIC_LINE_LABEL_REALVALUES , index);
		    	  max = Math.max(max,actualValue);
	  	    	  min = Math.min(min, actualValue);
			}
			
			predicted = scheme.getPredictedValue(trainingSet);
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
	
	private void configureLearningCurve(AbsDatabase trainingSet) throws Exception {
		dataset = new DefaultCategoryDataset();
		int groupInstances = 1;//Config.Graphic.GRAPHIC_LINE_INSTANCES_LEARNING_CURVE;
		//int last = trainingSet.numInstances();
		SimpleERMetric rae=new SimpleERMetric();
		int traininglimit=(int) Math.ceil(trainingSet.numInstances()*0.6);
		int CVlimit = (int) Math.ceil(trainingSet.numInstances()*0.8);
		AbsDatabase newTrainingSet = trainingSet.subDatabase(0, traininglimit);
		AbsDatabase CVSet = trainingSet.subDatabase(traininglimit, CVlimit);
		
		int trainingNum = (newTrainingSet.numInstances() / groupInstances);
		int cvNum = CVSet.numInstances() / groupInstances;
		int instances = trainingNum;
		if (trainingNum>cvNum){
			instances=cvNum;
		}
		
		
		for(AbsModeler scheme: series){
			for(int i=1; i<=instances; i++){
				Integer cantInstances = groupInstances * i; 
				//int first = cantInstances+1;
				//if(i == instances)
					//newTrainingSet =  trainingSet;
				//else
				
				/*Si se quiere hacer CVvalidation tenemos que:
				 * iterar k veces:
				 * 		Mezclar los instances
				 * 		Obtener training y CV
				 * 		scheme.calculatemodel(training.subDatabase(0, cantInstances));
				 * 		training : value+calculate(training.subDatabase(0, cantInstances), scheme);
				 * 		CV : value + calculate(CVSet, scheme);
				 * value/k;
				 * 
				 * 		
				 */
				
				
				AbsDatabase mTDataset =  newTrainingSet.subDatabase(0, cantInstances);
				//AbsDatabase mCVDatabase = CVSet.subDatabase(0, cantInstances);
				
				//Double value= rae.calculate(mTDataset, scheme,5);	//CV con fold=5
				
				scheme.calculateModeler(mTDataset);
				Double value = rae.calculate(mTDataset, scheme);
				
				dataset.addValue(value/5, Config.Graphic.GRAPHIC_LINE_LABEL_TRAINING, cantInstances);
				
				max = Math.max(max,value);
		    	min = Math.min(min, value);
		    	
		    	//value = rae.calculate(mCVDatabase, scheme,5);
		    	value=rae.calculate(CVSet, scheme);
				
				dataset.addValue(value, Config.Graphic.GRAPHIC_LINE_LABEL_CROSSVALIDATION, cantInstances);
				
				max = Math.max(max,value);
		    	min = Math.min(min, value);
			}	
		}
		
		
	}
	
	public AFreeChart graphedLearningCurve(AbsDatabase trainingSet, Vector<AbsModeler> schemes) throws Exception {
		setSeries(schemes);
		configureLearningCurve(trainingSet);
		image = Config.Graphic.GRAPHIC_LINE_BACKGROUND_IMAGE_LC;
		return getChart(series, Config.Graphic.GRAPHIC_LINE_TITLE_CHART_LC,
				Config.Graphic.GRAPHIC_LINE_TITLE_AXISX,
				Config.Graphic.GRAPHIC_LINE_TITLE_AXISY);
	}
	
	public AFreeChart graphedErrorPrediction(AbsDatabase trainingSet, Vector<AbsModeler> schemes, int indexClass) throws Exception{
		setSeries(schemes);
		configureErrorPrediction(trainingSet, indexClass);
		image = Config.Graphic.GRAPHIC_LINE_BACKGROUND_IMAGE_EP;
		return getChart(series, Config.Graphic.GRAPHIC_LINE_TITLE_CHART_EP,
				Config.Graphic.GRAPHIC_LINE_TITLE_AXISX,
				Config.Graphic.GRAPHIC_LINE_TITLE_AXISY);
	}
}
