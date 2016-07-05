package com.example.prediction.logica.models;

import com.example.prediction.logica.database.WekaDatabase;
import com.example.prediction.logica.libraries.WekaLibrary;
import com.example.prediction.logica.optimization.AbsWekaOptimizer;

import weka.attributeSelection.CfsSubsetEval;
import weka.attributeSelection.ExhaustiveSearch;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.classifiers.meta.AttributeSelectedClassifier;
import weka.core.Instances;
import weka.core.OptionHandler;

public abstract class AbsWekaClassifier extends AbsClassifier{
	protected AbstractClassifier classifier;
	protected AbsWekaOptimizer optimizer;
	private int index;
	
	//--Public methods
	/**/
	public AbsWekaClassifier(AbstractClassifier clas, AbsWekaOptimizer optimizer, int index){
		database_=new WekaDatabase();
		this.index=index;
		classifier=clas;
		this.optimizer=optimizer;
	}

	public void setClassifier(AbstractClassifier classifier){
		this.classifier=classifier;
		WekaLibrary.parseOptions(classifier.getOptions(), this);
	}

	public String toString(){
		return classifier.toString();
	}
	
	public Classifier getClassifier(){
		return classifier;
	}

	@Override
	public AbsModeler getModel(){
		setIndexAttribute(index);
		selectBestAttributes();
		optimizer.optimiceParams(this);
		try {
			classifier.buildClassifier(((Instances)database_.getDatabaseImplementation()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this;
	}
	
	public void setIndexAttribute(int index){
		((Instances)database_.getDatabaseImplementation()).setClassIndex(index);
	}
	
	public void selectBestAttributes(){
			AttributeSelectedClassifier asc = new AttributeSelectedClassifier();
		    CfsSubsetEval evaluator = new CfsSubsetEval();
		    ExhaustiveSearch search = new ExhaustiveSearch();
		    asc.setClassifier(classifier);
		    asc.setEvaluator(evaluator);
		    asc.setSearch(search);
		    try {
				asc.buildClassifier(((Instances)database_.getDatabaseImplementation()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    WekaLibrary.parseOptions(((OptionHandler)asc.getClassifier()).getOptions(), this);
	}
	
	//--Abstract methods
	public abstract String getName();
}
