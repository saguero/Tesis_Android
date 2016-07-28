package com.example.prediction;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;

import org.afree.chart.AFreeChart;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

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
	static final int defaultValue = 0;
	static int IMG_LC_serialId = defaultValue;
	static int IMG_EP_serialId = defaultValue;
	static int IMG_SC_serialId = defaultValue;
	
	static Vector<Bitmap> images_learningCurve = new Vector<Bitmap>();
	static Vector<Bitmap> images_errorPrediction = new Vector<Bitmap>();
	static Vector<Bitmap> images_schemesComparator = new Vector<Bitmap>();
	
	static CharSequence[] atts; 
	static CharSequence[] libs;
	static CharSequence[] filesDataset;
	static CharSequence[] schemes;
	
	
	static int attributeSelected;
	static LibraryClasses librarySelected;
	static File fileDatasetSelected;
	static AbsDataset trainingSet;
	static Vector<AbsClassifier> schemesSelected = new Vector<AbsClassifier>();
	static ClassEstructure classEstructure;
	static int typePrediction;
	
	static AbsClassifier bestScheme;
	static Vector<AbsClassifier> filteredBestSchemes = new Vector<AbsClassifier>();
	
	
	public Info() {
		setLibraries();
		setTypePrediction(0);
	}
	

	/*	SET OPTIONS TO SELECT	*/
	
	public void setLibraries(){
		classEstructure = new ClassEstructure();
		libs = classEstructure.getListLibraries();
	}
	
	public void setListFilesDataset(Context context){
		File dir = new File(Config.InitialSettings.getDirWorking());
        File[] files =  dir.listFiles();
        Vector<CharSequence> aux = new Vector<CharSequence>();
        for (int f = 0; f < files.length; f++) {
        	if(files[f].isFile() && files[f].getAbsolutePath().toString().endsWith(context.getString(Config.InitialSettings.getFormatDataset())))
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
	
	
	/*	GET OPTIONS TO SELECT	*/
	
	public Integer[] getConfigureItems(){
		return Config.AppSettings.TITLE_CONFIGURES_ITEMS;
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

	
										/*	GRAPHIC IMAGES	*/
	
	/*	GENERATE IMAGE	*/
	
	public Bitmap generateImageLearningCurve(Context context, BitmapDrawable image) throws Exception {
		// TODO Auto-generated method stub	
		
		AbsEvaluation evaluator = this.getLibrarySelected().getEvaluationObject();
		Vector<AbsClassifier> schemes = new Vector<AbsClassifier>(); 
		schemes.add(this.getBestScheme());
		
		LineGraphics linechart = new LineGraphics(context,image);
		AFreeChart chart = linechart.graphedLearningCurve(trainingSet, evaluator, schemes);
        ChartView chartView = new ChartView(context, Config.Graphic.GRAPHIC_TYPE_LINE, chart );
        chartView.drawChart(chart);
        return ( (BitmapDrawable) chartView.getDrawable()).getBitmap();	 
	}
	
	public Bitmap generateImageErrorPrediction(Context context, BitmapDrawable image) throws Exception {
		// TODO Auto-generated method stub
		
		AbsEvaluation evaluator = this.getLibrarySelected().getEvaluationObject();
		Vector<AbsClassifier> schemes = new Vector<AbsClassifier>(); 
		schemes.add(this.getBestScheme());
		
		LineGraphics linechart = new LineGraphics(context,image);
		AFreeChart chart = linechart.graphedErrorPrediction(trainingSet, evaluator, schemes);
        ChartView chartView = new ChartView(context, Config.Graphic.GRAPHIC_TYPE_LINE, chart );
        chartView.drawChart(chart);
        return ( (BitmapDrawable) chartView.getDrawable()).getBitmap();	 
	}

	public Vector<Bitmap> generateImagesSchemesComparator(Context context, BitmapDrawable image) throws Exception {
		// TODO Auto-generated method stub
		
		Vector<AbsClassifier> listschemes = getBestSchemes();
		
		BarGraphics barchart = new BarGraphics(context,image);
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
	
	/* SAVE IMAGES	*/
	
	private boolean persist(Context context, Bitmap bitmap, String path, String name){
		File appDirectory = new File(path );
		if(!appDirectory.exists()){
			appDirectory.mkdir();
		}
		File archivo = new File(path + "/" + name);
		boolean result = false;
		
		FileOutputStream out = null;
		try {
		    out = new FileOutputStream(archivo);
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
	
	public void saveLearningCurveImage(Context context, Bitmap img){
		images_learningCurve.add(0,img);
		String name = "LearningCurve_" + getFileDatasetSelected().getName() + "_" + this.getBestScheme().getName() + "_" + IMG_LC_serialId + ".png";
		
		String path = 	Config.InitialSettings.getDirWorking() +
				context.getString(Config.InitialSettings.SUBDIR_APP) +
				context.getString(Config.InitialSettings.SUBDIR_OPT_PARAMS);
		
		if(persist(context,img, path, name))
			IMG_LC_serialId++;		
	}
	
	public void saveErrorPredictionImage(Context context,Bitmap img){
		images_errorPrediction.add(0,img);
		
		String path = 	Config.InitialSettings.getDirWorking() +
				context.getString(Config.InitialSettings.SUBDIR_APP) +
				context.getString(Config.InitialSettings.SUBDIR_OPT_PARAMS);
		
		String name = "ErrorPrediction_" + getFileDatasetSelected().getName() + "_" + this.getBestScheme().getName() + "_" + IMG_EP_serialId + ".png";
		if(persist(context,img, path, name))
			IMG_EP_serialId++;		
	}
	
	public boolean saveSchemesComparatorImage(Context context,Bitmap img){
		images_schemesComparator.add(0,img);
		String name = "SchemesComparator_" +  getFileDatasetSelected().getName() + "_" + IMG_SC_serialId + ".png";
		
		String path = 	Config.InitialSettings.getDirWorking() +
				context.getString(Config.InitialSettings.SUBDIR_APP) +
				context.getString(Config.InitialSettings.SUBDIR_OPT_SCHEMES);
		
		if(persist(context,img, path, name)) {
			IMG_SC_serialId++;	
			return true;
		}
		return false;
	}
	
	/*	GET IMAGES		*/
	
	public Vector<Bitmap> getSchemesComparatorImages(){
		return images_schemesComparator;
	}
	
	public Vector<Bitmap> getLearningCurveImages(){
		return images_learningCurve;
	}
	
	public Vector<Bitmap> getErrorPredictionImages(){
		return images_errorPrediction;
	}
	
	
												/*	OPTIONS SELECTED	*/
	/*	SET OPTIONS SELECTED	*/
	
	public void setLibrarySelected(String ID){
		librarySelected = classEstructure.getLibrary(ID);
	}
	
	public void setFileDatasetSelected(String name, String destination) throws Exception{			
		fileDatasetSelected = new File(Config.InitialSettings.getDirWorking() + name);
		trainingSet = getLibrarySelected().getDatasetObject();
		File aux = trainingSet.convertFile(fileDatasetSelected, destination );
		trainingSet.convertInstancesObject(aux);
		setListAttributes(trainingSet);
	}
	
	public void setAttributeSelected(int att){
		attributeSelected = att;
		trainingSet.setPredictedAtt(attributeSelected);
	}
	
	
	public void setSchemesSelected(Vector<Integer> schemes){
		schemesSelected.removeAllElements();
		Vector<AbsClassifier> aux = librarySelected.getListSchemes();
		for(Integer s:schemes)
			schemesSelected.add(aux.elementAt(s));
		
	}
	
	/*	GET OPTIONS SELECTED	*/
	
	public LibraryClasses getLibrarySelected(){		
		return librarySelected;
	}
	
	public File getFileDatasetSelected(){
		return fileDatasetSelected;
	}
	
	public AbsDataset getDatasetSelected(){
		return trainingSet;
	}
	
	public int getAttributeSelected(){
		return attributeSelected;
	}
	
	public Vector<AbsClassifier> getListSchemesSelected(){
		return schemesSelected;
	}

	
											/* PROCESSING	*/
	
	public void setFilteredBestSchemes(){							//MODIFICAR!!!!!!!!!
		filteredBestSchemes = this.getListSchemesSelected();
	}
	
	public Vector<AbsClassifier> getBestSchemes(){
		return filteredBestSchemes;
	}
	
	public void setBestScheme(AbsClassifier scheme){
		bestScheme = scheme;
	}

	public AbsClassifier getBestScheme() {				//AL CUAL SE LE APLICA LA OPTIMIZACION DE LOS PARAMETROS
		// TODO Auto-generated method stub
		return bestScheme;
	}
	
	public static int getTypePrediction() {
		// TODO Auto-generated method stub
		return typePrediction;
	}
	
	public void setTypePrediction(int type){
		typePrediction = type;
	}
	
	
	public Vector<Integer> getListSchemesHandlerData(AbsDataset dataset){
		return librarySelected.getSchemesHandleData(dataset);	
	}
	
	public Vector<Integer> getListAttHandlerPrediction(AbsDataset dataset){
		Vector<String> types = null;
		switch(typePrediction){
		case Config.InitialSettings.CODE_REGRESSION:
			types = librarySelected.getNumericalTypes();
			break;
		case Config.InitialSettings.CODE_CLASSIFICATION:
			types = librarySelected.getCategoricalTypes();
			break;
		case Config.InitialSettings.CODE_CLUSTERING:
			
		}

		Vector<Integer> result = new Vector<Integer>();
		for(int index = 0; index < dataset.numAttributes(); index++){
			if(types.contains(dataset.getTypeAttribute(index)))
				result.add(index);		
		}
		return result;
	}
	
	
	/*
	 * DEBERIAN ESTAR LAS SUGERENCIAS, AYUDAS ...
	 * ESTO ES RESPECTO A LOS SCHEMES
	 * 
	 */

}
