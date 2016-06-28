package com.example.prediction.logica;

import java.util.Vector;

public class ClassEstructure {
	
	public abstract class LibraryClasses {
		
		String ID;											
		
		AbsDataset trainingSet;
		AbsEvaluation evaluator;					
		AbsMetricsEvaluation metricsEvaluation;	
		AbsClassifier scheme;
		Vector<AbsClassifier> listSchemes;								
		
		public LibraryClasses(String ID){
			this.ID =ID;
		}
		
		public String getID(){
			return ID;
		}
		
		public AbsDataset getDatasetObject(){
			return trainingSet;
		}
		
		public AbsEvaluation getEvaluationObject(){
			return evaluator;
		}
		
		public AbsMetricsEvaluation getMetricsEvaluationObject(){
			return metricsEvaluation;
		}
		
		public Vector<AbsClassifier> getListSchemes(){
			return listSchemes;
		}
		
		public AbsClassifier getSchemeObject(){
			return scheme;
		}
		
		
		public abstract void setDatasetObject();
		public abstract void setEvaluationObject();
		public abstract void setMetricsEvaluationObject();
		public abstract void setSchemeObject();
		public abstract void setListSchemes();
		
	}
	
	public class WekaLibrary extends LibraryClasses{

		public WekaLibrary(String ID) {
			super(ID);
			// TODO Auto-generated constructor stub
			setDatasetObject();
			setEvaluationObject();
			setMetricsEvaluationObject();
			setSchemeObject();
			setListSchemes();
		}

		@Override
		public void setDatasetObject() {
			// TODO Auto-generated method stub
			this.trainingSet = new DatasetWeka();
		}

		@Override
		public void setEvaluationObject() {
			// TODO Auto-generated method stub
			this.evaluator = new EvaluationWeka();
		}

		@Override
		public void setMetricsEvaluationObject() {
			// TODO Auto-generated method stub
			AbsDataset d = getDatasetObject();
			AbsEvaluation e = getEvaluationObject();
			this.metricsEvaluation = new MetricsEvaluationWeka(d, e);
		}

		@Override
		public void setSchemeObject() {
			// TODO Auto-generated method stub
			this.scheme = new ClassifierWeka();	
		}

		@Override
		public void setListSchemes() {
			// TODO Auto-generated method stub
			listSchemes = new Vector<AbsClassifier>();
			listSchemes.add(new LinearReg());
			listSchemes.add(new SimpleLinearReg());
			listSchemes.add(new Smoreg());
		}
		
	}
	
	LibraryClasses[] libraries = {new WekaLibrary("Weka")
															}; 
	
	public ClassEstructure(){
		
	}
	
	public String[] getListLibraries(){
		String[] result = new String[libraries.length];
		int index = 0;
		for(LibraryClasses lib:libraries){
			result[index] = lib.getID();
		}
		return result;	
	}
	
	public LibraryClasses getLibrary(String ID){
		LibraryClasses result = null;
		for(LibraryClasses lib:libraries){
			if(lib.getID().equals(ID))
				result = lib;
		}
		return result;
	}

	
}
