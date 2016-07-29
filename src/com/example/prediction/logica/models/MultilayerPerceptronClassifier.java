package com.example.prediction.logica.models;

import com.example.prediction.logica.optimization.WekaClassifierOptimizer;
import com.example.prediction.logica.parameters.WekaSimpleParameter;

import weka.classifiers.Classifier;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instances;

@SuppressWarnings("unused")
public class MultilayerPerceptronClassifier extends AbsWekaClassifier {

	/*
	 * neural network regression
	 */

	private static final double DEFAULT_LR = 0.8;
	private static final double DEFAULT_M = 0.3;
	private static final double DEFAULT_TT = 200; // No se pueden optimizar
													// parametros integers
	private static final double DEFAULT_VS = 0;
	
	private static final double MIN_VALUE=2;
	private static final double MAX_VALUE=5;

	/**/
	public MultilayerPerceptronClassifier(int index) {
		super(new MultilayerPerceptron(), new WekaClassifierOptimizer(MIN_VALUE,MAX_VALUE), index);
		WekaSimpleParameter l = new WekaSimpleParameter('L', DEFAULT_LR, "L"/* "Learning Rate" */);
		l.setMaxValue(1);
		addParameter(l); /* 0-1 Double */
		WekaSimpleParameter m = new WekaSimpleParameter('M', DEFAULT_M, "M"/* "Momentum" */);
		m.setMaxValue(1);
		addParameter(m); /* 0-1 Double */
		/*
		 * addParameter(new WekaSimpleParameter('N', DEFAULT_TT, "N")); //
		 * "Training Time" - No tiene limites - Integer WekaSimpleParameter
		 * per=new WekaSimpleParameter('V', DEFAULT_VS, "V"); //
		 * "Percentage size of validation set" - 0-100 - Integer
		 * per.setMaxValue(100); addParameter(per);
		 */
	}

	@Override
	public String getName() {
		return "MultilayerPerceptron";
	}
}
