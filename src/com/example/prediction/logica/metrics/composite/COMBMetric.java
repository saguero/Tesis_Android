package com.example.prediction.logica.metrics.composite;

import com.example.prediction.logica.database.AbsDatabase;
import com.example.prediction.logica.metrics.AbsCompMetric;
import com.example.prediction.logica.metrics.AbsMetric;
import com.example.prediction.logica.metrics.collection.AbsMetricsCollection;
import com.example.prediction.logica.metrics.collection.AbsMetricsCollection.Info;
import com.example.prediction.logica.metrics.collection.AbsMetricsCollection.Representation;
import com.example.prediction.logica.metrics.collection.AbsMetricsCollection.Required;
import com.example.prediction.logica.metrics.collection.AbsMetricsCollection.Type;
import com.example.prediction.logica.metrics.evaluation_metric.CCMetric;
import com.example.prediction.logica.metrics.evaluation_metric.RAEMetric;
import com.example.prediction.logica.metrics.evaluation_metric.RRSEMetric;
import com.example.prediction.logica.models.AbsModeler;

public class COMBMetric extends AbsCompMetric{

	public COMBMetric() {
		super(AbsMetricsCollection.COMB,Required.MAX, Representation.NORMALIZED, Type.REGRESSION, Info.ERROR_PREDICTION, "Comb");
		addMetric(new CCMetric());
		addMetric(new RRSEMetric());
		addMetric(new RAEMetric());
		// TODO Auto-generated constructor stub
	}

	@Override
	public Double calculate(int mode) throws Exception {
		// TODO Auto-generated method stub
		double r=0;
		r+=1-Math.abs(getMetrics().get(0).calculate(mode));
		r+=getMetrics().get(1).calculateNormalized(mode);
		r+=getMetrics().get(2).calculateNormalized(mode);
		return r;
	}

	@Override
	public void configurateTrainingMode(AbsDatabase database, AbsModeler modeler) {
		for (AbsMetric m:getMetrics()){
			m.configurateTrainingMode(database, modeler);
		}
		
	}

	@Override
	public void configurateCVMode(AbsDatabase database, AbsModeler modeler) {
		// TODO Auto-generated method stub
		for (AbsMetric m:getMetrics()){
			m.configurateCVMode(database, modeler);
		}
	}
	
	

}
