package com.example.prediction.logica.metrics;

import com.example.prediction.logica.libraries.AbsLibrary;
import com.example.prediction.logica.metrics.AbsMetricsEvaluation.Info;
import com.example.prediction.logica.metrics.AbsMetricsEvaluation.Representation;
import com.example.prediction.logica.metrics.AbsMetricsEvaluation.Required;
import com.example.prediction.logica.metrics.AbsMetricsEvaluation.Type;

public class RECALLMetric extends AbsMetric{
	
	RECALLMetric(AbsLibrary lib) {
		super(AbsMetricsEvaluation.RECALL,Required.MAX, Representation.NORMALIZED, Type.CLASSIFICATION, Info.ERROR_PREDICTION, lib); //NO ESTOY SEGURA
	}
	
	Double calculate(Object evaluation) throws Exception {
		return library.calculateRECALL(evaluation);						
	}
	
	public String getID(){
		return "RECALL";
	}

}
