package com.example.prediction.logica.metrics;

import com.example.prediction.logica.metrics.MetricsCollection.Info;
import com.example.prediction.logica.metrics.MetricsCollection.Representation;
import com.example.prediction.logica.metrics.MetricsCollection.Required;
import com.example.prediction.logica.metrics.MetricsCollection.Type;

public class RECALLMetric extends AbsMetric{

		RECALLMetric() {
			super(MetricsCollection.RECALL,Required.MAX, Representation.NORMALIZED, Type.CLASSIFICATION, Info.ERROR_PREDICTION); //NO ESTOY SEGURA
		}
		
		Double calculate(Object evaluation) throws Exception {
			return calculateRECALL(evaluation);								
		}
		
		public String getID(){
			return "RECALL";
		}
}