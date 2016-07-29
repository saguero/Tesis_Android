/*package main;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;

import models.AbsWekaClassifier;
import weka.attributeSelection.CfsSubsetEval;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.LinearRegression;
import weka.classifiers.meta.AttributeSelectedClassifier;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.meta.MultiScheme;
import weka.classifiers.xml.XMLClassifier;
import weka.core.Attribute;
import weka.core.Instances;
import weka.core.SelectedTag;
import weka.core.Tag;
import weka.filters.Filter;
import weka.filters.MultiFilter;
import weka.filters.supervised.attribute.NominalToBinary;
import weka.filters.unsupervised.attribute.RemoveType;
import weka.filters.unsupervised.attribute.StringToNominal;

public class MachineLearning {
	protected Vector<AbsWekaClassifier> classifiers = new Vector<AbsWekaClassifier>();
	private Classifier optimizerClassifier = null;
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
				nomAtts.add(att);
		}
		
		for(int at:nomAtts){
			double[] values = new double[numInstances];
			String name = dataset.attribute(at).name();
			for(int j=0;j<numInstances;j++){
				values[j] = new Double(dataset.instance(j).value(at));
			}
			dataset.deleteAttributeAt(at);
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
		for (AbsWekaClassifier ac: classifiers){
			optimizers[index] = ac.optimizingParams(filteredInstances); 		
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
		eTest.crossValidateModel(lr, trainingSet, 10, null, new Random(1));
		
		
		//OPCION 4: PERCENTAGE SPLIT 
	
		boolean preservedOrder = true;
		if(!preservedOrder)
			trainingSet.randomize(new java.util.Random(0));
		
		int percent = 60; 
		int trainSize = (int) Math.round(trainingSet.numInstances() * percent / 100);
		int testSize = trainingSet.numInstances() - trainSize;
		Instances train = new Instances(trainingSet, 0, trainSize);
		Instances test = new Instances(trainingSet, trainSize, testSize);
		
		
	}
	
	
}
*/