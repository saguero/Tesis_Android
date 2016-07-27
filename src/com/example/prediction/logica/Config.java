package com.example.prediction.logica;

import org.afree.graphics.SolidColor;

import com.example.prediction.R;
import android.graphics.Color;
import android.os.Environment;

public class Config<context> {
	
	public static class InitialSettings {
		private static String DIR_STORAGE = Environment.getExternalStorageDirectory().toString() + "/";
		public static int SUBDIR_APP = R.string.initial_subdirectory_app;
		public static int SUBDIR_MODELS = R.string.initial_subdirectory_models;
		public static int SUBDIR_OPT_SCHEMES = R.string.initial_subdirectory_optschemes;
		public static int SUBDIR_OPT_PARAMS = R.string.initial_subdirectory_optparams;
		public static int SUBDIR_DATABASE = R.string.initial_subdirectory_trainingSets;
		
		public static Integer[] optionsFormatDataset = {R.string.initial_optionformatfile1};
		public static Integer[] optionsTypePrediction = {R.string.initial_optiontypeprediction1};
		
		
		public static Integer EXTENSION_DATASET = optionsFormatDataset[0]; 
		public static Integer TYPE_PREDICTION = optionsTypePrediction[0];
		public static String DIR_WORKING = Environment.getExternalStorageDirectory().toString() + "/" ;
		
		public static final int ITEM_FORMATFILE = 0;
		public static final int ITEM_TYPEPREDICTION = 1;
		public static final int ITEM_STORAGE = 2;
		
		
		public static final int REGRESSION = 0;
		public static final int CLASSIFICATION = 1;
		public static final int CLUSTERING = 2;
		
		public static final Integer[] INITIAL_ITEMS = 
			{	R.string.initial_item_formatfile,
				R.string.initial_item_typeprediction,
				R.string.initial_item_storage	};
		

		/*	GETTERS	*/
		public static int getFormatDataset() {
			return EXTENSION_DATASET;
		}
		
		public static int getTypePrediction() {
			return TYPE_PREDICTION;
		}
		
		public static String getDirStorage() {
			return DIR_STORAGE;
		}
		
		public static Integer[] getOptionsDatasetFormat(){
			return optionsFormatDataset;
		}
		
		public static Integer[] getOptionsTypePrediction(){
			return optionsTypePrediction;
		}
		
		/*	SETTERS	*/
		public static void setFormatDataset(int format) {
			EXTENSION_DATASET = format;
		}
		
		public static void setTypePrediction(int TypePrediction) {
			TYPE_PREDICTION = TypePrediction;
		}
		
		public static void setWorkingDir(String workingDir) {
			DIR_WORKING = workingDir;
		}
		
		
		
		
	}
		
	public static class AppSettings {
		public static final int ITEM_SELECT_LIBRARY = 0;
		public final static int ITEM_SELECT_FILE_DATASET = 1;
		public final static int ITEM_SELECT_PREDICTED_ATT = 2;
		public final static int ITEM_SELECT_SCHEMES = 3;
		
		public static final Integer[] TITLE_CONFIGURES_ITEMS = 
			{	R.string.configures_selectlibrary,
				R.string.configures_selectdataset,
				R.string.configures_selectpredAtt,
				R.string.configures_selectschemes	};
		
		public static final int[] ICON_CONFIGURES_ITEMS = {	R.drawable.icon_library,
															R.drawable.icon_file,
															R.drawable.icon_attribute,
															R.drawable.icon_schemes	};
 		
 	}
	
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
		
		public static final int GRAPHIC_LINE_LABEL_REALVALUES = R.string.graphic_line_label_realvalues;
		public static final int GRAPHIC_LINE_TITLE_CHART_EP = R.string.graphic_line_title_ep;
		public static final int GRAPHIC_LINE_TITLE_CHART_LC = R.string.graphic_line_title_lc;
		public static final int GRAPHIC_LINE_TITLE_AXISX = R.string.graphic_line_axisx;
		public static final int GRAPHIC_LINE_TITLE_AXISY = R.string.graphic_line_axisx;
		public static final int GRAPHIC_LINE_BACKGROUND_IMAGE_LC = R.drawable.learning_curve;
		public static final int GRAPHIC_LINE_BACKGROUND_IMAGE_EP = R.drawable.learning_curve;
		public static final int GRAPHIC_LINE_LABEL_TRAINING = R.string.graphic_line_label_trainingset;
		public static final int GRAPHIC_LINE_LABEL_CROSSVALIDATION = R.string.graphic_line_label_cv;
		
		public static final SolidColor GRAPHIC_LINE_COLOR_REALVALUES = new SolidColor(Color.BLACK);
		
		public static final int GRAPHIC_BAR_HEIGHT = 400;
		public static final int GRAPHIC_BAR_WIDTH = 500;
		public static final int GRAPHIC_BAR_MAXLABELSHORIZONTAL = 5;
		
		public static final float GRAPHIC_BAR_MAXBARWIDTH = 0.05F;
		
		public static final int GRAPHIC_BAR_TITLE_CHART = R.string.graphic_bar_title;
		public static final int GRAPHIC_BAR_TITLE_AXISX = R.string.graphic_bar_axisx;
		public static final int GRAPHIC_BAR_TITLE_AXISY = R.string.graphic_bar_axisy;
		public static final int GRAPHIC_BAR_BACKGROUND_IMAGE = R.drawable.machine_learning;

		public static final SolidColor GRAPHIC_BAR_COLOR = new SolidColor(Color.GRAY);
		public static final SolidColor GRAPHIC_BAR_COLOR_BESTRESULT1 = new SolidColor(Color.rgb(133,47,4));
		public static final SolidColor GRAPHIC_BAR_COLOR_BESTRESULT2 = new SolidColor(Color.rgb(174,60,4));
		
	}
	
 	public static class Message {
 		
		
 		public final static int ITEM_CODE_FILE_SELECT = 0;
		

		public final static int ITEM_PROGRESSDIALOG_TITLE = R.string.message_progressDialog_title;
		public final static int ITEM_PROGRESSDIALOG_DETAIL = R.string.message_progressDialog_detail;
		public final static int ITEM_DIALOG_SAVEMESSAGE = R.string.message_alertdialog_save;
		public final static int ITEM_DIALOG_YES = R.string.message_alertdialog_yes;
		public final static int ITEM_DIALOG_NO = R.string.message_alertdialog_no;
		public final static int ITEM_DIALOG_CONFIRMSAVE_TITLE = R.string.message_alertdialog_confirmsave_title;
		public final static int ITEM_DIALOG_CONFIRMSAVE_DETAIL = R.string.message_alertdialog_confirmsave_detail;
		
		
		
		public final static Integer SELECT_FORMATFILE = R.string.initial_message_selectformatfile;		
		public final static Integer SELECT_TYPEPREDICTION = R.string.initial_message_selecttypeprediction;
		public final static Integer SELECT_STORAGE = R.string.initial_message_selectstorage;
		
	}
	
	public static class Exception {
		
		public final static Integer MISSING_LIBRARY = R.string.configures_exception_missinglibrary;	
		public final static Integer MISSING_FILEDATASET = R.string.configures_exception_missingfiledataset;
		public final static Integer MISSING_PREDATT = R.string.configures_exception_missingpredatt;

		public final static Integer ALREADY_SAVED = R.string.exception_alreadysaved;
		public final static Integer MISSING_SCHEME = R.string.exception_selectscheme;	
	}
	
	
}
