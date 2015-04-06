package knn;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
/*ebiz Task 11
 * Author: Wei Dai Ningxin Fan
 * Data class contains attributes for an entry/record 
 * It stores data in a HashMap:
  */
public class KNN {

	private Comparator<Data> comparator = new Comparator<Data>() {
		public int compare(Data node1, Data node2) {
			if (node1.getSimilarity() >= node2.getSimilarity())
				return 1;
			else
				return -1;
		}
	};

	/*
	 * @param traindata: reading from data for training
	 * 
	 * @param data: node to be tested 
	 * 
	 * @param k nearest neighbors
	 * 
	 * @param weight: different weight for each attributes
	 * 
	 * @param matrix Map: similarity matrix for those symbolic attributes
	 * 
	 * @return: the label of the test node
	 */
	
	public String knn(DataSet traindata, Data testnode,
			HashMap<String, Double> weight, int k,
			HashMap<String, double[][]> matrixMap) {

		String category = null;
		Double score = 0.0;
		PriorityQueue<Data> queue = new PriorityQueue<Data>(k, comparator);
		ArrayList<Data> datalist = traindata.getData();
		for (int i = 0; i < k; i++) { // add first k nodes from traindata to
										// queue;
			Data tmpnode = datalist.get(i);
			tmpnode.setSimilarity(calSimilarity1(testnode, tmpnode,
					traindata.getObjective(), weight, matrixMap));
			queue.add(tmpnode);
		}
		for (int i = k; i < datalist.size(); i++) {// modify queue to k most
													// similar nodes
			Data tmp = datalist.get(i);
			double similarity = calSimilarity1(testnode, tmp,
					traindata.getObjective(), weight, matrixMap);
			Data queuetop = queue.peek();
			if (queuetop.getSimilarity() < similarity) {
				tmp.setSimilarity(similarity);
				queue.poll();
				queue.add(tmp);
			}
		}

		// map is label's attribute name match to index
		HashMap<String, Double> map = traindata.getAttributeMap()
				.get(traindata.getObjective()).getValueSet();

		if (!traindata.getAttributeMap().get(traindata.getObjective())
				.isRealNum()) {

			category = getCategory(queue, map, traindata.getObjective(), k);

			// set test node label
			testnode.setLabel(map.get(category), traindata.getObjective());
			return category;
		} else {
			score = calCategory(queue, traindata.getObjective(), k);
			testnode.setLabel(score, traindata.getObjective());
			category = score + "";
		}
		return category;
	}

	// get the Min and Max value of input dataset, double[0] ==>min, double[1]
	// ==>max, String ==>attribute name
	public HashMap<String, double[]> getMinMax(DataSet traindata) {
		ArrayList<String> attributelist = traindata.getAttributeList();
		HashMap<String, double[]> rs = new HashMap<String, double[]>();
		for (int i = 0; i < attributelist.size(); i++) {

			// numeric attribute to find min & max
			if (traindata.isAttriReal(attributelist.get(i))) {
				double max = Double.MIN_VALUE;
				double min = Double.MAX_VALUE;
				for (Data d : traindata.getData()) {
					if (d.getData(attributelist.get(i)) > max)
						max = d.getData(attributelist.get(i));
					if (d.getData(attributelist.get(i)) < min)
						min = d.getData(attributelist.get(i));
				}
				double[] tmp = new double[2];
				tmp[0] = min;
				tmp[1] = max;

				rs.put(attributelist.get(i), tmp);
			}
		}
		return rs;
	}

	// normalize each attribute data=(data-min)/(max - min);
	// use the getMinMax function to find the max value and min value

	public void normalization(DataSet traindata,
			HashMap<String, double[]> minmax) {
		for (Map.Entry<String, double[]> entry : minmax.entrySet()) {
			String key = entry.getKey().toString();
			double[] mm = entry.getValue();
			for (Data d : traindata.getData()) {
				double norm = (d.getData(key) - mm[0]) / (mm[1] - mm[0]);
				if (norm > 1)
					norm = 1;
				if (norm < 0)
					norm = 0;
				d.setData(key, norm);
			}
		}
	}

	// input the queue to find the category
	/*
	 * @param queue: priority queue to store k the nearest neighbors
	 * 
	 * @param MatchMap: HashMap which stores the value of each attributes
	 * 
	 * @param k: the number of nearest neighbors
	 * 
	 * @param label: label the test node should be classified
	 * 
	 * @return: the category of the test node
	 */
	
	private String getCategory(PriorityQueue<Data> queue,
			HashMap<String, Double> MatchMap, String label, int k) {
		// map to store label and total score
		Map<String, Double> map = new HashMap<String, Double>();
		String rs = null;
		// put label and total score pair into map
		for (int i = 0; i < k; i++) {
			Data tmp = queue.poll();
			String category = null;
			// use MatchMap to match tmp label's value with value stored in the map
			// then determine which category tmp node belongs to
			for (Map.Entry<String, Double> entry : MatchMap.entrySet()) {
				if (entry.getValue() == tmp.getData(label)) {
					category = entry.getKey().toString();
				}
			}

			// consider all nodes in the queue, if some of them are in the same
			// category just update that category's score(similarity) otherwise
			// add a new record in the map
			double similarity = tmp.getSimilarity();
			if (map.containsKey(category)) {
				similarity += map.get(category);
				map.put(category, similarity);
			} else {
				map.put(category, similarity);
			}
		}
		double score = Integer.MIN_VALUE; // total score for rs

		// using the map find the label with highest score
		for (Map.Entry<String, Double> entry : map.entrySet()) {
			String key = entry.getKey().toString();
			Double value = entry.getValue();
			if (value > score) {
				rs = key;
				score = value;
			}
		}
		return rs;
	}

	// input the queue to find the category for those attributes are real class
	// simply use the average value of total values of nodes in queue
	private Double calCategory(PriorityQueue<Data> queue, String label, int k) {
		Double rs = 0.00;
		// put label and total score pair into map
		for (int i = 0; i < k; i++) {
			Data tmp = queue.poll();
			rs += tmp.getData(label);
		}
		return rs / k;
	}

	// considering weight for different attributes and similarity matrix for
	// some attributes (send parameter from main method)
	// determine whether the attribute is real firstly and calculate similarity
	// accordingly and times according weight from HashMap
	public double calSimilarity1(Data d1, Data d2, String label,
			HashMap<String, Double> weight,
			HashMap<String, double[][]> matrixMap) {
		double similarity = 0;
		HashMap<String, Attribute> map = d1.getAttributes();
		for (Map.Entry<String, Attribute> entry : map.entrySet()) {
			String key = entry.getKey().toString();
			if (!key.equals(label)) {
				Attribute value = entry.getValue();
				double wt = weight.get(key);
				if (value.isRealNum()) {
					similarity += Math
							.pow(d1.getData(key) - d2.getData(key), 2) * wt;
				} else {
					double[][] matrix = matrixMap.get(key);
					int d1attr = (int) d1.getData(key).doubleValue();
					int d2attr = (int) d2.getData(key).doubleValue();
					similarity += Math.pow((1 - matrix[d1attr][d2attr]), 2)
							* wt;

				}
			}
		}
		return 1 / Math.sqrt(similarity);
	}

}
