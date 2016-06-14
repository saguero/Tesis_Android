package com.example.prediction.logica;

import weka.classifiers.Classifier;
import weka.classifiers.functions.SimpleLinearRegression;
import weka.core.Instances;

public class SimpleLinearReg extends AbsClassifier {
	/*
	 * Learns a simple linear regression model. Picks the attribute that results in the lowest squared error. 
	 * Can only deal with numeric attributes.
	 * 
	 * Predicted attribute: A
	 * Function on B (attributeIndex): A = Slope * B + Intercept 	A y B high correlation 
	 */

	public SimpleLinearReg(){
		super();
		scheme=new SimpleLinearRegression();
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "SimpleLinearRegression";
	}

	@Override
	public Classifier[] bestClassifiers(Instances dataset) throws Exception {
		// TODO Auto-generated method stub
		Classifier[] c= {new SimpleLinearRegression()};
		return c ;
	}
	
	@Override
	public String globalInfo() {
		// TODO Auto-generated method stub
		return null;
	}

}
