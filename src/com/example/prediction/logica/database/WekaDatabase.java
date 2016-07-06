package com.example.prediction.logica.database;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;

import com.example.prediction.logica.Config;
import com.example.prediction.logica.individual.Individual;
import com.example.prediction.logica.individual.WekaIndividual;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.instance.RemoveRange;


public class WekaDatabase extends AbsDatabase {

	private Instances trainingSet;

	public WekaDatabase() {
		// TODO Auto-generated constructor stub
		database = new Vector<Individual>();
	}
	
	public WekaDatabase(WekaDatabase wd){
		this.trainingSet=(Instances)wd.getDatabaseImplementation();
		this.database = wd.getIndividuals();
		this.classIndex = wd.classIndex;
	}
	
	public WekaDatabase(Instances instances){
		trainingSet = instances;
		Enumeration<Instance> enDatabase = trainingSet.enumerateInstances();
		while (enDatabase.hasMoreElements()) {
			Instance i = enDatabase.nextElement();
			WekaIndividual wi = new WekaIndividual(i);
			database.addElement(wi);
		}
	}

	@Override
	public void parseFile(File file) {
		// TODO Auto-generated method stub
		CSVLoader cvsloader = new CSVLoader();
		try {
			cvsloader.setSource(file);
			trainingSet = cvsloader.getDataSet();
			Enumeration<Instance> enDatabase = trainingSet.enumerateInstances();
			while (enDatabase.hasMoreElements()) {
				Instance i = enDatabase.nextElement();
				WekaIndividual wi = new WekaIndividual(i);
				database.addElement(wi);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Object getDatabaseImplementation() {
		// TODO Auto-generated method stub
		return trainingSet;
	}

	@Override
	public void addData(File file) {
		// TODO Auto-generated method stub
		CSVLoader cvsloader = new CSVLoader();
		try {
			cvsloader.setSource(file);
			trainingSet.addAll(cvsloader.getDataSet());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		super.addData(file);

	}

	@Override
	public void addAttribute(String name) {
		// TODO Auto-generated method stub
		Attribute at = new Attribute(name);
		trainingSet.insertAttributeAt(at, trainingSet.numAttributes());
		super.addAttribute(name);
	}
	
	/*Sil*/
	

	public WekaDatabase(File file) throws Exception{
		super(file);
	}

	public File convertFile(File file) throws Exception {
		// TODO Auto-generated method stub
		Instances data = null;
		if(file.exists()){
			if(file.canRead()){
				CSVLoader loader = new CSVLoader();	//-M ? -E ",'
				
				loader.setFieldSeparator(",");
				loader.setSource(file);
				data = loader.getDataSet();
			}
		}		
		// save ARFF
       ArffSaver saver = new ArffSaver();
       saver.setInstances(data);
       saver.setFile(new File(Config.DIR_EXTERNAL_STORAGE +"/dataset.arff"));
       saver.writeBatch();
       
       return new File(Config.DIR_EXTERNAL_STORAGE +"/dataset.arff");
	}
	@Override
	public void setClassIndex(int classIndex) {
		// TODO Auto-generated method stub
		trainingSet.setClassIndex(classIndex);
	}
	@Override
	public int numAttributes() {
		// TODO Auto-generated method stub
		return trainingSet.numAttributes();
	}
	@Override
	public int numInstances() {
		// TODO Auto-generated method stub
		return trainingSet.numInstances();
	}
	@Override
	public void removeInstances(int first, int last) throws Exception  {
		// TODO Auto-generated method stub
		String[] options = new String[2];
		options[0] = "-R";
		options[1] = first+"-"+last;
		RemoveRange remove = new RemoveRange();
		remove.setInputFormat(trainingSet);
		remove.setOptions(options);
		remove.getOptions();
		trainingSet.numInstances();
		trainingSet = Filter.useFilter(trainingSet, remove);
	}
	@Override
	public Double getInstanceValue(int instance, int classIndex) {
		// TODO Auto-generated method stub
		return trainingSet.instance(instance).value(classIndex);
	}
	@Override
	public String getAttribute(int attribute) {
		// TODO Auto-generated method stub
		return trainingSet.attribute(attribute).name(); 
	}
	
	@Override
	public int getClassIndex() {
		// TODO Auto-generated method stub
		return trainingSet.classIndex();
	}
	@Override
	public void convertInstancesObject(File fileInstances) throws Exception {
		// TODO Auto-generated method stub
		trainingSet = new Instances(new FileReader(fileInstances));
	}

	@Override
	public AbsDatabase newInstance() {
		// TODO Auto-generated method stub
		return new WekaDatabase();
	}
	
	public AbsDatabase getNewDatasetByRemove(int first, int last) throws Exception{
		WekaDatabase result = (WekaDatabase) newInstance();
		result.classIndex = this.classIndex;
		result.trainingSet = this.trainingSet.stringFreeStructure();
		result.trainingSet.addAll(this.trainingSet);
		result.removeInstances(first,last);
		return result;
	}
	
}
