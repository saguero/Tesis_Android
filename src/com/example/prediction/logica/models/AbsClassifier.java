package com.example.prediction.logica.models;

public abstract class AbsClassifier extends AbsModeler{

	public abstract void setIndexAttribute(int index);
	
	public abstract void selectBestAttributes();
	
}
