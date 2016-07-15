package com.example.prediction.logica.models;

import com.example.prediction.logica.database.WekaDatabase;

import weka.clusterers.Clusterer;
import weka.core.Instances;

public abstract class AbsWekaClusterer extends AbsClusterer {
	
	protected Clusterer clusterer_;
	protected int index;
	
	//--Public methods
	
	public AbsWekaClusterer(Clusterer clusterer, int index){
		database_=new WekaDatabase();
		this.index=index;
		clusterer_=clusterer;
	}

	@Override
	protected void getModel() {
		// TODO Auto-generated method stub
		getClusterer((Instances)database_.getDatabaseImplementation());
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
