package com.example.prediction.logica.models;

import com.example.prediction.logica.database.WekaDatabase;

import weka.clusterers.Clusterer;
import weka.core.Instances;

public abstract class AbsWekaClusterer extends AbsClusterer {
	
	protected Clusterer clusterer_;
	
	//--Public methods
	
	public AbsWekaClusterer(Clusterer clusterer){
		database_=new WekaDatabase();
		clusterer_=clusterer;
	}

	@Override
	protected AbsModeler getModel() {
		// TODO Auto-generated method stub
		getClusterer((Instances)database_.getDatabaseImplementation());
		return this;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return clusterer_.toString();
	}
	
	public Clusterer getClusterer(){
		return clusterer_;
	}
	
	//--Abstract methods

	protected abstract void getClusterer(Instances instances);
	
}
