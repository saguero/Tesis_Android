package com.example.prediction.logica.optimization;

import java.util.Random;
import java.util.Vector;

import com.example.prediction.logica.models.AbsModeler;
import com.example.prediction.logica.models.AbsWekaClusterer;
import com.example.prediction.logica.parameters.AbsWekaParameter;
import com.example.prediction.logica.parameters.AbsParameter;
import com.example.prediction.logica.parameters.WekaSimpleParameter;

import weka.clusterers.SimpleKMeans;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.OptionHandler;

public class WekaClustererOptimizer extends AbsWekaOptimizer {

	private static final long SEED = 10;
	private static final int DEFAULT_CLUSTER_STEPS = 30;
	
	public WekaClustererOptimizer(double min, double max){
		minValue=min;
		maxValue=max;
	}
	
	public WekaClustererOptimizer(){
		
	}

	@Override
	protected void optimiceParams(AbsModeler modeler, Instances isTrainingSet){		
		double maxAv = Double.MAX_VALUE;
		int finalk=0;
		OptionHandler oh=(OptionHandler) ((AbsWekaClusterer)modeler).getClusterer();
		Vector<AbsParameter> parameters=modeler.getParameters();
		((SimpleKMeans)oh).setPreserveInstancesOrder(true);
		for (AbsParameter p:parameters){
			Random rand = new Random(SEED); 
			Instances randData = new Instances(isTrainingSet); 										
			randData.randomize(rand); 
			SimpleKMeans tester=new SimpleKMeans();
			Double min=((AbsWekaParameter)p).getFirstValue(minValue);
			Double max=((AbsWekaParameter)p).getLastValue(maxValue);
			if (((WekaSimpleParameter) p).getMaxValue()==-1){
				max+=DEFAULT_CLUSTER_STEPS;
			}
			if (max > isTrainingSet.size()){
				max= (double) isTrainingSet.size();
			}
			for (int k = min.intValue(); k < max.intValue(); k++) {
				double error = 0;
				double s = 0.0;
				for (int n = 0; n < k; n++) {
					Instances train = randData.trainCV(k, n);
					Instances test = randData.testCV(k, n);
					tester.setPreserveInstancesOrder(true);
					try {
						tester.setNumClusters(k);
						tester.buildClusterer(train);
						for (int i = 0; i < test.numInstances(); i++) {
							s += s(tester, test.instance(i), train);
						}
						error += tester.getSquaredError()/test.numInstances();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				s = s/k;
				error = error/k;
				if (((error - s) > 0) && (error - s) < maxAv) {
					maxAv = error-s;
					finalk = k;
				}
			}
			if (finalk==0){
				finalk=2;
			}
			System.out.println(finalk);
			((WekaSimpleParameter) p).setValue(finalk);
			p.modifyModel(modeler);
		}
		
	}
	
	private double s(SimpleKMeans knn, Instance i, Instances is) throws Exception {
		double a = a(knn, i, is);
		double b = b(knn, i, is);
		if (a == b)
			return 0.0;
		if (a < b)
			return 1 - (a / b);
		return (b / a) - 1;
	}

	private double b(SimpleKMeans knn, Instance i, Instances is) throws Exception {
		// TODO Auto-generated method stub
		int[] ass = knn.getAssignments();
		int cluster = knn.clusterInstance(i);
		double minDist = Double.MAX_VALUE;
		for (int j = 0; j < ass.length; j++) {
			if (ass[j] != cluster) {
				double dist = knn.getDistanceFunction().distance(i, is.instance(j));
				if (dist < minDist) {
					minDist = dist;
				}
			}

		}
		return minDist;
	}

	private double a(SimpleKMeans knn, Instance i, Instances is) throws Exception {
		// TODO Auto-generated method stub
		int[] ass = knn.getAssignments();
		int cluster = knn.clusterInstance(i);
		double maxDist = 0;
		for (int j = 0; j < ass.length; j++) {
			if (ass[j] == cluster) {
				double dist = knn.getDistanceFunction().distance(i, is.instance(j));
				if (dist > maxDist) {
					maxDist = dist;
				}
			}

		}
		return maxDist;
	}

}
