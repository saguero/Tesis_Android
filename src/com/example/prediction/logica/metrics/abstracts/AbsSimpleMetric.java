package com.example.prediction.logica.metrics.abstracts;

import java.util.Vector;

import com.example.prediction.logica.Config;
import com.example.prediction.logica.database.AbsDatabase;
import com.example.prediction.logica.models.AbsModeler;

public abstract class AbsSimpleMetric extends AbsMetric {

	protected Vector<Double> predictedValuesTraining = new Vector<Double>();
	protected Vector<Double> realValuesTraining = new Vector<Double>();

	protected Vector<Double> predictedValuesCV = new Vector<Double>();
	protected Vector<Double> realValuesCV = new Vector<Double>();

	protected Double acumulative = (double) 0;

	protected int folds = 10;

	public AbsSimpleMetric(int ID, Required req, Representation rep, Type t, Info i, String name) {
		super(ID, req, rep, t, i, name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Double calculate(int mode) {
		// TODO Auto-generated method stub
		if (mode == Config.TrainingMode.TRAINING_MODE) {
			return calculate(predictedValuesTraining, realValuesTraining);
		} else {
			return acumulative;
		}
	}

	@Override
	public void configurateTraining(AbsDatabase database, AbsModeler scheme) {
		// TODO Auto-generated method stub
		this.database = database;
		try {
			predictedValuesTraining = scheme.getPredictedValue(database);
			realValuesTraining = database.getActualValues(scheme.getIndexAttribute());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	protected void configurateCV(AbsDatabase database, AbsModeler scheme, int folds) {
		// TODO Auto-generated method stub
		int cantPerFold = database.getIndividuals().size() / folds;
		this.folds = folds;
		AbsDatabase newDatabase = null;
		AbsDatabase restDatabase = null;
		for (int i = 0; i < folds - 1; i++) {
			int inic = i * cantPerFold;
			int limit = (i + 1) * cantPerFold;
			if ((i + 1) * cantPerFold > database.getIndividuals().size()) {
				limit = database.numInstances();
			}
			newDatabase = database.subDatabase(inic, limit);
			restDatabase = database.removeSubAbsDatabase(inic, limit);
			scheme.calculateModeler(restDatabase);
			try {
				predictedValuesCV = scheme.getPredictedValue(newDatabase);
				realValuesCV = newDatabase.getActualValues(scheme.getIndexAttribute());
				acumulative += calculate(predictedValuesCV, realValuesCV);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		acumulative /= folds;
		/*
		 * try { predictedValuesCV=scheme.getPredictedValue(database);
		 * realValuesCV = database.getActualValues(scheme.getIndexAttribute());
		 * } catch (Exception e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
	}

	protected abstract Double calculate(Vector<Double> predicted, Vector<Double> reals);

}
