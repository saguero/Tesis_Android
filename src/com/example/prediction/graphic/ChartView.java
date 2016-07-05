package com.example.prediction.graphic;

import org.afree.chart.AFreeChart;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import org.afree.graphics.geom.RectShape;

import com.example.prediction.logica.Config;

import android.widget.ImageView;

public class ChartView extends ImageView {
	
	
	private Bitmap              bitmap;
    private RectShape           rectArea;
    private Canvas              canvas;
    private AFreeChart          chart;

	public ChartView(Context context, int type, AFreeChart chart) {
		// TODO Auto-generated constructor stub
		super(context);
		
		switch(type){
		case Config.Graphic.GRAPHIC_TYPE_BAR:
			bitmap = Bitmap.createBitmap(Config.Graphic.GRAPHIC_BAR_WIDTH, Config.Graphic.GRAPHIC_BAR_HEIGHT, Bitmap.Config.ARGB_8888);
	        rectArea = new RectShape(0.0, 0.0, Config.Graphic.GRAPHIC_BAR_WIDTH, Config.Graphic.GRAPHIC_BAR_HEIGHT);
	        break;
	        
		case Config.Graphic.GRAPHIC_TYPE_LINE:
			bitmap = Bitmap.createBitmap(Config.Graphic.GRAPHIC_LINE_WIDTH, Config.Graphic.GRAPHIC_LINE_HEIGHT, Bitmap.Config.ARGB_8888);
	        rectArea = new RectShape(0.0, 0.0, Config.Graphic.GRAPHIC_LINE_WIDTH, Config.Graphic.GRAPHIC_LINE_HEIGHT);
	        break;
		}
		
	}
	
	public void drawChart( AFreeChart chart )
    {
        canvas = new Canvas(bitmap);
        this.chart = chart;   
        this.chart.draw(canvas, rectArea);
        setImageBitmap(bitmap);
    }
	
    @Override
    protected void onDraw( Canvas canvas )
    {
        super.onDraw(canvas);               
    }


                  
    
}
