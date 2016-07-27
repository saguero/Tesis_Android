package com.example.prediction.logica;

import java.io.File;
import java.util.Vector;

public abstract class AbsDataset {
	int classIndex = 0;
	Object trainingSet;
	
	public AbsDataset(){
		
	}
	
	public AbsDataset(File file, String destination) throws Exception{
		convertInstancesObject(convertFile(file, destination));
	}
	
	
	
	public AbsDataset newInstance(Object trainingSet){
		AbsDataset result = newInstance();
		result.setPredictedAtt(this.classIndex);
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
			String a = getNameAttribute(i);
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
		AbsDataset result = newInstance();
		result.classIndex = this.classIndex;
		result.trainingSet = this.trainingSet;
		result.trainingSet = result.removeInstances(first,last);
		return result;
		
	}
	
	public abstract AbsDataset newInstance();
	public abstract void convertInstancesObject(File fileInstances) throws Exception;
	public abstract Object removeInstances(int first, int last) throws Exception;
	public abstract File convertFile(File file, String destination) throws Exception;
	public abstract int getClassIndex();
	public abstract void setClassIndex(int classIndex);
	public abstract int numAttributes();
	public abstract int numInstances();
	public abstract Double getInstanceValue(int instance, int classIndex); 
	public abstract String getNameAttribute(int attribute);
	public abstract String getTypeAttribute(int index);
}
