package com.example.prediction.logica.models;

import java.util.Vector;

import com.example.prediction.logica.database.AbsDatabase;
import com.example.prediction.logica.individual.Individual;
import com.example.prediction.logica.parameters.AbsParameter;

public abstract class AbsModeler {
	
	protected Vector<AbsParameter> modelParameters_=new Vector<AbsParameter>();
	protected AbsDatabase database_;
	protected int indexClass;
	
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
	

	public boolean calculateModeler(AbsDatabase database){
		database_=database;
		setIndexAttribute(indexClass);
		return getModel();
	}
	
	public AbsDatabase getDatabase(){
		return database_;
	}
	
	public void setIndexAttribute(int index){
		indexClass=index;
	}
	
	public int getIndexAttribute(){
		return indexClass;
	}	

	public Vector<Double> getPredictedValue(AbsDatabase database) throws Exception{
		Vector<Double> r=new Vector<Double>();
		setIndexAttribute(indexClass);
		for (Individual i:database.getIndividuals()){
			r.add(predictIndividualValue(i));
		}
		return r;
	}
	
	//--Abstract methods
	
	protected abstract boolean getModel();
	public abstract String getName();
	public abstract String toString();
	
	public abstract Double predictIndividualValue(Individual ind) throws Exception;
	
	public abstract boolean handles(AbsDatabase trainingSet);
}
