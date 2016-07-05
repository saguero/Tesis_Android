package com.example.prediction;

import java.util.Vector;

import android.app.ActionBar;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.prediction.Info;
import com.example.prediction.logica.Config;

public class OptimizingSchemeActivity<LineChart> extends Activity{
	
	static final int HISTORIAL = 0;
	static final int SAVE = 1;
	int alertDialog;
	
	private ImageSwitcher imageswitcher;
	private int index = 0;
	private Info info = new Info();
	private Bitmap image_learningcurve;
	private Bitmap image_errorprediction;
	private boolean saved = false;
	private Vector<Bitmap> images = new Vector<Bitmap>();
	
	Button button_historial;
	String state_historial = "SHOW";
	TextView title;
	private int actionAlert;
	
	
	public class CautionDialog extends DialogFragment {
		
		public CautionDialog(){	
		}
		
		public  CautionDialog newInstance(int alert){
			switch(alert){
			case SAVE:
				alertDialog = R.string.optimizing_dialogsave;
				actionAlert = SAVE;
				break;
			case HISTORIAL:
				alertDialog = R.string.optimizing_dialogloadhistorial;
				actionAlert = HISTORIAL;
				break;
			}
			
			CautionDialog dialogFragment = new CautionDialog();
		    Bundle bundle = new Bundle();
		    dialogFragment.setArguments(bundle);
		    return dialogFragment;
		}

		@Override
	    public Dialog onCreateDialog(Bundle savedInstanceState) {
	        // Use the Builder class for convenient dialog construction
	        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	        builder.setTitle(alertDialog)													//VER QUE ONDA ESTO!!
	               .setPositiveButton(R.string.optimizing_dialogyes, new DialogInterface.OnClickListener() {
	                   
	            	   public void onClick(DialogInterface dialog, int id) {
	                	   switch(actionAlert){
	                	   case SAVE:
	                		   actionSave();
	                		   break;
	                	   case HISTORIAL:
	                		   actionLoadHistorial(state_historial);
	                		   break;
	                	   }  
	                   }
	               })
	               .setNegativeButton(R.string.optimizing_dialogno, new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                       // User cancelled the dialog
	                   }
	               });
	        return builder.create();
	    }
	}
	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_optimizingscheme);
	        
	        
	        Button button_suggestion = (Button) findViewById(R.id.button_optimizing_help);
	        button_suggestion.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					showSuggestion();
				}
	        	
	        });
	        
	       button_historial = (Button) findViewById(R.id.button_optimizing_historial);
	       button_historial.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					CautionDialog  dialog = new CautionDialog();
					dialog.newInstance(HISTORIAL);
					dialog.show(getFragmentManager().beginTransaction(), "dialog");	
				}
	        	
	        });
	        
	        
	        Button button_save = (Button) findViewById(R.id.button_optimizing_save);
	        button_save.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					CautionDialog  dialog = new CautionDialog();
					dialog.newInstance(SAVE);
					dialog.show(getFragmentManager().beginTransaction(), "dialog");
				}	        	
	        });
	        
	        Button button_settings = (Button) findViewById(R.id.button_optimizing_settings);
	        button_settings.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					configParameters();
				}	        	
	        });
	        
	        Button button_next = (Button) findViewById(R.id.button_optimizing_next);
	        button_next.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					nextImage();
				}	        	
	        });
	        
	        Button button_previous = (Button) findViewById(R.id.button_optimizing_previous);
	        button_previous.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					previousImage();
				}	        	
	        });
	        
	        
	        
	        title = (TextView) findViewById(R.id.textView_optimizing_subtitle);
	        title.setText(R.string.optimizing_subtitle_loading);
	        
	        try {
	        	image_learningcurve = info.generateImageLearningCurve(this, info.getBestScheme());
	    		images.add(image_learningcurve);
	    		image_errorprediction = info.generateImageErrorPrediction(this);
	    		images.add(image_errorprediction);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
			
	 
	        imageswitcher=(ImageSwitcher) findViewById(R.id.imageSwitcher_optimizing_display);
	        imageswitcher.setFactory(new ViewSwitcher.ViewFactory() {
				
	        	//PONER UN ADAPTADOR?????
				@Override
				public View makeView() {
					// TODO Auto-generated method stub
					ImageView imageview = new ImageView(getApplicationContext());
					imageview.setScaleType(ImageView.ScaleType.FIT_CENTER);
					imageview.setLayoutParams(new ImageSwitcher.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT) );
					imageview.setImageBitmap(images.elementAt(0));
					title.setText(R.string.optimizing_subtitle_principle);
					return imageview;
				}
			});    
	      
	    }

	 
	 
	 private void showSuggestion(){
		startActivity(new Intent(this, ModelfitActivity.class));
		overridePendingTransition(R.anim.anim_left_in, R.anim.anim_left_out);
	 }
	
	 private void nextImage(){
		 Drawable drawable = new BitmapDrawable(getResources(), images.elementAt(index));
		 imageswitcher.setImageDrawable(drawable); 
		 index = (index+1)%images.size();		
		 Animation in = AnimationUtils.loadAnimation(this, R.anim.anim_slide_in_right);
	     Animation out = AnimationUtils.loadAnimation(this, R.anim.anim_slide_out_left);
	     imageswitcher.setAnimation(in);
	     imageswitcher.setAnimation(out);
	 }
	
	 public void previousImage(){
		 Drawable drawable = new BitmapDrawable(getResources(), images.elementAt(index));
		 imageswitcher.setImageDrawable(drawable);
		 index = (index-1);
		 if(index < 0)
			 index = images.size();
		 Animation in = AnimationUtils.loadAnimation(this, R.anim.anim_slide_in_left);
	     Animation out = AnimationUtils.loadAnimation(this, R.anim.anim_slide_out_right);
	     imageswitcher.setAnimation(in);
	     imageswitcher.setAnimation(out);
	 }
	 
	 public void saveImages(){ 
		if(!saved){
			CautionDialog dialog = new CautionDialog();
			dialog.newInstance(R.string.optimizing_dialogsave).show(getFragmentManager().beginTransaction(), "dialog");
			saved = true;
		}
		else
			Toast.makeText(getApplicationContext(), Config.Exception.ALREADY_SAVED,Toast.LENGTH_LONG).show(); 	
	}
	
	 public void loadHistorial(){
		if(!saved)
			saveImages();
		CautionDialog dialog = new CautionDialog();
		dialog.newInstance(R.string.optimizing_dialogloadhistorial).show(getFragmentManager().beginTransaction(), "dialog");
	}
	
	 private void actionSave(){
		info.saveLearningCurveImage(images.elementAt(0));
        info.saveErrorPredictionImage(images.elementAt(1));
	}
	
	 private void actionLoadHistorial(String state){
		
		if(state.equals("SHOW")){
			button_historial.setBackgroundResource(R.drawable.icon_undo);
			images = info.getLearningCurveImages();	
			state = "DISMISS";
		}
		else {
			button_historial.setBackgroundResource(R.drawable.save2);
			images = new Vector<Bitmap>();
			images.add(image_learningcurve);
			images.add(image_errorprediction);
			state = "SHOW";
		}
		index = 0;
		Drawable drawable = new BitmapDrawable(getResources(), images.elementAt(index));
		imageswitcher.setImageDrawable(drawable);	
		title.setText(R.string.optimizing_subtitle_principle);
	}
	
	 public void configParameters(){
		Intent intent = new Intent(this, ConfigParametersActivity.class);
    	startActivity(intent);
    	overridePendingTransition(R.anim.anim_left_in, R.anim.anim_left_out);
	}
	  

}
