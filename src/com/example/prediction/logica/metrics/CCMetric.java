package com.example.prediction.logica.metrics;

import com.example.prediction.logica.metrics.MetricsCollection.Info;
import com.example.prediction.logica.metrics.MetricsCollection.Representation;
import com.example.prediction.logica.metrics.MetricsCollection.Required;
import com.example.prediction.logica.metrics.MetricsCollection.Type;

public class CCMetric extends AbsMetric{

		CCMetric() {
			super(MetricsCollection.CC,Required.MAX, Representation.NORMALIZED, Type.REGRESSION, Info.RELATION_DATA);	
		}

		Double calculate(Object evaluation) throws Exception {
			return calculateCC(evaluation);
		}
		
		public String getID(){
			return "CC";
		}
	
}
