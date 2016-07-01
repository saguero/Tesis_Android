package com.example.prediction.logica.metrics;

import com.example.prediction.logica.metrics.AbsMetricsEvaluation.Info;
import com.example.prediction.logica.metrics.AbsMetricsEvaluation.Representation;
import com.example.prediction.logica.metrics.AbsMetricsEvaluation.Required;
import com.example.prediction.logica.metrics.AbsMetricsEvaluation.Type;

public class RAEMetric extends AbsMetric{

		RAEMetric() {
			super(AbsMetricsEvaluation.RAE,Required.MIN, Representation.PERCENTUAL, Type.REGRESSION, Info.ERROR_PREDICTION);
		}
		
		Double calculate(Object evaluation) throws Exception {
			return calculateRAE(evaluation);
		}
		
		public String getID(){
			return "RAE";
		}
		
	}

}
