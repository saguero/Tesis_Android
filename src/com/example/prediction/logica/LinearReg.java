package com.example.prediction.logica;

import weka.classifiers.Classifier;
import weka.classifiers.functions.LinearRegression;
import weka.core.Instances;

public class LinearReg extends AbsClassifier{
	/*
	 * Generates a linear regression function predictor.
	 * Predicted attribute: A
	 * coefficients[att0, att1,...,N] 
	 * A = coefficients[att0] + coefficients[att1] + ... for all i: coefficients[atti] != 0
	 */

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

}
