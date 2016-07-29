package com.example.prediction.logica.metrics.abstracts;

import java.util.Vector;

import com.example.prediction.logica.Config;
import com.example.prediction.logica.database.AbsDatabase;
import com.example.prediction.logica.metrics.abstracts.Type;
import com.example.prediction.logica.models.AbsModeler;

public abstract class AbsMetric {
	
		private int ID;
		private Required required;
		private Representation representation;
		private Type type;
		private Info info;
		private boolean accept=false;
		private String name;
		
		protected AbsDatabase database;
		protected AbsModeler modeler;

		AbsMetric(int ID, Required req, Representation rep, Type t, Info i, String name) {
			this.ID = ID;
			required = req;
			representation = rep;
			type = t; 
			info = i;
			this.name = name;
		}
		
		 public Required getRequired(){
			return required;
		}
		
		 public Representation getRepresentation(){
			return representation;
		}
		
		 public Type getType(){
			return type;
		}
		
		public Info getInfo(){
			return info;
		}
		
		public int getId(){
			return ID;
		}
				
		public boolean isRep(Representation rep){	
			return representation.equals(rep);
		}
		
		public boolean isInfo(Info info){
			return this.info.equals(info);
		}
		
		boolean forRegression(){
			return type.equals(Type.REGRESSION);
		}
		
		boolean forClassification(){
			return type.equals(Type.CLASSIFICATION);
		}
		
		public Double calculateNormalized(AbsDatabase database, AbsModeler scheme, int folds) throws Exception{
			if(representation.equals(Representation.PERCENTUAL))
				return calculate(database, scheme, folds)/100;
			if(representation.equals(Representation.NORMALIZED))
				return calculate(database, scheme, folds);
			if(representation.equals(Representation.SCALE))
				return calculate(database, scheme, folds);
			return null;
		}
		
		public Double calculateNormalized(AbsDatabase database, AbsModeler scheme) throws Exception{
			if(representation.equals(Representation.PERCENTUAL))
				return calculate(database, scheme)/100;
			if(representation.equals(Representation.NORMALIZED))
				return calculate(database, scheme);
			if(representation.equals(Representation.SCALE))
				return calculate(database, scheme);
			return null;
		}
				
		public Vector<AbsMetric> associationRep(Representation rep){	//ESTE METODO SIRVE PARA LAS TRES REP!
			Vector<AbsMetric> result = new Vector<AbsMetric>();
			if(accept && representation.equals(rep))
				result.add(this);
			return result;
		}
		
		public boolean canBeNormalized(){
			return representation.equals(Representation.NORMALIZED) ||
					representation.equals(Representation.PERCENTUAL) ;
		}
		
		public String getID(){
			return name;
		}
		
		public Double calculate(AbsDatabase database, AbsModeler scheme, int folds) throws Exception{
			configurateCVMode(database, scheme, folds);
			return calculate(Config.TrainingMode.CROSS_VALIDATION_MODE);
		}
		
		public Double calculate(AbsDatabase database, AbsModeler scheme) throws Exception{
			configurateTrainingMode(database, scheme);
			return calculate(Config.TrainingMode.TRAINING_MODE);
		}
		
		public void configurateTrainingMode(AbsDatabase database, AbsModeler scheme){
			this.database=database;
			this.modeler=scheme;
			configurateTraining(database, scheme);
		}
		
		public void configurateCVMode(AbsDatabase database, AbsModeler scheme, int folds){
			this.database=database;
			this.modeler=scheme;
			configurateCV(database, scheme, folds);
		}

		public abstract Double calculate(int mode) throws Exception;
		protected abstract void configurateTraining(AbsDatabase database, AbsModeler scheme);
		protected abstract void configurateCV(AbsDatabase database, AbsModeler scheme, int folds);			
	
}
