package com.example.prediction.logica;

import java.io.File;

import org.afree.graphics.SolidColor;

import android.graphics.Color;
import android.os.Environment;

public class Config {
	public static final String DIR_RESOURCES = System.getProperty("user.dir") + System.getProperty("file.separator");
	public static final File DIR_EXTERNAL_STORAGE = new File(Environment.getExternalStorageDirectory() + "/" );
	
	
	public static class Graphic {
		
		public static final int GRAPHIC_TYPE_LINE = 0;
		public static final int GRAPHIC_TYPE_BAR = 1;
		
		public static final int GRAPHIC_LINE_HEIGHT = 367;
		public static final int GRAPHIC_LINE_WIDTH = 560;
		public static final int GRAPHIC_LINE_LIMIT_INSTANCES = 300;
		public static final int GRAPHIC_LINE_INSTANCES_LEARNING_CURVE = 10;
		
		public static final float GRAPHIC_LINE_STROKE_REALVALUES = 3F;
		public static final float GRAPHIC_LINE_STROKE_BESTPRED = 3F;
		public static final float GRAPHIC_LINE_STROKE_PREDVALUES = 1.5F;
		
		public static final String GRAPHIC_LINE_LABEL_REALVALUES = "Real values";
		public static final String GRAPHIC_LINE_TITLE_CHART_EP = "Error Prediction";
		public static final String GRAPHIC_LINE_TITLE_CHART_LC = "Learning Curve";
		public static final String GRAPHIC_LINE_TITLE_AXISX = "Instances";
		public static final String GRAPHIC_LINE_TITLE_AXISY = "Measure";
		public static final String GRAPHIC_LINE_BACKGROUND_IMAGE_LC = "learning_curve.jpg";
		public static final String GRAPHIC_LINE_BACKGROUND_IMAGE_EP = "Prediction_error.png";
		public static final String GRAPHIC_LINE_LABEL_TRAINING = "TrainingSet";
		public static final String GRAPHIC_LINE_LABEL_CROSSVALIDATION = "Cross Validation";
		
		public static final SolidColor GRAPHIC_LINE_COLOR_REALVALUES = new SolidColor(Color.BLACK);
		
		public static final int GRAPHIC_BAR_HEIGHT = 400;
		public static final int GRAPHIC_BAR_WIDTH = 500;
		public static final int GRAPHIC_BAR_MAXLABELSHORIZONTAL = 5;
		
		public static final float GRAPHIC_BAR_MAXBARWIDTH = 0.05F;
		
		public static final String GRAPHIC_BAR_TITLE_CHART = "Schemes Comparator";
		public static final String GRAPHIC_BAR_TITLE_AXISX = "Metrics";
		public static final String GRAPHIC_BAR_TITLE_AXISY = "Measure";
		public static final String GRAPHIC_BAR_BACKGROUND_IMAGE = "ML.png";

		public static final SolidColor GRAPHIC_BAR_COLOR = new SolidColor(Color.GRAY);
		public static final SolidColor GRAPHIC_BAR_COLOR_BESTRESULT1 = new SolidColor(Color.rgb(133,47,4));
		public static final SolidColor GRAPHIC_BAR_COLOR_BESTRESULT2 = new SolidColor(Color.rgb(174,60,4));
		
	}
	
	public static class Item {
		
		public static final int ITEM_SELECT_LIBRARY = 0;
		public final static int ITEM_SELECT_FILE_DATASET = 1;
		public final static int ITEM_SELECT_PREDICTED_ATT = 2;
		public final static int ITEM_SELECT_SCHEMES = 3;
		public final static int ITEM_CODE_FILE_SELECT = 0;
		
		public final static String ITEM_PROGRESSDIALOG_TITLE = "Please wait ...";
		public final static String ITEM_PROGRESSDIALOG_DETAIL = "Task in progress ...";
		
		
	}
	
	public class Exception {
		
		public final static String MISSING_LIBRARY = "Please, select the library before";
		public final static String MISSING_FILEDATASET = "Please, select the file dataset before";
		public final static String ALREADY_SAVED = "Files are already saved";
		
	}
	
}
