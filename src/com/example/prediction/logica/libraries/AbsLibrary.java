package com.example.prediction.logica.libraries;

import java.util.Vector;

import com.example.prediction.logica.database.AbsDatabase;
import com.example.prediction.logica.evaluation.AbsEvaluation;
import com.example.prediction.logica.metrics.AbsMetricsEvaluation;
import com.example.prediction.logica.models.AbsModeler;

public abstract class AbsLibrary {
	
	private String ID;											
	
	protected AbsDatabase trainingSet;
	protected AbsEvaluation evaluator;					
	protected AbsMetricsEvaluation metricsEvaluation;	
	protected Vector<Integer> acceptedModelers = new Vector<Integer>();								
	
	public AbsLibrary(String ID){
		this.ID =ID;
	}
	
	public String getID(){
		return ID;
	}
	
	public AbsDatabase getDatasetObject(){
		return trainingSet;
	}
	
	public AbsEvaluation getEvaluationObject(){
		return evaluator;
	}
	
	public AbsMetricsEvaluation getMetricsEvaluationObject(){
		return metricsEvaluation;
	}
	
	public Vector<Integer> getAcceptedModelers(){
		return acceptedModelers;
	}
	
	
	public abstract void setDatasetObject();
	public abstract void setEvaluationObject();
	public abstract void setMetricsEvaluationObject();
	public abstract void setAcceptedModelers();
	
	public abstract Vector<AbsModeler> getModelers(Vector<Integer> selectedModels, int index);

	public abstract Double calculateCC(Object evaluation) throws Exception;
	public abstract Double calculateRMSE(Object evaluation) throws Exception;
	public abstract Double calculateMAE(Object evaluation) throws Exception;
	public abstract Double calculateRAE(Object evaluation) throws Exception;
	public abstract Double calculateRRSE(Object evaluation) throws Exception;
	public abstract Double calculateACC(Object evaluation) throws Exception;
	public abstract Double calculateKAP(Object evaluation) throws Exception;
	public abstract Double calculateROC(Object evaluation) throws Exception;
	public abstract Double calculateRECALL(Object evaluation) throws Exception;


}
