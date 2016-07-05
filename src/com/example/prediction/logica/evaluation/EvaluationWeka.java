package com.example.prediction.logica.evaluation;

import java.util.Random;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;

public class EvaluationWeka extends AbsEvaluation {

	@Override
	public void buildClassifier(Object trainingSet, Object scheme) throws Exception {
		// TODO Auto-generated method stub 
		((Classifier) scheme).buildClassifier( (Instances) trainingSet);
	}

	@Override
	public Object testTrainingSet(Object dataset, Object scheme) throws Exception {
		// TODO Auto-generated method stub 
		Evaluation test = new Evaluation((Instances) dataset);
		test.evaluateModel((Classifier) scheme, (Instances) dataset);
		return test;
	}

	@Override
	public Object testCV(Object dataset, Object scheme) throws Exception {
		// TODO Auto-generated method stub
		Evaluation test = new Evaluation((Instances) dataset);
		test.crossValidateModel((Classifier) scheme, (Instances) dataset,10, new Random(1));		
		return test;
	}

	@Override
	public Double getErrorEvaluation(Object evaluation){
		return ((Evaluation) evaluation).errorRate();
	}

	@Override
	public double[] distributionForInstance(Object trainingSet, Object scheme, int classIndex) throws Exception {
		// TODO Auto-generated method stub
		return ((Classifier)scheme).distributionForInstance(((Instances) trainingSet).instance(classIndex));
	}
}
