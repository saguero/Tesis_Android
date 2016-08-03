package com.example.prediction.logica.individual;

import java.util.Hashtable;

public class Individual {
	
	private Hashtable<String, Double> attributes=new Hashtable<String, Double>();
	
	public Hashtable<String, Double> getIndividualAttributes(){
		return attributes;
	}
	
	public void addAttribute(String name, Double value){
		attributes.put(name, value);
	}

	public Object getIndividualRepresentation(){
		return attributes; //Puede ser override si se tiene una representación interna (Por ejemplo weka - Instance)
	}
	
	public void setAttributeValue(String name, Double value){
		attributes.put(name, (Double)value);
	}
	
	public double getValueAttribute(int att){
		return attributes.get(attributes.keySet().toArray()[att]);
	}
	
	public boolean equalsHeader(Individual ind){
		Hashtable<String, Double> attsInd = ind.getIndividualAttributes();
		if (attsInd.keySet().equals(attributes.keySet()))
			return true;
		return false;
	}

}
