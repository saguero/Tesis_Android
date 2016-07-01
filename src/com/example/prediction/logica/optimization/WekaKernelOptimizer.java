package com.example.prediction.logica.optimization;

import java.util.Vector;

import com.example.prediction.logica.libraries.WekaLibrary;
import com.example.prediction.logica.models.AbsModeler;
import com.example.prediction.logica.models.SMOregClassifier;
import com.example.prediction.logica.parameters.AbsParameter;
import com.example.prediction.logica.parameters.AbsWekaParameter;
import com.example.prediction.logica.parameters.WekaKernelParameter;
import com.example.prediction.logica.parameters.WekaSimpleParameter;

import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.classifiers.functions.SMOreg;
import weka.classifiers.functions.supportVector.NormalizedPolyKernel;
import weka.classifiers.functions.supportVector.PolyKernel;
import weka.classifiers.functions.supportVector.Puk;
import weka.classifiers.functions.supportVector.RBFKernel;
import weka.classifiers.meta.GridSearch;
import weka.classifiers.meta.MultiScheme;
import weka.classifiers.meta.MultiSearch;
import weka.core.Instances;
import weka.core.SelectedTag;
import weka.core.Utils;
import weka.core.setupgenerator.AbstractParameter;
import weka.core.setupgenerator.MathParameter;

public class WekaKernelOptimizer extends AbsWekaOptimizer {

	private Vector<WekaKernelParameter> kernels = new Vector<WekaKernelParameter>();
	
	public WekaKernelOptimizer(double min, double max){
		minValue=min;
		maxValue=max;
	}

	public void setKernelOptions() {
		NormalizedPolyKernel npk = new NormalizedPolyKernel(); // exponent (-E)
		PolyKernel pk = new PolyKernel(); // exponent (-E)
		Puk p = new Puk(); // omega (-O) - sigma (-S)
		RBFKernel rbfk = new RBFKernel(); // gamma (-G)

		WekaKernelParameter npkparam = new WekaKernelParameter(npk);
		WekaKernelParameter pkparam = new WekaKernelParameter(pk);
		WekaKernelParameter pukparam = new WekaKernelParameter(p);
		WekaKernelParameter rbfkparam = new WekaKernelParameter(rbfk);

		Vector<AbsWekaParameter> exponents = new Vector<AbsWekaParameter>();
		WekaSimpleParameter exp=new WekaSimpleParameter('E', 2.0, "exponent");
		exp.setMinValor(2);
		exponents.add(exp);
		Vector<AbsWekaParameter> pukparams = new Vector<AbsWekaParameter>(); // PUK
		pukparams.add(new WekaSimpleParameter('O', 1.0, "omega"));
		pukparams.add(new WekaSimpleParameter('S', 1.0, "sigma"));
		Vector<AbsWekaParameter> rbfkparams = new Vector<AbsWekaParameter>();
		rbfkparams.add(new WekaSimpleParameter('G', 1.0, "gamma"));

		npkparam.setKernelOptions(exponents);
		pkparam.setKernelOptions(exponents);
		pukparam.setKernelOptions(pukparams);
		rbfkparam.setKernelOptions(rbfkparams);

		kernels.add(npkparam);
		kernels.add(rbfkparam);
		kernels.add(pkparam);
		kernels.add(pukparam);

	}

	private Classifier[] bestClassifiers(AbsModeler modeler, Instances dataset) throws Exception {
		MultiSearch ms = new MultiSearch();
		SMOreg smoreg = new SMOreg();
		ms.setEvaluation(new SelectedTag(GridSearch.EVALUATION_CC, GridSearch.TAGS_EVALUATION));
		
		setKernelOptions();

		Classifier[] cls = new Classifier[kernels.size()];
		int kindex = 0;
		for (WekaKernelParameter k : kernels) {
			modeler.addParameter(k);
			AbstractParameter[] params = new AbstractParameter[WekaLibrary.getSimpleParamsCount(modeler)];
			int i = 0;
			for (AbsParameter p : modeler.getParameters()) {
				double max=((AbsWekaParameter)p).getLastValue(maxValue);
				double min=((AbsWekaParameter)p).getFirstValue(minValue);
				for (String op : ((AbsWekaParameter) p).getPropertyString(min, max)) {
					AbstractParameter aux = new MathParameter();
					aux.setOptions(Utils.splitOptions(op));
					params[i] = aux;
					i++;
				}
			}
			ms.setSearchParameters(params);
			smoreg.setKernel(k.getKernel());
			ms.setClassifier(smoreg);
			ms.buildClassifier(dataset);
			cls[kindex] = (Classifier) ms.getBestClassifier();
			kindex++;
		}
		return cls;
	}

	@Override
	protected void optimiceParams(AbsModeler modeler, Instances isTrainingSet) {
		// TODO Auto-generated method stub
		try {
			MultiScheme ms = new MultiScheme();
			Classifier[] optclassifiers = bestClassifiers(modeler, isTrainingSet);
			ms.setClassifiers(optclassifiers);
			ms.buildClassifier(isTrainingSet);
			((SMOregClassifier) modeler).setClassifier(optclassifiers[ms.getBestClassifierIndex()]);
			WekaLibrary.parseOptions(((SMOreg) (optclassifiers[ms.getBestClassifierIndex()])).getOptions(), modeler);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
