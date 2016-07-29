package com.example.prediction.logica.user_options;

import java.util.Hashtable;
import java.util.Vector;

import com.example.prediction.logica.database.AbsDatabase;
import com.example.prediction.logica.individual.Individual;

public class PolinomialTransformer implements DatabaseTransformer {

	private int degree = 1;
	private Vector<Solution> solutions = new Vector<Solution>();

	private class node extends Object {
		public String name = new String();
		public double value = 0;
		public int nodeDegree = 1;

		@Override
		public boolean equals(Object object) {
			node newNode = (node) object;
			if ((newNode.name.equals(name)) && (newNode.value == value) && (newNode.nodeDegree == nodeDegree)) {
				return true;
			}
			return false;
		}

		@Override
		public int hashCode() {
			return (int) (name.hashCode() + value + nodeDegree);
		}
	};

	private class Solution extends Object {
		public Vector<node> sol = new Vector<node>();

		@Override
		public int hashCode() {
			int i = 0;
			for (node n : sol) {
				i += n.hashCode();
			}
			return i;
		}
	}

	public PolinomialTransformer(int degree) {
		this.degree = degree;
	}

	@Override
	public void transformDatabase(AbsDatabase database) {
		// TODO Auto-generated method stub

		Vector<node> partial = new Vector<node>();
		Vector<node> originals = getNodes(database.getIndividuals().firstElement());
		completePolynomial(0, partial, originals);

		for (Solution n : solutions) {
			String name = getName(n);
			database.addAttribute(name);
		}

		for (Individual ind : database.getIndividuals()) {
			applyTransformation(ind);
		}
	}

	private String getName(Solution n) {
		// TODO Auto-generated method stub
		String name = new String();
		for (node na : n.sol) {
			name += na.name + na.nodeDegree;
		}
		return name;
	}

	private void applyTransformation(Individual ind) {
		// TODO Auto-generated method stub
		for (Solution sol : solutions) {
			ind.setAttributeValue(getName(sol), getValue(sol));
		}
	}

	private Double getValue(Solution sol) {
		// TODO Auto-generated method stub
		double val = 1;
		for (node n : sol.sol) {
			val *= Math.pow(n.value, n.nodeDegree);
		}
		return val;
	}

	private void completePolynomial(int step, Vector<node> partial, Vector<node> originals) {
		// TODO Auto-generated method stub
		if (step < originals.size()) {
			if (isSolution(partial)) {
				if ((getDegree(partial) != 0) && (getDegree(partial) != 1) && (!containsSol(partial))) {
					Solution sol = new Solution();
					sol.sol.addAll(partial);
					solutions.addElement(sol);
				}
				for (int j = 0; j <= degree; j++) {
					node n = new node();
					n.value = originals.elementAt(step).value;
					n.name = originals.elementAt(step).name;
					n.nodeDegree = j;
					partial.addElement(n);
					step++;
					completePolynomial(step, partial, originals);
					step--;
					partial.removeElement(n);
				}
			}
		}
	}

	private boolean containsSol(Vector<node> partial) {
		// TODO Auto-generated method stub

		Vector<node> copyPartial = new Vector<node>();
		copyPartial.addAll(partial);
		for (node n : partial) {
			if (n.nodeDegree == 0)
				copyPartial.removeElement(n);
		}

		for (Solution s : solutions) {
			Vector<node> currSol = new Vector<node>();
			currSol.addAll(s.sol);
			for (node n : s.sol) {
				if (n.nodeDegree == 0)
					currSol.removeElement(n);
			}
			boolean found = true;
			int i = 0;
			while (i < copyPartial.size()) {
				node n = copyPartial.elementAt(i);
				found = found && findNode(currSol, n);
				i++;
			}
			if (found==true)
				return true;
		}
		return false;
	}

	private boolean findNode(Vector<node> currSol, node n2) {
		// TODO Auto-generated method stub
		for (node n : currSol) {
			if (n.equals(n2))
				return true;
		}
		return false;
	}

	private boolean isSolution(Vector<node> partial) {
		// TODO Auto-generated method stub
		if (getDegree(partial) <= degree)
			return true;
		return false;
	}

	private int getDegree(Vector<node> partial) {
		int i = 0;
		for (node n : partial) {
			i += n.nodeDegree;
		}
		return i;
	}

	private Vector<node> getNodes(Individual firstElement) {
		// TODO Auto-generated method stub
		Vector<node> ret = new Vector<node>();
		Hashtable<String, Double> atts = firstElement.getIndividualAttributes();
		for (String s : atts.keySet()) {
			node n = new node();
			n.name = s;
			n.value = atts.get(s);
			ret.addElement(n);
		}
		return ret;
	}

}
