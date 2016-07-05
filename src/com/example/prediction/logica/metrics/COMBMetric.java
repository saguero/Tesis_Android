package com.example.prediction.logica.metrics;

import com.example.prediction.logica.libraries.AbsLibrary;
import com.example.prediction.logica.metrics.AbsMetricsEvaluation.Info;
import com.example.prediction.logica.metrics.AbsMetricsEvaluation.Representation;
import com.example.prediction.logica.metrics.AbsMetricsEvaluation.Required;
import com.example.prediction.logica.metrics.AbsMetricsEvaluation.Type;

public class COMBMetric extends AbsMetric{
	
	COMBMetric(AbsLibrary lib) {
		super(AbsMetricsEvaluation.COMB,Required.MAX, Representation.NORMALIZED, Type.REGRESSION, Info.ERROR_PREDICTION, lib);	
	}
	
	Double calculate(Object evaluation) throws Exception {
		return  1-Math.abs(library.calculateCC(evaluation)) + library.calculateRRSE(evaluation) 
				+ library.calculateRAE(evaluation);
	}
	
	public String getID(){
		return "COMB";
	}

}
