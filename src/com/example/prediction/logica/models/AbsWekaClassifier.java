package com.example.prediction.logica.models;

import com.example.prediction.logica.individual.Individual;
import com.example.prediction.logica.individual.WekaIndividual;
import com.example.prediction.logica.libraries.WekaLibrary;
import com.example.prediction.logica.optimization.AbsWekaOptimizer;

import weka.attributeSelection.CfsSubsetEval;
import weka.attributeSelection.ExhaustiveSearch;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.classifiers.meta.AttributeSelectedClassifier;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.OptionHandler;

public abstract class AbsWekaClassifier extends AbsClassifier{
	protected AbstractClassifier classifier;
	protected AbsWekaOptimizer optimizer;
	
	//--Public methods
	/**/
	public AbsWekaClassifier(AbstractClassifier clas, AbsWekaOptimizer optimizer, int index){
		this.indexClass=index;
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
	public void getModel(){
		super.setIndexAttribute(indexClass);
		selectBestAttributes();
		optimizer.optimiceParams(this);
		try {
			classifier.buildClassifier(((Instances)database_.getDatabaseImplementation()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	public Double predictIndividualValue(Individual ind) throws Exception{
		return classifier.classifyInstance((Instance) ((WekaIndividual)ind).getIndividualRepresentation());
	}
	
	public void setIndexClassifier(){
		((Instances)database_.getDatabaseImplementation()).setClassIndex(indexClass);
	}
	
	//--Abstract methods
	public abstract String getName();
	
}
