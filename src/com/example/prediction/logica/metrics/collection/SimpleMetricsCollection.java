package com.example.prediction.logica.metrics.collection;

import com.example.prediction.logica.metrics.simple_metrics.SimpleERMetric;
import com.example.prediction.logica.metrics.simple_metrics.SimpleMAEMetric;
import com.example.prediction.logica.models.AbsModeler;

public class SimpleMetricsCollection extends MetricsCollection{
	
	public SimpleMetricsCollection(){
		acceptMetric(new SimpleERMetric());
		acceptMetric(new SimpleMAEMetric());
	}

	@Override
	public boolean aceptModel(AbsModeler model) {
		// TODO Auto-generated method stub
		return true;
	}

}
