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
import org.afree.graphics.PaintType;
import org.afree.graphics.SolidColor;
import org.afree.ui.RectangleInsets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;

public class BarGraphics extends AbsGraphics {
	private Vector<Integer> bestResults = new Vector<Integer>();
	private Metric[] categorys;
	Context context;
	
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
	
	public BarGraphics() {
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
				bestResults.add(values.indexOf(auxValues.elementAt(auxValues.size()-2)));
			}
			else {
				bestResults.add(values.indexOf(auxValues.elementAt(0)));
				bestResults.add(values.indexOf(auxValues.elementAt(1)));
			}
			auxValues.removeAllElements();
			values.removeAllElements();
		}
		max += max/10;				
		min = 0;
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
		AbsClassifier[] axisY = (AbsClassifier[]) series;										
		  double value=0;
		  for(int y=0; y< axisY.length; y++){	
			  Object e = evaluator.evaluateUseTrainingSet(trainingSet, axisY[y]);
			  for(int x=0; x< categorys.length; x++){
				  value = categorys[x].calculateNormalized(e);								
				  dataset.addValue(value, axisY[y].getName(), categorys[x].getID()); 
			  }
		  }
	}
	
	protected void customizeChart(AFreeChart chart) throws Exception{
		height = Config.Graphic.GRAPHIC_BAR_HEIGHT;
		width = Config.Graphic.GRAPHIC_BAR_WIDTH;
		settings();
		
		String workingDir = Config.DIR_RESOURCES;
		
		// chart.setBackgroundImageAlpha(0.1F);
		chart.setBackgroundPaintType(new SolidColor(Color.WHITE));
		 
	    Bitmap img = BitmapFactory.decodeFile(workingDir + Config.Graphic.GRAPHIC_BAR_BACKGROUND_IMAGE);
	    CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundImage(new BitmapDrawable(context.getResources(),img));			
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
	    
	    plot.setRenderer(renderer);
	    //renderer.setItemLabelsVisible(true);
	    
	    for(int i=0;i<dataset.getRowCount();i++)
	    	renderer.setSeriesItemLabelsVisible(i, true);
	    
	    renderer.setBaseItemLabelsVisible(true);
	 //   renderer.setBaseItemLabelFont(new Font("SansSerif", Font.ITALIC, 15));
	  //  renderer.setItemLabelFont(new Font("SansSerif", Font.ITALIC, 15));
	
	    //SET RANGE AXIS
	    ValueAxis yAxis = plot.getRangeAxis();
	    yAxis.setRange(min, max);  	    
	}

	public AFreeChart graphed(AbsDataset trainingSet, AbsEvaluation evaluator, AbsMetricsEvaluation metricsEvaluation, Info info, Vector<Representation> rep) throws Exception{
	
		Vector<Metric> metrics = new Vector<Metric>(); 
		Vector<Metric> aux = new Vector<Metric>(); 
		for(Representation r: rep){	
			Vector<Metric> toAdd = metricsEvaluation.association(r);
			for(Metric m: toAdd)
				if(m.canBeNormalized())
					metrics.add(m);
		}
		
		for(Metric m:metrics){
			if( m.getInfo().equals(info) ) 
				aux.add(m);
		}
		metrics.removeAllElements(); 
		int index=0;
		categorys = new Metric[aux.size()];
		for(Metric m:aux){	
			categorys[index]=m;
			index++;
		}
		configureDataset(trainingSet, evaluator);
		return getChart(series, Config.Graphic.GRAPHIC_BAR_TITLE_CHART,
				Config.Graphic.GRAPHIC_BAR_TITLE_AXISX,  Config.Graphic.GRAPHIC_BAR_TITLE_AXISY);	
	}
	
	
	
}
