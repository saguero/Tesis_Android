package com.example.prediction.logica;

import weka.classifiers.Classifier;
import weka.classifiers.functions.LinearRegression;
import weka.core.Instances;

public class LinearReg extends AbsClassifier{
	

	public LinearReg(){
		super();
		scheme=new LinearRegression(); 
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "LinearRegression";
	}

	@Override
	public Classifier[] bestClassifiers(Instances dataset) throws Exception {
		return null;
	}

	
	@Override
	public String globalInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean handles(AbsDataset dataset) {
		// TODO Auto-generated method stub
		return scheme.getCapabilities().test((Instances) dataset.getTrainingSet());		
	}


	

}
