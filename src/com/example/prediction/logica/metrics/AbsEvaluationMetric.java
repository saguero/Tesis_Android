package com.example.prediction.logica.metrics;

import com.example.prediction.logica.Config;
import com.example.prediction.logica.database.AbsDatabase;
import com.example.prediction.logica.evaluation.AbsEvaluation;
import com.example.prediction.logica.metrics.collection.AbsMetricsCollection.Info;
import com.example.prediction.logica.metrics.collection.AbsMetricsCollection.Representation;
import com.example.prediction.logica.metrics.collection.AbsMetricsCollection.Required;
import com.example.prediction.logica.metrics.collection.AbsMetricsCollection.Type;
import com.example.prediction.logica.models.AbsModeler;

public abstract class AbsEvaluationMetric extends AbsMetric{
	
	protected AbsEvaluation trainingEvaluation;
	protected AbsEvaluation CVEvaluation;

	protected AbsEvaluationMetric(int ID, Required req, Representation rep, Type t, Info i, String name) {
		super(ID, req, rep, t, i, name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Double calculate(int mode) throws Exception {
		// TODO Auto-generated method stub
		if (mode == Config.TrainingMode.TRAINING_MODE){
			return calculate(trainingEvaluation);
		}
		if (mode == Config.TrainingMode.CROSS_VALIDATION_MODE){
			return calculate(CVEvaluation);
		}
		return null;
	}
	
	public void configurateTrainingMode(AbsDatabase database, AbsModeler scheme){
		try {
			trainingEvaluation.evaluateUseTrainingSet(database, scheme);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void configurateCVMode(AbsDatabase database, AbsModeler scheme){
		try {
			CVEvaluation.evaluateUseCrossValidation(database, scheme);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected abstract Double calculate(AbsEvaluation evaluation) throws Exception;

}
