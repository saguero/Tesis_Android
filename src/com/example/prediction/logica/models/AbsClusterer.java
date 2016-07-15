package com.example.prediction.logica.models;

import java.util.Vector;

import com.example.prediction.logica.individual.Individual;

public abstract class AbsClusterer extends AbsModeler{
	
	public abstract int getCluster(Individual individual);
	
	public abstract Vector<Individual> getClusterMembers(int cluster);

}
