package com.example.prediction.logica.metrics.collection;

import com.example.prediction.logica.metrics.composite.COMBMetric;
import com.example.prediction.logica.metrics.evaluation_metric.CCMetric;
import com.example.prediction.logica.metrics.evaluation_metric.MAEMetric;
import com.example.prediction.logica.metrics.evaluation_metric.RAEMetric;
import com.example.prediction.logica.metrics.evaluation_metric.RMSEMetric;
import com.example.prediction.logica.metrics.evaluation_metric.RRSEMetric;
import com.example.prediction.logica.metrics.simple_metrics.SimpleMAEMetric;

public class WekaMetricsCollection extends AbsMetricsCollection {
	
	public WekaMetricsCollection() {
		// TODO Auto-generated constructor stub
		super();
		//acceptMetric(new CCMetric());
		//acceptMetric(new COMBMetric());
		acceptMetric(new SimpleMAEMetric());
		//acceptMetric(new MAEMetric());
		//acceptMetric(new RMSEMetric());
		//acceptMetric(new RRSEMetric());
	}

}
