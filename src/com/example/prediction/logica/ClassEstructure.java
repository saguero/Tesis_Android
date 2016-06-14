package com.example.prediction.logica;



public class ClassEstructure {
	
	public abstract class LibraryClasses {
		
		String ID;											
		
		AbsDataset trainingSet;
		AbsEvaluation evaluator;					
		AbsMetricsEvaluation metricsEvaluation;	
		AbsClassifier schemes;								
		
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
		
		public AbsClassifier getSchemesObject(){
			return schemes;
		}
		
		public abstract void setDatasetObject();
		public abstract void setEvalutionObject();
		public abstract void setMetricsEvaluationObject();
		public abstract void setSchemesObject();	
	}
	
	public class WekaLibrary extends LibraryClasses{

		public WekaLibrary(String ID) {
			super(ID);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void setDatasetObject() {
			// TODO Auto-generated method stub
			this.trainingSet = new DatasetWeka();
		}

		@Override
		public void setEvalutionObject() {
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
		public void setSchemesObject() {
			// TODO Auto-generated method stub
			
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
