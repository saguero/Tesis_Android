package com.example.prediction;


import java.io.File;

import com.example.prediction.logica.AbsDataset;
import com.example.prediction.logica.DatasetWeka;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.widget.ImageView;
//import android.widget.ImageView;
import android.widget.Toast;



public class FilteredSchemesActivity extends Activity {
	Integer[] imageIds = {R.drawable.background_fase2,
							R.drawable.background_fase3,
							R.drawable.grafico};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filteredschemes);
        
        ImageView imageview = (ImageView) findViewById(R.id.imageView_display);
       
        
        
        /*	HARDCODE PARA PROBAR		*/
        Info info = new Info();
        AbsDataset dataset = info.getDatasetSelected();
		dataset.setPredictedAtt(0);
		/*
		AbsEvaluation evaluation = new EvaluationWeka();
		AbsClassifier[] schemes = {new Smoreg(), new LinearReg()};
		AbsMetricsEvaluation metric = new MetricsEvaluationWEKA(dataset,evaluation);
		
		BarGraphics graphic = new BarGraphics(metric);
		graphic.setSeries(schemes);
		Vector<Representation> rep = new Vector<Representation>();
		rep.add(Representation.NORMALIZED);
		rep.add(Representation.PERCENTUAL);
		AFreeChart chart;
		try {
			
		//	chart = graphic.graphed(Info.ERROR_PREDICTION, rep);
		//	ImageView chartview = new ChartView(this, ChartView.BAR_CHART, chart);
		//	imageview = chartview;
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(),"Exception has occurred!",Toast.LENGTH_LONG).show();
		}*/
		
    }

    
}
