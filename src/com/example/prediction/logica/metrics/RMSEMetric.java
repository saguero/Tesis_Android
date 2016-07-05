package com.example.prediction.logica.metrics;

import com.example.prediction.logica.libraries.AbsLibrary;
import com.example.prediction.logica.metrics.AbsMetricsEvaluation.Info;
import com.example.prediction.logica.metrics.AbsMetricsEvaluation.Representation;
import com.example.prediction.logica.metrics.AbsMetricsEvaluation.Required;
import com.example.prediction.logica.metrics.AbsMetricsEvaluation.Type;

import weka.classifiers.Evaluation;

public class RMSEMetric extends AbsMetric{

	RMSEMetric(AbsLibrary lib) {
		super(AbsMetricsEvaluation.RMSE,Required.MIN, Representation.SCALE, Type.REGRESSION, Info.ERROR_PREDICTION, lib);	
	}

	Double calculate(Object evaluation) throws Exception {
		return ((Evaluation) evaluation).rootMeanSquaredError();
	}	
	
	public String getID(){
		return "RMSE";
	}
}
