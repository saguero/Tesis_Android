package com.example.prediction.logica.metrics.collection;

import com.example.prediction.logica.metrics.composite.COMBMetric;
import com.example.prediction.logica.metrics.evaluation_metric.WekaCCMetric;
import com.example.prediction.logica.metrics.evaluation_metric.WekaMAEMetric;
import com.example.prediction.logica.metrics.evaluation_metric.WekaRMSEMetric;
import com.example.prediction.logica.metrics.evaluation_metric.WekaRRSEMetric;
import com.example.prediction.logica.models.AbsModeler;
import com.example.prediction.logica.models.AbsWekaClassifier;

public class WekaMetricsCollection extends MetricsCollection {
	
	public WekaMetricsCollection() {
		// TODO Auto-generated constructor stub
		super();
		acceptMetric(new WekaCCMetric());
		acceptMetric(new COMBMetric());
		acceptMetric(new WekaMAEMetric());
		acceptMetric(new WekaRMSEMetric());
		acceptMetric(new WekaRRSEMetric());
	}

	@Override
	public boolean aceptModel(AbsModeler model) {
		// TODO Auto-generated method stub
		if (model.getClass().isInstance(AbsWekaClassifier.class)){
			return true;
		}
		return false;
	}

}
