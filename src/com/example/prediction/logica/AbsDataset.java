package com.example.prediction.logica;

import java.io.File;
import java.util.Vector;

public abstract class AbsDataset {
	int classIndex = 0;
	Object trainingSet;
	
	public AbsDataset(){
		
	}
	
	public AbsDataset(File file) throws Exception{
		setFile(file);
	}
	
	public AbsDataset newInstance(){
		return this;
	}
	
	public AbsDataset newInstance(Object trainingSet){
		AbsDataset result = this;
		result.setTrainingSet(trainingSet);
		return result;
	}
			
	public void setPredictedAtt(int classIndex){
		setClassIndex(classIndex);
		this.classIndex = classIndex;	
	}
	
	public  Vector<Double> getActualValues(){
		Vector<Double> actual = new Vector<Double>();
		for(int instance=0;instance<numInstances();instance++){
			double valueActual =  getInstanceValue(instance, classIndex);
	        actual.add(valueActual);
		}
		return actual;
	}
	
	public Vector<String> getNamesAttributes(){
		Vector<String> result = new Vector<String>();
		for(int i=0; i< numAttributes(); i++){
			String a = getAttribute(i);
			result.add(a);
		}
		return result;
	}

	public Object getTrainingSet() {
		// TODO Auto-generated method stub
		return trainingSet;
	}
	
	public void setTrainingSet(Object trainingSet){
		this.trainingSet = trainingSet;
	}
	
	public AbsDataset getNewDatasetByRemove(int first, int last) throws Exception{
		return newInstance(removeInstances(first, last));
		
	}
	
	
	public abstract void setFile(File file) throws Exception;
	public abstract Object removeInstances(int first, int last) throws Exception;
	public abstract File convertFile(File file) throws Exception;
	public abstract int getClassIndex();
	public abstract void setClassIndex(int classIndex);
	public abstract int numAttributes();
	public abstract int numInstances();
	public abstract Double getInstanceValue(int instance, int classIndex); 
	public abstract String getAttribute(int attribute);
}
