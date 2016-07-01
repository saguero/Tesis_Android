package com.example.prediction.logica.metrics;

import java.util.Vector;

import com.example.prediction.logica.AbsClassifier;
import com.example.prediction.logica.AbsDataset;
import com.example.prediction.logica.AbsEvaluation;

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
		
	public enum Required {MAX, MIN}
	public enum Representation {NORMALIZED, SCALE, PERCENTUAL}
	public enum Type {REGRESSION, CLASSIFICATION}
	public enum Info {ERROR_PREDICTION, RELATION_DATA}
	
	private Vector<AbsMetric> metricsEvaluation = new Vector<AbsMetric>();
	public AbsDataset trainingSet;
	public AbsEvaluation evaluator;
	public AbsClassifier scheme;
	
	public MetricsCollection(AbsDataset trainingSet, AbsEvaluation eval){
		CCMetric cc = new CCMetric();
		metricsEvaluation.add(cc);
		RMSEMetric rmse = new RMSEMetric();
		metricsEvaluation.add(rmse);
		RRSEMetric rrse = new RRSEMetric();
		metricsEvaluation.add(rrse);
		MAEMetric mae = new MAEMetric();
		metricsEvaluation.add(mae);
		RAEMetric rae = new RAEMetric();
		metricsEvaluation.add(rae);
		COMBMetric comb = new COMBMetric();
		metricsEvaluation.add(comb);
		ACCMetric acc = new ACCMetric();
		metricsEvaluation.add(acc);
		KAPMetric kap = new KAPMetric();
		metricsEvaluation.add(kap);
		ROCMetric roc = new ROCMetric();
		metricsEvaluation.add(roc);
		RECALLMetric recall = new RECALLMetric();
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
	
	public Vector<AbsMetric> association(Representation rep){
		
		Vector<AbsMetric> result = new Vector<AbsMetric>();
		for(AbsMetric m: metricsEvaluation){
			result.addAll(m.associationRep(rep));
		}
		for(AbsMetric m:result)
			System.out.println(m.getID());
		return result;
	}
	
	
	public abstract Double calculateCC(Object evaluation) throws Exception;
	public abstract Double calculateRMSE(Object evaluation) throws Exception;
	public abstract Double calculateMAE(Object evaluation) throws Exception;
	public abstract Double calculateRAE(Object evaluation) throws Exception;
	public abstract Double calculateRRSE(Object evaluation) throws Exception;
	public abstract Double calculateACC(Object evaluation) throws Exception;
	public abstract Double calculateKAP(Object evaluation) throws Exception;
	public abstract Double calculateROC(Object evaluation) throws Exception;
	public abstract Double calculateRECALL(Object evaluation) throws Exception;

}

