package com.example.prediction;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;

import org.afree.chart.AFreeChart;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import com.example.prediction.graphic.ChartView;
import com.example.prediction.graphic.LineGraphics;
import com.example.prediction.logica.AbsClassifier;
import com.example.prediction.logica.Config;
import com.example.prediction.logica.libraries.AbsLibrary;
import com.example.prediction.logica.libraries.LibrariesCollection;
import com.example.prediction.logica.database.AbsDataset;
import com.example.prediction.logica.evaluation.AbsEvaluation;

public class Info {
	static int IMG_LC_serialId = 0;
	static int IMG_EP_serialId = 0;
	static int IMG_SC_serialId = 0;
	
	Vector<Bitmap> images_learningCurve = new Vector<Bitmap>();
	Vector<Bitmap> images_errorPrediction = new Vector<Bitmap>();
	Vector<Bitmap> images_schemesComparator = new Vector<Bitmap>();
	
	CharSequence[] atts; 
	CharSequence[] libs;
	CharSequence[] filesDataset;
	CharSequence[] schemes = {};
	String[] configureItems = {	"Select library",
										"Select File Dataset",
										"Select predicted attribute",
										"Select schemes"};
	
	int attributeSelected;
	AbsLibrary librarySelected;
	File fileDatasetSelected;
	AbsDataset trainingSet;
	Vector<AbsClassifier> schemesSelected;
	
	public Info() {
		setLibraries();
		setListFilesDataset();
	}
	
	public void setLibraries(){
		libs = LibrariesCollection.getListLibraries();
	}
	
	public void setListFilesDataset(){
		File dir = Environment.getExternalStorageDirectory();
        File[] files =  dir.listFiles();
        Vector<CharSequence> aux = new Vector<CharSequence>();
        for (int f = 0; f < files.length; f++) {
        	if(files[f].isFile() && files[f].getAbsolutePath().contains(Config.EXTENSION_DATASET))
        		aux.add(files[f].getName());
        }
        filesDataset = new CharSequence[aux.size()];
        int index=0;
        for(CharSequence cs:aux){
        	filesDataset[index]=cs;
        	index++;
        }
	}
	
	public void setListAttributes(AbsDataset trainingSet){
		Vector<String> aux = trainingSet.getNamesAttributes();
		atts = new CharSequence[aux.size()];
		for(int index = 0; index < aux.size(); index++)
			atts[index] = aux.elementAt(index);
	}
	
	public void setListSchemes(){
		
	}
	
	public String[] getConfigureItems(){
		return configureItems;
	}
	
	public CharSequence[] getListLibraries(){
		return libs;
	}
	
	public CharSequence[] getListFilesDataset(){
		return filesDataset;
	}
	
	public CharSequence[] getListAttributes(){
		return atts;
	}
	
	public CharSequence[] getListSchemes(){
		return schemes;
	}

	public void saveLearningCurveImage(Bitmap img){
		images_learningCurve.add(0,img);
		String name = "learning_curve" + IMG_LC_serialId;
		if(persist(img, name))
			IMG_LC_serialId++;		
	}
	
	public void saveErrorPredictionImage(Bitmap img){
		images_errorPrediction.add(0,img);
		String name = "error_prediction" + IMG_EP_serialId;
		if(persist(img, name))
			IMG_EP_serialId++;		
	}
	
	public void saveSchemesComparatorImage(Bitmap img){
		images_schemesComparator.add(0,img);
		String name = "schemes_comparator" + IMG_SC_serialId;
		if(persist(img, name))
			IMG_SC_serialId++;		
	}
	
	public Vector<Bitmap> getSchemesComparatorImages(){
		return images_schemesComparator;
	}
	
	public Vector<Bitmap> getLearningCurveImages(){
		return images_learningCurve;
	}
	
	public Vector<Bitmap> getErrorPredictionImages(){
		return images_errorPrediction;
	}
	
	
	private boolean persist(Bitmap bitmap, String name){
		
		boolean result = false;
		String path = Config.DIR_EXTERNAL_STORAGE  + name;
		
		FileOutputStream out = null;
		try {
		    out = new FileOutputStream(path);
		    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
		    result = true;  
		} 
		catch (Exception e) {  }
		finally {
		    try  {
		        if (out != null) 
		            out.close(); 
		    } 
		    catch (IOException e) { }
		}
		return result;
	}
	
	
	public void setLibrarySelected(String ID){
		librarySelected = LibrariesCollection.getLibrary(ID);
	}
	
	public void setFileDatasetSelected(String name) throws Exception{			
		fileDatasetSelected = new File(Config.DIR_EXTERNAL_STORAGE + name);
		fileDatasetSelected.getAbsolutePath();
		trainingSet = getLibrarySelected().getDatasetObject();
		File aux = trainingSet.convertFile(fileDatasetSelected);
		trainingSet.convertInstancesObject(aux);
		setListAttributes(trainingSet);
	}
	
	public void setAttributeSelected(int att){
		attributeSelected = att;
	}
	
	public void setSchemesSelected(Vector<Integer> schemes){		//NO EXISTE UN METODO QUE RETORNE LOS SCHEMES
		
	}
	
	public AbsDataset getDatasetSelected(){
		return trainingSet;
	}
	
	public AbsLibrary getLibrarySelected(){		
		return librarySelected;
	}
	
	public int getAttributeSelected(){
		return attributeSelected;
	}
	
	public File getFileDatasetSelected(){
		return fileDatasetSelected;
	}
	
	public Vector<AbsClassifier> getListSchemesSelected(){
		return schemesSelected;
	}

	public Bitmap generateImageLearningCurve(Context context) throws Exception {
		// TODO Auto-generated method stub	
		
		AbsEvaluation evaluator = this.getLibrarySelected().getEvaluationObject();
		AbsClassifier scheme = null;				//-->MODIFICAR!
		
		LineGraphics linechart = new LineGraphics();
		AFreeChart chart = linechart.graphedLearningCurve(trainingSet, evaluator, scheme);
        ChartView chartView = new ChartView(context, Config.Graphic.GRAPHIC_TYPE_LINE, chart );
        chartView.drawChart(chart);
        
        return chartView.getDrawingCache();	 
	}
	
	public Bitmap generateImageErrorPrediction(Context context) throws Exception {
		// TODO Auto-generated method stub
		
		AbsEvaluation evaluator = this.getLibrarySelected().getEvaluationObject();
		LineGraphics linechart = new LineGraphics();
		AFreeChart chart = linechart.graphedErrorPrediction(trainingSet, evaluator);
        ChartView chartView = new ChartView(context, Config.Graphic.GRAPHIC_TYPE_LINE, chart );
        chartView.drawChart(chart);
        
        return chartView.getDrawingCache();	 
	}
	

	/*
	 * DEBERIAN ESTAR LAS SUGERENCIAS, AYUDAS ...
	 * ESTO ES RESPECTO A LOS SCHEMES, LOS PARAMETROS (LEARNING CURVE) --> FALTA LA ACTIVIDAD DE LA AYUDA SOBRE LEARNING CURVE
	 * 
	 */

}
