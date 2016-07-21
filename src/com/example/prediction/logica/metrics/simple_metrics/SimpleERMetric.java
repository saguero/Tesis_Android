package com.example.prediction.logica.metrics.simple_metrics;

import java.util.Vector;

import com.example.prediction.logica.metrics.AbsSimpleMetric;
import com.example.prediction.logica.metrics.collection.AbsMetricsCollection;
import com.example.prediction.logica.metrics.collection.AbsMetricsCollection.Info;
import com.example.prediction.logica.metrics.collection.AbsMetricsCollection.Representation;
import com.example.prediction.logica.metrics.collection.AbsMetricsCollection.Required;
import com.example.prediction.logica.metrics.collection.AbsMetricsCollection.Type;

/*Mean Squared Error*/

public class SimpleERMetric extends AbsSimpleMetric{

	public SimpleERMetric() {
		super(AbsMetricsCollection.ER, Required.MIN, Representation.PERCENTUAL, Type.REGRESSION, Info.ERROR_PREDICTION, "Error rate");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Double calculate(Vector<Double> predicted, Vector<Double> reals) {
		// TODO Auto-generated method stub
		Double r=0.0;
		for (int loc=0; loc<reals.size();loc++){
			r+=Math.pow(reals.get(loc)-predicted.get(loc), 2);
		}
		return r/reals.size();
	}

}
