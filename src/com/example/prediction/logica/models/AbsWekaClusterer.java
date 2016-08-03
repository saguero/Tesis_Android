package com.example.prediction.logica.models;

import com.example.prediction.logica.database.AbsDatabase;
import com.example.prediction.logica.database.WekaDatabase;

import weka.clusterers.Clusterer;
import weka.core.Instances;

public abstract class AbsWekaClusterer extends AbsClusterer {

	protected Clusterer clusterer_;

	// --Public methods

	public AbsWekaClusterer(Clusterer clusterer, int index) {
		database_ = new WekaDatabase();
		indexClass=index;
		clusterer_ = clusterer;
	}

	@Override
	protected boolean getModel() {
		// TODO Auto-generated method stub
		removeIndexClass();
		if (handles(database_)){
		getClusterer((Instances) database_.getDatabaseImplementation());
		return true;}
		return false;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return clusterer_.toString();
	}

	public Clusterer getClusterer() {
		return clusterer_;
	}

	public void removeIndexClass() {
		try {
			((Instances) database_.getDatabaseImplementation()).setClassIndex(-1);
		} catch (IllegalArgumentException e) {
			System.out.println("Si pusiste clusterer, está todo bien :D");
		}
	}
	
	public boolean handles(AbsDatabase trainingSet){
		return clusterer_.getCapabilities().test((Instances)((WekaDatabase)trainingSet).getDatabaseImplementation());
	}

	// --Abstract methods

	protected abstract void getClusterer(Instances instances);

}
