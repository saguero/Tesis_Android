package com.example.prediction.logica;

import java.util.Vector;


public abstract class AbsEvaluation {
	
	public AbsEvaluation newInstance(){
		return this;
	}
	
	private Object configEvaluation(AbsDataset trainingSet, AbsClassifier scheme) throws Exception{
		Object classifier = scheme.getScheme();
		Object dataset = trainingSet.getTrainingSet();
		buildClassifier(dataset, classifier);
		return classifier;
	}
	
	public Object evaluateUseTrainingSet(AbsDataset trainingSet, AbsClassifier scheme) throws Exception{
		Object classifier = configEvaluation(trainingSet, scheme);
		return testTrainingSet(trainingSet, classifier);
	}
	
	public Object evaluateUseCrossValidation(AbsDataset trainingSet, AbsClassifier scheme) throws Exception{
		Object classifier = configEvaluation(trainingSet, scheme);
		return testCV(trainingSet, classifier);
	}
	
	public Vector<Double> getPredictedValues(AbsDataset trainingSet, AbsClassifier scheme) throws Exception{
		configEvaluation(trainingSet, scheme);
		
		Object t = trainingSet.getTrainingSet();
		Object s = scheme.getScheme();
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
