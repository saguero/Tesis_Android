package com.example.prediction;

import java.util.Vector;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ImagesLearningCurveActivity extends Activity {
	private static Vector<Bitmap> images;
	private int index;
	private ImageView imageView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageslearningcurve);
        
        images = OptimizingSchemeActivity.getImages();
        index = 0;
        
        imageView = (ImageView) findViewById(R.id.imageView_images);
        imageView.setImageBitmap(images.firstElement());
        
        
        Button button_return = (Button) findViewById(R.id.button_images_return);
        button_return.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				finish();
			}
		});
        
        Button button_next = (Button) findViewById(R.id.button_images_next);
        button_next.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				index = (index+1) % images.size();
				imageView.setImageBitmap(images.elementAt(index));
			}
		});
        
        Button button_prev = (Button) findViewById(R.id.button_images_previuous);
        button_prev.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				 index = (index-1);
				 if(index < 0)
					 index = images.size() - 1;
				 imageView.setImageBitmap(images.elementAt(index));
			}
		});
        
	}


}
