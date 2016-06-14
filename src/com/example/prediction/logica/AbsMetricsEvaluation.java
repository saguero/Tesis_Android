package com.example.prediction.logica;

import java.util.Vector;

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
	
	public abstract class Metric  {
		private int ID;
		private Required required;
		private Representation representation;
		private Type type;
		private Info info;
		private boolean accept=false;

		Metric(int ID, Required req, Representation rep, Type t, Info i) {
			this.ID = ID;
			required = req;
			representation = rep;
			type = t; 
			info = i;
		}
		
		 public Required getRequired(){
			return required;
		}
		
		Representation getRepresentation(){
			return representation;
		}
		
		Type getType(){
			return type;
		}
		
		public Info getInfo(){
			return info;
		}
		
		int getId(){
			return ID;
		}
				
		boolean isRep(Representation rep){	
			return representation.equals(rep);
		}
		
		
		boolean forRegression(){
			return type.equals(Type.REGRESSION);
		}
		
		boolean forClassification(){
			return type.equals(Type.CLASSIFICATION);
		}
		
		void accept(){
			accept=true;
		}
		
		public Double calculateNormalized(Object evaluation) throws Exception{
			
			if(representation.equals(Representation.PERCENTUAL))
				return calculate(evaluation)/100;
			if(representation.equals(Representation.NORMALIZED))
				return calculate(evaluation);
			return null;
		}
				
		Vector<Metric> associationRep(Representation rep){	//ESTE METODO SIRVE PARA LAS TRES REP!
			Vector<Metric> result = new Vector<Metric>();
			if(accept && representation.equals(rep))
				result.add(this);
			return result;
		}
		
		public boolean canBeNormalized(){
			
			return representation.equals(Representation.NORMALIZED) ||
					representation.equals(Representation.PERCENTUAL) ;
		}
		
		abstract Double calculate(Object evaluation) throws Exception;
		public abstract String getID();
		
	}
	
	class CC extends Metric {

		CC() {
			super(CC,Required.MAX, Representation.NORMALIZED, Type.REGRESSION, Info.RELATION_DATA);	
		}

		Double calculate(Object evaluation) throws Exception {
			return calculateCC(evaluation);
		}
		
		public String getID(){
			return "CC";
		}
	}
	
	class RMSE extends Metric {

		RMSE() {
			super(RMSE,Required.MIN, Representation.SCALE, Type.REGRESSION, Info.ERROR_PREDICTION);	
		}

		Double calculate(Object evaluation) throws Exception {
			return calculateRMSE(evaluation);
		}	
		
		public String getID(){
			return "RMSE";
		}
	}
	
	class RRSE extends Metric {

		RRSE() {
			super(RRSE,Required.MIN, Representation.PERCENTUAL, Type.REGRESSION, Info.ERROR_PREDICTION);	
		}
		
		Double calculate(Object evaluation) throws Exception {
			return calculateRRSE(evaluation);
		}
		
		public String getID(){
			return "RRSE";
		}
	}
	
	class MAE extends Metric {

		MAE() {
			super(MAE,Required.MIN, Representation.SCALE, Type.REGRESSION, Info.ERROR_PREDICTION);	
		}
		
		Double calculate(Object evaluation) throws Exception {
			return calculateMAE(evaluation);
		}
		
		public String getID(){
			return "MAE";
		}
	}
	
	class RAE extends Metric {

		RAE() {
			super(RAE,Required.MIN, Representation.PERCENTUAL, Type.REGRESSION, Info.ERROR_PREDICTION);
		}
		
		Double calculate(Object evaluation) throws Exception {
			return calculateRAE(evaluation);
		}
		
		public String getID(){
			return "RAE";
		}
		
	}
	
	class COMB extends Metric {

		COMB() {
			super(COMB,Required.MAX, Representation.NORMALIZED, Type.REGRESSION, Info.ERROR_PREDICTION);	
		}
		
		Double calculate(Object evaluation) throws Exception {
			RAE rae = new RAE();
			RRSE rrse = new RRSE();
			return  1-Math.abs(calculateCC(evaluation)) + rrse.calculateNormalized(evaluation) 
					+ rae.calculateNormalized(evaluation);
		}
		
		public String getID(){
			return "COMB";
		}
	}
	
	class ACC extends Metric {

		ACC() {
			super(ACC,Required.MAX, Representation.NORMALIZED, Type.CLASSIFICATION, Info.ERROR_PREDICTION);	
		}
		
		Double calculate(Object evaluation) throws Exception {
			return calculateACC(evaluation);							
		}
		
		public String getID(){
			return "ACC";
		}
	}
	
	class KAP extends Metric {

		KAP() {
			super(KAP,Required.MAX, Representation.NORMALIZED, Type.CLASSIFICATION, Info.ERROR_PREDICTION);	
		}
		
		Double calculate(Object evaluation) throws Exception {
			return calculateKAP(evaluation);								
		}
		
		public String getID(){
			return "KAP";
		}
	}
	
	class ROC extends Metric {

		ROC() {
			super(ROC,Required.MAX, Representation.NORMALIZED, Type.CLASSIFICATION, Info.ERROR_PREDICTION);	
		}
		
		Double calculate(Object evaluation) throws Exception {
			return calculateROC(evaluation);								
		}
		
		public String getID(){
			return "ROC";
		}
	}
	
	class RECALL extends Metric {

		RECALL() {
			super(RECALL,Required.MAX, Representation.NORMALIZED, Type.CLASSIFICATION, Info.ERROR_PREDICTION); //NO ESTOY SEGURA
		}
		
		Double calculate(Object evaluation) throws Exception {
			return calculateRECALL(evaluation);								
		}
		
		public String getID(){
			return "RECALL";
		}
	}
	
	private Vector<Metric> metricsEvaluation = new Vector<Metric>();
	public AbsDataset trainingSet;
	public AbsEvaluation evaluator;
	public AbsClassifier scheme;
	
	public AbsMetricsEvaluation(AbsDataset trainingSet, AbsEvaluation eval){
		CC cc = new CC();
		metricsEvaluation.add(cc);
		RMSE rmse = new RMSE();
		metricsEvaluation.add(rmse);
		RRSE rrse = new RRSE();
		metricsEvaluation.add(rrse);
		MAE mae = new MAE();
		metricsEvaluation.add(mae);
		RAE rae = new RAE();
		metricsEvaluation.add(rae);
		COMB comb = new COMB();
		metricsEvaluation.add(comb);
		ACC acc = new ACC();
		metricsEvaluation.add(acc);
		KAP kap = new KAP();
		metricsEvaluation.add(kap);
		ROC roc = new ROC();
		metricsEvaluation.add(roc);
		RECALL recall = new RECALL();
		metricsEvaluation.add(recall);
				
		this.trainingSet = trainingSet;
		this.evaluator = eval;
		
	}
	
	public void acceptMetric(int metric){
		for(Metric m: metricsEvaluation){
			if(m.getId() == metric)
				m.accept();
		}	
	}
	
	public Vector<Metric> association(Representation rep){
		
		Vector<Metric> result = new Vector<Metric>();
		for(Metric m: metricsEvaluation){
			result.addAll(m.associationRep(rep));
		}
		for(Metric m:result)
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

