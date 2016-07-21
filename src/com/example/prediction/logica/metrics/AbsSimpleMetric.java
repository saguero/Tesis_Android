package com.example.prediction.logica.metrics;

import java.util.Vector;

import com.example.prediction.logica.Config;
import com.example.prediction.logica.database.AbsDatabase;
import com.example.prediction.logica.metrics.collection.AbsMetricsCollection.Info;
import com.example.prediction.logica.metrics.collection.AbsMetricsCollection.Representation;
import com.example.prediction.logica.metrics.collection.AbsMetricsCollection.Required;
import com.example.prediction.logica.metrics.collection.AbsMetricsCollection.Type;
import com.example.prediction.logica.models.AbsModeler;

public abstract class AbsSimpleMetric extends AbsMetric {
	
	protected Vector<Double> predictedValuesTraining = new Vector<Double>();
	protected Vector<Double> realValuesTraining=new Vector<Double>();
	
	protected Vector<Double> predictedValuesCV = new Vector<Double>();
	protected Vector<Double> realValuesCV = new Vector<Double>();
	
	protected Double acumulative =(double) 0;

	protected static final int FOLDS = 10; 

	public AbsSimpleMetric(int ID, Required req, Representation rep, Type t, Info i, String name) {
		super(ID, req, rep, t, i, name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Double calculate(int mode) throws Exception {
		// TODO Auto-generated method stub
		if (mode==Config.TrainingMode.TRAINING_MODE){
			return calculate(predictedValuesTraining, realValuesTraining);
		}
		else{
			return acumulative*(1/(2*realValuesTraining.size()));
		}
	}

	@Override
	public void configurateTrainingMode(AbsDatabase database, AbsModeler scheme) {
		// TODO Auto-generated method stub
		this.database=database;
		try {
			predictedValuesTraining=scheme.getPredictedValue(database);
			realValuesTraining = database.getActualValues(scheme.getIndexAttribute());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void configurateCVMode(AbsDatabase database, AbsModeler scheme) {
		// TODO Auto-generated method stub
		int cantPerFold=database.getIndividuals().size()/FOLDS;
		for (int i=0; i<FOLDS-1; i++){
			AbsDatabase newDatabase=null;
			if ((i+1)*cantPerFold<database.getIndividuals().size()){
				newDatabase=database.subDatabase(i*cantPerFold, (i+1)*cantPerFold);
				scheme.calculateModeler(database.removeSubAbsDatabase(i*cantPerFold, (i+1)*cantPerFold));
				}
			else{
				newDatabase=database.subDatabase(i*cantPerFold, database.getIndividuals().size());
				scheme.calculateModeler(database.removeSubAbsDatabase(i*cantPerFold, database.getIndividuals().size()));
				}
			try {
				predictedValuesCV=scheme.getPredictedValue(newDatabase);
				realValuesCV = newDatabase.getActualValues(scheme.getIndexAttribute());
				acumulative += calculate(predictedValuesCV, realValuesCV);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	protected abstract Double calculate(Vector<Double> predicted, Vector<Double> reals);

}
