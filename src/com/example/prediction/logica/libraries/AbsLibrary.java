package com.example.prediction.logica.libraries;

import java.util.Vector;

import com.example.prediction.logica.database.AbsDatabase;
import com.example.prediction.logica.evaluation.AbsEvaluation;
import com.example.prediction.logica.metrics.collection.MetricsCollection;
import com.example.prediction.logica.models.AbsModeler;

public abstract class AbsLibrary {
	
	private String ID;											
	
	protected AbsDatabase trainingSet;
	protected AbsEvaluation evaluator;					
	protected MetricsCollection metricsEvaluation;	
	protected Vector<Integer> acceptedModelers = new Vector<Integer>();								
	
	public AbsLibrary(String ID){
		createEvaluator();
		createMetricsEvaluation();
		createDatabase();
		setAcceptedModelers();
		this.ID =ID;
	}
	
	public String getID(){
		return ID;
	}
	
	public AbsDatabase getDatasetObject(){
		return trainingSet;
	}
	
	public AbsEvaluation getEvaluator(){
		return evaluator;
	}
	
	public MetricsCollection getMetricsEvaluationObject(){
		return metricsEvaluation;
	}
	
	public Vector<Integer> getAcceptedModelers(){
		return acceptedModelers;
	}
	
	
	public abstract void createDatabase();
	public abstract void createEvaluator();
	public abstract void createMetricsEvaluation();
	public abstract void setAcceptedModelers();
	
	public abstract Vector<AbsModeler> createModelers(Vector<Integer> selectedModels, int index);

}
