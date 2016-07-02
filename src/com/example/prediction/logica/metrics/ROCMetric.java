package com.example.prediction.logica.metrics;

import com.example.prediction.logica.metrics.MetricsCollection.Info;
import com.example.prediction.logica.metrics.MetricsCollection.Representation;
import com.example.prediction.logica.metrics.MetricsCollection.Required;
import com.example.prediction.logica.metrics.MetricsCollection.Type;

import weka.classifiers.Evaluation;

public class ROCMetric extends AbsMetric{

		ROCMetric() {
			super(MetricsCollection.ROC,Required.MAX, Representation.NORMALIZED, Type.CLASSIFICATION, Info.ERROR_PREDICTION);	
		}
		
		Double calculate(Object evaluation) throws Exception {
			return ((Evaluation) evaluation).areaUnderROC(trainingSet.getClassIndex());								
		}
		
		public String getID(){
			return "ROC";
		}

}
