package com.example.prediction.logica;

import weka.classifiers.Classifier;
import weka.classifiers.functions.SMOreg;
import weka.core.Instances;

public class Smoreg extends AbsClassifier {
	
	public Smoreg(){
		super();
		scheme=new SMOreg();
	}
	
	public String getName(){
		return "SMOreg";
	}
	
	@Override
	public Classifier[] bestClassifiers(Instances dataset) throws Exception {
		Classifier[] result = {};
		return result;		
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
