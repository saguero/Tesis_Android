package com.example.prediction.logica.metrics;

import com.example.prediction.logica.metrics.MetricsCollection.Info;
import com.example.prediction.logica.metrics.MetricsCollection.Representation;
import com.example.prediction.logica.metrics.MetricsCollection.Required;
import com.example.prediction.logica.metrics.MetricsCollection.Type;

public class RMSEMetric extends AbsMetric {

		RMSEMetric() {
			super(MetricsCollection.RMSE,Required.MIN, Representation.SCALE, Type.REGRESSION, Info.ERROR_PREDICTION);	
		}

		Double calculate(Object evaluation) throws Exception {
			return calculateRMSE(evaluation);
		}	
		
		public String getID(){
			return "RMSE";
		}

}
