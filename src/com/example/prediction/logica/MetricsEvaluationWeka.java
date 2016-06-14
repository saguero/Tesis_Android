package com.example.prediction.logica;

import weka.classifiers.Evaluation;

public class MetricsEvaluationWeka extends AbsMetricsEvaluation {
	
	
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

	@Override
	public Double calculateMAE(Object evaluation) throws Exception {
		// TODO Auto-generated method stub
		return ((Evaluation) evaluation).meanAbsoluteError();
		 
	}

	@Override
	public Double calculateRMSE(Object evaluation) throws Exception {
		// TODO Auto-generated method stub
		return ((Evaluation) evaluation).rootMeanSquaredError();
	}

	@Override
	public Double calculateRAE(Object evaluation) throws Exception {
		// TODO Auto-generated method stub
		return ((Evaluation) evaluation).relativeAbsoluteError();
	}

	@Override
	public Double calculateRRSE(Object evaluation) throws Exception {
		// TODO Auto-generated method stub
		return ((Evaluation) evaluation).rootRelativeSquaredError();
	}

	@Override
	public Double calculateCC(Object evaluation) throws Exception {
		// TODO Auto-generated method stub
		return  ((Evaluation) evaluation).correlationCoefficient();
	}

	@Override
	public Double calculateACC(Object evaluation) throws Exception {
		// TODO Auto-generated method stub
		return ((Evaluation) evaluation).precision(trainingSet.getClassIndex());
	}

	@Override
	public Double calculateKAP(Object evaluation) throws Exception {
		// TODO Auto-generated method stub
		return ((Evaluation) evaluation).kappa();
	}

	@Override
	public Double calculateROC(Object evaluation) throws Exception {
		// TODO Auto-generated method stub
		return ((Evaluation) evaluation).areaUnderROC(trainingSet.getClassIndex());
	}

	@Override
	public Double calculateRECALL(Object evaluation) throws Exception {
		// TODO Auto-generated method stub
		return ((Evaluation) evaluation).recall(trainingSet.getClassIndex());
	}

}
