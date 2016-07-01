package com.example.prediction.logica.libraries;

import com.example.prediction.logica.AbsClassifier;
import com.example.prediction.logica.AbsDataset;
import com.example.prediction.logica.AbsEvaluation;
import com.example.prediction.logica.metrics.MetricsCollection;

public abstract class AbsLibrary {

	protected String ID;											
	
	protected AbsDataset trainingSet;
	protected AbsEvaluation evaluator;					
	protected MetricsCollection metricsEvaluation;	
	protected AbsClassifier schemes;								
	
	public AbsLibrary(String ID){
		this.ID =ID;
	}
	
	public String getID(){
		return ID;
	}
	
	public AbsDataset getDatasetObject(){
		return trainingSet;
	}
	
	public AbsEvaluation getEvaluationObject(){
		return evaluator;
	}
	
	public MetricsCollection getMetricsEvaluationObject(){
		return metricsEvaluation;
	}
	
	public AbsClassifier getSchemesObject(){
		return schemes;
	}
	
	public abstract void setDatasetObject();
	public abstract void setEvaluationObject();
	public abstract void setMetricsEvaluationObject();
	public abstract void setSchemesObject();
	
	
}
