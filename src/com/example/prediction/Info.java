package com.example.prediction;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;

import org.afree.chart.AFreeChart;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;

import com.example.prediction.graphic.BarGraphics;
import com.example.prediction.graphic.ChartView;
import com.example.prediction.graphic.LineGraphics;
import com.example.prediction.logica.AbsClassifier;
import com.example.prediction.logica.AbsDataset;
import com.example.prediction.logica.AbsEvaluation;
import com.example.prediction.logica.ClassEstructure.LibraryClasses;
import com.example.prediction.logica.Config;
import com.example.prediction.logica.ClassEstructure;

public class Info {
	static int IMG_LC_serialId = 0;
	static int IMG_EP_serialId = 0;
	static int IMG_SC_serialId = 0;
	
	static Vector<Bitmap> images_learningCurve = new Vector<Bitmap>();
	static Vector<Bitmap> images_errorPrediction = new Vector<Bitmap>();
	static Vector<Bitmap> images_schemesComparator = new Vector<Bitmap>();
	
	static CharSequence[] atts; 
	static CharSequence[] libs;
	static CharSequence[] filesDataset;
	static CharSequence[] schemes;								//VER ESTO!!
	static String[] configureItems = {	"Select library",
										"Select File Dataset",
										"Select predicted attribute",
										"Select schemes"};
	static AbsClassifier bestScheme;
	static int attributeSelected;
	static LibraryClasses librarySelected;
	static File fileDatasetSelected;
	static AbsDataset trainingSet;
	static Vector<AbsClassifier> schemesSelected = new Vector<AbsClassifier>();
	static ClassEstructure classEstructure;
	
	public Info() {
		setLibraries();
		setListFilesDataset();
	}
	
	public void setLibraries(){
		classEstructure = new ClassEstructure();
		libs = classEstructure.getListLibraries();
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
		Vector<AbsClassifier> Listschemes = librarySelected.getListSchemes();
		schemes = new CharSequence[Listschemes.size()]; 
		for(int i = 0; i < schemes.length; i++){
			schemes[i] = Listschemes.elementAt(i).getName();
		}
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
		librarySelected = classEstructure.getLibrary(ID);
	}
	
	public void setFileDatasetSelected(String name) throws Exception{			
		fileDatasetSelected = new File(Config.DIR_EXTERNAL_STORAGE + name);
		trainingSet = getLibrarySelected().getDatasetObject();
		File aux = trainingSet.convertFile(fileDatasetSelected);
		trainingSet.convertInstancesObject(aux);
		setListAttributes(trainingSet);
	}
	
	public void setAttributeSelected(int att){
		attributeSelected = att;
		trainingSet.setPredictedAtt(attributeSelected);
	}
	
	public void setSchemesSelected(Vector<Integer> schemes){		//NO EXISTE UN METODO QUE RETORNE LOS SCHEMES
		Vector<AbsClassifier> aux = librarySelected.getListSchemes();
		for(Integer s:schemes)
			schemesSelected.add(aux.elementAt(s));
		
	}
	
	public AbsDataset getDatasetSelected(){
		return trainingSet;
	}
	
	public LibraryClasses getLibrarySelected(){		
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

	public Bitmap generateImageLearningCurve(Context context,AbsClassifier scheme) throws Exception {
		// TODO Auto-generated method stub	
		
		AbsEvaluation evaluator = this.getLibrarySelected().getEvaluationObject();
		
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

	public Vector<Bitmap> generateImagesSchemesComparator(Context context) throws Exception {
		// TODO Auto-generated method stub
		
		Vector<AbsClassifier> listschemes = getListSchemesSelected();
		
		BarGraphics barchart = new BarGraphics(context);
		barchart.setSeries(listschemes);
		AFreeChart chart1 = barchart.graphedErrorPredictionNormalized(getDatasetSelected(), getLibrarySelected().getEvaluationObject(),
				getLibrarySelected().getMetricsEvaluationObject());
		
		ChartView chartView = new ChartView(context, Config.Graphic.GRAPHIC_TYPE_BAR, chart1 );
        chartView.drawChart(chart1);
        
        chartView.measure(chartView.getWidth(), chartView.getHeight());
        chartView.setDrawingCacheEnabled(true);
        chartView.buildDrawingCache();
        images_schemesComparator.add( ((BitmapDrawable) chartView.getDrawable()).getBitmap() );
	
		
		AFreeChart chart2 = barchart.graphedErrorPredictionScale(getDatasetSelected(), getLibrarySelected().getEvaluationObject(),
				getLibrarySelected().getMetricsEvaluationObject());
		chartView = new ChartView(context, Config.Graphic.GRAPHIC_TYPE_BAR, chart2 );
        chartView.drawChart(chart2);
        chartView.buildDrawingCache();
        images_schemesComparator.add( ((BitmapDrawable) chartView.getDrawable()).getBitmap() );	 
	
		AFreeChart chart3 = barchart.graphedRelationData(getDatasetSelected(), getLibrarySelected().getEvaluationObject(),
				getLibrarySelected().getMetricsEvaluationObject());
		chartView = new ChartView(context, Config.Graphic.GRAPHIC_TYPE_BAR, chart3 );
        chartView.drawChart(chart3);
        chartView.buildDrawingCache();
        images_schemesComparator.add( ((BitmapDrawable) chartView.getDrawable()).getBitmap() );	 
	
		return images_schemesComparator;
	}
	
	public void setBestScheme(AbsClassifier scheme){
		bestScheme = scheme;
	}

	public AbsClassifier getBestScheme() {				//AL CUAL SE LE APLICA LA OPTIMIZACION DE LOS PARAMETROS
		// TODO Auto-generated method stub
		return bestScheme;
	}
	

	/*
	 * DEBERIAN ESTAR LAS SUGERENCIAS, AYUDAS ...
	 * ESTO ES RESPECTO A LOS SCHEMES, LOS PARAMETROS (LEARNING CURVE) --> FALTA LA ACTIVIDAD DE LA AYUDA SOBRE LEARNING CURVE
	 * 
	 */

}
