package com.example.prediction.logica.metrics.evaluation_metric;

import com.example.prediction.logica.evaluation.AbsEvaluation;
import com.example.prediction.logica.metrics.abstracts.Info;
import com.example.prediction.logica.metrics.abstracts.Representation;
import com.example.prediction.logica.metrics.abstracts.Required;
import com.example.prediction.logica.metrics.abstracts.Type;
import com.example.prediction.logica.metrics.collection.MetricsCollection;

public class WekaROCMetric extends AbsWekaEvaluationMetric {

	public WekaROCMetric() {
		super(MetricsCollection.ROC,Required.MAX, Representation.NORMALIZED, Type.CLASSIFICATION, Info.ERROR_PREDICTION, "Area under ROC curves");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Double calculate(AbsEvaluation evaluation) throws Exception {
		// TODO Auto-generated method stub
		return evaluation.calculateROC(database, modeler);
	}

}