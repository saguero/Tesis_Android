package com.example.prediction.logica.metrics;

import java.util.Vector;

import com.example.prediction.logica.database.AbsDatabase;
import com.example.prediction.logica.metrics.collection.AbsMetricsCollection.Info;
import com.example.prediction.logica.metrics.collection.AbsMetricsCollection.Representation;
import com.example.prediction.logica.metrics.collection.AbsMetricsCollection.Required;
import com.example.prediction.logica.metrics.collection.AbsMetricsCollection.Type;
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
		
		public void configMetricModes(AbsDatabase database, AbsModeler modeler){
			this.database = database;
			this.modeler = modeler;
			configurateTrainingMode(database, modeler);
			configurateCVMode(database, modeler);
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
		
		public Double calculateNormalized(int mode) throws Exception{
			
			if(representation.equals(Representation.PERCENTUAL))
				return calculate(mode)/100;
			if(representation.equals(Representation.NORMALIZED))
				return calculate(mode);
			if(representation.equals(Representation.SCALE))
				return calculate(mode);
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
		
		public abstract Double calculate(int mode) throws Exception;
		public abstract void configurateTrainingMode(AbsDatabase database, AbsModeler scheme);		//El database que le pasamos es el training set
		public abstract void configurateCVMode(AbsDatabase database, AbsModeler scheme);			//El dataset que le pasamos es el que se usa para hacer CV 
	
}
