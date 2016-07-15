package com.example.prediction.logica.database;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Vector;

import com.example.prediction.logica.individual.Individual;


public abstract class AbsDatabase implements Cloneable{		//Database general - se puede reescribir si se quiere utilizar una representación específica como Weka - Instances
	
	protected Vector<Individual> database;
	protected int classIndex = 0;
	
	public AbsDatabase(){
		database=new Vector<Individual>();
	}
	
	public Vector<Individual> getIndividuals(){
		return database;
	}
	
	public Object getDatabaseImplementation(){
		return database; 
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

	/*Sil*/
	
	public AbsDatabase(File file) throws Exception{
		newInstanceFromARFF(saveFile(file));
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
	
	public AbsDatabase getNewDatasetByRemove(int first, int last) throws Exception{
		AbsDatabase result = newInstance();
		result.classIndex = this.classIndex;
		result.database = this.getIndividuals();
		result.database.remove(0);
		result.database.remove(result.database.size()-1);
		return result;
	}
	
	public int getClassIndex(){
		return classIndex;
	}
	
	public void setClassIndex(int classIndex){
		this.classIndex=classIndex;
	}
	

	public int numInstances(){
		return database.size();
	}
	
	public int numAttributes(){
		return database.get(0).getIndividualAttributes().size();
	}
	
	public void removeInstances(int first, int last) throws Exception{
		if (database.size()>2){
			database.remove(first);
			database.remove(last);
		}
	}
	
	public Double getInstanceValue(int instance, Object att){
		return database.get(instance).getIndividualAttributes().get((String)att);
	}
	
	public String getAttribute(int attribute){
		return (String) database.get(0).getIndividualAttributes().keySet().toArray()[attribute];
	}
	
	public abstract AbsDatabase newInstance();
	public abstract File saveFile(File file) throws Exception;						//Ver como se va a almacenar el file
	public abstract void newInstanceFromARFF(File saveFile) throws Exception;		//Replace with default save format
	public abstract void parseFile(File file);	//Parsing del archivo
	
}
