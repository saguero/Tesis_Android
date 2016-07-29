package com.example.prediction.logica.models;

import java.util.Vector;

import com.example.prediction.logica.individual.Individual;

public abstract class AbsClusterer extends AbsModeler{
	
	@Override
	public void setIndexAttribute(int index){
		super.setIndexAttribute(index);
		removeIndexClass();
	}
	
	public abstract int getCluster(Individual individual);
	
	public abstract Vector<Individual> getClusterMembers(int cluster);
	
	public abstract void removeIndexClass();		//Si el clusterer no puede manejar implementaciones con clas index internas (weka)

}
