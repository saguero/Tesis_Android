package com.example.prediction.logica.metrics;

import com.example.prediction.logica.metrics.MetricsCollection.Info;
import com.example.prediction.logica.metrics.MetricsCollection.Representation;
import com.example.prediction.logica.metrics.MetricsCollection.Required;
import com.example.prediction.logica.metrics.MetricsCollection.Type;

import weka.classifiers.Evaluation;

public class RRSEMetric extends AbsMetric{

		RRSEMetric() {
			super(MetricsCollection.RRSE,Required.MIN, Representation.PERCENTUAL, Type.REGRESSION, Info.ERROR_PREDICTION);	
		}
		
		Double calculate(Object evaluation) throws Exception {
			return ((Evaluation) evaluation).rootRelativeSquaredError();
		}
		
		public String getID(){
			return "RRSE";
		}

}
