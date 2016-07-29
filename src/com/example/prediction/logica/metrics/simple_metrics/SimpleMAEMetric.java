package com.example.prediction.logica.metrics.simple_metrics;

import java.util.Vector;

import com.example.prediction.logica.metrics.abstracts.AbsSimpleMetric;
import com.example.prediction.logica.metrics.abstracts.Info;
import com.example.prediction.logica.metrics.abstracts.Representation;
import com.example.prediction.logica.metrics.abstracts.Required;
import com.example.prediction.logica.metrics.abstracts.Type;
import com.example.prediction.logica.metrics.collection.MetricsCollection;

public class SimpleMAEMetric extends AbsSimpleMetric{

	public SimpleMAEMetric() {
		super(MetricsCollection.MAE,Required.MIN, Representation.SCALE, Type.REGRESSION, Info.ERROR_PREDICTION, "Mean absolute error");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Double calculate(Vector<Double> predicted, Vector<Double> reals) {
		// TODO Auto-generated method stub
		Double r=0.0;
		for (int loc=0; loc<reals.size();loc++){
			r+=Math.abs(reals.get(loc)-predicted.get(loc));
		}
		return r/reals.size();
	}

}
