package com.example.prediction.logica.metrics;

import com.example.prediction.logica.metrics.MetricsCollection.Info;
import com.example.prediction.logica.metrics.MetricsCollection.Representation;
import com.example.prediction.logica.metrics.MetricsCollection.Required;
import com.example.prediction.logica.metrics.MetricsCollection.Type;

public class COMBMetric extends AbsMetric {
		
		COMBMetric() {
			super(MetricsCollection.COMB,Required.MAX, Representation.NORMALIZED, Type.REGRESSION, Info.ERROR_PREDICTION);	
		}
		
		Double calculate(Object evaluation) throws Exception {
			RAEMetric rae = new RAEMetric();
			RRSEMetric rrse = new RRSEMetric();
			CCMetric cc=new CCMetric();
			return  1-Math.abs(cc.calculateNormalized(evaluation)) + rrse.calculateNormalized(evaluation) 
					+ rae.calculateNormalized(evaluation);
		}
		
		public String getID(){
			return "COMB";
		}

}
