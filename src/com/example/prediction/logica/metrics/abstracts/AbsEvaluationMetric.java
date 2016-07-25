package com.example.prediction.logica.metrics.abstracts;

import com.example.prediction.logica.Config;
import com.example.prediction.logica.database.AbsDatabase;
import com.example.prediction.logica.evaluation.AbsEvaluation;
import com.example.prediction.logica.metrics.abstracts.Type;
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
	
	@Override
	protected void configurateTraining(AbsDatabase database, AbsModeler scheme){
		try {
			trainingEvaluation.evaluateUseTrainingSet(database, scheme);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	protected void configurateCV(AbsDatabase database, AbsModeler scheme, int folds){
		try {
			CVEvaluation.evaluateUseCrossValidation(database, scheme, folds);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected abstract Double calculate(AbsEvaluation evaluation) throws Exception;

}
