package com.example.prediction.logica.models;

import com.example.prediction.logica.optimization.WekaClassifierOptimizer;
import com.example.prediction.logica.parameters.WekaSimpleParameter;

import weka.classifiers.functions.LinearRegression;

public class LinearRegClassifier extends AbsWekaClassifier {
	/*
	 * Generates a linear regression function predictor. Predicted attribute: A
	 * coefficients[att0, att1,...,N] A = coefficients[att0] +
	 * coefficients[att1] + ... for all i: coefficients[atti] != 0
	 */

	private static final double DEFAULT_RIDGE = 1;
	private static final double MIN_VALUE=0;
	private static final double MAX_VALUE=5;
	
	/**/
	public LinearRegClassifier(int index) {
		super(new LinearRegression(), new WekaClassifierOptimizer(MIN_VALUE, MAX_VALUE), index);
		addParameter(new WekaSimpleParameter('R', DEFAULT_RIDGE, "R"));	/*No tiene limites - Double*/
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "LinearRegression";
	}
}
