package com.example.prediction.logica.parameters;

import java.util.Vector;

import com.example.prediction.logica.libraries.WekaLibrary;
import com.example.prediction.logica.models.AbsModeler;
import com.example.prediction.logica.models.SMOregClassifier;

import weka.classifiers.functions.SMOreg;
import weka.classifiers.functions.supportVector.Kernel;

public class WekaKernelParameter extends AbsWekaParameter {

	private Kernel kernel_;
	protected Vector<AbsWekaParameter> kernelOptions_;

	/**/
	public WekaKernelParameter(Kernel kernel) {
		super("Kernel Parameter");
		kernelOptions_ = new Vector<AbsWekaParameter>();
		setKernel(kernel);
	}

	// Setters
	/**/
	public void setKernel(Kernel kernel) {
		kernel_ = kernel;
		String[] koptions = kernel.getOptions();
		WekaLibrary.parseKernelOptions(koptions, this);
	}
	/**/
	public void setKernelOptions(Vector<AbsWekaParameter> options) {
		kernelOptions_ = options;
		for (AbsWekaParameter wkp : kernelOptions_) {
			try {
				WekaLibrary.modifyObject(wkp, kernel_);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**/
	public void addKernelOption(AbsWekaParameter option) {
		for (AbsWekaParameter op : kernelOptions_) {
			if (op.getName().equals(option.getName())) {
				kernelOptions_.removeElement(op);
				kernelOptions_.addElement(option);
				try {
					WekaLibrary.modifyObject(option, kernel_);
					return;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		kernelOptions_.addElement(option);
		try {
			WekaLibrary.modifyObject(option, kernel_);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Getters

	public Vector<AbsWekaParameter> getKernelParams() {
		return kernelOptions_;
	}

	public Kernel getKernel() {
		return kernel_;
	}

	// Publics methods
	/**/
	@Override
	public void modifyModel(AbsModeler modeler) {
		// TODO Auto-generated method stub
		SMOreg smoreg = (SMOreg) ((SMOregClassifier) modeler).getClassifier();
		smoreg.setKernel(kernel_);
	}

	/**/
	@Override
	public String getParameterString() throws Exception {
		// TODO Auto-generated method stub
		String stringVal = new String();
		for (AbsWekaParameter p : kernelOptions_) {
			stringVal += " " + p.getParameterString();
		}
		return "-K \"" + kernel_.getClass().toString() + " " + stringVal + "\"";
	}

	@Override
	public Vector<String> getPropertyString(double min, double max) {
		// TODO Auto-generated method stub
		Vector<String> r = new Vector<String>();
		for (AbsWekaParameter p : kernelOptions_) {
			double first = p.getFirstValue(min);
			double last = p.getLastValue(max);
			String ps = p.getPropertyString(first, last).firstElement().replaceAll("-property [A-Z]",
					"-property kernel." + p.getName());
			r.add(ps);
		}
		return r;
	}

	@Override
	public int getSimpleParametersCount() {
		// TODO Auto-generated method stub
		int i = 0;
		for (AbsWekaParameter p : kernelOptions_) {
			i += p.getSimpleParametersCount();
		}
		return i;
	}

	@Override
	public double getFirstValue(double min) {
		// TODO Auto-generated method stub
		return min;
	}

	@Override
	public double getLastValue(double max) {
		// TODO Auto-generated method stub
		return max;
	}

}
