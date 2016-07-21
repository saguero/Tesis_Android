package com.example.prediction.logica.metrics.evaluation_metric;

import com.example.prediction.logica.evaluation.AbsEvaluation;
import com.example.prediction.logica.evaluation.EvaluationWeka;
import com.example.prediction.logica.metrics.AbsEvaluationMetric;
import com.example.prediction.logica.metrics.collection.AbsMetricsCollection;
import com.example.prediction.logica.metrics.collection.AbsMetricsCollection.Info;
import com.example.prediction.logica.metrics.collection.AbsMetricsCollection.Representation;
import com.example.prediction.logica.metrics.collection.AbsMetricsCollection.Required;
import com.example.prediction.logica.metrics.collection.AbsMetricsCollection.Type;

public class ERMetric extends AbsEvaluationMetric {

	public ERMetric() {
		super(AbsMetricsCollection.ER, Required.MIN, Representation.PERCENTUAL, Type.REGRESSION, Info.ERROR_PREDICTION, "Error rate");
		trainingEvaluation = new EvaluationWeka();
		CVEvaluation = new EvaluationWeka();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Double calculate(AbsEvaluation evaluation) throws Exception {
		// TODO Auto-generated method stub
		return evaluation.calculateER(database, modeler);
	}

}
