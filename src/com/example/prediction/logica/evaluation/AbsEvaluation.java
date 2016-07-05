package com.example.prediction.logica.evaluation;

import java.util.Vector;

import com.example.prediction.logica.database.AbsDatabase;
import com.example.prediction.logica.models.AbsClassifier;
import com.example.prediction.logica.models.AbsWekaClassifier;


public abstract class AbsEvaluation {
	
	public AbsEvaluation newInstance(){
		return this;
	}
	
	private Object configEvaluation(AbsDatabase trainingSet, AbsClassifier scheme) throws Exception{
		Object classifier = ((AbsWekaClassifier)scheme).getClassifier();
		Object dataset = trainingSet.getTrainingSet();
		buildClassifier(dataset, classifier);
		return classifier;
	}
	
	public Object evaluateUseTrainingSet(AbsDatabase trainingSet, AbsClassifier scheme) throws Exception{
		Object classifier = configEvaluation(trainingSet, scheme);
		return testTrainingSet(trainingSet.getTrainingSet(), classifier);
	}
	
	public Object evaluateUseCrossValidation(AbsDatabase trainingSet, AbsClassifier scheme) throws Exception{
		Object classifier = configEvaluation(trainingSet, scheme);
		return testCV(trainingSet.getTrainingSet(), classifier);
	}
	
	public Vector<Double> getPredictedValues(AbsDatabase trainingSet, AbsClassifier scheme) throws Exception{
		configEvaluation(trainingSet, scheme);
		
		Object t = trainingSet.getTrainingSet();
		Object s = ((AbsWekaClassifier)scheme).getClassifier();
		int classIndex = trainingSet.getClassIndex();
		
		Vector<Double> predicted = new Vector<Double>();
		for(int instance=0;instance<trainingSet.numInstances();instance++){
			
			double[] prediction=distributionForInstance(t, s, classIndex);
	        for(int pred=0; pred<prediction.length; pred++)
	        {
	        	double valuePred = prediction[pred];
	        	predicted.add(valuePred);
	        }	
		}
		return predicted;
	}
	
	
	public abstract void buildClassifier(Object trainingSet, Object scheme) throws Exception;
	public abstract Object testTrainingSet(Object trainingSet, Object scheme) throws Exception;
	public abstract Object testCV(Object trainingSet, Object scheme) throws Exception;
	public abstract Double getErrorEvaluation(Object evaluation);
	public abstract double[] distributionForInstance(Object trainingSet, Object scheme, int classIndex) throws Exception;
	
	//OTROS METODOS PARA TESTEAR... 
}
