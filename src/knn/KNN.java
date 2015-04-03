package knn;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;

public class KNN {

	// calculation of similarity between two nodes
	double[] weight = { 1.0, 1.0, 1.0, 1.0, 1.0, 1.0 };
	double[][] matrix1 = { { 1, 0, 0, 0, 0 }, { 0, 1, 0, 0, 0 },
			{ 0, 0, 1, 0, 0 }, { 0, 0, 0, 1, 0 }, { 0, 0, 0, 0, 1 } };
	double[][] matrix2 = { { 1, 0, 0, 0 }, { 0, 1, 0, 0 }, { 0, 0, 1, 0 },
			{ 0, 0, 0, 1 } };

	public double calSimilarity(Data d1, Data d2, String label) {
		double similarity = 0;

		HashMap<String, Attribute> map = d1.getAttributes();
		for (Map.Entry<String, Attribute> entry : map.entrySet()) {
			String key = entry.getKey().toString();
			if (!key.equals(label)) {
				Attribute value = entry.getValue();
				if (value.isRealNum()) {
					similarity += Math
							.pow(d1.getData(key) - d2.getData(key), 2);
				} else {
					if (d1.getData(key) == d2.getData(key)) {
						similarity += Math.pow(1, 2);
					}
				}
			}
		}
		return 1 / similarity;
	}

	private Comparator<Data> comparator = new Comparator<Data>() {
		public int compare(Data node1, Data node2) {
			if (node1.getSimilarity() >= node2.getSimilarity())
				return 1;
			else
				return -1;
		}
	};

	// body of knn
	public String knn(DataSet traindata, Data testnode) {
		String category = null;
		int k = 3; // set k;
		PriorityQueue<Data> queue = new PriorityQueue<Data>(k, comparator);
		ArrayList<Data> datalist = traindata.getData();
		for (int i = 0; i < k; i++) { // add first k nodes from traindata to
										// queue;
			Data tmpnode = datalist.get(i);
			tmpnode.setSimilarity(calSimilarity(
					testnode,
					tmpnode,
					traindata.getAttributeList().get(
							traindata.getAttributeList().size() - 1)));
			queue.add(tmpnode);
		}
		for (int i = k; i < datalist.size(); i++) {// modify queue to k most
													// similar nodes
			Data tmp = datalist.get(i);
			double similarity = calSimilarity(
					testnode,
					tmp,
					traindata.getAttributeList().get(
							traindata.getAttributeList().size() - 1));
			Data queuetop = queue.peek();
			if (queuetop.getSimilarity() < similarity) {
				tmp.setSimilarity(similarity);
				queue.poll();
				queue.add(tmp);
			}
		}

		HashMap<String, Double> map = traindata.getAttributeMap()
				.get(traindata.getObjective()).getValueSet();
		category = getCategory(queue, map, traindata.getObjective());

		testnode.setLabel(map.get(category), traindata.getObjective());
		return category;
	}

	// input the queue to find the category
	private String getCategory(PriorityQueue<Data> queue,
			HashMap<String, Double> MatchMap, String label) {
		// map to store label and total score
		Map<String, Double> map = new HashMap<String, Double>();
		String rs = null;
		// put label and total score pair into map
		for (int i = 0; i < queue.size(); i++) {
			Data tmp = queue.poll();
			String category = null;

			for (Map.Entry<String, Double> entry : MatchMap.entrySet()) {
				if (entry.getValue() == tmp.getData(label)) {
					category = entry.getKey().toString();
				}
			}

			double similarity = tmp.getSimilarity();
			if (map.containsKey(category)) {
				similarity += map.get(category);
				map.put(category, similarity);
			} else {
				map.put(category, similarity);
			}
		}
		double score = Integer.MIN_VALUE; // total score for rs

		// find the label with highest score
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
}
