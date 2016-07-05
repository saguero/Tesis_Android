package com.example.prediction.logica;

import java.io.FileReader;
import java.util.Random;
import java.util.Vector;

import com.example.prediction.logica.database.WekaDatabase;
import com.example.prediction.logica.models.AbsClassifier;
import com.example.prediction.logica.models.AbsWekaClassifier;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.LinearRegression;
import weka.classifiers.meta.MultiScheme;
import weka.classifiers.xml.XMLClassifier;
import weka.core.Attribute;
import weka.core.Instances;
import weka.core.SelectedTag;
import weka.core.Tag;
import weka.filters.Filter;
import weka.filters.MultiFilter;
import weka.filters.unsupervised.attribute.RemoveType;
import weka.filters.unsupervised.attribute.StringToNominal;

public class MachineLearning {
	protected Vector<AbsClassifier> classifiers = new Vector<AbsClassifier>();
	private Instances dataset = null;
	
	
	public MachineLearning(Instances d, String predictedAtt){
		if(!(dataset.attribute(predictedAtt).isNumeric() ))
			throw new IllegalArgumentException("The predicted attribute must be Numeric");
		else {
			dataset=d;
			for(int i=0; i<dataset.numAttributes();i++){
	        	String nombre=dataset.attribute(i).name();
	       	 	if(nombre.equals(predictedAtt)){
	       	 		dataset.setClassIndex(i);
	       	 		break;
	       	 	}  	
	        }		
		}
		 		
	}
	
	
	private Instances nominalToNumeric(Instances dataset){
		int numInstances = dataset.numInstances();
		int numAtts = dataset.numAttributes();
		
		Vector<Integer> nomAtts = new Vector<Integer>();
		for(int att=0; att<numAtts; att++){
			if(dataset.attribute(att).isNominal())
				nomAtts.add(0,att);
		}
		
		for(int at:nomAtts){
			double[] values = new double[numInstances];
			String name = dataset.attribute(at).name();
			for(int j=0;j<numInstances;j++){
				values[j] = (Double) dataset.instance(j).value(at);
			}
			dataset.deleteAttributeAt(at);									//SE SUPONE QUE EL ATTCLASS NO ES NOMINAL.
			dataset.insertAttributeAt(new Attribute(name), numAtts-1);
			for(int j=0;j<numInstances;j++){
				dataset.instance(j).setValue(numAtts-1, values[j]);
			}
		}
		return dataset;	
	}
	
	private Instances applyFilter(Instances data) throws Exception{
		Vector<Filter> filtersVector = new Vector<Filter>();
		Tag[] attsType = RemoveType.TAGS_ATTRIBUTETYPE;
		
		// filters
		Filter removedate = new RemoveType();
		((RemoveType) removedate).setAttributeType(new SelectedTag(Attribute.DATE,attsType));
		filtersVector.add(removedate);
		Filter removeRel = new RemoveType();
		((RemoveType) removeRel).setAttributeType(new SelectedTag(Attribute.RELATIONAL,attsType));
		filtersVector.add(removeRel);
		
		Filter StoN = new StringToNominal();
		filtersVector.add(StoN);
		
		Instances newData = nominalToNumeric(data);
	
		Filter[] filtersArray = new Filter[filtersVector.size()];
		for(int i=0; i<filtersVector.size();i++){
			filtersArray[i]=filtersVector.elementAt(i);
		}
		MultiFilter mf = new MultiFilter();
		mf.setFilters(filtersArray);
		
		return Filter.useFilter(newData, mf);
	}
	
	public void buildModelBasic() throws Exception{
		
		int index=0;
		String[] names = new String[classifiers.size()];
		Instances filteredInstances = applyFilter(dataset);
		Classifier[] optimizers = new Classifier[classifiers.size()];
		for (AbsClassifier ac: classifiers){
			WekaDatabase wd=new WekaDatabase(filteredInstances);
			optimizers[index] = ((AbsWekaClassifier)(ac.calculateModeler(wd))).getClassifier(); 		
			names[index] = ac.getName();
			index++;
		}
		MultiScheme ms = new MultiScheme();
		ms.setClassifiers(optimizers);
		ms.buildClassifier(filteredInstances);
		
		//Documentation
		String directory = "";	// BY DEFAULT
		String separator = System.getProperty("file.separator");
		XMLClassifier xmlclassifier = new XMLClassifier();
		
		int best = ms.getBestClassifierIndex();
		String name = "Opt"+ names[best] + "_" + dataset.relationName() + ".xml";
		xmlclassifier.write(directory + separator + name, optimizers[best]);
		
	}
	

	public void evaluateModel(Instances testData) throws Exception{
		
		Instances trainingSet = new Instances(new FileReader(""));
		LinearRegression lr = new LinearRegression();
		
		lr.buildClassifier(trainingSet);
		Evaluation eTest = new Evaluation(trainingSet);
		
		//OPCION 1: USE TRAINING SET
		eTest.evaluateModel(lr, trainingSet);
		
		//OPCION 2: SUPPLIED TEST SET
		eTest.evaluateModel(lr, testData);
		
		//OPCION 3: CROSS VALIDATION
		//numFolds: can´t have more folds than instances and greater than 1
		/*
		 * For K-fold, you break the data into K-blocks. Then, for K = 1 to X, you make the Kth block the test block 
		 * and the rest of the data becomes the training data. Train, test, record and then update K. In this case, 
		 * the standard value for K is 10.
		 */
		eTest.crossValidateModel(lr, trainingSet, 10, null, new Random(1));
		
		
		//OPCION 4: PERCENTAGE SPLIT 
	
		boolean preservedOrder = true;
		if(!preservedOrder)
			trainingSet.randomize(new java.util.Random(0));
		
	/*	int percent = 60; 
		int trainSize = (int) Math.round(trainingSet.numInstances() * percent / 100);
		int testSize = trainingSet.numInstances() - trainSize;
		Instances train = new Instances(trainingSet, 0, trainSize);
		Instances test = new Instances(trainingSet, trainSize, testSize);*/
		
		
	}
	
	
	public static void outputPrediction(Instances dataset, Classifier scheme) throws Exception{
		
		int numIns = dataset.numInstances();
		double[] actual = new double[numIns];
		double[] predicted = new double[numIns];
		double[] error = new double[numIns];
				
		scheme.buildClassifier(dataset);
		
		for(int instance=0;instance<dataset.numInstances();instance++){
			
			double[] prediction=scheme.distributionForInstance(dataset.instance(instance));
			 //output predictions
	        for(int pred=0; pred<prediction.length; pred++)
	        {
	        	actual[instance] = dataset.instance(instance).value(pred);
	        	predicted[instance] = prediction[pred];
	        	error[instance] = predicted[instance] - actual[instance];
	        }
		}
		 
	}
	
	
	
}
