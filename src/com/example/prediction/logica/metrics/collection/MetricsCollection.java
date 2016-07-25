package com.example.prediction.logica.metrics.collection;

import java.util.Vector;

import com.example.prediction.logica.metrics.abstracts.AbsMetric;
import com.example.prediction.logica.metrics.abstracts.Info;
import com.example.prediction.logica.metrics.abstracts.Representation;
import com.example.prediction.logica.metrics.abstracts.Type;
import com.example.prediction.logica.models.AbsModeler;

public abstract class MetricsCollection {
	public static final int CC = 1;
	public static final int RMSE = 2;
	public static final int RRSE = 3;
	public static final int MAE = 4;
	public static final int RAE = 5;
	public static final int COMB = 6;
	public static final int ACC = 7;
	public static final int KAP = 8;
	public static final int ROC = 9;
	public static final int RECALL = 10;
	public static final int ER = 11;
	
	public 	Type type = Type.REGRESSION;												//VER DESPUES
	
	private Vector<AbsMetric> metricsEvaluation = new Vector<AbsMetric>();
	
	public MetricsCollection(){
			
	}
	
	public void acceptMetric(AbsMetric metric){
		metricsEvaluation.add(metric);
	}

	public Vector<AbsMetric> ErrorPredictionNormalizedMetrics(){
		Vector<AbsMetric> result = new Vector<AbsMetric>();
		for(AbsMetric m: metricsEvaluation){
			if(m.getType().equals(type) && m.canBeNormalized() && m.isInfo(Info.ERROR_PREDICTION) )
				result.add(m);
		}
		return result;
	}
	
	public Vector<AbsMetric> ErrorPredictionScaleMetrics(){
		Vector<AbsMetric> result = new Vector<AbsMetric>();
		for(AbsMetric m: metricsEvaluation){
			if(m.getType().equals(type) && m.isRep(Representation.SCALE) && m.isInfo(Info.ERROR_PREDICTION) )
				result.add(m);
		}		
		return result;
	}
	
	public Vector<AbsMetric> RelationDataMetrics(){
		Vector<AbsMetric> result = new Vector<AbsMetric>();
		for(AbsMetric m: metricsEvaluation){
			if(m.getType().equals(type) && m.canBeNormalized() && m.isInfo(Info.RELATION_DATA) )
				result.add(m);
		}		
		return result;
	}

	public abstract boolean aceptModel(AbsModeler model);
	
}

