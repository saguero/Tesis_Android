package com.example.prediction.logica.metrics.composite;

import com.example.prediction.logica.database.AbsDatabase;
import com.example.prediction.logica.metrics.abstracts.AbsCompMetric;
import com.example.prediction.logica.metrics.abstracts.AbsMetric;
import com.example.prediction.logica.metrics.abstracts.Info;
import com.example.prediction.logica.metrics.abstracts.Representation;
import com.example.prediction.logica.metrics.abstracts.Required;
import com.example.prediction.logica.metrics.abstracts.Type;
import com.example.prediction.logica.metrics.collection.MetricsCollection;
import com.example.prediction.logica.metrics.evaluation_metric.WekaCCMetric;
import com.example.prediction.logica.metrics.evaluation_metric.WekaRAEMetric;
import com.example.prediction.logica.metrics.evaluation_metric.WekaRRSEMetric;
import com.example.prediction.logica.models.AbsModeler;

public class COMBMetric extends AbsCompMetric{

	public COMBMetric() {
		super(MetricsCollection.COMB,Required.MAX, Representation.NORMALIZED, Type.REGRESSION, Info.ERROR_PREDICTION, "Comb");
		addMetric(new WekaCCMetric());
		addMetric(new WekaRRSEMetric());
		addMetric(new WekaRAEMetric());
		// TODO Auto-generated constructor stub
	}

	@Override
	public Double calculate(int mode) throws Exception {
		// TODO Auto-generated method stub
		double r=0;
		r+=1-Math.abs(getMetrics().get(0).calculate(mode));
		r+=getMetrics().get(1).calculate(mode);
		r+=getMetrics().get(2).calculate(mode);
		return r;
	}

	@Override
	public void configurateTraining(AbsDatabase database, AbsModeler modeler) {
		for (AbsMetric m:getMetrics()){
			m.configurateTrainingMode(database, modeler);
		}
		
	}

	@Override
	public void configurateCV(AbsDatabase database, AbsModeler modeler, int folds) {
		// TODO Auto-generated method stub
		for (AbsMetric m:getMetrics()){
			m.configurateCVMode(database, modeler, folds);
		}
	}
	
	

}
