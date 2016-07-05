package com.example.prediction.logica.metrics;

import com.example.prediction.logica.libraries.AbsLibrary;
import com.example.prediction.logica.metrics.AbsMetricsEvaluation.Info;
import com.example.prediction.logica.metrics.AbsMetricsEvaluation.Representation;
import com.example.prediction.logica.metrics.AbsMetricsEvaluation.Required;
import com.example.prediction.logica.metrics.AbsMetricsEvaluation.Type;

public class MAEMetric extends AbsMetric{
	
	MAEMetric(AbsLibrary lib) {
		super(AbsMetricsEvaluation.MAE,Required.MIN, Representation.SCALE, Type.REGRESSION, Info.ERROR_PREDICTION, lib);	
	}
	
	Double calculate(Object evaluation) throws Exception {
		return library.calculateMAE(evaluation);
	}
	
	public String getID(){
		return "MAE";
	}

}
