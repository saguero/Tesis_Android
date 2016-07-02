package com.example.prediction.logica.metrics;

import com.example.prediction.logica.metrics.MetricsCollection.Info;
import com.example.prediction.logica.metrics.MetricsCollection.Representation;
import com.example.prediction.logica.metrics.MetricsCollection.Required;
import com.example.prediction.logica.metrics.MetricsCollection.Type;

import weka.classifiers.Evaluation;

public class MAEMetric extends AbsMetric {

		MAEMetric() {
			super(MetricsCollection.MAE,Required.MIN, Representation.SCALE, Type.REGRESSION, Info.ERROR_PREDICTION);	
		}
		
		Double calculate(Object evaluation) throws Exception {
			return ((Evaluation) evaluation).meanAbsoluteError();
		}
		
		public String getID(){
			return "MAE";
		}

}
