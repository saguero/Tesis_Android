package com.example.prediction.logica.libraries;

import java.util.Vector;

import com.example.prediction.logica.database.AbsDatabase;
import com.example.prediction.logica.evaluation.AbsEvaluation;
import com.example.prediction.logica.metrics.AbsMetricsEvaluation;
import com.example.prediction.logica.models.AbsClassifier;

public abstract class AbsLibrary {
	
	String ID;											
	
	AbsDatabase trainingSet;
	AbsEvaluation evaluator;					
	AbsMetricsEvaluation metricsEvaluation;	
	AbsClassifier scheme;
	Vector<AbsClassifier> listSchemes;								
	
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
	
	public Vector<AbsClassifier> getListSchemes(){
		return listSchemes;
	}
	
	public AbsClassifier getSchemeObject(){
		return scheme;
	}
	
	
	public abstract void setDatasetObject();
	public abstract void setEvaluationObject();
	public abstract void setMetricsEvaluationObject();
	public abstract void setSchemeObject();
	public abstract void setListSchemes();
	

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
