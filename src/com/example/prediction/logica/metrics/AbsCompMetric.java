package com.example.prediction.logica.metrics;

import java.util.Vector;

import com.example.prediction.logica.database.AbsDatabase;
import com.example.prediction.logica.metrics.collection.AbsMetricsCollection.Info;
import com.example.prediction.logica.metrics.collection.AbsMetricsCollection.Representation;
import com.example.prediction.logica.metrics.collection.AbsMetricsCollection.Required;
import com.example.prediction.logica.metrics.collection.AbsMetricsCollection.Type;
import com.example.prediction.logica.models.AbsModeler;

public abstract class AbsCompMetric extends AbsMetric{
	
	protected Vector<AbsMetric> metrics;

	protected AbsCompMetric(int ID, Required req, Representation rep, Type t, Info i, String name) {
		super(ID, req, rep, t, i, name);
		metrics = new Vector<AbsMetric>();
		// TODO Auto-generated constructor stub
	}
	
	public void addMetric(AbsMetric metric){
		metrics.add(metric);
	}
	
	public Vector<AbsMetric> getMetrics(){
		return metrics;
	}

	@Override
	public abstract Double calculate(int mode) throws Exception;
	

}
