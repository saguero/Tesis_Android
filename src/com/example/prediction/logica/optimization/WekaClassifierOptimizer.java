package com.example.prediction.logica.optimization;

import java.util.Vector;

import com.example.prediction.logica.libraries.WekaLibrary;
import com.example.prediction.logica.models.AbsModeler;
import com.example.prediction.logica.models.AbsWekaClassifier;
import com.example.prediction.logica.parameters.AbsParameter;
import com.example.prediction.logica.parameters.WekaSimpleParameter;

import weka.classifiers.meta.CVParameterSelection;
import weka.core.Instances;

public class WekaClassifierOptimizer extends AbsWekaOptimizer {

	private static final int FOLDS = 5;
	private static final int DEFAULT_CLASSIFIER_STEPS = 6;
	
	public WekaClassifierOptimizer(double min, double max){
		minValue=min;
		maxValue=max;
	}

	@Override
	public void optimiceParams(AbsModeler modeler, Instances isTrainingSet) throws Exception {
		// TODO Auto-generated method stub
			Vector<AbsParameter> parameters = modeler.getParameters();
			CVParameterSelection cvps = new CVParameterSelection();
			double max=0;
			double min=0;
			double d=0;
			Double minInt=Double.valueOf(min);
			Double maxInt=Double.valueOf(max);
			for (AbsParameter p : parameters) {
				String val = new String();
				max=((WekaSimpleParameter) p).getLastValue(maxValue);
				min=((WekaSimpleParameter) p).getFirstValue(minValue);
				d = ((WekaSimpleParameter) p).getValue() % 1;
				if (d == 0) {
					minInt = Double.valueOf(min);
					maxInt = Double.valueOf(max);
					val = ((WekaSimpleParameter) p).getParameterString().charAt(1) + " " + minInt.intValue() + " " + maxInt.intValue() + " "
							+ DEFAULT_CLASSIFIER_STEPS;
				} else {
					val = ((WekaSimpleParameter) p).getParameterString().charAt(1) + " " + ((WekaSimpleParameter) p).getMinValor() + " " + max + " "
							+ DEFAULT_CLASSIFIER_STEPS;
				}
				cvps.addCVParameter(val);
			}
			cvps.setNumFolds(FOLDS);
			cvps.setClassifier(((AbsWekaClassifier) modeler).getClassifier());
			cvps.buildClassifier(isTrainingSet);
			WekaLibrary.parseOptions(cvps.getBestClassifierOptions(), modeler);
	}

}
