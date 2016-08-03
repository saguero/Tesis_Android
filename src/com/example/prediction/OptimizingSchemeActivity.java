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
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.prediction.Info;
import com.example.prediction.logica.Config;

public class OptimizingSchemeActivity<LineChart> extends Activity{
	
	private final int HISTORIAL = 0;
	private final int SAVE = 1;
	
	private Info info = Info.getInstance();
	
	private int index = 0;
	private boolean saved; 
	private static Bitmap image_learningcurve;
	private static Bitmap image_errorprediction;
	private static Vector<Bitmap> images = new Vector<Bitmap>();
	private ImageSwitcher imageswitcher;
	private TextView subtitle;
	

	public class CautionDialog extends AlertDialog {
		private int titleId;
		private int messageId;
		private int actionAlert;
		private int icon;
		
		public CautionDialog(int alert) {
			super(OptimizingSchemeActivity.this);
			// TODO Auto-generated constructor stub
			switch(alert){
			case SAVE:
				titleId = R.string.optimizing_dialogsave;
				actionAlert = SAVE;
				icon = R.drawable.icon_alert_save;
				messageId = R.string.message_alertdialog_save;
				break;
			case HISTORIAL:
				titleId = R.string.optimizing_dialogloadhistorial;
				actionAlert = HISTORIAL;
				icon = R.drawable.icon_alert_historial;
				messageId = R.string.message_alertdialog_historial;
				break;
			}
		}

		
	    public void onCreateDialog() {
	        AlertDialog.Builder builder = new AlertDialog.Builder(OptimizingSchemeActivity.this);
	        builder.setTitle(titleId)													
	        .setMessage(messageId)
	        .setIcon(icon)
	        .setPositiveButton(R.string.optimizing_dialogyes, new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	                	  switch(actionAlert){
	                	  case SAVE:
	                		  info.saveLearningCurveImage(OptimizingSchemeActivity.this,image_learningcurve);
	                	      info.saveErrorPredictionImage(OptimizingSchemeActivity.this,image_errorprediction);
	                	      saved = true;
	                		  break;
	                	 
	                	  case HISTORIAL:
	                		  launchActivity(ImagesLearningCurveActivity.class);
	                	  	  
	                	  }
	               	}
	             })
	        .setNegativeButton(R.string.optimizing_dialogno, new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                       dialog.dismiss();
	                   }
	               });
	        builder.create().show();
	    }
	}
	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_optimizingscheme);
	        
	        saved = false;
	        generateImages();
	        
	        subtitle = (TextView) findViewById(R.id.textView_optimizing_subtitle);
	        subtitle.setText(R.string.optimizing_subtitle_principle);
	        
	        Button button_suggestion = (Button) findViewById(R.id.button_optimizing_help);
	        button_suggestion.setOnClickListener(new View.OnClickListener(){
	        	@Override
				public void onClick(View view) {
					// TODO Auto-generated method stub
	        		startActivity(new Intent(OptimizingSchemeActivity.this, ModelfitActivity.class));
	        		overridePendingTransition(R.anim.anim_left_in, R.anim.anim_left_out);
				}	
	        });
	        
	       Button button_historial = (Button) findViewById(R.id.button_optimizing_historial);
	       button_historial.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View view) {
					// TODO Auto-generated method stub
					CautionDialog  dialog = new CautionDialog(HISTORIAL);
					dialog.onCreateDialog();	
				}	
	        });
	        
	        
	        Button button_save = (Button) findViewById(R.id.button_optimizing_save);
	        button_save.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View view) {
					// TODO Auto-generated method stub
					if(!saved){
						CautionDialog dialog = new CautionDialog(SAVE);
						dialog.onCreateDialog();
					}
					else {
						AlertDialog.Builder builder = new AlertDialog.Builder(OptimizingSchemeActivity.this);
				        builder.setTitle(R.string.message_alert_title)													
				        .setMessage(Config.Exception.EXCEPTION_ALREADY_SAVED)
				        .setIcon(R.drawable.icon_alert_save)
				        .setPositiveButton(R.string.optimizing_dialogyes, new DialogInterface.OnClickListener() {
				        	@Override
							public void onClick(DialogInterface dialog, int arg1) {
								// TODO Auto-generated method stub
								dialog.dismiss();
							}
				        	
				        });
				        builder.create().show();
					}	        	
				}
	        });
	        
	        Button button_settings = (Button) findViewById(R.id.button_optimizing_settings);
	        button_settings.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View view) {
					// TODO Auto-generated method stub
					launchActivity(ConfigParametersActivity.class);
				}	        	
	        });
	        
	        Button button_next = (Button) findViewById(R.id.button_optimizing_next);
	        button_next.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View view) {
					// TODO Auto-generated method stub
					nextImage();
				}	        	
	        });
	        
	        Button button_previous = (Button) findViewById(R.id.button_optimizing_previous);
	        button_previous.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View view) {
					// TODO Auto-generated method stub
					previousImage();
				}	        	
	        });
	        
	        Button button_model = (Button) findViewById(R.id.button_optimizing_model);
	        button_model.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View view) {
					// TODO Auto-generated method stub
					generateModel();
				}	        	
	        });
	        
	        
	        imageswitcher=(ImageSwitcher) findViewById(R.id.imageSwitcher_optimizing_display);
	        imageswitcher.setFactory(new ViewSwitcher.ViewFactory() {
				@Override
				public View makeView() {
					// TODO Auto-generated method stub
					ImageView imageview = new ImageView(OptimizingSchemeActivity.this);
					imageview.setScaleType(ImageView.ScaleType.FIT_CENTER);
					imageview.setLayoutParams(new ImageSwitcher.LayoutParams(	ActionBar.LayoutParams.WRAP_CONTENT,
																				ActionBar.LayoutParams.WRAP_CONTENT) );
					imageview.setImageBitmap(images.elementAt(0));
					return imageview;
				}
			}); 
	    }
	 
	 private void generateImages(){
		try {
		    addImageError(info.generateImageErrorPrediction(info.getDatasetSelected(), this));
		    addImageLearningCurve(info.generateImageLearningCurve(info.getDatasetSelected(), this));
	       
		 } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 }

	 private void generateModel(){
		final EditText input = new EditText(this);
		 input.setSingleLine(true);
		 input.setHint(Config.Message.MESSAGE_DIALOG_GENERATEMODEL_INTROTEXT);

		 AlertDialog.Builder builder = new AlertDialog.Builder(this);
		 builder.setView(input);
		 builder.setTitle(Config.Message.MESSAGE_DIALOG_GENERATEMODEL_TITLE);
		 builder.setMessage(Config.Message.MESSAGE_DIALOG_GENERATEMODEL_DETAIL);
		 builder.setPositiveButton(
		     getString(Config.Message.MESSAGE_DIALOG_GENERATEMODEL_ACCEPT),
		     new DialogInterface.OnClickListener() {
		         public void onClick(DialogInterface dialog, int whichButton) {
		             String value = input.getText().toString().trim();
		             value = value.concat("!");
		             
		         }
		     });
		 builder.setNegativeButton( Config.Message.MESSAGE_DIALOG_GENERATEMODEL_CANCEL, null);
		 builder.create().show();
		 
	 }
	 
	 
	 private void nextImage(){
		 Animation in = AnimationUtils.loadAnimation(this, R.anim.anim_slide_in_left);
	     Animation out = AnimationUtils.loadAnimation(this, R.anim.anim_slide_out_right);
	     imageswitcher.setAnimation(out);
	     index = (index+1)%images.size();
		 Drawable drawable = new BitmapDrawable(getResources(), images.elementAt(index));
		 imageswitcher.setImageDrawable(drawable); 		
	     imageswitcher.setAnimation(in);
	 }
	
	 private void previousImage(){
		 Animation in = AnimationUtils.loadAnimation(this, R.anim.anim_slide_in_right);
	     Animation out = AnimationUtils.loadAnimation(this, R.anim.anim_slide_out_left);
	     imageswitcher.setAnimation(out);
	     index = (index-1);
		 if(index < 0)
			 index = images.size() - 1;
		 Drawable drawable = new BitmapDrawable(getResources(), images.elementAt(index));
		 imageswitcher.setImageDrawable(drawable);
		 imageswitcher.setAnimation(in);
	     
	 }
	 
	 
	private void launchActivity(Class<?> activity){			
		startActivity(new Intent(this, activity));
		overridePendingTransition(R.anim.anim_left_in, R.anim.anim_left_out);	
	}
	
	public static Vector<Bitmap> getImages(){
		return images;
	}
	  
	
	public static void addImageLearningCurve(Bitmap image){
		image_learningcurve=image;
		images.add(0, image);
	}
	
	public static void addImageError(Bitmap image){
		image_errorprediction=image;
		images.add(0, image);
	}
}
