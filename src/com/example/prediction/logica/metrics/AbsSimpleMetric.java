package com.example.prediction.logica.metrics;

import com.example.prediction.logica.database.AbsDatabase;
import com.example.prediction.logica.metrics.collection.AbsMetricsCollection.Info;
import com.example.prediction.logica.metrics.collection.AbsMetricsCollection.Representation;
import com.example.prediction.logica.metrics.collection.AbsMetricsCollection.Required;
import com.example.prediction.logica.metrics.collection.AbsMetricsCollection.Type;
import com.example.prediction.logica.models.AbsModeler;

public abstract class AbsSimpleMetric extends AbsMetric {

	AbsSimpleMetric(int ID, Required req, Representation rep, Type t, Info i, String name) {
		super(ID, req, rep, t, i, name);
		// TODO Auto-generated constructor stub
	}

}
