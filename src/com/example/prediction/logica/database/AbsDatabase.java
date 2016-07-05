package com.example.prediction.logica.database;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Vector;

import com.example.prediction.logica.individual.Individual;


public abstract class AbsDatabase implements Cloneable{
	
	protected Vector<Individual> database;
	
	public AbsDatabase(){
		database=new Vector<Individual>();
	}
	
	public Vector<Individual> getIndividuals(){
		return database;
	}
	
	public Object getDatabaseImplementation(){
		return database; ////Puede ser override si se tiene una representación interna (Por ejemplo weka - Instances)
	}
	
	public void addAttribute(String name){		//Numeric attribute
		for (Individual i:database){
			i.addAttribute(name, Double.valueOf(-1));
		}
	}
	
	public void addData(File file){
		AbsDatabase second=clone();
		second.parseFile(file);
		for (Individual i:second.getIndividuals()){
			database.add(i);
		}
	}
	
	public AbsDatabase clone(){
		 AbsDatabase b=null;
			try {
				b = getClass().getDeclaredConstructor().newInstance();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return b;
		
	}
	
	public abstract void parseFile(File file);	//Parsing del archivo

	/*Sil*/
	
	int classIndex = 0;
	Object trainingSet;
	
	public AbsDatabase(File file) throws Exception{
		convertInstancesObject(convertFile(file));
	}
	
	
	
	public AbsDatabase newInstance(Object trainingSet){
		AbsDatabase result = newInstance();
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
	
	public AbsDatabase getNewDatasetByRemove(int first, int last) throws Exception{
		AbsDatabase result = newInstance();
		result.classIndex = this.classIndex;
		result.trainingSet = this.trainingSet;
		result.trainingSet = result.removeInstances(first,last);
		return result;
		
	}
	
	public abstract AbsDatabase newInstance();
	public abstract void convertInstancesObject(File fileInstances) throws Exception;
	public abstract Object removeInstances(int first, int last) throws Exception;
	public abstract File convertFile(File file) throws Exception;
	public abstract int getClassIndex();
	public abstract void setClassIndex(int classIndex);
	public abstract int numAttributes();
	public abstract int numInstances();
	public abstract Double getInstanceValue(int instance, int classIndex); 
	public abstract String getAttribute(int attribute);
	
}
