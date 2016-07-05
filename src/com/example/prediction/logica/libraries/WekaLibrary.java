package com.example.prediction.logica.libraries;

import java.util.Vector;

import com.example.prediction.logica.database.AbsDatabase;
import com.example.prediction.logica.database.WekaDatabase;
import com.example.prediction.logica.evaluation.AbsEvaluation;
import com.example.prediction.logica.evaluation.EvaluationWeka;
import com.example.prediction.logica.metrics.WekaMetricEvaluation;
import com.example.prediction.logica.models.AbsClassifier;
import com.example.prediction.logica.models.AbsModeler;
import com.example.prediction.logica.parameters.AbsParameter;
import com.example.prediction.logica.parameters.AbsWekaParameter;
import com.example.prediction.logica.parameters.WekaKernelParameter;
import com.example.prediction.logica.parameters.WekaSimpleParameter;

import weka.classifiers.Evaluation;
import weka.core.OptionHandler;
import weka.core.Utils;

public class WekaLibrary extends AbsLibrary{
	
	public static void addParameter(AbsParameter modelParameter, AbsModeler model) {
		model.addParameter(modelParameter);
		modelParameter.modifyModel(model);
	}

	public static int getSimpleParamsCount(AbsModeler model){
		Vector<AbsParameter> modelParameters_=model.getParameters();
		int i=0;
		for (AbsParameter p: modelParameters_){
			i+=((AbsWekaParameter)p).getSimpleParametersCount();
		}
		return i;
	}
	
	public static void parseOptions(String[] options, AbsModeler model) {
		int index = 0;
		for (index = 0; index < options.length; index = index + 2) {
			try {
				if (!options[index].isEmpty() && (options[index].length() == 2)) {
					AbsParameter p = model.getParameter(options[index].charAt(1));
					((WekaSimpleParameter) p).setValue(Double.valueOf(options[index + 1]));
				}
			} catch (Exception e) {
				System.out.println("Parametro no parseado");
			}
		}
		for (AbsParameter p : model.getParameters()) {
			p.modifyModel(model);
		}
	}
	
	public static void modifyObject(AbsWekaParameter parameter, OptionHandler oh) throws Exception {
		String[] currOptions = oh.getOptions();
		String[] newOptions = Utils.splitOptions(parameter.getParameterString());
		for (int i=0;i<currOptions.length;i++){
			if (newOptions[0].equals(currOptions[i])){
				currOptions[i+1]=newOptions[1];
			}
		}
		oh.setOptions(currOptions);
	}

	public static void parseKernelOptions(String[] options, WekaKernelParameter kernel) {
		// TODO Auto-generated method stub
		int index=0;
		for (index=0;index<options.length;index=index+2){
			try{
			if (!options[index].isEmpty()&&(options[index].length()==2)){
				WekaSimpleParameter p=new WekaSimpleParameter(options[index].charAt(1), Double.valueOf(options[index+1]), String.valueOf(options[index].charAt(1)) );
				kernel.addKernelOption(p);
			}}
			catch  (Exception e){
				System.err.println(options[index]);
				System.err.println("Error de parametro");
			}
		}
	}
	
	public WekaLibrary(String ID) {
		super(ID);
		// TODO Auto-generated constructor stub
		setDatasetObject();
		setEvaluationObject();
		setMetricsEvaluationObject();
		setSchemeObject();
		setListSchemes();
	}

	@Override
	public void setDatasetObject() {
		// TODO Auto-generated method stub
		this.trainingSet = new WekaDatabase();
	}

	@Override
	public void setEvaluationObject() {
		// TODO Auto-generated method stub
		this.evaluator = new EvaluationWeka();
	}

	@Override
	public void setMetricsEvaluationObject() {
		// TODO Auto-generated method stub
		AbsDatabase d = getDatasetObject();
		AbsEvaluation e = getEvaluationObject();
		this.metricsEvaluation = new WekaMetricEvaluation(d, e);
	}

	@Override
	public void setSchemeObject() {
		// TODO Auto-generated method stub
		//this.scheme = new ClassifierWeka();	
	}

	@Override
	public void setListSchemes() {						//HARDCODE!
		// TODO Auto-generated method stub
		listSchemes = new Vector<AbsClassifier>();
		/*listSchemes.add(new Multilayerperceptron());
		listSchemes.add(new LinearReg());
		listSchemes.add(new SimpleLinearReg());
		listSchemes.add(new Smoreg());*/
	}
	
	@Override
	public Double calculateMAE(Object evaluation) throws Exception {
		// TODO Auto-generated method stub
		return ((Evaluation) evaluation).meanAbsoluteError();
		 
	}

	@Override
	public Double calculateRMSE(Object evaluation) throws Exception {
		// TODO Auto-generated method stub
		return ((Evaluation) evaluation).rootMeanSquaredError();
	}

	@Override
	public Double calculateRAE(Object evaluation) throws Exception {
		// TODO Auto-generated method stub
		return ((Evaluation) evaluation).relativeAbsoluteError();
	}

	@Override
	public Double calculateRRSE(Object evaluation) throws Exception {
		// TODO Auto-generated method stub
		return ((Evaluation) evaluation).rootRelativeSquaredError();
	}

	@Override
	public Double calculateCC(Object evaluation) throws Exception {
		// TODO Auto-generated method stub
		return  ((Evaluation) evaluation).correlationCoefficient();
	}

	@Override
	public Double calculateACC(Object evaluation) throws Exception {
		// TODO Auto-generated method stub
		return ((Evaluation) evaluation).precision(trainingSet.getClassIndex());
	}

	@Override
	public Double calculateKAP(Object evaluation) throws Exception {
		// TODO Auto-generated method stub
		return ((Evaluation) evaluation).kappa();
	}

	@Override
	public Double calculateROC(Object evaluation) throws Exception {
		// TODO Auto-generated method stub
		return ((Evaluation) evaluation).areaUnderROC(trainingSet.getClassIndex());
	}

	@Override
	public Double calculateRECALL(Object evaluation) throws Exception {
		// TODO Auto-generated method stub
		return ((Evaluation) evaluation).recall(trainingSet.getClassIndex());
	}
	
}
