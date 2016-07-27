package com.example.prediction.logica;
import java.io.File;
import java.io.FileReader;

import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.instance.RemoveRange;

public class DatasetWeka extends AbsDataset {
	
	public DatasetWeka(){
		super();
	}
	
	public DatasetWeka(File file, String destination) throws Exception{
		super(file, destination);
	}
	
	public File convertFile(File file, String destination) throws Exception {
		// TODO Auto-generated method stub
		Instances data = null;
		if(file.exists()){
			if(file.canRead()){
				CSVLoader loader = new CSVLoader();
				
				loader.setFieldSeparator(",");
				loader.setSource(file);
				data = loader.getDataSet();
			}
		}	
		String name = file.getName();
		name = name.substring(0, name.lastIndexOf(".") );
		// save ARFF
       ArffSaver saver = new ArffSaver();
       saver.setInstances(data);
       File f = new File(destination +"/" + name +".arff");
       saver.setFile(f);
       saver.writeBatch();
       
       return f;
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
	public Object removeInstances(int first, int last) throws Exception  {
		// TODO Auto-generated method stub
		String[] options = new String[2];
		options[0] = "-R";
		options[1] = first+"-"+last;
		RemoveRange remove = new RemoveRange();
		remove.setInputFormat((Instances) trainingSet );
		remove.setOptions(options);
		remove.getOptions();
		((Instances) trainingSet).numInstances();
		Object result = Filter.useFilter((Instances) trainingSet, remove);
		return result;
	}
	@Override
	public Double getInstanceValue(int instance, int classIndex) {
		// TODO Auto-generated method stub
		return ((Instances)trainingSet).instance(instance).value(classIndex);
	}
	@SuppressWarnings("static-access")
	@Override
	public String getTypeAttribute(int attribute) {
		// TODO Auto-generated method stub
		return ((Instances) trainingSet).attribute(attribute).typeToString(attribute);
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

	@Override
	public AbsDataset newInstance() {
		// TODO Auto-generated method stub
		return new DatasetWeka();
	}

	@Override
	public String getNameAttribute(int index) {
		// TODO Auto-generated method stub
		return ((Instances) trainingSet).attribute(index).name();
	}
}