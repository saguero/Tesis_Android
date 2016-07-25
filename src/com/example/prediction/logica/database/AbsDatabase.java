package com.example.prediction.logica.database;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.example.prediction.logica.individual.Individual;


public abstract class AbsDatabase implements Cloneable{		//Database general - se puede reescribir si se quiere utilizar una representación específica como Weka - Instances
	
	protected Vector<Individual> database;
	
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
	
	public  Vector<Double> getActualValues(int classIndex){
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
		AbsDatabase result = newInstance(this);
		result.database = this.getIndividuals();
		result.database.remove(0);
		result.database.remove(result.database.size()-1);
		return result;
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
	
	public AbsDatabase subDatabase(int i, int d) {
		// TODO Auto-generated method stub
		AbsDatabase result = newInstance(this);
		result.database=new Vector<Individual>();
		List<Individual> list=this.getIndividuals().subList(i, d);
		Iterator<Individual> it=list.iterator();
		while (it.hasNext()){
			Individual ind=it.next();
			result.database.add(ind);
		}
		return result;
	}
	
	public AbsDatabase removeSubAbsDatabase(int i, int j) {
		// TODO Auto-generated method stub
		AbsDatabase result = newInstance(this);
		Vector<Individual> copy=new Vector<Individual>();
		copy.addAll(database);
		List<Individual> list=this.getIndividuals().subList(i, j);
		Iterator<Individual> it=list.iterator();
		while (it.hasNext()){
			Individual ind=it.next();
			copy.remove(ind);
		}
		result.database=copy;
		return result;
	}
	
	public abstract AbsDatabase newInstance(AbsDatabase database);
	public abstract File saveFile(File file) throws Exception;						//Ver como se va a almacenar el file
	public abstract void newInstanceFromARFF(File saveFile) throws Exception;		//Replace with default save format
	public abstract void parseFile(File file);	//Parsing del archivo

	
}
