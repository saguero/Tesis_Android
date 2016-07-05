package com.example.prediction.logica.metrics;

import com.example.prediction.logica.libraries.AbsLibrary;
import com.example.prediction.logica.metrics.AbsMetricsEvaluation.Info;
import com.example.prediction.logica.metrics.AbsMetricsEvaluation.Representation;
import com.example.prediction.logica.metrics.AbsMetricsEvaluation.Required;
import com.example.prediction.logica.metrics.AbsMetricsEvaluation.Type;

public class CCMetric extends AbsMetric{
	
	CCMetric(AbsLibrary lib) {
		super(AbsMetricsEvaluation.CC,Required.MAX, Representation.NORMALIZED, Type.REGRESSION, Info.RELATION_DATA, lib);	
	}

	Double calculate(Object evaluation) throws Exception {
		return  library.calculateCC(evaluation);
	}
	
	public String getID(){
		return "CC";
	}

}
