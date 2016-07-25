package com.example.prediction.logica.metrics.evaluation_metric;

import com.example.prediction.logica.evaluation.EvaluationWeka;
import com.example.prediction.logica.metrics.abstracts.AbsEvaluationMetric;
import com.example.prediction.logica.metrics.abstracts.Info;
import com.example.prediction.logica.metrics.abstracts.Representation;
import com.example.prediction.logica.metrics.abstracts.Required;
import com.example.prediction.logica.metrics.abstracts.Type;

public abstract class AbsWekaEvaluationMetric extends AbsEvaluationMetric {
	
	protected AbsWekaEvaluationMetric(int ID, Required req, Representation rep, Type t, Info i, String name) {
		super(ID, req, rep, t, i, name);
		trainingEvaluation = new EvaluationWeka();
		CVEvaluation = new EvaluationWeka();
		// TODO Auto-generated constructor stub
	}

}
