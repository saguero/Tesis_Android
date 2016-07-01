package com.example.prediction.logica.models;

import com.example.prediction.logica.optimization.WekaClassifierOptimizer;
import com.example.prediction.logica.parameters.WekaSimpleParameter;

import weka.classifiers.functions.SGD;

public class SGDClassifier extends AbsWekaClassifier {

	private static final double MIN_VALUE = 0;
	private static final double MAX_VALUE = 1;
	private static final double DEFAULT_LEARNING = 0.01;
	private static final double DEFAULT_LAMBDA = 0.5;

	public SGDClassifier(int index) {
		super(new SGD(), new WekaClassifierOptimizer(MIN_VALUE, MAX_VALUE), index);
		SGD sgd=new SGD();
		String[] loss=new String[2];
		loss[0]="-F";
		loss[1]="2";
		try {
			sgd.setOptions(loss);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		classifier=sgd;
		addParameter(new WekaSimpleParameter('L', DEFAULT_LEARNING, "L"));
		addParameter(new WekaSimpleParameter('R', DEFAULT_LAMBDA, "R"));
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Stocastic Descendent Gradient";
	}

}
