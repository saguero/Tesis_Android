package com.example.prediction.logica.individual;

import java.util.Enumeration;

import weka.core.Attribute;
import weka.core.Instance;

public class WekaIndividual extends Individual{
	
	private Instance individual;
	
	public WekaIndividual(Instance individual){
		this.individual=individual;
		for (int i=0; i<individual.numAttributes();i++){
			Attribute a=individual.attribute(i);
			addAttribute(a.name(),individual.value(a));
		}
	}
	
	@Override
	public Object getIndividualRepresentation(){
		return individual;
	}

	@Override
	public void setAttributeValue(String name, Double value) {
		// TODO Auto-generated method stub
		Enumeration<Attribute> ats=individual.dataset().enumerateAttributes();
		int index=0;
		int atindex=0;
		while (ats.hasMoreElements()){
			Attribute at=ats.nextElement();
			if (at.name().equals(name)){
				atindex=index;
			}
			index++;
		}
		individual.setValue(atindex, (double)value);
		super.setAttributeValue(name, value);
	}
	
	@Override
	public double getValueAttribute(int att){
		return individual.value(att);
	}

}
