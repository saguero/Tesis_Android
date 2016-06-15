package com.example.prediction.logica;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.instance.RemoveRange;

public class DatasetWeka extends AbsDataset {
	
	public DatasetWeka(){
		super();
	}
	
	public DatasetWeka(File file) throws Exception{
		super(file);
	}
	
	public File convertFile(File file) throws Exception {
		// TODO Auto-generated method stub
		String prueba = "";
		CSVLoader loader = new CSVLoader();
		loader.setSource(file);
		Instances data = loader.getDataSet();
		
		// save ARFF
       ArffSaver saver = new ArffSaver();
       saver.setInstances(data);
       saver.setFile(new File(Config.DIR_EXTERNAL_STORAGE +"\\dataset.arff"));
       saver.writeBatch();
       
       return new File(Config.DIR_EXTERNAL_STORAGE +"\\dataset.arff");
	}
	@Override
	public void setClassIndex(int classIndex) {
		// TODO Auto-generated method stub
		((Instances) trainingSet).setClassIndex(classIndex);
	}
	@Override
	public int numAttributes() {
		// TODO Auto-generated method stub
		return ((Instances) trainingSet).numAttributes();
	}
	@Override
	public int numInstances() {
		// TODO Auto-generated method stub
		return ((Instances) trainingSet).numInstances();
	}
	@Override
	public Object removeInstances(int first, int last) throws Exception {
		// TODO Auto-generated method stub
		String[] options = {"-R", ""+first+"-"+last};
		RemoveRange remove = new RemoveRange();
		remove.setOptions(options);
		return Filter.useFilter((Instances) trainingSet, remove);	
	}
	@Override
	public Double getInstanceValue(int instance, int classIndex) {
		// TODO Auto-generated method stub
		return ((Instances)trainingSet).instance(instance).value(classIndex);
	}
	@Override
	public String getAttribute(int attribute) {
		// TODO Auto-generated method stub
		return ((Instances) trainingSet).attribute(attribute).name(); 
	}
	
	@Override
	public int getClassIndex() {
		// TODO Auto-generated method stub
		return ((Instances) trainingSet).classIndex();
	}
	@Override
	public void convertInstancesObject(File fileInstances) throws Exception {
		// TODO Auto-generated method stub
		trainingSet = new Instances(new FileReader(fileInstances));
	}
}