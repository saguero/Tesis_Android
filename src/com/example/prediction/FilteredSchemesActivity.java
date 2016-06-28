package com.example.prediction;



import java.util.Vector;

import com.example.prediction.logica.Config;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class FilteredSchemesActivity extends Activity {
	private ImageView imageview;
	private Vector<Bitmap> images;
	private int index = 0; 
	private ProgressDialog progressDialog;
	private Button button_change;

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
    	
    	button_change.setVisibility(View.VISIBLE);
    }
    
    private void changeImage(){
    	imageview.setImageBitmap(images.elementAt(index));
    	index = (index+1) % images.size();
    }

    
}
