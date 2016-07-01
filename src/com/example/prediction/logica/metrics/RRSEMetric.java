package com.example.prediction.logica.metrics;

import com.example.prediction.logica.metrics.MetricsCollection.Info;
import com.example.prediction.logica.metrics.MetricsCollection.Representation;
import com.example.prediction.logica.metrics.MetricsCollection.Required;
import com.example.prediction.logica.metrics.MetricsCollection.Type;

public class RRSEMetric extends AbsMetric{

		RRSEMetric() {
			super(MetricsCollection.RRSE,Required.MIN, Representation.PERCENTUAL, Type.REGRESSION, Info.ERROR_PREDICTION);	
		}
		
		Double calculate(Object evaluation) throws Exception {
			return calculateRRSE(evaluation);
		}
		
		public String getID(){
			return "RRSE";
		}

}
