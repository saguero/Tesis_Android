package com.example.prediction.logica.models;

public abstract class AbsClassifier extends AbsModeler{
	
	public abstract void selectBestAttributes();
	
	@Override
	public void setIndexAttribute(int index){
		super.setIndexAttribute(index);
		setIndexClassifier();
	}
	
	public abstract void setIndexClassifier();
	
}
