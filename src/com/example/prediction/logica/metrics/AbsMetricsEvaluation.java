package com.example.prediction.logica.metrics;

import java.util.Vector;

import com.example.prediction.logica.database.AbsDatabase;
import com.example.prediction.logica.evaluation.AbsEvaluation;
import com.example.prediction.logica.libraries.AbsLibrary;
import com.example.prediction.logica.models.AbsClassifier;

public abstract class AbsMetricsEvaluation {
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
		
	public enum Required {MAX, MIN}
	public enum Representation {NORMALIZED, SCALE, PERCENTUAL}
	public enum Type {REGRESSION, CLASSIFICATION}
	public enum Info {ERROR_PREDICTION, RELATION_DATA}
	
	public 	Type type = Type.REGRESSION;												//VER DESPUES
	
	protected AbsLibrary library;
	
	private Vector<AbsMetric> metricsEvaluation = new Vector<AbsMetric>();
	public AbsDatabase trainingSet;
	public AbsEvaluation evaluator;
	public AbsClassifier scheme;
	
	public AbsMetricsEvaluation(AbsDatabase trainingSet, AbsEvaluation eval){
		CCMetric cc = new CCMetric(library);
		metricsEvaluation.add(cc);
		RMSEMetric rmse = new RMSEMetric(library);
		metricsEvaluation.add(rmse);
		RRSEMetric rrse = new RRSEMetric(library);
		metricsEvaluation.add(rrse);
		MAEMetric mae = new MAEMetric(library);
		metricsEvaluation.add(mae);
		RAEMetric rae = new RAEMetric(library);
		metricsEvaluation.add(rae);
		COMBMetric comb = new COMBMetric(library);
		metricsEvaluation.add(comb);
		ACCMetric acc = new ACCMetric(library);
		metricsEvaluation.add(acc);
		KAPMetric kap = new KAPMetric(library);
		metricsEvaluation.add(kap);
		ROCMetric roc = new ROCMetric(library);
		metricsEvaluation.add(roc);
		RECALLMetric recall = new RECALLMetric(library);
		metricsEvaluation.add(recall);
				
		this.trainingSet = trainingSet;
		this.evaluator = eval;
		
	}
	
	public void acceptMetric(int metric){
		for(AbsMetric m: metricsEvaluation){
			if(m.getId() == metric)
				m.accept();
		}	
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
	
}

