package com.example.prediction.logica.parameters;

import java.util.Vector;

import com.example.prediction.logica.libraries.WekaLibrary;
import com.example.prediction.logica.models.AbsModeler;
import com.example.prediction.logica.models.AbsWekaClassifier;
import com.example.prediction.logica.models.AbsWekaClusterer;

import weka.core.OptionHandler;

public class WekaSimpleParameter extends AbsWekaParameter {

	private char charOp_;
	private double value_;
	private float minValue = 0;			//Para eliminar el limite minimo, setear esto a -1
	private float maxValue = -1;		//No tiene limite

	public WekaSimpleParameter(char charOp, Object value, String name) {
		super(name);
		charOp_ = charOp;
		value_ = (double) value;
	}

	// getters and setters

	public void setValue(double value) {
		value_ = value;
	}

	public double getValue() {
		return value_;
	}

	public float getMinValor() {
		return minValue;
	}

	public void setMinValor(float min) {
		minValue = min;
	}

	public float getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(float maxValue) {
		this.maxValue = maxValue;
	}

	// Public methods
	/**/
	@Override
	public void modifyModel(AbsModeler modeler) {
		try {
			WekaLibrary.modifyObject(this, (OptionHandler) ((AbsWekaClassifier)modeler).getClassifier());
		} catch (Exception e) {
			try {
				WekaLibrary.modifyObject(this, (OptionHandler) ((AbsWekaClusterer)modeler).getClusterer());
			} catch (Exception ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
		}
	}
	/**/
	@Override
	public String getParameterString() throws Exception {
		// TODO Auto-generated method stub
		double r=value_%1;
		if (r==0){
			Double val=new Double(value_);
			return "-" + String.valueOf(charOp_).toUpperCase() + " " + String.valueOf(val.intValue());
		}
		return "-" + String.valueOf(charOp_).toUpperCase() + " " + String.valueOf(value_);
	}

	@Override
	public Vector<String> getPropertyString(double min, double max) {
		// TODO Auto-generated method stub
		Vector<String> r=new Vector<>();
		r.addElement("-property " + charOp_ + " -min "+min+" -max "+max+" -step 1.0 -base 10.0 -expression I");
		return r;
	}

	@Override
	public int getSimpleParametersCount() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public double getFirstValue(double min) {
		// TODO Auto-generated method stub
		if (getMinValor() == -1) { // No tiene limite mínimo
			return min;
		} else {
			if (min < getMinValor())
				return getMinValor();
		}
		return min;
	}

	@Override
	public double getLastValue(double max) {
		// TODO Auto-generated method stub
		if (getMaxValue() == -1) { // No tiene limite maximo
			if (max==-1)
				return 0;
			else
				return max;
		} else {
			if (max > getMaxValue())
				return getMaxValue();
		}
		return max;
	}

}
