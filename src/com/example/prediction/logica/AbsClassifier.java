package com.example.prediction.logica;

import java.util.Vector;
import weka.attributeSelection.CfsSubsetEval;
import weka.attributeSelection.ExhaustiveSearch;
import weka.classifiers.Classifier;
import weka.classifiers.meta.AttributeSelectedClassifier;
import weka.classifiers.meta.MultiScheme;
import weka.core.Instances;

public abstract class AbsClassifier {
	protected static Vector<AbsClassifier> classifiers = new Vector<AbsClassifier>();
	protected Classifier scheme;
	
	public AbsClassifier(){
		classifiers.add(this);
	}
	
	
	
	private Classifier performAttSelect(Classifier base, Instances dataset) throws Exception{
		AttributeSelectedClassifier asc = new AttributeSelectedClassifier();
	    CfsSubsetEval evaluator = new CfsSubsetEval();
	    ExhaustiveSearch search = new ExhaustiveSearch();
	    asc.setClassifier(base);
	    asc.setEvaluator(evaluator);
	    asc.setSearch(search);
	    asc.buildClassifier(dataset);
	    return asc.getClassifier();
	}
	
	public Classifier optimizingParams(Instances dataset) throws Exception{
		Classifier[] bestclasifiers = bestClassifiers(dataset);
		int index=0;
		Classifier[] optclassifiers = new Classifier[bestclasifiers.length];
		for(Classifier bc: bestclasifiers){
			optclassifiers[index] = performAttSelect(bc,dataset);
			index++;
		}
		//determinate the best classifier  
		MultiScheme ms = new MultiScheme();
		ms.setClassifiers(optclassifiers);
		ms.buildClassifier(dataset);
			    
		return optclassifiers[ms.getBestClassifierIndex()];
	}
	
	public Classifier getScheme(){
		return scheme;
	}
	
	//abstract methods
	public abstract String getName();
	public abstract Classifier[] bestClassifiers(Instances dataset) throws Exception;
	public abstract String globalInfo();
	

}
