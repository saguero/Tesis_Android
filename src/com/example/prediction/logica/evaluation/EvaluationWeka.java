package com.example.prediction.logica.evaluation;

import java.util.Random;

import com.example.prediction.logica.database.AbsDatabase;
import com.example.prediction.logica.models.AbsModeler;
import com.example.prediction.logica.models.AbsWekaClassifier;

import weka.classifiers.Evaluation;
import weka.core.Instances;

public class EvaluationWeka extends AbsEvaluation {

	private Evaluation test;
	
	public EvaluationWeka(){
	}

	@Override
	public void testTrainingSet(AbsDatabase dataset, AbsModeler scheme) throws Exception {
		// TODO Auto-generated method stub 
		test.evaluateModel(((AbsWekaClassifier) scheme).getClassifier(), (Instances)dataset.getDatabaseImplementation());
	}

	@Override
	public void testCV(AbsDatabase dataset, AbsModeler scheme) throws Exception {
		// TODO Auto-generated method stub
		test.crossValidateModel(((AbsWekaClassifier) scheme).getClassifier(), (Instances)dataset.getDatabaseImplementation(),10, new Random(1));		
	}

	@Override
	public double[] distributionForInstance(AbsDatabase trainingSet, AbsModeler scheme, int classIndex) throws Exception {
		// TODO Auto-generated method stub
		return (((AbsWekaClassifier) scheme).getClassifier()).distributionForInstance(((Instances)trainingSet.getDatabaseImplementation()).instance(classIndex));
	}
	
	@Override
	public Double calculateER(AbsDatabase trainingSet, AbsModeler scheme){
		return test.errorRate();
	}
	
	@Override
	public Double calculateMAE(AbsDatabase trainingSet, AbsModeler scheme) throws Exception {
		// TODO Auto-generated method stub
		return test.meanAbsoluteError();
		 
	}

	@Override
	public Double calculateRMSE(AbsDatabase trainingSet, AbsModeler scheme) throws Exception {
		// TODO Auto-generated method stub
		return test.rootMeanSquaredError();
	}

	@Override
	public Double calculateRAE(AbsDatabase trainingSet, AbsModeler scheme) throws Exception {
		// TODO Auto-generated method stub
		return test.relativeAbsoluteError();
	}

	@Override
	public Double calculateRRSE(AbsDatabase trainingSet, AbsModeler scheme) throws Exception {
		// TODO Auto-generated method stub
		return test.rootRelativeSquaredError();
	}

	@Override
	public Double calculateCC(AbsDatabase trainingSet, AbsModeler scheme) throws Exception {
		// TODO Auto-generated method stub
		return  test.correlationCoefficient();
	}

	@Override
	public Double calculateACC(AbsDatabase trainingSet, AbsModeler scheme) throws Exception {
		// TODO Auto-generated method stub
		return test.precision(scheme.getIndexAttribute());
	}

	@Override
	public Double calculateKAP(AbsDatabase trainingSet, AbsModeler scheme) throws Exception {
		// TODO Auto-generated method stub
		return test.kappa();
	}

	@Override
	public Double calculateROC(AbsDatabase trainingSet, AbsModeler scheme) throws Exception {
		// TODO Auto-generated method stub
		return test.areaUnderROC(scheme.getIndexAttribute());
	}

	@Override
	public Double calculateRECALL(AbsDatabase trainingSet, AbsModeler scheme) throws Exception {
		// TODO Auto-generated method stub
		return test.recall(scheme.getIndexAttribute());
	}

	@Override
	public void configEvaluation(AbsDatabase trainingSet, AbsModeler scheme) {
		// TODO Auto-generated method stub
		try {
			test = new Evaluation((Instances) trainingSet.getDatabaseImplementation());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
