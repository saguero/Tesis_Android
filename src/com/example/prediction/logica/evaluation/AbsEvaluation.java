package com.example.prediction.logica.evaluation;

import java.util.Vector;

import com.example.prediction.logica.database.AbsDatabase;
import com.example.prediction.logica.models.AbsModeler;


public abstract class AbsEvaluation {
	
	public AbsEvaluation newInstance(){
		return this;
	}
	
	
	public void evaluateUseTrainingSet(AbsDatabase trainingSet, AbsModeler scheme) throws Exception{
		configEvaluation(trainingSet, scheme);
		testTrainingSet(trainingSet, scheme);
	}
	
	public void evaluateUseCrossValidation(AbsDatabase trainingSet, AbsModeler scheme) throws Exception{
		configEvaluation(trainingSet, scheme);
		testCV(trainingSet, scheme);
	}
	
	public Vector<Double> getPredictedValues(AbsDatabase trainingSet, AbsModeler scheme) throws Exception{
		configEvaluation(trainingSet, scheme);
		
		Vector<Double> predicted = new Vector<Double>();
		scheme.calculateModeler(trainingSet);
		for(int instance=0;instance<trainingSet.numInstances();instance++){
			double[] prediction=distributionForInstance(trainingSet, scheme, instance);
	        for(int pred=0; pred<prediction.length; pred++)
	        {
	        	double valuePred = prediction[pred];
	        	predicted.add(valuePred);
	        }	
		}
		return predicted;
	}
	
	
	//public abstract void buildClassifier(AbsDatabase trainingSet, AbsModeler scheme) throws Exception;
	public abstract void configEvaluation(AbsDatabase trainingSet, AbsModeler scheme);
	public abstract void testTrainingSet(AbsDatabase trainingSet, AbsModeler scheme) throws Exception;
	public abstract void testCV(AbsDatabase trainingSet, AbsModeler scheme) throws Exception;
	public abstract double[] distributionForInstance(AbsDatabase trainingSet, AbsModeler scheme, int classIndex) throws Exception;
	
	
	public abstract Double calculateCC(AbsDatabase trainingSet, AbsModeler scheme) throws Exception;
	public abstract Double calculateRMSE(AbsDatabase trainingSet, AbsModeler scheme) throws Exception;
	public abstract Double calculateMAE(AbsDatabase trainingSet, AbsModeler scheme) throws Exception;
	public abstract Double calculateRAE(AbsDatabase trainingSet, AbsModeler scheme) throws Exception;
	public abstract Double calculateRRSE(AbsDatabase trainingSet, AbsModeler scheme) throws Exception;
	public abstract Double calculateACC(AbsDatabase trainingSet, AbsModeler scheme) throws Exception;
	public abstract Double calculateKAP(AbsDatabase trainingSet, AbsModeler scheme) throws Exception;
	public abstract Double calculateROC(AbsDatabase trainingSet, AbsModeler scheme) throws Exception;
	public abstract Double calculateRECALL(AbsDatabase trainingSet, AbsModeler scheme) throws Exception;
	public abstract Double calculateER(AbsDatabase trainingSet, AbsModeler scheme);
	
	//OTROS METODOS PARA TESTEAR... 
}
