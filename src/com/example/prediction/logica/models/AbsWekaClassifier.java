package com.example.prediction.logica.models;

import com.example.prediction.logica.database.AbsDatabase;
import com.example.prediction.logica.database.WekaDatabase;
import com.example.prediction.logica.individual.Individual;
import com.example.prediction.logica.individual.WekaIndividual;
import com.example.prediction.logica.libraries.WekaLibrary;
import com.example.prediction.logica.optimization.AbsWekaOptimizer;

import android.util.Log;
import weka.attributeSelection.CfsSubsetEval;
import weka.attributeSelection.ExhaustiveSearch;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.classifiers.meta.AttributeSelectedClassifier;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.OptionHandler;

public abstract class AbsWekaClassifier extends AbsClassifier {
	protected AbstractClassifier classifier;
	protected AbsWekaOptimizer optimizer;

	// --Public methods
	/**/
	public AbsWekaClassifier(AbstractClassifier clas, AbsWekaOptimizer optimizer, int index) {
		this.indexClass = index;
		classifier = clas;
		this.optimizer = optimizer;
	}

	public void setClassifier(AbstractClassifier classifier) {
		this.classifier = classifier;
		WekaLibrary.parseOptions(classifier.getOptions(), this);
	}

	public String toString() {
		return classifier.toString();
	}

	public Classifier getClassifier() {
		return classifier;
	}

	@Override
	public boolean getModel() {
		super.setIndexAttribute(indexClass);
		if (handles(database_)){
		selectBestAttributes();
		try {
			optimizer.optimiceParams(this);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			Log.d("com.example.prediction", "No se puede optimizar");
		}
		try {
			classifier.buildClassifier(((Instances) database_.getDatabaseImplementation()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		return false;
	}

	public void selectBestAttributes() {
		AttributeSelectedClassifier asc = new AttributeSelectedClassifier();
		CfsSubsetEval evaluator = new CfsSubsetEval();
		ExhaustiveSearch search = new ExhaustiveSearch();
		asc.setClassifier(classifier);
		asc.setEvaluator(evaluator);
		asc.setSearch(search);
		try {
			asc.buildClassifier(((Instances) database_.getDatabaseImplementation()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WekaLibrary.parseOptions(((OptionHandler) asc.getClassifier()).getOptions(), this);
	}

	public Double predictIndividualValue(Individual ind) throws Exception {
		return classifier.classifyInstance((Instance) ((WekaIndividual) ind).getIndividualRepresentation());
	}

	public void setIndexClassifier() {
		((Instances) database_.getDatabaseImplementation()).setClassIndex(indexClass);
	}
	
	public boolean handles(AbsDatabase trainingSet){
		return classifier.getCapabilities().test((Instances)((WekaDatabase)trainingSet).getDatabaseImplementation());
	}

	// --Abstract methods
	public abstract String getName();

}
