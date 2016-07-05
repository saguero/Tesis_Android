package com.example.prediction.logica.metrics;

import java.util.Vector;

import com.example.prediction.logica.libraries.AbsLibrary;
import com.example.prediction.logica.metrics.AbsMetricsEvaluation.Info;
import com.example.prediction.logica.metrics.AbsMetricsEvaluation.Representation;
import com.example.prediction.logica.metrics.AbsMetricsEvaluation.Required;
import com.example.prediction.logica.metrics.AbsMetricsEvaluation.Type;

public abstract class AbsMetric {
	
		private int ID;
		private Required required;
		private Representation representation;
		private Type type;
		private Info info;
		private boolean accept=false;
		
		protected AbsLibrary library;

		AbsMetric(int ID, Required req, Representation rep, Type t, Info i, AbsLibrary lib) {
			this.ID = ID;
			required = req;
			representation = rep;
			type = t; 
			info = i;
			library = lib;
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
		
		boolean isInfo(Info info){
			return this.info.equals(info);
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
			if(representation.equals(Representation.SCALE))
				return calculate(evaluation);
			return null;
		}
				
		Vector<AbsMetric> associationRep(Representation rep){	//ESTE METODO SIRVE PARA LAS TRES REP!
			Vector<AbsMetric> result = new Vector<AbsMetric>();
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
