package com.example.prediction.logica.metrics.evaluation_metric;

import com.example.prediction.logica.evaluation.AbsEvaluation;
import com.example.prediction.logica.metrics.abstracts.Info;
import com.example.prediction.logica.metrics.abstracts.Representation;
import com.example.prediction.logica.metrics.abstracts.Required;
import com.example.prediction.logica.metrics.abstracts.Type;
import com.example.prediction.logica.metrics.collection.MetricsCollection;

public class WekaRMSEMetric extends AbsWekaEvaluationMetric {

	public WekaRMSEMetric() {
		super(MetricsCollection.RMSE,Required.MIN, Representation.SCALE, Type.REGRESSION, Info.ERROR_PREDICTION, "Root mean square error");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Double calculate(AbsEvaluation evaluation) throws Exception {
		// TODO Auto-generated method stub
		return evaluation.calculateRMSE(database, modeler);
	}

}
