package com.example.prediction.logica.metrics;

import com.example.prediction.logica.database.AbsDatabase;
import com.example.prediction.logica.evaluation.AbsEvaluation;
import com.example.prediction.logica.libraries.AbsLibrary;

public class WekaMetricEvaluation extends AbsMetricsEvaluation {
	
	public WekaMetricEvaluation(AbsDatabase dataset, AbsEvaluation eval, AbsLibrary lib) {
		// TODO Auto-generated constructor stub
		super(dataset, eval, lib);
		acceptMetric(CC);
		acceptMetric(COMB);
		acceptMetric(MAE);
		acceptMetric(RAE);
		acceptMetric(RMSE);
		acceptMetric(RRSE);
	}

}
