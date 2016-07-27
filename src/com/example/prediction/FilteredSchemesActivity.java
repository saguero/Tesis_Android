package com.example.prediction;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.example.prediction.logica.AbsClassifier;
import com.example.prediction.logica.Config;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class FilteredSchemesActivity extends Activity {
	private ImageView imageview;
	private static Vector<Bitmap> images;
	private int index = 0; 
	private ProgressDialog progressDialog;
	private Info info = new Info();
	private boolean schemeSelected = false;
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filteredschemes);
        
        images = new Vector<Bitmap>();
        
        imageview = (ImageView) findViewById(R.id.imageView_display); 
        imageview.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				//startActivity(new Intent(FilteredSchemesActivity.this, ImagesLearningCurveActivity.class) );
				//overridePendingTransition(R.anim.anim_left_in, R.anim.anim_left_out);
			}
		});
        
        Button button_change = (Button) findViewById(R.id.button_filtered_changeImage);
        button_change.setOnClickListener(new View.OnClickListener() {
        	@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				changeImage();	
			}
        	
        });
        
        Button button_nextStep = (Button) findViewById(R.id.button_nextStep);
        button_nextStep.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				if(schemeSelected)
					launchActivity();
				else
					Toast.makeText(getApplicationContext(),getString(Config.Exception.MISSING_SCHEME),Toast.LENGTH_LONG).show();
			}
        	
        });
       
		
		Button button_save = (Button) findViewById(R.id.button_filtered_save);
        button_save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				showDialogSave();
			}
        });	
        
        
       Spinner spinner_selectScheme = (Spinner) findViewById(R.id.spinner_selectScheme);
       List<String> list = new ArrayList<String>();
       list.add(getString(R.string.filtered_selectscheme));
       for(AbsClassifier c :info.getBestSchemes()) {
    	   list.add(c.getName());
       }
       
       ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.item_spinner ,list);	//MODIFICAR
       spinner_selectScheme.setAdapter(adapter);
       spinner_selectScheme.setOnItemSelectedListener( new OnItemSelectedListener(){

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
			// TODO Auto-generated method stub
			if(pos > 0) {
				int index = pos - 1;
				schemeSelected = true;
				info.setBestScheme(info.getBestSchemes().elementAt(index));
			}	
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
		}
    	   
       });
        generateImages();	
    }
    
    private void showDialogSave(){
		AlertDialog.Builder builder = new AlertDialog.Builder(FilteredSchemesActivity.this);
		builder.setTitle(R.string.optimizing_dialogsave)
		.setIcon(R.drawable.icon_alert_save)	
		.setMessage(Config.Message.ITEM_DIALOG_SAVEMESSAGE)
		.setPositiveButton(Config.Message.ITEM_DIALOG_YES, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int arg1) {
				// TODO Auto-generated method stub
				boolean successful = false; 
				for(Bitmap b:images) 
					if(! info.saveSchemesComparatorImage(FilteredSchemesActivity.this,b))
						successful = false;
				dialog.dismiss();
				
				if(!successful)
					showConfirmDialog();
			}
		})
		.setNegativeButton(Config.Message.ITEM_DIALOG_NO, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int arg1) {
				// TODO Auto-generated method stub
			}
			
		});
		AlertDialog dialog = builder.create();
		dialog.show();	
	}
    
    
    private void generateImages(){
    	progressDialog = ProgressDialog.show(this, 
    										getString(Config.Message.ITEM_PROGRESSDIALOG_TITLE), 
    										getString(Config.Message.ITEM_PROGRESSDIALOG_DETAIL) );
    	try {
    		Info info = new Info();
    		Bitmap img = BitmapFactory.decodeResource(getResources(),Config.Graphic.GRAPHIC_BAR_BACKGROUND_IMAGE);
    		images = info.generateImagesSchemesComparator(this, new BitmapDrawable(getResources(),img));
			changeImage();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	progressDialog.dismiss();
    }
    
    private void changeImage(){
    	imageview.setImageBitmap(images.elementAt(index));
    	index = (index+1) % images.size();
    }
    
    public void showConfirmDialog(){
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setIcon(R.drawable.icon_alert_exception)
    	.setTitle(Config.Message.ITEM_DIALOG_CONFIRMSAVE_TITLE)
    	.setMessage(Config.Message.ITEM_DIALOG_CONFIRMSAVE_DETAIL)
    	.setPositiveButton(R.string.configures_dialogok, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int arg1) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
    }
    
    
    private void launchActivity(){
    	Intent intent = new Intent(this, OptimizingSchemeActivity.class);
		startActivity(intent);
    }

    
}
