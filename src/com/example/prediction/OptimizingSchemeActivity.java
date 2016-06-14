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
	private ImageSwitcher imageswitcher;
	private int index = 0;
	private Info info = new Info();
	private Bitmap image_learningcurve;
	private Bitmap image_errorprediction;
	private boolean saved = false;
	private Vector<Bitmap> images;
	
	Button button_historial;
	String state_historial = "SHOW";
	TextView title;
	
	public class CautionDialog extends DialogFragment {
		int alert;
		
		public  CautionDialog newInstance(int alert){
			
			switch(alert){
			case R.string.alert_save:
				alert = R.string.alert_save;
				break;
			case R.string.alert_loadhistorial:
				alert = R.string.alert_loadhistorial;
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
	        builder.setMessage(alert)
	               .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                	   switch(alert){
	                	   case R.string.alert_save:
	                		   actionSave();
	                		   break;
	                	   case R.string.alert_loadhistorial:
	                		   actionLoadHistorial(state_historial);
	                		   break;
	                	   }  
	                   }
	               })
	               .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
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
	        setContentView(R.layout.activity_otimizingscheme);
	        
	        button_historial = (Button) findViewById(R.id.button_opt_historial);
	        title = (TextView) findViewById(R.id.textView_optscheme_action);
	        title.setText(R.string.loading);
	        
	        //Learning curve 
	        try {
	        	image_learningcurve = info.generateImageLearningCurve(this);
				images.add(image_learningcurve);
				image_errorprediction = info.generateImageErrorPrediction(this);
				images.add(image_errorprediction);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 
	        imageswitcher=(ImageSwitcher) findViewById(R.id.imageSwitcher1);
	        imageswitcher.setFactory(new ViewSwitcher.ViewFactory() {
				
	        	//PONER UN ADAPTADOR?????
				@Override
				public View makeView() {
					// TODO Auto-generated method stub
					ImageView imageview = new ImageView(getApplicationContext());
					imageview.setScaleType(ImageView.ScaleType.FIT_CENTER);
					imageview.setLayoutParams(new ImageSwitcher.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT) );
					imageview.setImageBitmap(images.elementAt(0));
					title.setText(R.string.title2_optimizing);
					return imageview;
				}
			});    
	      
	    }

	 
	 public void showSuggestion(){
		startActivity(new Intent(this, ModelfitActivity.class));
		overridePendingTransition(R.anim.anim_left_in, R.anim.anim_left_out);
	 }
	
	 public void button_next(){
		 Drawable drawable = new BitmapDrawable(getResources(), images.elementAt(index));
		 imageswitcher.setImageDrawable(drawable); 
		 index = (index+1)%images.size();		
		 Animation in = AnimationUtils.loadAnimation(this, R.anim.anim_slide_in_right);
	     Animation out = AnimationUtils.loadAnimation(this, R.anim.anim_slide_out_left);
	     imageswitcher.setAnimation(in);
	     imageswitcher.setAnimation(out);
	 }
	
	 public void button_previuos(){
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
			dialog.newInstance(R.string.alert_save).show(getFragmentManager().beginTransaction(), "dialog");
			saved = true;
		}
		else
			Toast.makeText(getApplicationContext(), Config.Exception.ALREADY_SAVED,Toast.LENGTH_LONG).show(); 	
	}
	
	public void loadHistorial(){
		if(!saved)
			saveImages();
		CautionDialog dialog = new CautionDialog();
		dialog.newInstance(R.string.alert_loadhistorial).show(getFragmentManager().beginTransaction(), "dialog");
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
		title.setText(R.string.title2_optimizing);
	}
	
	public void configParameters(){
		Intent intent = new Intent(this, ConfigParametersActivity.class);
    	startActivity(intent);
    	overridePendingTransition(R.anim.anim_left_in, R.anim.anim_left_out);
	}
	  

}
