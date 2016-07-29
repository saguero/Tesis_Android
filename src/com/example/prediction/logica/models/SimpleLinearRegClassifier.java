package com.example.prediction.logica.models;

import com.example.prediction.logica.optimization.WekaClassifierOptimizer;

import weka.classifiers.functions.SimpleLinearRegression;

public class SimpleLinearRegClassifier extends AbsWekaClassifier {
	/*
	 * Learns a simple linear regression model. Picks the attribute that results in the lowest squared error. 
	 * Can only deal with numeric attributes.
	 * 
	 * Predicted attribute: A
	 * Function on B (attributeIndex): A = Slope * B + Intercept 	A y B high correlation 
	 */
	/**/
	
	private static final double MIN_VALUE=0;
	private static final double MAX_VALUE=5;
	
	public SimpleLinearRegClassifier(int index){
		super(new SimpleLinearRegression(), new WekaClassifierOptimizer(MIN_VALUE,MAX_VALUE), index);
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "SimpleLinearRegression";
	}

}
