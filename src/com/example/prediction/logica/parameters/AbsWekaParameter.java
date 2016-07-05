package com.example.prediction.logica.parameters;

import java.util.Vector;

import com.example.prediction.logica.models.AbsModeler;


public abstract class AbsWekaParameter extends AbsParameter {

	public AbsWekaParameter(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	/**/

	public abstract void modifyModel(AbsModeler modeler);

	public abstract String getParameterString() throws Exception;

	public abstract Vector<String> getPropertyString(double min, double max);

	public abstract int getSimpleParametersCount();

	public abstract double getFirstValue(double min);

	public abstract double getLastValue(double max);

}
