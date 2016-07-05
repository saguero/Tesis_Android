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
	private static final double DEFAULT_CLASSIFIER_STEPS = 5;
	
	public WekaClassifierOptimizer(double min, double max){
		minValue=min;
		maxValue=max;
	}

	@Override
	public void optimiceParams(AbsModeler modeler, Instances isTrainingSet) {
		// TODO Auto-generated method stub
		try {
			Vector<AbsParameter> parameters = modeler.getParameters();
			CVParameterSelection cvps = new CVParameterSelection();

			for (AbsParameter p : parameters) {
				WekaSimpleParameter wsp = (WekaSimpleParameter) p;
				String val = new String();
				double max=wsp.getLastValue(maxValue);
				double min=wsp.getFirstValue(minValue);
				double d = wsp.getValue() % 1;
				if (d == 0) {
					Double minInt = Double.valueOf(min);
					Double maxInt = Double.valueOf(max);
					val = wsp.getParameterString().charAt(1) + " " + minInt.intValue() + " " + maxInt.intValue() + " "
							+ DEFAULT_CLASSIFIER_STEPS;
				} else {
					val = wsp.getParameterString().charAt(1) + " " + wsp.getMinValor() + " " + max + " "
							+ DEFAULT_CLASSIFIER_STEPS;
				}
				cvps.addCVParameter(val);
			}
			cvps.setNumFolds(FOLDS);
			cvps.setClassifier(((AbsWekaClassifier) modeler).getClassifier());
			cvps.buildClassifier(isTrainingSet);
			WekaLibrary.parseOptions(cvps.getBestClassifierOptions(), modeler);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
