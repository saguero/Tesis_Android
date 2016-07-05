package com.example.prediction.logica.metrics;

import com.example.prediction.logica.libraries.AbsLibrary;
import com.example.prediction.logica.metrics.AbsMetricsEvaluation.Info;
import com.example.prediction.logica.metrics.AbsMetricsEvaluation.Representation;
import com.example.prediction.logica.metrics.AbsMetricsEvaluation.Required;
import com.example.prediction.logica.metrics.AbsMetricsEvaluation.Type;

public class ACCMetric extends AbsMetric{
	
	ACCMetric(AbsLibrary lib) {
		super(AbsMetricsEvaluation.ACC,Required.MAX, Representation.NORMALIZED, Type.CLASSIFICATION, Info.ERROR_PREDICTION, lib);	
	}
	
	Double calculate(Object evaluation) throws Exception {
		return library.calculateACC(evaluation);						
	}
	
	public String getID(){
		return "ACC";
	}

}
