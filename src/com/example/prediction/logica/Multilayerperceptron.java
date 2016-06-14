package com.example.prediction.logica;

import weka.classifiers.Classifier;
import weka.core.Instances;

public class Multilayerperceptron extends AbsClassifier {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Classifier[] bestClassifiers(Instances dataset) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String globalInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * neural network regression
	 
	
	public Multilayerperceptron(){
		super();
		scheme=new MultilayerPerceptron();
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "MultilayerPerceptron";
	}

	@Override
	public Classifier[] bestClassifiers(Instances dataset) throws Exception {
		// TODO Auto-generated method stub
		MultilayerPerceptron mlp = new MultilayerPerceptron();
		CVParameterSelection cvps = new CVParameterSelection();
		cvps.setNumFolds(5);
		//Learning rate: -L 0.3
		cvps.addCVParameter("L 0.1 0.5 5");
		//momentum: -M 0.2
		cvps.addCVParameter("M 0.1 0.5 5");
		//trainingTime: -N 500
		cvps.addCVParameter("N 500 1000 6");
		//validationsetSize: -V 0
		cvps.addCVParameter("V 0 100 5");
		cvps.setClassifier(mlp);
		cvps.buildClassifier(dataset);
		mlp.setOptions(cvps.getBestClassifierOptions());
		return new Classifier[]{mlp};
	}

	@Override
	public String globalInfo() {
		// TODO Auto-generated method stub
		return null;
	}*/
}
