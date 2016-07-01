package com.example.prediction.logica.models;

import java.util.Vector;

import com.example.prediction.logica.database.AbsDatabase;
import com.example.prediction.logica.parameters.AbsParameter;

public abstract class AbsModeler {
	
	protected Vector<AbsParameter> modelParameters_=new Vector<AbsParameter>();
	protected AbsDatabase database_;
	
	//--Public methods
	/**/
	public void addParameter(AbsParameter modelParameter){
		for (AbsParameter ap:modelParameters_){
			if (ap.getName().equals(modelParameter.getName())){
				modelParameters_.remove(ap);
				modelParameters_.add(modelParameter);
				return;
			}
		}
		modelParameters_.add(modelParameter);
	}
	
	public AbsParameter getParameter(char c){
		for (AbsParameter ap:modelParameters_){
			if (ap.getName().equals(String.valueOf(c))){
				return ap;
			}
		}
		return null;
	}

	public Vector<AbsParameter> getParameters() {
		// TODO Auto-generated method stub
		return modelParameters_;
	}
	

	public AbsModeler calculateModeler(AbsDatabase database){
		database_=database;
		return getModel();
	}
	
	public AbsDatabase getDatabase(){
		return database_;
	}
	
	//--Abstract methods
	
	protected abstract AbsModeler getModel();
	public abstract String getName();
	public abstract String toString();
}
