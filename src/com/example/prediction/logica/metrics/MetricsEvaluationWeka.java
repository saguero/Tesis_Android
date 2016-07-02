package com.example.prediction.logica.metrics;

import com.example.prediction.logica.database.AbsDataset;
import com.example.prediction.logica.evaluation.AbsEvaluation;

public class MetricsEvaluationWeka extends MetricsCollection {
	
	public MetricsEvaluationWeka(AbsDataset dataset, AbsEvaluation eval) {
		// TODO Auto-generated constructor stub
		super(dataset, eval);
		acceptMetric(CC);
		acceptMetric(COMB);
		acceptMetric(MAE);
		acceptMetric(RAE);
		acceptMetric(RMSE);
		acceptMetric(RRSE);
	}

}
