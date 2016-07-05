package com.example.prediction;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.example.prediction.logica.Config;
import com.example.prediction.logica.models.AbsClassifier;


import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
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
	private Vector<Bitmap> images;
	private int index = 0; 
	private ProgressDialog progressDialog;
	private Button button_change;
	private Spinner spinner_selectScheme;
	private Info info = new Info();
	private boolean schemeSelected = false;
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filteredschemes);
        
        imageview = (ImageView) findViewById(R.id.imageView_display); 
        button_change = (Button) findViewById(R.id.button_changeImage);
        
        button_change.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				changeImage();	
			}
        	
        });
        
        Button button_nextStep = (Button) findViewById(R.id.button_nextStep);
        button_nextStep.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(schemeSelected)
					launchActivity();
				else
					Toast.makeText(getApplicationContext(),Config.Exception.MISSING_SCHEME,Toast.LENGTH_LONG).show();
			}
        	
        });
        
       spinner_selectScheme = (Spinner) findViewById(R.id.spinner_selectScheme);
       List<String> list = new ArrayList<String>();
       list.add("Select scheme...");
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

		spinner_selectScheme.setVisibility(View.INVISIBLE);
        button_change.setVisibility(View.INVISIBLE);
        generateImages();	
    }
    
    public void generateImages(){
    	progressDialog = ProgressDialog.show(this, Config.Item.ITEM_PROGRESSDIALOG_TITLE, Config.Item.ITEM_PROGRESSDIALOG_DETAIL);
    	try {
    		Info info = new Info();
			images = info.generateImagesSchemesComparator(this);
			changeImage();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	progressDialog.dismiss();
    	spinner_selectScheme.setVisibility(View.VISIBLE);
    	button_change.setVisibility(View.VISIBLE);
    }
    
    private void changeImage(){
    	imageview.setImageBitmap(images.elementAt(index));
    	index = (index+1) % images.size();
    }
    
    private void launchActivity(){
    	Intent intent = new Intent(this, OptimizingSchemeActivity.class);
		startActivity(intent);
    }

    
}
